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

import {fetch} from 'frontend-js-web';

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
	RESET_FORM_DATA: 'resetFormData',
	RESET_PRODUCTS: 'resetProducts',
	SELECT_DETAIL: 'selectDetail',
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

export function getAcceptLanguageHeaderParam() {
	const browserLang = navigator.language || navigator.userLanguage;
	const themeLang = Liferay.ThemeDisplay.getLanguageId().replace('_', '-');

	if (browserLang === themeLang) {
		return browserLang;
	}

	return `${browserLang}, ${themeLang};q=0.8`;
}

export const fetchHeaders = new Headers({
	Accept: 'application/json',
	'Accept-Language': getAcceptLanguageHeaderParam(),
	'Content-Type': 'application/json',
});

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

	return fetch(endpoint + '/' + id)
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

	return fetch(endpoint + (query ? `?q=${query}` : ''))
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
	const {number, position, productId} = formData;

	dispatch({
		type: actionDefinition.SUBMIT_NEW_SPOT_PENDING,
	});

	return fetch(endpoint + '/' + areaId + '/spot', {
		body: JSON.stringify({number, position, productId}),
		headers: fetchHeaders,
		method: 'POST',
	})
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

	return fetch(endpoint + '/' + areaId + '/spot/' + spotId, {
		method: 'DELETE',
	})
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
	const {id, ...data} = formData;

	dispatch({
		type: actionDefinition.SUBMIT_SPOT_CHANGES_PENDING,
	});

	return fetch(endpoint + '/' + areaId + '/spot/' + id, {
		body: JSON.stringify(data),
		headers: fetchHeaders,
		method: 'PUT',
	})
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
	selectSpot,
	submitNewSpot,
	submitSpotChanges,
	unselectSpot,
	updateFormValue,
};

export default actions;
