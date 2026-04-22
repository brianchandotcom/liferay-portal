import context_variables as cv
import models.v1beta1.cloudplatform_gcpm_upbound_io_v1beta1_project_i_a_m_member as project_iam
import models.v1beta1.cloudplatform_gcpm_upbound_io_v1beta1_service_account as sa
import models.v1beta1.cloudplatform_gcpm_upbound_io_v1beta1_service_account_i_a_m_member as sa_iam
import models.v1beta1.kms_gcpm_upbound_io_v1beta1_crypto_key as kms_key
import models.v1beta1.kms_gcpm_upbound_io_v1beta1_crypto_key_i_a_m_member as kms_iam
import models.v1beta1.kms_gcpm_upbound_io_v1beta1_key_ring as kms_keyring

get_items = lambda obs: any, vars: {str:any} -> [any] {
    _env = vars?.env or {}
    _gsaEmail = str(vars?.gsaEmail or "")
    _gsaName = str(vars?.gsaName or "")

    # KMS resources are global in GCP — use hashedName for uniqueness across
    # deployments that share a project.
    _kmsName = str(vars?.hashedName or "")
    _labels = vars?.labels or {}
    _managementPolicies = vars?.managementPolicies or ["*"]
    _namespace = str(vars?.namespace or "")
    _projectId = str(_env?.projectId or "")

    _security_items: [any] = [
        sa.ServiceAccount {
            apiVersion = "cloudplatform.gcp.m.upbound.io/v1beta1"
            kind = "ServiceAccount"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-gsa"}
                labels = _labels
                name = _gsaName
            }
            spec = {
                forProvider = {
                    displayName = "Liferay {} Service Account".format(_gsaName)
                }
                managementPolicies = _managementPolicies
            }
        }
        sa_iam.ServiceAccountIAMMember {
            apiVersion = "cloudplatform.gcp.m.upbound.io/v1beta1"
            kind = "ServiceAccountIAMMember"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-wi-binding"}
                labels = _labels
            }
            spec = {
                forProvider = {
                    member = "serviceAccount:{}.svc.id.goog[{}/liferay-default]".format(_projectId, _namespace)
                    role = "roles/iam.workloadIdentityUser"
                    serviceAccountIdRef = {name = _gsaName}
                }
                managementPolicies = _managementPolicies
            }
        }
        project_iam.ProjectIAMMember {
            apiVersion = "cloudplatform.gcp.m.upbound.io/v1beta1"
            kind = "ProjectIAMMember"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-sql-instance-user-role"}
                labels = _labels
            }
            spec = {
                forProvider = {
                    member = "serviceAccount:{}".format(_gsaEmail)
                    project = _projectId
                    role = "roles/cloudsql.instanceUser"
                }
                managementPolicies = _managementPolicies
            }
        }
        project_iam.ProjectIAMMember {
            apiVersion = "cloudplatform.gcp.m.upbound.io/v1beta1"
            kind = "ProjectIAMMember"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-sql-client-role"}
                labels = _labels
            }
            spec = {
                forProvider = {
                    member = "serviceAccount:{}".format(_gsaEmail)
                    project = _projectId
                    role = "roles/cloudsql.client"
                }
                managementPolicies = _managementPolicies
            }
        }
    ]

    if vars?.cmkEnabled:
        _security_items += [
            kms_keyring.KeyRing {
                apiVersion = "kms.gcp.m.upbound.io/v1beta1"
                kind = "KeyRing"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-keyring"}
                    labels = _labels
                    name = "{}-keyring".format(_kmsName)
                }
                spec = {
                    forProvider = {location = str(vars?.region or "")}
                    managementPolicies = _managementPolicies
                }
            }
            kms_key.CryptoKey {
                apiVersion = "kms.gcp.m.upbound.io/v1beta1"
                kind = "CryptoKey"
                metadata = {
                    annotations = {"crossplane.io/composition-resource-name": "liferay-cryptokey"}
                    labels = _labels
                    name = "{}-key".format(_kmsName)
                }
                spec = {
                    forProvider = {
                        keyRingSelector = {matchControllerRef = True}
                        purpose = "ENCRYPT_DECRYPT"
                        rotationPeriod = "86400s"
                    }
                    managementPolicies = _managementPolicies
                }
            }
        ]

        _kms_targets = [
            {"name": "sql", "svc": "gcp-sa-cloud-sql"}
            {"name": "storage", "svc": "gs-project-accounts"}
        ]
        _security_items += [kms_iam.CryptoKeyIAMMember {
            apiVersion = "kms.gcp.m.upbound.io/v1beta1"
            kind = "CryptoKeyIAMMember"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "liferay-{}-kms-binding".format(t.name)}
                labels = _labels
            }
            spec = {
                forProvider = {
                    cryptoKeyIdRef = {name = "{}-key".format(_kmsName)}
                    member = "serviceAccount:service-{}@{}.iam.gserviceaccount.com".format(str(_env?.projectNumber or ""), t.svc)
                    role = "roles/cloudkms.cryptoKeyEncrypterDecrypter"
                }
                managementPolicies = _managementPolicies
            }
        } for t in _kms_targets]

    _security_items
}

_vars = {
    "cmkEnabled": cv.cmkEnabled
    "cmkKeyName": cv.cmkKeyName
    "env": cv.env
    "gsaEmail": cv.gsaEmail
    "gsaName": cv.gsaName
    "hashedName": cv.hashedName
    "labels": cv.labels
    "managementPolicies": cv.managementPolicies
    "namespace": cv.namespace
    "region": cv.region
}

items = {"items": get_items(cv.obs, _vars)}
