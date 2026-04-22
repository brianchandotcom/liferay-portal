import compositions.managed_service_details as msd

_vars: {str:any} = {
    "baseName": "test-base"
    "databaseIamUser": "test-sa@test.iam"
    "databasePort": "5432"
    "gsaEmail": "test-sa@test.iam.gserviceaccount.com"
    "instanceConnectionName": "test:us-central1:test-base-db-instance"
    "labels": {"app": "liferay"}
    "managementPolicies": ["*"]
    "metadata": {"name": "test-infra"}
    "namespace": "test-ns"
}

_obs_grant_ready = {
    "resources": {
        "liferay-db-grant-job": {
            "resource": {
                "status": {
                    "conditions": [{"type": "Ready", "status": "True"}]
                }
            }
        }
    }
}

test_secret_created_when_ready = lambda {
    _items = msd.get_items(_obs_grant_ready, _vars)
    assert len(_items) == 1, "Secret should be created when grant-job is Ready"
    assert _items[0].metadata.annotations["crossplane.io/composition-resource-name"] == "managed-service-details-secret"
}
