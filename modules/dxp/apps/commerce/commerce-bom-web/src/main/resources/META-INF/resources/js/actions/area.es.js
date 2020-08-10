export const actionDefinition = {
	HIGHLIGHT_DETAIL: 'highlightDetail',
	SELECT_DETAIL: 'selectDetail',
	GET_AREA_FULFILLED: 'getAreaFulfilled',
	GET_AREA_REJECTED: 'getAreaRejected',
	GET_AREA_PENDING: 'getAreaPending',
};

const highlightDetail = dispatch => (number, showFirstResume = false) =>
	dispatch({
		type: actionDefinition.HIGHLIGHT_DETAIL,
		payload: {
			number,
			showFirstResume,
		},
	});

const select = dispatch => id =>
	dispatch({
		type: actionDefinition.SELECT_DETAIL,
		payload: id,
	});

const getArea = dispatch => (endpoint, id) => {
	const url = endpoint + (id ? `/${id}` : '') + `?p_auth=${window.Liferay.authToken}`;

	dispatch({
		type: actionDefinition.GET_AREA_PENDING,
	});

	return fetch(url)
		.then(response => response.json())
		.then(data =>
			dispatch({
				type: actionDefinition.GET_AREA_FULFILLED,
				payload: data,
			})
		)
		.catch(err =>
			dispatch({
				type: actionDefinition.GET_AREA_REJECTED,
				payload: err,
			})
		);
};

export const actions = {
	getArea,
	highlightDetail,
	select,
};
