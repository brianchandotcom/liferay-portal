export const actionDefinition = {
    HIGHLIGHT_DETAIL: 'highlightDetail',
    SELECT_DETAIL: 'selectDetail',
    RESET_PRODUCTS: 'resetProducts',
    SELECT_SPOT: 'selectSpot',
    UNSELECT_SPOT: 'unselectSpot',
    CREATE_SPOT: 'createSpot',
    RESET_FORM_DATA: 'resetFormData',
    UPDATE_FORM_VALUE: 'updateFormValue',
    GET_AREA_FULFILLED: 'getAreaFulfilled',
    GET_AREA_REJECTED: 'getAreaRejected',
    GET_AREA_PENDING: 'getAreaPending',
    GET_PRODUCTS_FULFILLED: 'getProductsFulfilled',
    GET_PRODUCTS_REJECTED: 'getProductsRejected',
    GET_PRODUCTS_PENDING: 'getProductsPending',
    SUBMIT_NEW_SPOT_PENDING: 'submitNewSpotPending',
    SUBMIT_NEW_SPOT_FULFILLED: 'submitNewSpotFulfilled',
    SUBMIT_NEW_SPOT_REJECTED: 'submitNewSpotRejected',
    SUBMIT_SPOT_CHANGES_PENDING: 'submitSpotChangesPending',
    SUBMIT_SPOT_CHANGES_FULFILLED: 'submitSpotChangesFulfilled',
    SUBMIT_SPOT_CHANGES_REJECTED: 'submitSpotChangesRejected',
    DELETE_SPOT_PENDING: 'deleteSpotPending',
    DELETE_SPOT_FULFILLED: 'deleteSpotFulfilled',
    DELETE_SPOT_REJECTED: 'deleteSpotRejected',
}

const highlightDetail = dispatch => (number, showFirstResume = false) => dispatch(
    { 
        type: actionDefinition.HIGHLIGHT_DETAIL, 
        payload: {
            number,
            showFirstResume
        }
    }
)

const select = dispatch => id => dispatch(
    { 
        type: actionDefinition.SELECT_DETAIL,
        payload: id
    }
)

const getArea = dispatch => (endpoint, id) => {
    dispatch(
        {
            type: actionDefinition.GET_AREA_PENDING 
        }
    )
    return fetch(endpoint + '/' + id + `?p_auth=${window.Liferay.authToken}`)
        .then(
            response => response.json()
        )
        .then(
            (data) => dispatch({ 
                type: actionDefinition.GET_AREA_FULFILLED,
                payload: data 
            })
        )
        .catch(
            (err) => dispatch({ 
                type: actionDefinition.GET_AREA_REJECTED,
                payload: err 
            })
        )
}

const getProducts = dispatch => (endpoint, query) => {
    dispatch(
        {
            type: actionDefinition.GET_PRODUCTS_PENDING 
        }
    )
    return fetch(endpoint + (query ? `?p_auth=${window.Liferay.authToken}&q=${query}` : '' ))
        .then(
            response => response.json()
        )
        .then(
            (data) => dispatch({ 
                type: actionDefinition.GET_PRODUCTS_FULFILLED,
                payload: data && data.items
            })
        )
        .catch(
            (err) => dispatch({ 
                type: actionDefinition.GET_PRODUCTS_REJECTED,
                payload: err 
            })
        )
}

const resetProducts = dispatch => () => dispatch(
    {
        type: actionDefinition.RESET_PRODUCTS
    }
)

const createSpot = dispatch => position => dispatch(
    {
        type: actionDefinition.CREATE_SPOT,
        payload: position
    }
)

const selectSpot = dispatch => spotId => dispatch(
    {
        type: actionDefinition.SELECT_SPOT,
        payload: spotId
    }
)

const unselectSpot = dispatch => () => dispatch(
    {
        type: actionDefinition.UNSELECT_SPOT
    }
)


const updateFormValue = dispatch => (key, value) => dispatch(
    {
        type: actionDefinition.UPDATE_FORM_VALUE,
        payload: {
            key,
            value
        }
    }
)

const submitNewSpot = dispatch => (
    endpoint,
    areaId,
    formData
) => {
    const {
        query, 
        originalData,
        state,
        changed,
        ...data
    } = formData;

    dispatch(
        {
            type: actionDefinition.SUBMIT_NEW_SPOT_PENDING 
        }
    )

    return fetch(
        endpoint + '/' + areaId + `/spot?p_auth=${window.Liferay.authToken}`,
        {
            method: 'POST',
            body: JSON.stringify(data),
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }
    )
        .then(
            () => {
                dispatch({ 
                    type: actionDefinition.SUBMIT_NEW_SPOT_FULFILLED,
                })
                return getArea(dispatch)(endpoint, areaId)
            }
        )
        .catch(
            (err) => dispatch({ 
                type: actionDefinition.SUBMIT_NEW_SPOT_REJECTED,
                payload: err 
            })
        )
}

const deleteSpot = dispatch => (
        endpoint,
        areaId,
        spotId
    ) => {

    dispatch(
        {
            type: actionDefinition.DELETE_SPOT_PENDING 
        }
    )

    return fetch(
        endpoint + '/' + areaId + '/spot/' + spotId  + `?p_auth=${window.Liferay.authToken}`,
        {
            method: 'DELETE'
        }
    )
        .then(
            () => {
                dispatch({ 
                    type: actionDefinition.DELETE_SPOT_FULFILLED
                })
                return getArea(dispatch)(endpoint, areaId)
            }
        )
        .catch(
            (err) => dispatch({ 
                type: actionDefinition.DELETE_SPOT_REJECTED,
                payload: err 
            })
        )
}

const submitSpotChanges = dispatch => (
        endpoint,
        areaId,
        formData
) => {
    const { 
        query, 
        originalData,
        state,
        changed,
        id,
        ...data
    } = formData;
    
    dispatch(
        {
            type: actionDefinition.SUBMIT_SPOT_CHANGES_PENDING 
        }
    )

    return fetch(
        endpoint + '/' + areaId + '/spot/' + id  + `?p_auth=${window.Liferay.authToken}`,
        {
            method: 'PUT',
            body: JSON.stringify(data),
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }
    )
        .then(
            () => {
                dispatch({ 
                    type: actionDefinition.SUBMIT_SPOT_CHANGES_FULFILLED
                })
                return getArea(dispatch)(endpoint, areaId)
            }
        )
        .catch(
            (err) => dispatch({ 
                type: actionDefinition.SUBMIT_SPOT_CHANGES_REJECTED,
                payload: err 
            })
        )
}

export const actions = {
    getArea,
    highlightDetail,
    select,
    getProducts,
    resetProducts,
    createSpot,
    selectSpot,
    unselectSpot,
    updateFormValue,
    submitNewSpot,
    deleteSpot,
    submitSpotChanges
}

export default actions;
