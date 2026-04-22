import compositions.managed_service_details as msd
import compositions.sql as sql
import compositions.storage as storage
import compositions.overlay as overlay
import compositions.security as security
import compositions.elasticsearch as es
import crypto

test_arz_dev2025_resource_names = lambda {
    _projectId = "arz"
    _environmentId = "dev2025"
    _projectNumber = "1010819446150"
    _projectIdFull = _projectId + "-" + _environmentId
    _uidHash = crypto.sha256(_projectNumber + "-" + _projectIdFull)[:6:]
    _baseName = "arz-dev2025-00d245"
    _meta_name = "arz-dev2025-infra"
    _gsaEmail = "{}-sa@{}.iam.gserviceaccount.com".format(_baseName, _projectId)

    _vars: {str:any} = {
        "activeDataPlane": "blue"
        "backup": {"enabled": False}
        "backupBucketName": "{}-vault".format(_baseName)
        "baseName": _meta_name
        "cmkEnabled": False
        "cmkKeyName": ""
        "databaseIamUser": "{}-sa@{}".format(_baseName, _projectId)
        "databaseInstanceManagementPolicies": ["*"]
        "databaseName": "liferay_db"
        "databasePort": "5432"
        "databaseSpec": {
            "databaseVersion": "POSTGRES_18"
            "deletionProtection": True
            "instanceType": "db-custom-1-3840"
            "queryInsightsEnabled": True
            "storageGB": 20
        }
        "dataPlanes": ["blue"]
        "env": {
            "projectId": "arz"
            "projectNumber": _projectNumber
            "deploymentName": "liferay-portal-poc"
            "vpcName": "liferay-portal-poc-vpc"
        }
        "forceDestroy": False
        "gsaEmail": _gsaEmail
        "gsaName": "{}-sa".format(_baseName)
        "hashedName": _baseName
        "labels": {
            "app": "liferay"
            "deployment-name": "liferay-portal-poc"
            "environment-id": _environmentId
            "project-id": _projectId
        }
        "managedServiceDetailsExternalName": "{}-managed-service-details-secret".format(_baseName)
        "managementPolicies": ["*"]
        "managementPolicy": "Default"
        "metadata": {"name": _meta_name}
        "namespace": "liferay-arz-dev2025"
        "overlay": {"enabled": True, "region": "us-south1", "data": {}}
        "overlayBucketName": "{}-overlay".format(_baseName)
        "overlayEnabled": True
        "region": "us-central1"
        "searchName": "{}-es".format(_baseName)
        "searchNamespace": "liferay-arz-dev2025-elasticsearch"
        "searchSecretName": "{}-es-elastic-user".format(_baseName)
        "storage_spec": {"enableVersioning": True}
    }

    _obs = {
        "resources": {
            "liferay-db-grant-job-blue": {
                "resource": {
                    "status": {
                        "conditions": [{"type": "Ready", "status": "True"}]
                    }
                }
            }
            "elasticsearch-observer": {
                "resource": {
                    "status": {
                        "atProvider": {
                            "manifest": {
                                "status": {"health": "green"}
                            }
                        }
                    }
                }
            }
            "elasticsearch-secret-observer": {
                "resource": {
                    "status": {
                        "atProvider": {
                            "manifest": {
                                "data": {"elastic": "dGVzdA=="}
                            }
                        }
                    }
                }
            }
        }
    }

    _find_by_anno = lambda items: [any], anno: str -> any {
        _matches = [i for i in items if i?.metadata?.annotations?["crossplane.io/composition-resource-name"] == anno]
        _matches[0] if len(_matches) > 0 else None
    }

    _sec_items = security.get_items(_obs, _vars)
    assert _find_by_anno(_sec_items, "liferay-sql-instance-user-role") != None
    assert _find_by_anno(_sec_items, "liferay-sql-client-role") != None
    assert _find_by_anno(_sec_items, "liferay-wi-binding") != None
    assert _find_by_anno(_sec_items, "liferay-gsa").metadata.name == "arz-dev2025-00d245-sa"

    _msd_items = msd.get_items(_obs, _vars)
    assert _find_by_anno(_msd_items, "managed-service-details-secret") != None

    _storage_items = storage.get_items(_obs, _vars)
    assert _storage_items[0].metadata.name == "{}-dl-blue".format(_baseName)
    assert _find_by_anno(_storage_items, "liferay-gcs-iam-blue") != None

    _overlay_items = overlay.get_items(_obs, _vars)
    assert len(_overlay_items) == 2
    assert _overlay_items[0].metadata.name == "{}-overlay".format(_baseName)
    assert _find_by_anno(_overlay_items, "liferay-gsa-overlay-bucket-iam") != None

    _es_items = es.get_items(_obs, _vars)
    assert _es_items[0].metadata.name == "arz-dev2025-infra-es-observer"
    assert _es_items[1].metadata.name == "arz-dev2025-infra-es-es-elastic-user-observer"
}
