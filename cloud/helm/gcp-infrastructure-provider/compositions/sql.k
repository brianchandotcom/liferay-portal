import context_variables as cv
import models.v1beta1.sql_gcpm_upbound_io_v1beta1_database as sql_db
import models.v1beta1.sql_gcpm_upbound_io_v1beta1_database_instance as sql_instance
import models.v1beta1.sql_gcpm_upbound_io_v1beta1_user as sql_user

get_items = lambda obs: any, vars: {str:any} -> [any] {
    _activeDataPlane = str(vars?.activeDataPlane or "blue")
    _baseName = str(vars?.baseName or "")
    _cmkEnabled = bool(vars?.cmkEnabled or False)
    _cmkKeyName = str(vars?.cmkKeyName or "")
    _dataPlanes = vars?.dataPlanes or [_activeDataPlane]
    _db_spec = vars?.database or {}
    _dbInstanceManagementPolicies = vars?.databaseInstanceManagementPolicies or ["*"]
    _env = vars?.env or {}
    _labels = vars?.labels or {}
    _managementPolicies = vars?.managementPolicies or ["*"]
    _namespace = str(vars?.namespace or "")
    _projectId = str(vars?.projectId or "")
    _region = str(vars?.region or "")
    _obs_resources = obs?.resources or {}

    _plane_items = [item for _dataPlane in _dataPlanes for item in [
        sql_instance.DatabaseInstance {
            apiVersion = "sql.gcp.m.upbound.io/v1beta1"
            kind = "DatabaseInstance"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "database-primary-{}".format(_dataPlane)}
                labels = _labels | {"dataPlane": str(_dataPlane)}
                name = "{}-db-instance-{}".format(_baseName, _dataPlane)
            }
            spec = {
                forProvider = {
                    databaseVersion = str(_db_spec?.databaseVersion or "POSTGRES_18")
                    deletionProtection = bool((_db_spec?.deletionProtection or False) and (_dataPlane == _activeDataPlane))
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
                        retainBackupsOnDelete = True
                        tier = str(_db_spec?.instanceType or "db-custom-1-3840")
                        userLabels = _labels | {"should-back-up": "true", "active": "{}".format(_dataPlane == _activeDataPlane)}
                    }
                }
                if _cmkEnabled:
                    forProvider.encryptionKeyName = _cmkKeyName
                managementPolicies = _dbInstanceManagementPolicies
            }
        }
        sql_db.Database {
            apiVersion = "sql.gcp.m.upbound.io/v1beta1"
            kind = "Database"
            metadata = {
                annotations = {
                    "crossplane.io/composition-resource-name": "liferay-db-{}".format(_dataPlane)
                    "crossplane.io/external-name": str(vars?.databaseName or "lportal")
                }
                labels = _labels | {"dataPlane": str(_dataPlane)}
                name = "{}-db-{}".format(_baseName, _dataPlane)
            }
            spec = {
                forProvider = {
                    charset = "UTF8"
                    collation = "en_US.UTF8"
                    deletionPolicy = "ABANDON"
                    instanceSelector = {matchLabels = {"dataPlane": str(_dataPlane)}}
                }
                managementPolicies = _managementPolicies
            }
        }
        sql_user.User {
            apiVersion = "sql.gcp.m.upbound.io/v1beta1"
            kind = "User"
            metadata = {
                annotations = {
                    "crossplane.io/composition-resource-name": "liferay-iam-user-{}".format(_dataPlane)
                    "crossplane.io/external-name": str(vars?.databaseIamUser or "")
                }
                labels = _labels | {"dataPlane": str(_dataPlane)}
                name = "{}-db-user-{}".format(_baseName, _dataPlane)
            }
            spec = {
                forProvider = {
                    deletionPolicy = "ABANDON"
                    instanceSelector = {matchLabels = {"dataPlane": str(_dataPlane)}}
                    $type = "CLOUD_IAM_SERVICE_ACCOUNT"
                }
                managementPolicies = _managementPolicies
            }
        }
    ]]

    _db_usages = [
        {
            apiVersion = "protection.crossplane.io/v1beta1"
            kind = "Usage"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "usage-instance-by-database-{}".format(_dataPlane)}
                name = "{}-instance-by-database-{}".format(_baseName, _dataPlane)
                namespace = _namespace
            }
            spec = {
                of = {
                    apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                    kind = "DatabaseInstance"
                    resourceRef = {name = "{}-db-instance-{}".format(_baseName, _dataPlane)}
                }
                by = {
                    apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                    kind = "Database"
                    resourceRef = {name = "{}-db-{}".format(_baseName, _dataPlane)}
                }
            }
        }
        for _dataPlane in ["blue", "green"]
        if _dataPlane in _dataPlanes or "liferay-db-{}".format(_dataPlane) in _obs_resources
    ]

    _user_usages = [
        {
            apiVersion = "protection.crossplane.io/v1beta1"
            kind = "Usage"
            metadata = {
                annotations = {"crossplane.io/composition-resource-name": "usage-instance-by-user-{}".format(_dataPlane)}
                name = "{}-instance-by-user-{}".format(_baseName, _dataPlane)
                namespace = _namespace
            }
            spec = {
                of = {
                    apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                    kind = "DatabaseInstance"
                    resourceRef = {name = "{}-db-instance-{}".format(_baseName, _dataPlane)}
                }
                by = {
                    apiVersion = "sql.gcp.m.upbound.io/v1beta1"
                    kind = "User"
                    resourceRef = {name = "{}-db-user-{}".format(_baseName, _dataPlane)}
                }
            }
        }
        for _dataPlane in ["blue", "green"]
        if _dataPlane in _dataPlanes or "liferay-iam-user-{}".format(_dataPlane) in _obs_resources
    ]

    _plane_items + _db_usages + _user_usages
}

_vars = {
    "activeDataPlane": cv.activeDataPlane
    "baseName": cv.baseName
    "cmkEnabled": cv.cmkEnabled
    "cmkKeyName": cv.cmkKeyName
    "database": cv.databaseSpec
    "databaseIamUser": cv.databaseIamUser
    "databaseInstanceManagementPolicies": cv.databaseInstanceManagementPolicies
    "databaseName": cv.databaseName
    "dataPlanes": cv.dataPlanes
    "env": cv.env
    "labels": cv.labels
    "managementPolicies": cv.managementPolicies
    "namespace": cv.namespace
    "projectId": cv.env?.projectId or ""
    "region": cv.region
}

items = {"items": get_items(cv.obs, _vars)}
