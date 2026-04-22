import context_variables as cv
import models.v1beta1.storage_gcpm_upbound_io_v1beta1_bucket as gcp
import models.v1beta1.storage_gcpm_upbound_io_v1beta1_bucket_i_a_m_member as gcp_iam

get_items = lambda obs: any, vars: {str:any} -> [any] {
    _activeDataPlane = str(vars?.activeDataPlane or "blue")
    _cmkEnabled = bool(vars?.cmkEnabled or False)
    _cmkKeyName = str(vars?.cmkKeyName or "")
    _forceDestroy = bool(vars?.forceDestroy or False)
    _gsaEmail = str(vars?.gsaEmail or "")
    _hashedName = str(vars?.hashedName or "")
    _labels = vars?.labels or {}
    _managementPolicies = vars?.managementPolicies or ["*"]
    _storage_spec = vars?.storage_spec or {}
    _region = str(vars?.region or "")

    [item for _dataPlane in ["blue", "green"] for item in [
        gcp.Bucket {
            apiVersion = "storage.gcp.m.upbound.io/v1beta1"
            kind = "Bucket"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-gcs-bucket-{}".format(_dataPlane)}
                labels = _labels | {"dataPlane": _dataPlane}
                name = "{}-dl-{}".format(_hashedName, _dataPlane)
            }
            spec = {
                forProvider = {
                    forceDestroy = _forceDestroy
                    labels = _labels | {"should-back-up": "true", "active": "{}".format(_dataPlane == _activeDataPlane)}
                    location = str(_storage_spec?.region or _region)
                    storageClass = "STANDARD"
                    uniformBucketLevelAccess = True
                    versioning = {enabled = bool(_storage_spec?.enableVersioning if _storage_spec?.enableVersioning != Undefined else True)}
                }
                if _cmkEnabled:
                    forProvider.encryption = {defaultKmsKeyName = _cmkKeyName}
                managementPolicies = _managementPolicies
            }
        }
        gcp_iam.BucketIAMMember {
            apiVersion = "storage.gcp.m.upbound.io/v1beta1"
            kind = "BucketIAMMember"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-gcs-iam-{}".format(_dataPlane)}
                labels = _labels | {"dataPlane": _dataPlane}
            }
            spec = {
                forProvider = {
                    bucketSelector = {matchLabels = {"dataPlane": _dataPlane}}
                    member = "serviceAccount:{}".format(_gsaEmail)
                    role = "roles/storage.admin"
                }
                managementPolicies = _managementPolicies
            }
        }
    ]]
}

_vars = {
    "activeDataPlane": cv.activeDataPlane
    "cmkEnabled": cv.cmkEnabled
    "cmkKeyName": cv.cmkKeyName
    "forceDestroy": cv.forceDestroy
    "gsaEmail": cv.gsaEmail
    "hashedName": cv.hashedName
    "labels": cv.labels
    "managementPolicies": cv.managementPolicies
    "region": cv.region
    "storage_spec": cv.storage_spec
}

items = {"items": get_items(cv.obs, _vars)}
