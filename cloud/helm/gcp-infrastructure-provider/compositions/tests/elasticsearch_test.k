import compositions.elasticsearch as es

test_secret_created_when_green_and_password_present = lambda {
    _vars: {str:any} = {
        "labels": {"app": "liferay"}
        "managementPolicy": "Default"
        "metadata": {"name": "test-liferay"}
        "namespace": "test-ns"
        "searchName": "test-es"
        "searchNamespace": "test-es-ns"
        "searchSecretName": "test-es-secret"
    }
    _obs = {
        "resources": {
            "elasticsearch-observer": {
                "resource": {
                    "status": {
                        "atProvider": {
                            "manifest": {
                                "status": {"health": "green"}
                            }
                        }
                    }
                }
            }
            "elasticsearch-secret-observer": {
                "resource": {
                    "status": {
                        "atProvider": {
                            "manifest": {
                                "data": {"elastic": "dGVzdC1wYXNzd29yZA=="}
                            }
                        }
                    }
                }
            }
        }
    }
    _items = es.get_items(_obs, _vars)
    assert len(_items) == 3, "Should have 3 items (2 observers + 1 connection secret)"
    assert _items[2].metadata.annotations["crossplane.io/composition-resource-name"] == "elasticsearch-connection-details-secret"
}

test_secret_created_when_yellow_and_password_present = lambda {
    _vars: {str:any} = {
        "labels": {"app": "liferay"}
        "managementPolicy": "Default"
        "metadata": {"name": "test-liferay"}
        "namespace": "test-ns"
        "searchName": "test-es"
        "searchNamespace": "test-es-ns"
        "searchSecretName": "test-es-secret"
    }
    _obs = {
        "resources": {
            "elasticsearch-observer": {
                "resource": {
                    "status": {
                        "atProvider": {
                            "manifest": {
                                "status": {"health": "yellow"}
                            }
                        }
                    }
                }
            }
            "elasticsearch-secret-observer": {
                "resource": {
                    "status": {
                        "atProvider": {
                            "manifest": {
                                "data": {"elastic": "dGVzdC1wYXNzd29yZA=="}
                            }
                        }
                    }
                }
            }
        }
    }
    _items = es.get_items(_obs, _vars)
    assert len(_items) == 3, "Should have 3 items (2 observers + 1 connection secret)"
}

test_secret_not_created_when_red = lambda {
    _vars: {str:any} = {
        "labels": {"app": "liferay"}
        "managementPolicy": "Default"
        "metadata": {"name": "test-liferay"}
        "namespace": "test-ns"
        "searchName": "test-es"
        "searchNamespace": "test-es-ns"
        "searchSecretName": "test-es-secret"
    }
    _obs = {
        "resources": {
            "elasticsearch-observer": {
                "resource": {
                    "status": {
                        "atProvider": {
                            "manifest": {
                                "status": {"health": "red"}
                            }
                        }
                    }
                }
            }
            "elasticsearch-secret-observer": {
                "resource": {
                    "status": {
                        "atProvider": {
                            "manifest": {
                                "data": {"elastic": "dGVzdC1wYXNzd29yZA=="}
                            }
                        }
                    }
                }
            }
        }
    }
    _items = es.get_items(_obs, _vars)
    assert len(_items) == 2, "Should only have 2 items (observers only)"
}

test_secret_not_created_when_password_missing = lambda {
    _vars: {str:any} = {
        "labels": {"app": "liferay"}
        "managementPolicy": "Default"
        "metadata": {"name": "test-liferay"}
        "namespace": "test-ns"
        "searchName": "test-es"
        "searchNamespace": "test-es-ns"
        "searchSecretName": "test-es-secret"
    }
    _obs = {
        "resources": {
            "elasticsearch-observer": {
                "resource": {
                    "status": {
                        "atProvider": {
                            "manifest": {
                                "status": {"health": "green"}
                            }
                        }
                    }
                }
            }
            "elasticsearch-secret-observer": {
                "resource": {
                    "status": {
                        "atProvider": {
                            "manifest": {
                                "data": {}
                            }
                        }
                    }
                }
            }
        }
    }
    _items = es.get_items(_obs, _vars)
    assert len(_items) == 2, "Should only have 2 items (observers only)"
}
