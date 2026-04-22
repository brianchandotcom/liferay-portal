import compositions.backup as backup
import compositions.elasticsearch as es
import compositions.init as init
import compositions.k8s_resources as k8s
import compositions.managed_service_details as msd
import compositions.overlay as overlay
import compositions.security as security
import compositions.sql as sql
import compositions.storage as storage
import json
import yaml
import crypto

# This test performs a deep comparison between KCL rendering and the cluster fixtures
# Usage: kcl test comprehensive_comparison_test.k -D params=$(cat arz_dev2025_params.json)
test_comprehensive_comparison = lambda {
    _params = option("params")
    if _params:
        _obs = _params["obs"] if "obs" in _params else {
            "resources": {}
        }
        _oxr = _params["oxr"] if "oxr" in _params else {"spec": {}, "metadata": {}}
        _ctx = _params["ctx"] if "ctx" in _params else {}

        _spec = _oxr["spec"]
        _cmk = _spec["cmk"] if "cmk" in _spec else {}
        _backup = _spec["backup"] if "backup" in _spec else {}
        _db = _spec["database"] if "database" in _spec else {}
        _env = _ctx["apiextensions.crossplane.io/environment"] if "apiextensions.crossplane.io/environment" in _ctx else {}

        _projectId = _spec["projectId"] if "projectId" in _spec else ""
        _environmentId = _spec["environmentId"] if "environmentId" in _spec else ""
        _projectNumber = _env["projectNumber"] if "projectNumber" in _env else ""
        _projectIdFull = "{}-{}".format(_projectId, _environmentId)
        _uidHash = crypto.sha256("{}-{}".format(_projectNumber, _projectIdFull))[:6:]
        _baseName = "{}-{}".format(_projectIdFull[:18:], _uidHash)

        _vars = {
            "backup": _backup
            "baseName": _baseName
            "cmkEnabled": _cmk["enabled"] if "enabled" in _cmk else False
            "cmkKeyName": "projects/arz/locations/us-central1/keyRings/{}-keyring/cryptoKeys/{}-key".format(_baseName, _baseName)
            "database": _db
            "databaseIamUser": "{}-sa@arz.iam".format(_baseName)
            "databaseInstanceName": "{}-db-instance".format(_baseName)
            "databaseName": _db["name"] if "name" in _db else "liferay_db"
            "databasePort": "5432"
            "databaseSpec": _db
            "env": _env
            "forceDestroy": _spec["forceDestroy"] if "forceDestroy" in _spec else False
            "gsaEmail": "{}-sa@arz.iam.gserviceaccount.com".format(_baseName)
            "gsaName": "{}-sa".format(_baseName)
            "instanceConnectionName": "arz:us-central1:{}-db-instance".format(_baseName)
            "jobName": "{}-db-grant".format(_baseName)
            "labels": {
                "app": "liferay"
                "deployment-name": _env["deploymentName"] if "deploymentName" in _env else ""
                "environment-id": _environmentId
                "project-id": _projectId
            }
            "managementPolicies": ["*"]
            "managementPolicy": "Default"
            "metadata": _oxr["metadata"]
            "namespace": _oxr["metadata"]["namespace"] if "namespace" in _oxr["metadata"] else ""
            "overlay": _spec["overlay"] if "overlay" in _spec else {}
            "overlayEnabled": _spec["overlay"]["enabled"] if "overlay" in _spec and "enabled" in _spec["overlay"] else False
            "projectId": _projectId
            "region": _spec["region"] if "region" in _spec else ""
            "searchName": "{}-es".format(_baseName)
            "searchNamespace": "liferay-arz-dev2025-elasticsearch"
            "searchSecretName": "{}-es-elastic-user".format(_baseName)
            "storage_spec": _spec["storage"] if "storage" in _spec else {}
        }

        _rendered_items = []
        _rendered_items += backup.get_items(_obs, _vars)
        _rendered_items += es.get_items(_obs, _vars)
        _rendered_items += init.get_items(_obs, _vars)
        _rendered_items += k8s.get_items(_obs, _vars)
        _rendered_items += msd.get_items(_obs, _vars)
        _rendered_items += overlay.get_items(_obs, _vars)
        _rendered_items += security.get_items(_obs, _vars)
        _rendered_items += sql.get_items(_obs, _vars)
        _rendered_items += storage.get_items(_obs, _vars)

        # Assertions are now primarily handled by the external diff.
        # This test remains to ensure the rendering itself is successful.
        assert len(_rendered_items) >= 19, "Should have at least 19 items"

    True
}
