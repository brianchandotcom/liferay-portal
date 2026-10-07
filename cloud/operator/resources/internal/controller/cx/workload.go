package cx

import (
	"context"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"maps"
	"slices"
	"strconv"

	cxv1alpha1 "github.com/liferay/liferay-portal/cloud/operator/api/cx/v1alpha1"
	appsv1 "k8s.io/api/apps/v1"
	batchv1 "k8s.io/api/batch/v1"
	corev1 "k8s.io/api/core/v1"
	apierrors "k8s.io/apimachinery/pkg/api/errors"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
	types "k8s.io/apimachinery/pkg/types"
	controllerruntime "sigs.k8s.io/controller-runtime"
	client "sigs.k8s.io/controller-runtime/pkg/client"
	handler "sigs.k8s.io/controller-runtime/pkg/handler"
	reconcile "sigs.k8s.io/controller-runtime/pkg/reconcile"
)

const AnnotationConfigDigest = "cx.liferay.com/config-digest"

const (
	MountPathDxpMetadata     = "/etc/liferay/lxc/dxp-metadata"
	MountPathExtInitMetadata = "/etc/liferay/lxc/ext-init-metadata"
)

func configDigest(configMaps ...*corev1.ConfigMap) string {
	hash := sha256.New()

	for _, configMap := range configMaps {
		for _, key := range slices.Sorted(maps.Keys(configMap.Data)) {
			for _, value := range []string{key, configMap.Data[key]} {
				hash.Write([]byte(strconv.Itoa(len(value)) + ":" + value))
			}
		}
	}

	return hex.EncodeToString(hash.Sum(nil))
}

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

func (clientExtensionReconciler *ClientExtensionReconciler) requestsForWorkload(
	workloadKind cxv1alpha1.WorkloadKind,
) handler.MapFunc {
	return func(context context.Context, object client.Object) []reconcile.Request {
		var clientExtensionList cxv1alpha1.ClientExtensionList

		if error := clientExtensionReconciler.List(
			context, &clientExtensionList, client.InNamespace(object.GetNamespace()),
		); error != nil {
			controllerruntime.LoggerFrom(context).Error(
				error, "Unable to list client extensions", "kind", workloadKind, "workload",
				client.ObjectKeyFromObject(object),
			)

			return nil
		}

		var requests []reconcile.Request

		for index := range clientExtensionList.Items {
			workloadRef := clientExtensionList.Items[index].Spec.WorkloadRef

			if (workloadRef == nil) || (workloadKind != workloadRef.Kind) || (object.GetName() != workloadRef.Name) {
				continue
			}

			requests = append(requests, reconcile.Request{
				NamespacedName: client.ObjectKeyFromObject(&clientExtensionList.Items[index]),
			})
		}

		return requests
	}
}

func (clientExtensionReconciler *ClientExtensionReconciler) putConfigDigest(
	clientExtension *cxv1alpha1.ClientExtension,
	context context.Context,
	digest string,
	workload client.Object,
) error {
	if _, ok := workload.(*batchv1.Job); ok {
		return nil
	}

	podTemplate := podTemplateOf(workload)

	previousDigest := podTemplate.Annotations[AnnotationConfigDigest]

	if digest == previousDigest {
		return nil
	}

	patch := client.MergeFrom(workload.DeepCopyObject().(client.Object))

	if podTemplate.Annotations == nil {
		podTemplate.Annotations = map[string]string{}
	}

	podTemplate.Annotations[AnnotationConfigDigest] = digest

	if error := clientExtensionReconciler.Patch(
		context, workload, patch,
	); error != nil {
		return error
	}

	if previousDigest != "" {
		clientExtensionReconciler.Recorder.Eventf(
			clientExtension, corev1.EventTypeNormal, "WorkloadUpdated",
			"Updated %s %q because DXP's metadata changed.",
			clientExtension.Spec.WorkloadRef.Kind, workload.GetName(),
		)
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
	configMapsWithDigest []*corev1.ConfigMap,
	context context.Context,
) (metav1.Condition, []string, error) {
	workloadRef := clientExtension.Spec.WorkloadRef

	if workloadRef == nil {
		return newCondition(
			metav1.ConditionTrue, "The client extension is configuration only.",
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
				extInitName(clientExtension), "LIFERAY_ROUTES_CLIENT_EXTENSION",
				MountPathExtInitMetadata, podTemplate,
			)...,
		)
	}

	if len(workloadIssues) > 0 {
		return newCondition(
			metav1.ConditionFalse,
			fmt.Sprintf(
				"%s %q does not mount DXP's metadata; see status.workloadIssues.",
				workloadRef.Kind, workloadRef.Name,
			),
			ReasonWorkloadMisconfigured,
		), workloadIssues, nil
	}

	if configMapsWithDigest != nil {
		if error := clientExtensionReconciler.putConfigDigest(
			clientExtension, context, configDigest(configMapsWithDigest...), workload,
		); error != nil {
			return metav1.Condition{}, nil, error
		}
	}

	return newCondition(
		metav1.ConditionTrue,
		fmt.Sprintf("%s %q mounts DXP's metadata.", workloadRef.Kind, workloadRef.Name), ReasonInitialized,
	), nil, nil
}
