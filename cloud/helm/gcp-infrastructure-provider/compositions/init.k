import context_variables as cv

# Emits a composite-state-update item that function-kcl merges onto the XR's
# status. The old go-template composition wrote the same shape via a separate
# LiferayInfrastructure manifest. Consumers (liferay app) poll this flag.
get_items = lambda obs: any, vars: {str:any} -> [any] {
    _grant_conditions = obs?.resources?["liferay-db-grant-job"]?.resource?.status?.conditions or []
    _is_ready = any c in _grant_conditions {c.type == "Ready" and c.status == "True"}
    [
        {
            apiVersion = "gcp.liferay.com/v1alpha1"
            kind = "LiferayInfrastructure"
            status = {managedServiceDetailsReady = _is_ready}
        }
    ]
}

_vars = {}

items = {"items": get_items(cv.obs, _vars)}
