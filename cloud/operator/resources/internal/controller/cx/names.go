package cx

import (
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"slices"
	"strings"

	cxv1alpha1 "github.com/liferay/liferay-portal/cloud/operator/api/cx/v1alpha1"
	corev1 "k8s.io/api/core/v1"
)

const (
	AnnotationMainDomain     = "ext.lxc.liferay.com/mainDomain"
	AnnotationOwnerName      = "cx.liferay.com/owner-name"
	AnnotationOwnerNamespace = "cx.liferay.com/owner-namespace"
)

const (
	LabelMetadataType    = "lxc.liferay.com/metadataType"
	LabelOwner           = "cx.liferay.com/owner"
	LabelServiceID       = "ext.lxc.liferay.com/serviceId"
	LabelVirtualInstance = "dxp.lxc.liferay.com/virtualInstanceId"
)

const (
	MetadataTypeDxp          = "dxp"
	MetadataTypeExtInit      = "ext-init"
	MetadataTypeExtProvision = "ext-provision"
)

func allowedNamespaces(namespace *corev1.Namespace) []string {
	var allowedNamespaces []string

	for _, name := range strings.Split(
		namespace.Annotations[cxv1alpha1.AnnotationAllowedClientExtensionNamespaces], ",",
	) {
		if name = strings.TrimSpace(name); name != "" {
			allowedNamespaces = append(allowedNamespaces, name)
		}
	}

	return allowedNamespaces
}

func dxpMetadataName(virtualInstanceID string) string {
	return virtualInstanceID + "-lxc-dxp-metadata"
}

func effectiveDxpNamespace(clientExtension *cxv1alpha1.ClientExtension) string {
	if clientExtension.Spec.DxpNamespace != "" {
		return clientExtension.Spec.DxpNamespace
	}

	return clientExtension.Namespace
}

func extInitApplicationERCs(clientExtension *cxv1alpha1.ClientExtension) []string {
	var externalReferenceCodes []string

	for pid := range clientExtension.Spec.Configs {
		for _, separator := range []string{"~", "_", "-"} {
			index := strings.Index(pid, separator)

			if index <= 0 {
				continue
			}

			if !slices.Contains(extInitFactoryPIDs, pid[:index]) {
				break
			}

			externalReferenceCode, _, _ := strings.Cut(pid[index+1:], "/")

			if externalReferenceCode != "" {
				externalReferenceCodes = append(externalReferenceCodes, externalReferenceCode)
			}

			break
		}
	}

	slices.Sort(externalReferenceCodes)

	return slices.Compact(externalReferenceCodes)
}

func extInitName(clientExtension *cxv1alpha1.ClientExtension) string {
	return fmt.Sprintf(
		"%s-%s-lxc-ext-init-metadata",
		clientExtension.Spec.ServiceID, clientExtension.Spec.VirtualInstanceID,
	)
}

func extProvisionName(clientExtension *cxv1alpha1.ClientExtension) string {
	return fmt.Sprintf(
		"%s-%s-lxc-ext-provision-metadata",
		clientExtension.Spec.ServiceID, clientExtension.Spec.VirtualInstanceID,
	)
}

func ownerLabelValue(clientExtension *cxv1alpha1.ClientExtension) string {
	sum := sha256.Sum256([]byte(clientExtension.Namespace + "/" + clientExtension.Name))

	return hex.EncodeToString(sum[:16])
}

func ownsExtProvision(clientExtension *cxv1alpha1.ClientExtension, configMap *corev1.ConfigMap) bool {
	return (configMap.Annotations[AnnotationOwnerName] == clientExtension.Name) &&
		(configMap.Annotations[AnnotationOwnerNamespace] == clientExtension.Namespace)
}

var extInitFactoryPIDs = []string{
	"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationHeadlessServerConfiguration",
	"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration",
}
