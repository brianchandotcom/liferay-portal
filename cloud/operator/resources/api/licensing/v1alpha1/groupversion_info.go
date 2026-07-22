// +kubebuilder:object:generate=true
// +groupName=licensing.liferay.com
package v1alpha1

import (
	"k8s.io/apimachinery/pkg/runtime/schema"
	"sigs.k8s.io/controller-runtime/pkg/scheme"
)

var (
	SchemeBuilder = &scheme.Builder{
		GroupVersion: schema.GroupVersion{
			Group:   "licensing.liferay.com",
			Version: "v1alpha1",
		},
	}

	AddToScheme = SchemeBuilder.AddToScheme
)
