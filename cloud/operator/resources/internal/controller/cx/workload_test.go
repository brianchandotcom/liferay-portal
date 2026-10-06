package cx

import (
	"slices"
	"testing"

	corev1 "k8s.io/api/core/v1"
)

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
