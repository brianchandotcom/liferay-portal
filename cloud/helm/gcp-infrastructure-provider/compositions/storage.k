import context_variables as cv
import models.v1beta1.storage_gcpm_upbound_io_v1beta1_bucket as gcp
import models.v1beta1.storage_gcpm_upbound_io_v1beta1_bucket_i_a_m_member as gcp_iam

get_items = lambda obs: any, vars: {str:any} -> [any] {
    _storage_spec = vars?.storage_spec or {}
    _labels = vars?.labels or {}
    _bucket_name = str(vars?.bucketName or "")
    _managementPolicies = vars?.managementPolicies or ["*"]

    [
        gcp.Bucket {
            apiVersion = "storage.gcp.m.upbound.io/v1beta1"
            kind = "Bucket"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-gcs-bucket"}
                labels = _labels
                name = _bucket_name
            }
            spec = {
                forProvider = {
                    forceDestroy = bool(vars?.forceDestroy or False)
                    labels = _labels | {"active": "true", "should-back-up": "true"}
                    location = str(_storage_spec?.region or vars?.region or "")
                    storageClass = str(_storage_spec?.storageClass or "STANDARD")
                    uniformBucketLevelAccess = True
                    versioning = {enabled = bool(_storage_spec?.enableVersioning if _storage_spec?.enableVersioning != Undefined else True)}
                }
                if vars?.cmkEnabled:
                    forProvider.encryption = {
                        defaultKmsKeyName = str(vars?.cmkKeyName or "")
                    }
                managementPolicies = _managementPolicies
            }
        }
        gcp_iam.BucketIAMMember {
            apiVersion = "storage.gcp.m.upbound.io/v1beta1"
            kind = "BucketIAMMember"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-gcs-iam"}
                labels = _labels
            }
            spec = {
                forProvider = {
                    member = "serviceAccount:{}".format(str(vars?.gsaEmail or ""))
                    role = "roles/storage.admin"
                    bucketRef = {name = _bucket_name}
                }
                managementPolicies = _managementPolicies
            }
        }
    ]
}

_vars = {
    "bucketName": cv.bucketName
    "cmkEnabled": cv.cmkEnabled
    "cmkKeyName": cv.cmkKeyName
    "forceDestroy": cv.forceDestroy
    "gsaEmail": cv.gsaEmail
    "labels": cv.labels
    "managementPolicies": cv.managementPolicies
    "region": cv.region
    "storage_spec": cv.storage_spec
}

items = {"items": get_items(cv.obs, _vars)}
