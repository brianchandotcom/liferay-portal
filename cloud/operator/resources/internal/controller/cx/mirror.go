package cx

import (
	"context"
	"errors"
	"fmt"
	"maps"
	"slices"

	cxv1alpha1 "github.com/liferay/liferay-portal/cloud/operator/api/cx/v1alpha1"
	corev1 "k8s.io/api/core/v1"
	apierrors "k8s.io/apimachinery/pkg/api/errors"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
	schema "k8s.io/apimachinery/pkg/runtime/schema"
	client "sigs.k8s.io/controller-runtime/pkg/client"
	controllerutil "sigs.k8s.io/controller-runtime/pkg/controller/controllerutil"
	reconcile "sigs.k8s.io/controller-runtime/pkg/reconcile"
)

func (clientExtensionReconciler *ClientExtensionReconciler) applyMirror(
	clientExtension *cxv1alpha1.ClientExtension,
	context context.Context,
	controller bool,
	source *corev1.ConfigMap,
) error {
	configMap := &corev1.ConfigMap{
		ObjectMeta: metav1.ObjectMeta{
			Name:      source.Name,
			Namespace: clientExtension.Namespace,
		},
	}

	sourceReference := source.Namespace + "/" + source.Name

	_, error := controllerutil.CreateOrUpdate(
		context, clientExtensionReconciler.Client, configMap,
		func() error {
			if (configMap.ResourceVersion != "") && !mayWriteMirror(clientExtension, configMap, sourceReference) {
				return mirrorConflictError(configMap, sourceReference)
			}

			if configMap.Annotations == nil {
				configMap.Annotations = map[string]string{}
			}

			configMap.Annotations[AnnotationSource] = sourceReference

			configMap.BinaryData = maps.Clone(source.BinaryData)
			configMap.Data = maps.Clone(source.Data)

			configMap.Labels = maps.Clone(source.Labels)

			if configMap.Labels == nil {
				configMap.Labels = map[string]string{}
			}

			configMap.Labels[LabelMirror] = "true"

			if controller {
				return controllerutil.SetControllerReference(
					clientExtension, configMap, clientExtensionReconciler.Scheme(),
				)
			}

			return controllerutil.SetOwnerReference(clientExtension, configMap, clientExtensionReconciler.Scheme())
		},
	)

	var alreadyOwnedError *controllerutil.AlreadyOwnedError

	if errors.As(error, &alreadyOwnedError) {
		return fmt.Errorf(
			"%w: configmap %q mirrors %q for another client extension", errMirrorConflict, configMap.Name,
			sourceReference,
		)
	}

	if apierrors.IsAlreadyExists(error) {
		var existingConfigMap corev1.ConfigMap

		if getError := clientExtensionReconciler.APIReader.Get(
			context, client.ObjectKeyFromObject(configMap), &existingConfigMap,
		); getError != nil {
			return getError
		}

		if !mayWriteMirror(clientExtension, &existingConfigMap, sourceReference) {
			return mirrorConflictError(&existingConfigMap, sourceReference)
		}
	}

	return error
}

func (clientExtensionReconciler *ClientExtensionReconciler) cleanUpStaleMirrors(
	clientExtension *cxv1alpha1.ClientExtension,
	context context.Context,
	mirrorNames []string,
) error {
	var configMapList corev1.ConfigMapList

	if error := clientExtensionReconciler.List(
		context, &configMapList,
		client.InNamespace(clientExtension.Namespace), client.MatchingLabels{LabelMirror: "true"},
	); error != nil {
		return error
	}

	for index := range configMapList.Items {
		configMap := &configMapList.Items[index]

		if slices.Contains(mirrorNames, configMap.Name) || !ownsMirror(clientExtension, configMap) {
			continue
		}

		configMap.OwnerReferences = slices.DeleteFunc(
			configMap.OwnerReferences,
			func(ownerReference metav1.OwnerReference) bool {
				return clientExtension.UID == ownerReference.UID
			},
		)

		if len(configMap.OwnerReferences) > 0 {
			if error := clientExtensionReconciler.Update(context, configMap); error != nil {
				return error
			}

			continue
		}

		if error := clientExtensionReconciler.Delete(
			context, configMap, client.Preconditions{ResourceVersion: &configMap.ResourceVersion},
		); client.IgnoreNotFound(error) != nil {
			return error
		}
	}

	return nil
}

func isMirrorOf(configMap *corev1.ConfigMap, sourceReference string) bool {
	return (configMap.Labels[LabelMirror] == "true") && (configMap.Annotations[AnnotationSource] == sourceReference)
}

func mayWriteMirror(
	clientExtension *cxv1alpha1.ClientExtension,
	configMap *corev1.ConfigMap,
	sourceReference string,
) bool {
	return isMirrorOf(configMap, sourceReference) ||
		((configMap.Labels[LabelMirror] == "true") && ownsMirrorAlone(clientExtension, configMap))
}

func mirrorConflictError(configMap *corev1.ConfigMap, sourceReference string) error {
	return fmt.Errorf(
		"%w: configmap %q exists and is not the mirror of %q", errMirrorConflict, configMap.Name,
		sourceReference,
	)
}

func (clientExtensionReconciler *ClientExtensionReconciler) mirrorMetadata(
	clientExtension *cxv1alpha1.ClientExtension,
	context context.Context,
	dxpMetadata *corev1.ConfigMap,
	extInit *corev1.ConfigMap,
) ([]string, error) {
	if clientExtension.Namespace == dxpMetadata.Namespace {
		return nil, clientExtensionReconciler.cleanUpStaleMirrors(
			clientExtension, context, nil,
		)
	}

	if error := clientExtensionReconciler.applyMirror(
		clientExtension, context, false, dxpMetadata,
	); error != nil {
		return nil, error
	}

	mirrorNames := []string{dxpMetadata.Name}

	if extInit != nil {
		if error := clientExtensionReconciler.applyMirror(
			clientExtension, context, true, extInit,
		); error != nil {
			return nil, error
		}

		mirrorNames = append(mirrorNames, extInit.Name)
	}

	return mirrorNames, clientExtensionReconciler.cleanUpStaleMirrors(
		clientExtension, context, mirrorNames,
	)
}

func requestsForMirror(object client.Object) []reconcile.Request {
	var requests []reconcile.Request

	for _, ownerReference := range object.GetOwnerReferences() {
		groupVersion, error := schema.ParseGroupVersion(ownerReference.APIVersion)

		if (error != nil) || (cxv1alpha1.SchemeBuilder.GroupVersion.Group != groupVersion.Group) ||
			(ownerReference.Kind != "ClientExtension") {

			continue
		}

		requests = append(requests, reconcile.Request{
			NamespacedName: client.ObjectKey{Name: ownerReference.Name, Namespace: object.GetNamespace()},
		})
	}

	return requests
}

var errMirrorConflict = errors.New("mirror: conflict")
