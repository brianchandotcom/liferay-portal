import compositions.backup as backup

test_backup_enabled = lambda {
    _vars: {str:any} = {
        "backup": {
            "enabled": True
            "region": "us-west1"
            "retentionDays": 10
            "storageClass": "COLDLINE"
        }
        "backupBucketName": "test-liferay-123-vault"
        "baseName": "test-liferay-123"
        "cmkEnabled": True
        "cmkKeyName": "projects/p1/locations/global/keyRings/kr1/cryptoKeys/k1"
        "forceDestroy": False
        "gsaEmail": "test-sa@p1.iam.gserviceaccount.com"
        "labels": {"app": "liferay"}
        "managementPolicies": ["*"]
        "managementPolicy": "Default"
        "metadata": {"name": "test-infra"}
        "namespace": "test-ns"
        "region": "us-central1"
    }
    _items = backup.get_items({}, _vars)
    # 1 Bucket + 1 IAM + 1 SA Object + 1 RB Object = 4 items
    assert len(_items) == 4, "Should have 4 items: Bucket, IAM, SA, RoleBinding"

    _bucket = _items[0]
    assert _bucket.metadata.name == "test-liferay-123-vault"
    assert _bucket.spec.forProvider.location == "us-west1"
    assert _bucket.spec.forProvider.storageClass == "COLDLINE"
    assert _bucket.spec.forProvider.lifecycleRule[0].condition.age == 12
    assert _bucket.spec.forProvider.encryption.defaultKmsKeyName == _vars["cmkKeyName"]
}

test_backup_disabled = lambda {
    _vars: {str:any} = {
        "backup": {"enabled": False}
    }
    _items = backup.get_items({}, _vars)
    assert len(_items) == 0, "Should have 0 items when disabled"
}
