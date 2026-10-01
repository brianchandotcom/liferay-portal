package cx

import (
	"slices"
	"testing"

	cxv1alpha1 "github.com/liferay/liferay-portal/cloud/operator/api/cx/v1alpha1"
	corev1 "k8s.io/api/core/v1"
	apiextensionsv1 "k8s.io/apiextensions-apiserver/pkg/apis/apiextensions/v1"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
)

func TestAllowedNamespacesReadsAnnotation(t *testing.T) {
	testCases := map[string]struct {
		annotations map[string]string
		want        []string
	}{
		"an empty annotation permits nothing": {
			annotations: map[string]string{
				cxv1alpha1.AnnotationAllowedClientExtensionNamespaces: "",
			},
		},
		"blank entries and spaces are ignored": {
			annotations: map[string]string{
				cxv1alpha1.AnnotationAllowedClientExtensionNamespaces: " able, ,baker ,",
			},
			want: []string{"able", "baker"},
		},
		"no annotation permits nothing": {},
	}

	for name, testCase := range testCases {
		t.Run(name, func(t *testing.T) {
			namespace := &corev1.Namespace{
				ObjectMeta: metav1.ObjectMeta{Annotations: testCase.annotations, Name: "liferay-prod"},
			}

			if got := allowedNamespaces(namespace); !slices.Equal(got, testCase.want) {
				t.Errorf("allowedNamespaces() = %q, want %q", got, testCase.want)
			}
		})
	}
}

func TestEffectiveDxpNamespaceDefaultsToClientExtensionNamespace(t *testing.T) {
	testCases := map[string]struct {
		dxpNamespace string
		want         string
	}{
		"an empty dxpNamespace is the client extension namespace": {
			want: "able",
		},
		"dxpNamespace is used when set": {
			dxpNamespace: "liferay-prod",
			want:         "liferay-prod",
		},
	}

	for name, testCase := range testCases {
		t.Run(name, func(t *testing.T) {
			clientExtension := newClientExtension(testCase.dxpNamespace, "sample", "able")

			if got := effectiveDxpNamespace(clientExtension); got != testCase.want {
				t.Errorf("effectiveDxpNamespace() = %q, want %q", got, testCase.want)
			}
		})
	}
}

func TestRequiresExtInitForOAuth2Applications(t *testing.T) {
	testCases := map[string]struct {
		pids []string
		want bool
	}{
		"a CET configuration beside a user agent application requires ext-init": {
			pids: []string{
				"com.liferay.client.extension.type.configuration.CETConfiguration~able",
				"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration~able-oua",
			},
			want: true,
		},
		"a CET configuration requires no ext-init": {
			pids: []string{"com.liferay.client.extension.type.configuration.CETConfiguration~able"},
		},
		"a PID without a separator requires no ext-init": {
			pids: []string{"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration"},
		},
		"a headless server application requires ext-init": {
			pids: []string{
				"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationHeadlessServerConfiguration~able-ohs",
			},
			want: true,
		},
		"a user agent application requires ext-init": {
			pids: []string{
				"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration~able-oua",
			},
			want: true,
		},
		"an underscore separates the name when there is no tilde": {
			pids: []string{
				"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration_able-oua",
			},
			want: true,
		},
	}

	for name, testCase := range testCases {
		t.Run(name, func(t *testing.T) {
			clientExtension := newClientExtension("", "able", "able")

			clientExtension.Spec.Configs = make(map[string]cxv1alpha1.Configuration)

			for _, pid := range testCase.pids {
				clientExtension.Spec.Configs[pid] = cxv1alpha1.Configuration{JSON: apiextensionsv1.JSON{Raw: []byte(`{}`)}}
			}

			if got := requiresExtInit(clientExtension); got != testCase.want {
				t.Errorf("requiresExtInit() = %t, want %t", got, testCase.want)
			}
		})
	}
}
