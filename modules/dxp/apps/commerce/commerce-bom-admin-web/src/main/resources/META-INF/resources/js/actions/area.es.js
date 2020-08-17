/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export const actionDefinition = {
	CREATE_SPOT: 'createSpot',
	DELETE_SPOT_FULFILLED: 'deleteSpotFulfilled',
	DELETE_SPOT_PENDING: 'deleteSpotPending',
	DELETE_SPOT_REJECTED: 'deleteSpotRejected',
	GET_AREA_FULFILLED: 'getAreaFulfilled',
	GET_AREA_PENDING: 'getAreaPending',
	GET_AREA_REJECTED: 'getAreaRejected',
	GET_PRODUCTS_FULFILLED: 'getProductsFulfilled',
	GET_PRODUCTS_PENDING: 'getProductsPending',
	GET_PRODUCTS_REJECTED: 'getProductsRejected',
	HIGHLIGHT_DETAIL: 'highlightDetail',
	SELECT_DETAIL: 'selectDetail',
	RESET_FORM_DATA: 'resetFormData',
	RESET_PRODUCTS: 'resetProducts',
	SELECT_SPOT: 'selectSpot',
	SUBMIT_NEW_SPOT_FULFILLED: 'submitNewSpotFulfilled',
	SUBMIT_NEW_SPOT_PENDING: 'submitNewSpotPending',
	SUBMIT_NEW_SPOT_REJECTED: 'submitNewSpotRejected',
	SUBMIT_SPOT_CHANGES_FULFILLED: 'submitSpotChangesFulfilled',
	SUBMIT_SPOT_CHANGES_PENDING: 'submitSpotChangesPending',
	SUBMIT_SPOT_CHANGES_REJECTED: 'submitSpotChangesRejected',
	UNSELECT_SPOT: 'unselectSpot',
	UPDATE_FORM_VALUE: 'updateFormValue',
};

const highlightDetail = (dispatch) => (number, showFirstResume = false) =>
	dispatch({
		payload: {
			number,
			showFirstResume,
		},
		type: actionDefinition.HIGHLIGHT_DETAIL,
	});

const select = (dispatch) => (id) =>
	dispatch({
		payload: id,
		type: actionDefinition.SELECT_DETAIL,
	});

const getArea = (dispatch) => (endpoint, id) => {
	dispatch({
		type: actionDefinition.GET_AREA_PENDING,
	});

	return fetch(endpoint + '/' + id + `?p_auth=${window.Liferay.authToken}`)
		.then((response) => response.json())
		.then((data) =>
			dispatch({
				payload: data,
				type: actionDefinition.GET_AREA_FULFILLED,
			})
		)
		.catch((err) =>
			dispatch({
				payload: err,
				type: actionDefinition.GET_AREA_REJECTED,
			})
		);
};

const getProducts = (dispatch) => (endpoint, query) => {
	dispatch({
		type: actionDefinition.GET_PRODUCTS_PENDING,
	});

	return fetch(
		endpoint +
			(query ? `?p_auth=${window.Liferay.authToken}&q=${query}` : '')
	)
		.then((response) => response.json())
		.then((data) =>
			dispatch({
				payload: data && data.items,
				type: actionDefinition.GET_PRODUCTS_FULFILLED,
			})
		)
		.catch((err) =>
			dispatch({
				payload: err,
				type: actionDefinition.GET_PRODUCTS_REJECTED,
			})
		);
};

const resetProducts = (dispatch) => () =>
	dispatch({
		type: actionDefinition.RESET_PRODUCTS,
	});

const createSpot = (dispatch) => (position) =>
	dispatch({
		payload: position,
		type: actionDefinition.CREATE_SPOT,
	});

const selectSpot = (dispatch) => (spotId) =>
	dispatch({
		payload: spotId,
		type: actionDefinition.SELECT_SPOT,
	});

const unselectSpot = (dispatch) => () =>
	dispatch({
		type: actionDefinition.UNSELECT_SPOT,
	});

const updateFormValue = (dispatch) => (key, value) =>
	dispatch({
		payload: {
			key,
			value,
		},
		type: actionDefinition.UPDATE_FORM_VALUE,
	});

const submitNewSpot = (dispatch) => (endpoint, areaId, formData) => {
	const {changed, originalData, query, state, ...data} = formData;

	dispatch({
		type: actionDefinition.SUBMIT_NEW_SPOT_PENDING,
	});

	return fetch(
		endpoint + '/' + areaId + `/spot?p_auth=${window.Liferay.authToken}`,
		{
			method: 'POST',
			body: JSON.stringify(data),
			headers: new Headers({
				'Content-Type': 'application/json',
			}),
		}
	)
		.then(() => {
			dispatch({
				type: actionDefinition.SUBMIT_NEW_SPOT_FULFILLED,
			});

			return getArea(dispatch)(endpoint, areaId);
		})
		.catch((err) =>
			dispatch({
				payload: err,
				type: actionDefinition.SUBMIT_NEW_SPOT_REJECTED,
			})
		);
};

const deleteSpot = (dispatch) => (endpoint, areaId, spotId) => {
	dispatch({
		type: actionDefinition.DELETE_SPOT_PENDING,
	});

	return fetch(
		endpoint +
			'/' +
			areaId +
			'/spot/' +
			spotId +
			`?p_auth=${window.Liferay.authToken}`,
		{
			method: 'DELETE',
		}
	)
		.then(() => {
			dispatch({
				type: actionDefinition.DELETE_SPOT_FULFILLED,
			});

			return getArea(dispatch)(endpoint, areaId);
		})
		.catch((err) =>
			dispatch({
				payload: err,
				type: actionDefinition.DELETE_SPOT_REJECTED,
			})
		);
};

const submitSpotChanges = (dispatch) => (endpoint, areaId, formData) => {
	const {changed, id, originalData, query, state, ...data} = formData;

	dispatch({
		type: actionDefinition.SUBMIT_SPOT_CHANGES_PENDING,
	});

	return fetch(
		endpoint +
			'/' +
			areaId +
			'/spot/' +
			id +
			`?p_auth=${window.Liferay.authToken}`,
		{
			method: 'PUT',
			body: JSON.stringify(data),
			headers: new Headers({
				'Content-Type': 'application/json',
			}),
		}
	)
		.then(() => {
			dispatch({
				type: actionDefinition.SUBMIT_SPOT_CHANGES_FULFILLED,
			});

			return getArea(dispatch)(endpoint, areaId);
		})
		.catch((err) =>
			dispatch({
				payload: err,
				type: actionDefinition.SUBMIT_SPOT_CHANGES_REJECTED,
			})
		);
};

export const actions = {
	createSpot,
	deleteSpot,
	getArea,
	getProducts,
	highlightDetail,
	resetProducts,
	select,
	submitNewSpot,
	selectSpot,
	submitSpotChanges,
	unselectSpot,
	updateFormValue,
};

export default actions;
