package cx

import (
	"slices"
	"testing"

	cxv1alpha1 "github.com/liferay/liferay-portal/cloud/operator/api/cx/v1alpha1"
	appsv1 "k8s.io/api/apps/v1"
	corev1 "k8s.io/api/core/v1"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
	client "sigs.k8s.io/controller-runtime/pkg/client"
)

func TestReconcileReportsWorkload(t *testing.T) {
	testCases := map[string]struct {
		objects     []client.Object
		wantIssues  []string
		wantPhase   string
		wantReason  string
		wantStatus  metav1.ConditionStatus
		workloadRef bool
	}{
		"a workload that does not exist yet": {
			wantPhase:   cxv1alpha1.PhasePending,
			wantReason:  ReasonWorkloadNotFound,
			wantStatus:  metav1.ConditionFalse,
			workloadRef: true,
		},
		"a workload that does not mount dxp-metadata": {
			objects:     []client.Object{newDeployment(corev1.PodTemplateSpec{})},
			wantIssues:  []string{`No volume holds ConfigMap "liferay.com-lxc-dxp-metadata".`},
			wantPhase:   cxv1alpha1.PhaseDegraded,
			wantReason:  ReasonWorkloadMisconfigured,
			wantStatus:  metav1.ConditionFalse,
			workloadRef: true,
		},
		"an initialized workload": {
			objects:     []client.Object{newDeployment(newInitializedPodTemplate())},
			wantPhase:   cxv1alpha1.PhaseReady,
			wantReason:  ReasonInitialized,
			wantStatus:  metav1.ConditionTrue,
			workloadRef: true,
		},
		"no workload": {
			wantPhase:   cxv1alpha1.PhaseReady,
			wantReason:  ReasonConfigurationOnly,
			wantStatus:  metav1.ConditionTrue,
			workloadRef: false,
		},
	}

	for name, testCase := range testCases {
		t.Run(name, func(t *testing.T) {
			clientExtension := newClientExtension("liferay-dev", "able", "able")

			if testCase.workloadRef {
				clientExtension.Spec.WorkloadRef = &cxv1alpha1.WorkloadRef{
					Kind: cxv1alpha1.WorkloadKindDeployment, Name: "able",
				}
			}

			clientExtensionReconciler := newReconciler(
				nil, t,
				append(
					testCase.objects, clientExtension,
					newDxpMetadata("liferay-dev", "liferay.com"), newDxpNamespace("able"),
				)...,
			)

			if phase, _ := reconcileClientExtension(
				clientExtension, clientExtensionReconciler, t,
			); phase != testCase.wantPhase {
				t.Errorf("phase = %q, want %q", phase, testCase.wantPhase)
			}

			workloadAccepted := getCondition(
				clientExtension, clientExtensionReconciler,
				cxv1alpha1.ConditionWorkloadAccepted, t,
			)

			if (testCase.wantReason != workloadAccepted.Reason) ||
				(testCase.wantStatus != workloadAccepted.Status) ||
				(workloadAccepted == nil) {

				t.Errorf(
					"WorkloadAccepted = %v, want %s / %s", workloadAccepted,
					testCase.wantReason, testCase.wantStatus,
				)
			}

			clientExtension = getClientExtension(clientExtension, clientExtensionReconciler, t)

			if workloadIssues := clientExtension.Status.WorkloadIssues; !slices.Equal(workloadIssues, testCase.wantIssues) {
				t.Errorf("workloadIssues = %q, want %q", workloadIssues, testCase.wantIssues)
			}
		})
	}
}

func TestValidatePodTemplate(t *testing.T) {
	testCases := map[string]struct {
		change     func(podTemplate *corev1.PodTemplateSpec)
		wantIssues []string
	}{
		"a container that is missing volume mounts": {
			change: func(podTemplate *corev1.PodTemplateSpec) {
				podTemplate.Spec.Containers[0].VolumeMounts = nil
			},
			wantIssues: []string{`No container mounts volume "dxp-metadata" at "/etc/liferay/lxc/dxp-metadata".`},
		},
		"a container whose envvar is wrong": {
			change: func(podTemplate *corev1.PodTemplateSpec) {
				podTemplate.Spec.Containers[0].Env[0].Value = "/incorrect"
			},
			wantIssues: []string{
				`No container mounts volume "dxp-metadata" and sets LIFERAY_ROUTES_DXP to "/etc/liferay/lxc/dxp-metadata".`,
			},
		},
		"a template without the volume": {
			change: func(podTemplate *corev1.PodTemplateSpec) {
				podTemplate.Spec.Volumes = nil
			},
			wantIssues: []string{`No volume holds ConfigMap "liferay.com-lxc-dxp-metadata".`},
		},
		"an initialized template": {
			change: func(podTemplate *corev1.PodTemplateSpec) {},
		},
	}

	for name, testCase := range testCases {
		t.Run(name, func(t *testing.T) {
			podTemplate := newInitializedPodTemplate()

			testCase.change(&podTemplate)

			workloadIssues := validatePodTemplate(
				"liferay.com-lxc-dxp-metadata", "LIFERAY_ROUTES_DXP", MountPathDxpMetadata,
				&podTemplate,
			)

			if !slices.Equal(workloadIssues, testCase.wantIssues) {
				t.Errorf("validatePodtemplate() = %q, want %q", workloadIssues, testCase.wantIssues)
			}
		})
	}
}

func newDeployment(podTemplate corev1.PodTemplateSpec) *appsv1.Deployment {
	return &appsv1.Deployment{
		ObjectMeta: metav1.ObjectMeta{Name: "able", Namespace: "able"},
		Spec:       appsv1.DeploymentSpec{Template: podTemplate},
	}
}

func newInitializedPodTemplate() corev1.PodTemplateSpec {
	return corev1.PodTemplateSpec{
		Spec: corev1.PodSpec{
			Containers: []corev1.Container{
				{
					Env: []corev1.EnvVar{{
						Name:  "LIFERAY_ROUTES_DXP",
						Value: MountPathDxpMetadata,
					}},
					Name: "able",
					VolumeMounts: []corev1.VolumeMount{{
						MountPath: MountPathDxpMetadata,
						Name:      "dxp-metadata",
					}},
				},
			},
			Volumes: []corev1.Volume{{
				Name: "dxp-metadata",
				VolumeSource: corev1.VolumeSource{
					ConfigMap: &corev1.ConfigMapVolumeSource{
						LocalObjectReference: corev1.LocalObjectReference{Name: "liferay.com-lxc-dxp-metadata"},
					},
				},
			}},
		},
	}
}
