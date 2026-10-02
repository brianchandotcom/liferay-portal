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

func TestExtInitApplicationERCsReadsOAuth2PIDs(t *testing.T) {
	testCases := map[string]struct {
		pids []string
		want []string
	}{
		"a CET configuration beside a user agent application yields the application": {
			pids: []string{
				"com.liferay.client.extension.type.configuration.CETConfiguration~able",
				"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration~able-oua",
			},
			want: []string{"able-oua"},
		},
		"a CET configuration yields no application": {
			pids: []string{"com.liferay.client.extension.type.configuration.CETConfiguration~able"},
		},
		"a PID without a separator yields no application": {
			pids: []string{"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration"},
		},
		"a headless server application yields its external reference code": {
			pids: []string{
				"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationHeadlessServerConfiguration~able-ohs",
			},
			want: []string{"able-ohs"},
		},
		"a slash ends the external reference code": {
			pids: []string{
				"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationHeadlessServerConfiguration~able-ohs/able.liferay.cloud",
			},
			want: []string{"able-ohs"},
		},
		"an underscore separates the name when there is no tilde": {
			pids: []string{
				"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration_able-oua",
			},
			want: []string{"able-oua"},
		},
		"every application yields its external reference code in order": {
			pids: []string{
				"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration~baker-oua",
				"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationHeadlessServerConfiguration~able-ohs",
			},
			want: []string{"able-ohs", "baker-oua"},
		},
	}

	for name, testCase := range testCases {
		t.Run(name, func(t *testing.T) {
			clientExtension := newClientExtension("", "able", "able")

			clientExtension.Spec.Configs = make(map[string]cxv1alpha1.Configuration)

			for _, pid := range testCase.pids {
				clientExtension.Spec.Configs[pid] = cxv1alpha1.Configuration{JSON: apiextensionsv1.JSON{Raw: []byte(`{}`)}}
			}

			if got := extInitApplicationERCs(clientExtension); !slices.Equal(got, testCase.want) {
				t.Errorf("extInitApplicationERCs() = %v, want %v", got, testCase.want)
			}
		})
	}
}
