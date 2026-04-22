import context_variables as cv
import models.v1beta1.sql_gcpm_upbound_io_v1beta1_database as sql_db
import models.v1beta1.sql_gcpm_upbound_io_v1beta1_database_instance as sql_instance
import models.v1beta1.sql_gcpm_upbound_io_v1beta1_user as sql_user

get_items = lambda obs: any, vars: {str:any} -> [any] {
    _baseName = str(vars?.baseName or "")
    _cmkEnabled = bool(vars?.cmkEnabled or False)
    _cmkKeyName = str(vars?.cmkKeyName or "")
    _db_spec = vars?.database or {}
    _dbObjectName = str(vars?.databaseObjectName or "{}-db".format(_baseName))
    _env = vars?.env or {}
    _instanceName = str(vars?.databaseInstanceName or "")
    _labels = vars?.labels or {}
    _managementPolicies = vars?.managementPolicies or ["*"]
    _namespace = str(vars?.namespace or "")
    _projectId = str(vars?.projectId or "")
    _region = str(vars?.region or "")
    _userObjectName = str(vars?.databaseUserObjectName or "{}-db-user".format(_baseName))

    _instance = sql_instance.DatabaseInstance {
        apiVersion = "sql.gcp.m.upbound.io/v1beta1"
        kind = "DatabaseInstance"
        metadata = {
            annotations = {"crossplane.io/composition-resource-name": "database-primary"}
            labels = _labels
            name = _instanceName
        }
        spec = {
            forProvider = {
                databaseVersion = str(_db_spec?.databaseVersion or "POSTGRES_18")
                deletionProtection = bool(_db_spec?.deletionProtection or False)
                region = _region
                rootPasswordSecretRef = {
                    key = "password"
                    name = str(_db_spec?.rootPasswordSecretName or "placeholder")
                }
                settings = {
                    databaseFlags = [{name = "cloudsql.iam_authentication", value = "on"}]
                    diskSize = int(_db_spec?.storageGB or 10)
                    diskType = "PD_SSD"
                    edition = "ENTERPRISE"
                    insightsConfig = {
                        queryInsightsEnabled = bool(_db_spec?.queryInsightsEnabled if _db_spec?.queryInsightsEnabled != Undefined else True)
                    }
                    ipConfiguration = {
                        ipv4Enabled = False
                        privateNetwork = "projects/{}/global/networks/{}".format(_projectId, str(_env?.vpcName or ""))
                        sslMode = "TRUSTED_CLIENT_CERTIFICATE_REQUIRED"
                    }
                    tier = str(_db_spec?.instanceType or "db-custom-1-3840")
                    userLabels = _labels | {"active": "true", "should-back-up": "true"}
                }
            }
            if _cmkEnabled:
                forProvider.encryptionKeyName = _cmkKeyName
            managementPolicies = _managementPolicies
        }
    }

    [
        _instance
        sql_db.Database {
            apiVersion = "sql.gcp.m.upbound.io/v1beta1"
            kind = "Database"
            metadata = {
                annotations = {
                    "crossplane.io/composition-resource-name": "liferay-db"
                    "crossplane.io/external-name": str(vars?.databaseName or "lportal")
                }
                labels = _labels
                name = _dbObjectName
            }
            spec = {
                forProvider = {
                    charset = "UTF8"
                    collation = "en_US.UTF8"
                    instanceSelector = {matchControllerRef = True}
                }
                managementPolicies = _managementPolicies
            }
        }
        sql_user.User {
            apiVersion = "sql.gcp.m.upbound.io/v1beta1"
            kind = "User"
            metadata = {
                annotations = {
                    "crossplane.io/composition-resource-name": "liferay-iam-user"
                    "crossplane.io/external-name": str(vars?.databaseIamUser or "")
                }
                labels = _labels
                name = _userObjectName
            }
            spec = {
                forProvider = {
                    instanceSelector = {matchControllerRef = True}
                    $type = "CLOUD_IAM_SERVICE_ACCOUNT"
                }
                managementPolicies = _managementPolicies
            }
        }
        {
            apiVersion = "protection.crossplane.io/v1beta1"
            kind = "Usage"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "usage-instance-by-database"}
                name = "{}-instance-by-database".format(_baseName)
                namespace = _namespace
            }
            spec = {
                of = {
                    apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                    kind = "DatabaseInstance"
                    resourceRef = {name = _instanceName}
                }
                by = {
                    apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                    kind = "Database"
                    resourceRef = {name = _dbObjectName}
                }
            }
        }
        {
            apiVersion = "protection.crossplane.io/v1beta1"
            kind = "Usage"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "usage-instance-by-user"}
                name = "{}-instance-by-user".format(_baseName)
                namespace = _namespace
            }
            spec = {
                of = {
                    apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                    kind = "DatabaseInstance"
                    resourceRef = {name = _instanceName}
                }
                by = {
                    apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                    kind = "User"
                    resourceRef = {name = _userObjectName}
                }
            }
        }
    ]
}

_vars = {
    "baseName": cv.baseName
    "cmkEnabled": cv.cmkEnabled
    "cmkKeyName": cv.cmkKeyName
    "database": cv.databaseSpec
    "databaseIamUser": cv.databaseIamUser
    "databaseInstanceName": cv.databaseInstanceName
    "databaseName": cv.databaseName
    "databaseObjectName": cv.databaseObjectName
    "databaseUserObjectName": cv.databaseUserObjectName
    "env": cv.env
    "labels": cv.labels
    "managementPolicies": cv.managementPolicies
    "namespace": cv.namespace
    "projectId": cv.env?.projectId or ""
    "region": cv.region
}

items = {"items": get_items(cv.obs, _vars)}
