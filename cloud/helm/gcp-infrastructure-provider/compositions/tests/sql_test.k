import compositions.sql as sql

test_sql_orphan_policy = lambda {
    _vars: {str:any} = {
        "database": {
            "deletionProtection": True
            "instanceType": "db-custom-1-3840"
            "storageGB": 20
        }
        "databaseIamUser": "test-user@test.iam"
        "databaseInstanceName": "test-db-instance"
        "databaseName": "lportal"
        "env": {"projectId": "p1", "vpcName": "v1"}
        "labels": {"app": "liferay"}
        "managementPolicies": ["Create", "Observe", "Update"]
        "projectId": "p1"
        "region": "us-central1"
    }
    _items = sql.get_items({}, _vars)
    # Instance + Database + User + 2 Usages = 5 items
    assert len(_items) == 5

    _instance = _items[0]
    assert _instance.spec.managementPolicies == ["Create", "Observe", "Update"]
    # and not deletionProtection
    assert _instance.spec.forProvider.settings.deletionProtectionEnabled == Undefined
}
