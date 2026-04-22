import context_variables as cv
import crypto
import k8s.api.core.v1 as k8s_core
import k8s.api.rbac.v1 as k8s_rbac
import models.v1alpha1.kubernetesm_crossplane_io_v1alpha1_object as kubernetes
import models.v1beta1.storage_gcpm_upbound_io_v1beta1_bucket as storage_bucket
import models.v1beta1.storage_gcpm_upbound_io_v1beta1_bucket_i_a_m_member as storage_iam

get_items = lambda obs: any, vars: {str:any} -> [any] {
    _backup = vars?.backup or {}
    _gsaEmail = str(vars?.gsaEmail or "")
    _labels = vars?.labels or {}
    _managementPolicies = vars?.managementPolicies or ["*"]
    _managementPolicy = vars?.managementPolicy or "Default"
    _meta_name = str(vars?.metadata?.name or "")
    _namespace = str(vars?.namespace or "")

    _get_iam_name = lambda role: str, bucket: str -> str {
        _hash = crypto.sha256(_meta_name + role + "serviceAccount:" + _gsaEmail + bucket)[:12:]
        "{}-{}".format(_meta_name, _hash)
    }

    _backup_items: [any] = []
    if _backup?.enabled:
        _backup_vault_labels = _labels | {"is-backup-vault": "true"}
        _bucket_name = str(vars?.backupBucketName or "")

        _backup_items += [storage_bucket.Bucket {
            apiVersion = "storage.gcp.m.upbound.io/v1beta1"
            kind = "Bucket"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-gcs-backup"}
                labels = _backup_vault_labels
                name = _bucket_name
            }
            spec = {
                forProvider = {
                    forceDestroy = bool(vars?.forceDestroy or False)
                    labels = _backup_vault_labels
                    lifecycleRule = [{
                        action = {$type = "Delete"}
                        condition = {age = int(_backup?.retentionDays or 30) + 2}
                    }]
                    location = str(_backup?.region or vars?.region or "")
                    storageClass = str(_backup?.storageClass or "NEARLINE")
                    uniformBucketLevelAccess = True
                }
                if vars?.cmkEnabled:
                    forProvider.encryption = {
                        defaultKmsKeyName = str(vars?.cmkKeyName or "")
                    }
                managementPolicies = _managementPolicies
            }
        }]

        _backup_items += [storage_iam.BucketIAMMember {
            apiVersion = "storage.gcp.m.upbound.io/v1beta1"
            kind = "BucketIAMMember"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-gcs-backup-iam"}
                labels = _labels
                name = _get_iam_name("roles/storage.admin", _bucket_name)
            }
            spec = {
                forProvider = {
                    bucketRef = {"name": _bucket_name}
                    member = "serviceAccount:{}".format(_gsaEmail)
                    role = "roles/storage.admin"
                }
                managementPolicies = _managementPolicies
            }
        }]

        # The ClusterRole 'backup-create-cluster-role' is provisioned out-of-band
        # (baseline RBAC chart); this composition only creates the per-namespace
        # RoleBinding + ServiceAccount that use it.
        _backup_sa = k8s_core.ServiceAccount {
            apiVersion = "v1"
            kind = "ServiceAccount"
            metadata = {
                labels = _labels
                name = "backup-create-service-account"
                namespace = _namespace
            }
        }

        _backup_rb = k8s_rbac.RoleBinding {
            apiVersion = "rbac.authorization.k8s.io/v1"
            kind = "RoleBinding"
            metadata = {
                labels = _labels
                name = "backup-create-role-binding"
                namespace = _namespace
            }
            roleRef = {
                apiGroup = "rbac.authorization.k8s.io"
                kind = "ClusterRole"
                name = "backup-create-cluster-role"
            }
            subjects = [{
                kind = "ServiceAccount"
                name = "backup-create-service-account"
                namespace = _namespace
            }]
        }

        _backup_items += [
            kubernetes.Object {
                apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
                kind = "Object"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "backup-create-service-account"}
                    labels = _labels
                    name = "backup-create-service-account"
                }
                spec = {
                    forProvider = {"manifest": _backup_sa}
                    managementPolicies = _managementPolicies
                }
            }
            kubernetes.Object {
                apiVersion = "kubernetes.m.crossplane.io/v1alpha1"
                kind = "Object"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "backup-create-role-binding"}
                    labels = _labels
                    name = "backup-create-role-binding"
                }
                spec = {
                    forProvider = {"manifest": _backup_rb}
                    managementPolicies = _managementPolicies
                }
            }
        ]

    _backup_items
}

_vars = {
    "backup": cv.backup
    "backupBucketName": cv.backupBucketName
    "cmkEnabled": cv.cmkEnabled
    "cmkKeyName": cv.cmkKeyName
    "forceDestroy": cv.forceDestroy
    "gsaEmail": cv.gsaEmail
    "labels": cv.labels
    "managementPolicies": cv.managementPolicies
    "managementPolicy": cv.managementPolicy
    "metadata": cv.metadata
    "namespace": cv.namespace
    "region": cv.region
}

items = {"items": get_items(cv.obs, _vars)}
