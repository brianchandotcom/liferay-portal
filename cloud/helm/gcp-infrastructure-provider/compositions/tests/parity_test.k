import compositions.backup as backup
import compositions.k8s_resources as k8s
import compositions.managed_service_details as msd
import compositions.overlay as overlay
import compositions.sql as sql
import compositions.storage as storage

# Regression tests for behaviors the legacy go-template composition had that
# were missing in the initial KCL port. cluster_refs_test uses a subset check
# that passes when rendered leaves a field Undefined, so "we stopped setting a
# field" slips through. These tests assert the specific fields exist.

# KCL's `|` deep-merges dicts but errors on conflicting scalar values, so
# overrides go through a dict-comprehension helper instead.
_vars_with = lambda overrides: {str:any} -> {str:any} {
    {k: (overrides[k] if k in overrides else v) for k, v in _base_vars} | {k: v for k, v in overrides if k not in _base_vars}
}

_base_vars: {str:any} = {
    "backupBucketName": "hashed-vault"
    "baseName": "test"
    "bucketName": "hashed-dl"
    "cmkEnabled": False
    "cmkKeyName": "projects/p/locations/us-central1/keyRings/test-keyring/cryptoKeys/test-key"
    "database": {"rootPasswordSecretName": "sql-password"}
    "databaseIamUser": "test-sa@p.iam"
    "databaseInstanceName": "test-db-instance"
    "databaseName": "lportal"
    "databaseObjectName": "test-db"
    "databasePort": "5432"
    "databaseUserObjectName": "test-db-user"
    "env": {"projectId": "p", "vpcName": "v"}
    "forceDestroy": False
    "gsaEmail": "test-sa@p.iam.gserviceaccount.com"
    "hashedName": "hashed"
    "instanceConnectionName": "p:us-west2:test-db-instance"
    "jobName": "test-db-grant"
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

    # The schema calls it encryptionKeyName, not encryptionKmsKeyName; the
    # go-template used the latter but the CRD ignored it.
    assert _instance.spec.forProvider.encryptionKeyName == _base_vars["cmkKeyName"], "DatabaseInstance.forProvider.encryptionKeyName must be set when cmk is enabled."
}

test_sql_database_has_charset_and_collation = lambda {
    _items = sql.get_items({}, _base_vars)
    _db = [r for r in _items if r.kind == "Database"][0]
    assert _db.spec.forProvider.charset == "UTF8", "Database.forProvider.charset must be UTF8."
    assert _db.spec.forProvider.collation == "en_US.UTF8", "Database.forProvider.collation must be en_US.UTF8."
}

test_sql_database_uses_instance_selector = lambda {
    _items = sql.get_items({}, _base_vars)
    _db = [r for r in _items if r.kind == "Database"][0]
    assert _db.spec.forProvider.instanceSelector.matchControllerRef == True, "Database.forProvider.instanceSelector.matchControllerRef must be true."
}

test_sql_user_uses_instance_selector = lambda {
    _items = sql.get_items({}, _base_vars)
    _user = [r for r in _items if r.kind == "User"][0]
    assert _user.spec.forProvider.instanceSelector.matchControllerRef == True, "User.forProvider.instanceSelector.matchControllerRef must be true."
}

test_sql_usage_has_namespace = lambda {
    _items = sql.get_items({}, _base_vars)
    _usages = [r for r in _items if r.kind == "Usage"]
    assert len(_usages) == 2
    assert all u in _usages {u.metadata.namespace == "ns"}, "Usage resources must have metadata.namespace set."
}

test_overlay_bucket_has_cmk_encryption = lambda {
    _items = overlay.get_items({}, _vars_with({"cmkEnabled": True}))
    _bucket = [r for r in _items if r.kind == "Bucket"][0]
    assert _bucket.spec.forProvider.encryption.defaultKmsKeyName == _base_vars["cmkKeyName"], "Overlay bucket must have encryption.defaultKmsKeyName when cmk is enabled."
}

test_overlay_bucket_has_versioning = lambda {
    _items = overlay.get_items({}, _base_vars)
    _bucket = [r for r in _items if r.kind == "Bucket"][0]
    assert _bucket.spec.forProvider.versioning.enabled == True, "Overlay bucket must have versioning.enabled=true."
}

test_storage_location_prefers_storage_region = lambda {
    _items = storage.get_items({}, _vars_with({
        "storage_spec": {"region": "us-east1"}
    }))
    _bucket = [r for r in _items if r.kind == "Bucket"][0]
    assert _bucket.spec.forProvider.location == "us-east1", "Storage bucket location must prefer storage_spec.region over vars.region."
}

test_grant_job_object_has_readiness = lambda {
    _items = k8s.get_items({}, _base_vars)
    _grant = [r for r in _items if r.metadata.annotations["crossplane.io/composition-resource-name"] == "liferay-db-grant-job"][0]
    assert _grant.spec.readiness.celQuery == "object.status.succeeded == 1", "Grant-job Object must have spec.readiness.celQuery detecting job completion."
    assert _grant.spec.readiness.policy == "DeriveFromCelQuery"
}

test_msd_not_created_when_grant_job_not_ready = lambda {
    _obs_not_ready = {
        "resources": {}
    }
    _items = msd.get_items(_obs_not_ready, _base_vars)
    assert len(_items) == 0, "Managed service details secret must not be created until the grant-job is ready."
}

test_msd_created_when_grant_job_ready = lambda {
    _obs_ready = {
        "resources": {
            "liferay-db-grant-job": {
                "resource": {
                    "status": {"conditions": [{"type": "Ready", "status": "True"}]}
                }
            }
        }
    }
    _items = msd.get_items(_obs_ready, _base_vars)
    assert len(_items) == 1
    _secret_object = _items[0]
    assert _secret_object.spec.forProvider.manifest.stringData.DATABASE_INSTANCE_NAME == "p:us-west2:test-db-instance", "DATABASE_INSTANCE_NAME must come from instanceConnectionName, not a hardcoded region."
}

test_backup_uses_role_binding_and_service_account = lambda {
    _items = backup.get_items({}, _vars_with({
        "backup": {"enabled": True, "region": "us-west1", "retentionDays": 10, "storageClass": "COLDLINE"}
    }))
    _kinds = [r.spec.forProvider.manifest.kind for r in _items if r.kind == "Object"]
    assert "RoleBinding" in _kinds, "Backup must create a namespaced RoleBinding, matching the legacy design."
    assert "ServiceAccount" in _kinds, "Backup must create a backup-create-service-account ServiceAccount."
    assert "ClusterRole" not in _kinds, "Backup must not create a ClusterRole; the legacy design assumes it is externally provisioned."
    assert "ClusterRoleBinding" not in _kinds, "Backup must not create a ClusterRoleBinding."
}
