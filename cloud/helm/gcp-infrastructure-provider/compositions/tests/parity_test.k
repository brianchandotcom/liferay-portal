import compositions.backup as backup
import compositions.k8s_resources as k8s
import compositions.managed_service_details as msd
import compositions.overlay as overlay
import compositions.sql as sql
import compositions.storage as storage

_vars_with = lambda overrides: {str:any} -> {str:any} {
    {k: (overrides[k] if k in overrides else v) for k, v in _base_vars} | {k: v for k, v in overrides if k not in _base_vars}
}

_base_vars: {str:any} = {
    "activeDataPlane": "blue"
    "backupBucketName": "hashed-vault"
    "baseName": "test"
    "cmkEnabled": False
    "cmkKeyName": "projects/p/locations/us-central1/keyRings/test-keyring/cryptoKeys/test-key"
    "database": {"rootPasswordSecretName": "sql-password"}
    "databaseIamUser": "test-sa@p.iam"
    "databaseInstanceManagementPolicies": ["*"]
    "databaseName": "lportal"
    "dataPlanes": ["blue"]
    "databasePort": "5432"
    "env": {"projectId": "p", "vpcName": "v"}
    "forceDestroy": False
    "gsaEmail": "test-sa@p.iam.gserviceaccount.com"
    "hashedName": "hashed"
    "labels": {"app": "liferay"}
    "managedServiceDetailsExternalName": "hashed-managed-service-details-secret"
    "managementPolicies": ["*"]
    "managementPoliciesOnce": ["Create", "Observe", "Delete"]
    "managementPolicy": "Default"
    "metadata": {"name": "test", "namespace": "ns"}
    "namespace": "ns"
    "overlayBucketName": "hashed-overlay"
    "overlayEnabled": True
    "overlayRegion": ""
    "projectId": "p"
    "region": "us-central1"
    "storage_spec": {}
}

test_sql_database_instance_cmk_encryption = lambda {
    _items = sql.get_items({}, _vars_with({"cmkEnabled": True}))
    _instance = [r for r in _items if r.kind == "DatabaseInstance"][0]
    assert _instance.spec.forProvider.encryptionKeyName == _base_vars["cmkKeyName"]
}

test_sql_database_has_charset_and_collation = lambda {
    _items = sql.get_items({}, _base_vars)
    _db = [r for r in _items if r.kind == "Database"][0]
    assert _db.spec.forProvider.charset == "UTF8"
    assert _db.spec.forProvider.collation == "en_US.UTF8"
}

test_sql_database_uses_instance_selector = lambda {
    _items = sql.get_items({}, _base_vars)
    _db = [r for r in _items if r.kind == "Database"][0]
    assert _db.spec.forProvider.instanceSelector.matchLabels == {"dataPlane": "blue"}
}

test_sql_user_uses_instance_selector = lambda {
    _items = sql.get_items({}, _base_vars)
    _user = [r for r in _items if r.kind == "User"][0]
    assert _user.spec.forProvider.instanceSelector.matchLabels == {"dataPlane": "blue"}
}

test_sql_usage_has_namespace = lambda {
    _items = sql.get_items({}, _base_vars)
    _usages = [r for r in _items if r.kind == "Usage"]
    assert len(_usages) == 2
    assert all u in _usages {u.metadata.namespace == "ns"}
}

test_overlay_bucket_has_cmk_encryption = lambda {
    _items = overlay.get_items({}, _vars_with({"cmkEnabled": True}))
    _bucket = [r for r in _items if r.kind == "Bucket"][0]
    assert _bucket.spec.forProvider.encryption.defaultKmsKeyName == _base_vars["cmkKeyName"]
}

test_overlay_bucket_has_versioning = lambda {
    _items = overlay.get_items({}, _base_vars)
    _bucket = [r for r in _items if r.kind == "Bucket"][0]
    assert _bucket.spec.forProvider.versioning.enabled == True
}

test_storage_location_prefers_storage_region = lambda {
    _items = storage.get_items({}, _vars_with({"storage_spec": {"region": "us-east1"}}))
    _bucket = [r for r in _items if r.kind == "Bucket"][0]
    assert _bucket.spec.forProvider.location == "us-east1"
}

test_grant_job_object_has_readiness = lambda {
    _obs_db_ready = {
        "resources": {
            "database-primary-blue": {
                "resource": {
                    "status": {"conditions": [{"type": "Ready", "status": "True"}]}
                }
            }
        }
    }
    _items = k8s.get_items(_obs_db_ready, _base_vars)
    _grant = [r for r in _items if r.metadata.annotations["crossplane.io/composition-resource-name"] == "liferay-db-grant-job-blue"][0]
    assert _grant.spec.readiness.celQuery == "object.status.succeeded == 1"
    assert _grant.spec.readiness.policy == "DeriveFromCelQuery"
}

test_msd_not_created_when_grant_job_not_ready = lambda {
    _items = msd.get_items({"resources": {}}, _base_vars)
    assert len(_items) == 0
}

test_msd_created_when_grant_job_ready = lambda {
    _obs_ready = {
        "resources": {
            "liferay-db-grant-job-blue": {
                "resource": {
                    "status": {"conditions": [{"type": "Ready", "status": "True"}]}
                }
            }
        }
    }
    _items = msd.get_items(_obs_ready, _base_vars)
    assert len(_items) == 1
    _secret_object = _items[0]
    assert _secret_object.spec.forProvider.manifest.stringData.DATABASE_INSTANCE_NAME == "p:us-central1:test-db-instance-blue"
}

test_backup_uses_role_binding_and_service_account = lambda {
    _items = backup.get_items({}, _vars_with({
        "backup": {"enabled": True, "region": "us-west1", "retentionDays": 10, "storageClass": "COLDLINE"}
    }))
    _kinds = [r.spec.forProvider.manifest.kind for r in _items if r.kind == "Object"]
    assert "RoleBinding" in _kinds
    assert "ServiceAccount" in _kinds
}
