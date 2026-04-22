import compositions.managed_service_details as msd

_vars: {str:any} = {
    "activeDataPlane": "blue"
    "baseName": "test-infra"
    "databaseIamUser": "test-sa@test.iam"
    "databasePort": "5432"
    "hashedName": "test-hashed"
    "labels": {"app": "liferay"}
    "managedServiceDetailsExternalName": "test-hashed-managed-service-details-secret"
    "managementPolicies": ["*"]
    "namespace": "test-ns"
    "overlayBucketName": "test-hashed-overlay"
    "projectId": "test-project"
    "region": "us-central1"
}

_obs_grant_ready = {
    "resources": {
        "liferay-db-grant-job-blue": {
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
