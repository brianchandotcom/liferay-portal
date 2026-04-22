import context_variables as cv
import k8s.api.core.v1 as k8s_core
import models.v1alpha1.kubernetesm_crossplane_io_v1alpha1_object as kubernetes

get_items = lambda obs: any, vars: {str:any} -> [any] {
    _activeDataPlane = str(vars?.activeDataPlane or "blue")
    _res = obs?.resources or {}
    _grant_conditions = _res?["liferay-db-grant-job-{}".format(_activeDataPlane)]?.resource?.status?.conditions or []
    _is_ready = any c in _grant_conditions {c.type == "Ready" and c.status == "True"}

    _externalName = str(vars?.managedServiceDetailsExternalName or "")
    _hashedName = str(vars?.hashedName or "")
    _labels = vars?.labels or {}
    _managementPolicies = vars?.managementPolicies or ["*"]
    _namespace = str(vars?.namespace or "")
    _meta_name = str(vars?.baseName or "")
    _region = str(vars?.region or "")
    _projectId = str(vars?.projectId or "")

    _items: [any] = []
    if _is_ready:
        _secret = k8s_core.Secret {
            apiVersion = "v1"
            kind = "Secret"
            metadata = {
                labels = _labels
                name = "managed-service-details"
                namespace = _namespace
            }
            stringData = {
                DATABASE_ENDPOINT = "localhost"
                DATABASE_INSTANCE_NAME = "{}:{}:{}-db-instance-{}".format(_projectId, _region, _meta_name, _activeDataPlane)
                DATABASE_PORT = str(vars?.databasePort or "5432")
                DATABASE_USERNAME = str(vars?.databaseIamUser or "")
                LIFERAY_GCS_BUCKET_NAME = "{}-dl-{}".format(_hashedName, _activeDataPlane)
                LIFERAY_OVERLAY_BUCKET_NAME = str(vars?.overlayBucketName or "")
            }
            $type = "Opaque"
        }

        _items += [kubernetes.Object {
            apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
            kind = "Object"
            metadata = {
                annotations = {
                    "crossplane.io/composition-resource-name": "managed-service-details-secret"
                    "crossplane.io/external-name": _externalName
                }
                labels = _labels
                name = _externalName
            }
            spec = {
                forProvider = {manifest = _secret}
                managementPolicies = _managementPolicies
            }
        }]

    _items
}

_vars = {
    "activeDataPlane": cv.activeDataPlane
    "baseName": cv.baseName
    "databaseIamUser": cv.databaseIamUser
    "databasePort": cv.databasePort
    "hashedName": cv.hashedName
    "labels": cv.labels
    "managedServiceDetailsExternalName": cv.managedServiceDetailsExternalName
    "managementPolicies": cv.managementPolicies
    "namespace": cv.namespace
    "overlayBucketName": cv.overlayBucketName
    "projectId": cv.env?.projectId or ""
    "region": cv.region
}

items = {"items": get_items(cv.obs, _vars)}
