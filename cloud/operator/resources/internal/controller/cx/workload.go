package cx

import (
	"context"
	"fmt"
	"slices"

	cxv1alpha1 "github.com/liferay/liferay-portal/cloud/operator/api/cx/v1alpha1"
	appsv1 "k8s.io/api/apps/v1"
	batchv1 "k8s.io/api/batch/v1"
	corev1 "k8s.io/api/core/v1"
	apierrors "k8s.io/apimachinery/pkg/api/errors"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
	types "k8s.io/apimachinery/pkg/types"
	client "sigs.k8s.io/controller-runtime/pkg/client"
)

const (
	MountPathDxpMetadata     = "/etc/liferay/lxc/dxp-metadata"
	MountPathExtInitMetadata = "/etc/liferay/lxc/ext-init-metadata"
)

func podTemplateOf(object client.Object) *corev1.PodTemplateSpec {
	if cronJob, ok := object.(*batchv1.CronJob); ok {
		return &cronJob.Spec.JobTemplate.Spec.Template
	}

	if deployment, ok := object.(*appsv1.Deployment); ok {
		return &deployment.Spec.Template
	}

	if job, ok := object.(*batchv1.Job); ok {
		return &job.Spec.Template
	}

	return nil
}

func validatePodTemplate(
	configMapName string,
	environmentVariableName string,
	mountPath string,
	podTemplate *corev1.PodTemplateSpec,
) []string {
	volumeIndex := slices.IndexFunc(
		podTemplate.Spec.Volumes,
		func(volume corev1.Volume) bool {
			return (volume.ConfigMap != nil) && (configMapName == volume.ConfigMap.Name)
		},
	)

	if volumeIndex < 0 {
		return []string{fmt.Sprintf("No volume holds ConfigMap %q.", configMapName)}
	}

	volumeName := podTemplate.Spec.Volumes[volumeIndex].Name

	mounted := false

	for _, container := range podTemplate.Spec.Containers {
		if !slices.ContainsFunc(
			container.VolumeMounts,
			func(volumeMount corev1.VolumeMount) bool {
				return (mountPath == volumeMount.MountPath) && (volumeMount.Name == volumeName)
			},
		) {
			continue
		}

		mounted = true

		if slices.ContainsFunc(
			container.Env,
			func(environmentVariable corev1.EnvVar) bool {
				return (environmentVariable.Name == environmentVariableName) && (environmentVariable.Value == mountPath)
			},
		) {

			return nil
		}
	}

	if !mounted {
		return []string{
			fmt.Sprintf("No container mounts volume %q at %q.", volumeName, mountPath),
		}
	}

	return []string{
		fmt.Sprintf(
			"No container mounts volume %q and sets %s to %q.", volumeName,
			environmentVariableName, mountPath,
		),
	}
}

func (clientExtensionReconciler *ClientExtensionReconciler) workloadCondition(
	clientExtension *cxv1alpha1.ClientExtension,
	context context.Context,
) (metav1.Condition, []string, error) {
	workloadRef := clientExtension.Spec.WorkloadRef

	if workloadRef == nil {
		return newCondition(
			metav1.ConditionTrue, "The client extension is configuration only",
			ReasonConfigurationOnly,
		), nil, nil
	}

	workload, error := workloadRef.NewObject()

	if error != nil {
		return metav1.Condition{}, nil, error
	}

	error = clientExtensionReconciler.Get(
		context, types.NamespacedName{
			Name: workloadRef.Name, Namespace: clientExtension.Namespace,
		}, workload,
	)

	if apierrors.IsNotFound(error) {
		return newCondition(
			metav1.ConditionFalse,
			fmt.Sprintf(
				"%s %q does not exist in namespace %q", workloadRef.Kind,
				workloadRef.Name, clientExtension.Namespace),
			ReasonWorkloadNotFound,
		), nil, nil
	}

	if error != nil {
		return metav1.Condition{}, nil, error
	}

	podTemplate := podTemplateOf(workload)

	workloadIssues := validatePodTemplate(
		dxpMetadataName(clientExtension.Spec.VirtualInstanceID), "LIFERAY_ROUTES_DXP",
		MountPathDxpMetadata, podTemplate,
	)

	if len(extInitIdentifiers(clientExtension)) > 0 {
		workloadIssues = append(
			workloadIssues,
			validatePodTemplate(
				extInitName(clientExtension), "LIFERAY_ROUTES_CLIENT_EXTENSION", MountPathExtInitMetadata, podTemplate,
			)...,
		)
	}

	if len(workloadIssues) > 0 {
		return newCondition(
			metav1.ConditionFalse,
			fmt.Sprintf(
				"%s %q does not mount DXP's metadata; see status.workloadIssues",
				workloadRef.Kind, workloadRef.Name,
			),
			ReasonWorkloadMisconfigured,
		), workloadIssues, nil
	}

	return newCondition(
		metav1.ConditionTrue,
		fmt.Sprintf("%s %q mounts DXP's metadata.", workloadRef.Kind, workloadRef.Name), ReasonInitialized,
	), nil, nil
}
