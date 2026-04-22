import compositions.backup as backup
import compositions.elasticsearch as es
import compositions.init as init
import compositions.k8s_resources as k8s
import compositions.managed_service_details as msd
import compositions.overlay as overlay
import compositions.security as security
import compositions.sql as sql
import compositions.storage as storage
import crypto

_is_dict_like = lambda x -> bool {
    typeof(x) not in ["int", "float", "str", "bool", "list", "None", "UndefinedType"]
}
_is_subset = lambda a: any, b: any -> bool {
    (all k, v in b {v == Undefined or v == None or (k in a and _is_subset(a[k], v))}) if _is_dict_like(b) and _is_dict_like(a) else ((len(a) == len(b) and all i, item in b {_is_subset(a[i], item)}) if typeof(b) == "list" and typeof(a) == "list" else a == b)
}

test_cluster_refs_match = lambda {
    _params = option("params")
    _cluster_refs = option("cluster_refs")

    if _params and _cluster_refs:
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
        _hashedName = "{}-{}".format(_projectIdFull[:18:], _uidHash)
        _baseName = _oxr["metadata"]["name"]

        _vars = {
            "activeDataPlane": "blue"
            "backup": _backup
            "backupBucketName": "{}-vault".format(_hashedName)
            "baseName": _baseName
            "overlayBucketName": "{}-overlay".format(_hashedName)
            "managedServiceDetailsExternalName": "{}-managed-service-details-secret".format(_hashedName)
            "hashedName": _hashedName
            "cmkEnabled": _cmk["enabled"] if "enabled" in _cmk else False
            "cmkKeyName": "projects/cne-development/locations/us-central1/keyRings/{}-keyring/cryptoKeys/{}-key".format(_hashedName, _hashedName)
            "database": _db
            "databaseIamUser": "{}-sa@cne-development.iam".format(_baseName)
            "databaseInstanceManagementPolicies": ["*"]
            "databaseName": _db["name"] if "name" in _db else "lportal"
            "databasePort": "5432"
            "databaseSpec": _db
            "dataPlanes": ["blue"]
            "env": _env | {"projectId" = "cne-development"}
            "forceDestroy": _spec["forceDestroy"] if "forceDestroy" in _spec else False
            "gsaEmail": "{}-sa@cne-development.iam.gserviceaccount.com".format(_baseName)
            "gsaName": "{}-sa".format(_baseName)
            "labels": {
                "app": "liferay"
                "deployment-name": _env["deploymentName"] if "deploymentName" in _env else ""
                "environment-id": _environmentId
                "environment_id": _environmentId
                "project-id": _projectId
                "project_id": _projectId
            }
            "managementPolicies": ["*"]
            "managementPolicy": "Default"
            "metadata": _oxr["metadata"]
            "namespace": _oxr["metadata"]["namespace"] if "namespace" in _oxr["metadata"] else ""
            "overlay": _spec["overlay"] if "overlay" in _spec else {}
            "overlayEnabled": _spec["overlay"]["enabled"] if "overlay" in _spec and "enabled" in _spec["overlay"] else False
            "overlayRegion": _spec["overlay"]["region"] if "overlay" in _spec and "region" in _spec["overlay"] else ""
            "projectId": "cne-development"
            "region": _spec["region"] if "region" in _spec else ""
            "searchName": "{}-es".format(_baseName)
            "searchNamespace": "liferay-arz-dev2025-elasticsearch"
            "searchSecretName": "{}-es-es-elastic-user".format(_baseName)
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

        _rendered_simplified = [{
            "apiVersion": i?.apiVersion
            "kind": i?.kind
            "name": i?.metadata?.annotations?["crossplane.io/composition-resource-name"] or i?.metadata?.name
            "spec": i?.spec
            "metadata": {
                "labels": i?.metadata?.labels
                "annotations": i?.metadata?.annotations
            }
        } for i in _rendered_items if i?.spec != Undefined]

        _rendered_simplified_clean = [r | {
            "spec": {k: v for k, v in r.spec if k != "of" and k != "by" and k != "forProvider"} | ({"of": {k: v for k, v in r.spec.of if k != "resourceSelector"}, "by": {k: v for k, v in r.spec.by if k != "resourceSelector"}} if r.kind == "Usage" else {}) | ({"forProvider": {k: v for k, v in r.spec.forProvider if k != "manifest"} | {"manifest": {k: v for k, v in r.spec.forProvider.manifest if k != "data" and k != "stringData"}}} if r.kind == "Object" and r.spec?.forProvider?.manifest?.kind == "Secret" else ({"forProvider": r.spec.forProvider} if "forProvider" in r.spec else {}))
        } for r in _rendered_simplified]

        _filtered_cluster_refs = [c | {
            "spec": {k: v for k, v in c.spec if k != "forProvider"} | ({"forProvider": {k: v for k, v in c.spec.forProvider if k != "manifest"} | {"manifest": {k: v for k, v in c.spec.forProvider.manifest if k != "data" and k != "stringData"}}} if c.kind == "Object" and c.spec?.forProvider?.manifest?.kind == "Secret" else ({"forProvider": c.spec.forProvider} if "forProvider" in c.spec else {}))
        } for c in _cluster_refs if c.name != "composition-context"]
        _missing_in_rendered = [c for c in _filtered_cluster_refs if not any r in _rendered_simplified_clean {r.apiVersion == c.apiVersion and r.kind == c.kind and r.name == c.name and _is_subset(c.spec, r.spec) and _is_subset(c.metadata, r.metadata)}]

        if len(_missing_in_rendered) > 0:
            _failed_c = _missing_in_rendered[0]
            _failed_r = [r for r in _rendered_simplified_clean if r.name == _failed_c.name and r.kind == _failed_c.kind][0] if any r in _rendered_simplified_clean {r.name == _failed_c.name and r.kind == _failed_c.kind} else {"metadata": "<not rendered>", "spec": "<not rendered>"}
            print("=== CLUSTER METADATA ===")
            print(_failed_c.metadata)
            print("=== KCL METADATA ===")
            print(_failed_r.metadata)
            print("=== CLUSTER SPEC ===")
            print(_failed_c.spec)
            print("=== KCL SPEC ===")
            print(_failed_r.spec)

        _extra_in_rendered = [r for r in _rendered_simplified_clean if not any c in _filtered_cluster_refs {c.apiVersion == r.apiVersion and c.kind == r.kind and c.name == r.name and _is_subset(c.spec, r.spec) and _is_subset(c.metadata, r.metadata)}]

        assert len(_missing_in_rendered) == 0, "Missing resources in rendered KCL output compared to cluster:\n" + str(_missing_in_rendered)
        assert len(_extra_in_rendered) == 0, "Extra resources in rendered KCL output compared to cluster:\n" + str(_extra_in_rendered)

    True
}
