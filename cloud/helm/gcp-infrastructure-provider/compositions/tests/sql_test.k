import compositions.sql as sql

test_sql_orphan_policy = lambda {
    _vars: {str:any} = {
        "activeDataPlane": "blue"
        "baseName": "test"
        "database": {
            "deletionProtection": True
            "instanceType": "db-custom-1-3840"
            "storageGB": 20
        }
        "databaseIamUser": "test-user@test.iam"
        "databaseInstanceManagementPolicies": ["Create", "Observe", "Update"]
        "databaseName": "lportal"
        "dataPlanes": ["blue"]
        "env": {"projectId": "p1", "vpcName": "v1"}
        "labels": {"app": "liferay"}
        "managementPolicies": ["Create", "Observe", "Update"]
        "namespace": "ns"
        "projectId": "p1"
        "region": "us-central1"
    }
    _items = sql.get_items({}, _vars)
    assert len(_items) == 5

    _instance = _items[0]
    assert _instance.spec.managementPolicies == ["Create", "Observe", "Update"]
    assert _instance.spec.forProvider.deletionProtection == True
}
