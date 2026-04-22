import compositions.security as security

test_security_cmk_enabled = lambda {
    _vars: {str:any} = {
        "baseName": "test-base"
        "cmkEnabled": True
        "cmkKeyName": "projects/p1/locations/r1/keyRings/kr1/cryptoKeys/k1"
        "env": {"projectId": "p1", "projectNumber": "123"}
        "gsaEmail": "gsa@p1.iam.gserviceaccount.com"
        "gsaName": "test-gsa"
        "labels": {"app": "liferay"}
        "managementPolicies": ["*"]
        "namespace": "test-ns"
        "region": "r1"
    }
    _items = security.get_items({}, _vars)
    # GSA(1) + WI(1) + SQL_U(1) + SQL_C(1) + KR(1) + Key(1) + KMS_IAM(2) = 8 items
    assert len(_items) == 8, "Should have 8 items when CMK is enabled"

    _find_by_anno = lambda items: [any], anno: str -> any {
        _matches = [i for i in items if i?.metadata?.annotations?["crossplane.io/composition-resource-name"] == anno]
        _matches[0] if len(_matches) > 0 else None
    }

    assert _find_by_anno(_items, "liferay-keyring") != None
    assert _find_by_anno(_items, "liferay-cryptokey") != None
    assert _find_by_anno(_items, "liferay-sql-kms-binding") != None
    assert _find_by_anno(_items, "liferay-storage-kms-binding") != None
}
