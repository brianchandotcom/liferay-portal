import context_variables as cv
import models.v1beta1.storage_gcpm_upbound_io_v1beta1_bucket as gcp
import models.v1beta1.storage_gcpm_upbound_io_v1beta1_bucket_i_a_m_member as gcp_iam

get_items = lambda obs: any, vars: {str:any} -> [any] {
    _labels = vars?.labels or {}
    _overlayBucketName = str(vars?.overlayBucketName or "")
    _gsaEmail = str(vars?.gsaEmail or "")
    _managementPolicies = vars?.managementPolicies or ["*"]

    _items: [any] = []

    if vars?.overlayEnabled:
        _items += [
            gcp.Bucket {
                apiVersion = "storage.gcp.m.upbound.io/v1beta1"
                kind = "Bucket"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-overlay-bucket"}
                    labels = _labels
                    name = _overlayBucketName
                }
                spec = {
                    forProvider = {
                        forceDestroy = bool(vars?.forceDestroy or False)
                        labels = _labels
                        location = str(vars?.overlayRegion or vars?.region or "")
                        project = str(vars?.projectId or "")
                        storageClass = "STANDARD"
                        uniformBucketLevelAccess = True
                        versioning = {enabled = True}
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
                    annotations = {"crossplane.io/composition-resource-name": "liferay-gsa-overlay-bucket-iam"}
                    labels = _labels
                }
                spec = {
                    forProvider = {
                        bucketRef = {name = _overlayBucketName}
                        member = "serviceAccount:{}".format(_gsaEmail)
                        role = "roles/storage.objectViewer"
                    }
                    managementPolicies = _managementPolicies
                }
            }
        ]

    _items
}

_vars = {
    "cmkEnabled": cv.cmkEnabled
    "cmkKeyName": cv.cmkKeyName
    "forceDestroy": cv.forceDestroy
    "gsaEmail": cv.gsaEmail
    "labels": cv.labels
    "managementPolicies": cv.managementPolicies
    "overlayBucketName": cv.overlayBucketName
    "overlayEnabled": cv.overlayEnabled
    "overlayRegion": cv.overlay?.region or ""
    "projectId": cv.env?.projectId or ""
    "region": cv.region
}

items = {"items": get_items(cv.obs, _vars)}
