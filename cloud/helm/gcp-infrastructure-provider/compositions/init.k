import context_variables as cv

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
