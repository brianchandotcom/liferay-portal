import context_variables as cv
import k8s.api.core.v1 as k8s_core
import models.v1alpha1.kubernetesm_crossplane_io_v1alpha1_object as kubernetes

get_items = lambda obs: any, vars: {str:any} -> [any] {
    _res = obs?.resources or {}
    _es_status = _res?["elasticsearch-observer"]?.resource?.status?.atProvider?.manifest?.status or {}
    _sec_data = _res?["elasticsearch-secret-observer"]?.resource?.status?.atProvider?.manifest?.data or {}

    _health = _es_status?.health or ""
    _password = _sec_data?.elastic or ""
    _is_ready = _health in ["green", "yellow"] and _password

    _labels = vars?.labels or {}
    _name = str(vars?.metadata?.name or "")
    _namespace = str(vars?.namespace or "")
    _searchName = str(vars?.searchName or "")
    _searchNamespace = str(vars?.searchNamespace or "")
    _searchSecretName = str(vars?.searchSecretName or "")
    _managementPolicies = vars?.managementPolicies or ["Default"]

    _items = [
        kubernetes.Object {
            apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
            kind = "Object"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "elasticsearch-observer"}
                name = "{}-es-observer".format(_name)
            }
            spec = {
                forProvider = {
                    manifest = {
                        apiVersion = "elasticsearch.k8s.elastic.co/v1"
                        kind = "Elasticsearch"
                        metadata = {name = _searchName, namespace = _searchNamespace}
                    }
                }
                managementPolicies = ["Observe"]
            }
        }
        kubernetes.Object {
            apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
            kind = "Object"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "elasticsearch-secret-observer"}
                name = "{}-es-es-elastic-user-observer".format(_name)
            }
            spec = {
                forProvider = {
                    manifest = k8s_core.Secret {
                        apiVersion = "v1"
                        kind = "Secret"
                        metadata = {name = _searchSecretName, namespace = _searchNamespace}
                    }
                }
                managementPolicies = ["Observe"]
            }
        }
    ]

    if _is_ready:
        _items += [kubernetes.Object {
            apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
            kind = "Object"
            metadata = {
                annotations = {
                    "crossplane.io/external-name": "elasticsearch-connection-details"
                    "crossplane.io/composition-resource-name": "elasticsearch-connection-details-secret"
                }
                labels = _labels
                name = "{}-elasticsearch-connection-details-secret".format(_name)
            }
            spec = {
                forProvider = {
                    manifest = k8s_core.Secret {
                        apiVersion = "v1"
                        kind = "Secret"
                        data = {ELASTICSEARCH_PASSWORD = _password}
                        metadata = {
                            labels = _labels
                            name = "elasticsearch-connection-details"
                            namespace = _namespace
                        }
                        stringData = {
                            ELASTICSEARCH_URL = "http://{}-es-http.{}.svc:9200".format(_searchName, _searchNamespace)
                            ELASTICSEARCH_USERNAME = "elastic"
                        }
                        $type = "Opaque"
                    }
                }
                managementPolicies = _managementPolicies
            }
        }]

    _items
}

_vars = {
    "labels": cv.labels
    "managementPolicies": cv.managementPolicies
    "metadata": cv.metadata
    "namespace": cv.namespace
    "searchName": cv.searchName
    "searchNamespace": cv.searchNamespace
    "searchSecretName": cv.searchSecretName
}

items = {"items": get_items(cv.obs, _vars)}
