
import { actions as appActions } from '../actions/app.es'

const applyMiddleware = dispatch => action => {
    if(action.type.indexOf('Fulfilled') > -1) {
        appActions.setError(dispatch)(null)
        appActions.setLoading(dispatch)(false)
        appActions.updateBreadcrumbs(dispatch)(action.payload.breadcrumbs)
    }
    if(action.type.indexOf('Pending') > -1) {
        appActions.setLoading(dispatch)(true)
    }
    if(action.type.indexOf('Rejected') > -1) {
        appActions.setError(dispatch)(action.payload.message)
        appActions.setLoading(dispatch)(false)
    }
    dispatch(action)
}

export default applyMiddleware