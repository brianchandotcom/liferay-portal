export const actionDefinition = {
	UPDATE_BREADCRUMBS: 'updateBreadcrumbs',
	SET_ERROR: 'setError',
	SET_LOADING: 'setLoading',
	SET_SPRITEMAP: 'setSpritemap',
	INITIALIZE_APP_DATA: 'initializeAppData',
};

const initializeAppData = dispatch => data =>
	dispatch({
		type: actionDefinition.INITIALIZE_APP_DATA,
		payload: {
			spritemap: data.spritemap,
			areaApiUrl: data.areaApiUrl,
			productApiUrl: data.productApiUrl,
			areaId: data.areaId,
		},
	});

const setError = dispatch => error =>
	dispatch({
		type: actionDefinition.SET_ERROR,
		payload: error,
	});

const setLoading = dispatch => loading =>
	dispatch({
		type: actionDefinition.SET_LOADING,
		payload: loading,
	});

const setSpritemap = dispatch => spritemap =>
	dispatch({
		type: actionDefinition.SET_SPRITEMAP,
		payload: spritemap,
	});

export const actions = {
	initializeAppData,
	setError,
	setLoading,
	setSpritemap,
};

export default actions;
