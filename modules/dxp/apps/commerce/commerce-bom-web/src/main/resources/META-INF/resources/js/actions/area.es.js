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
	HIGHLIGHT_DETAIL: 'highlightDetail',
	SELECT_DETAIL: 'selectDetail',
	GET_AREA_FULFILLED: 'getAreaFulfilled',
	GET_AREA_REJECTED: 'getAreaRejected',
	GET_AREA_PENDING: 'getAreaPending',
};

const highlightDetail = (dispatch) => (number, showFirstResume = false) =>
	dispatch({
		type: actionDefinition.HIGHLIGHT_DETAIL,
		payload: {
			number,
			showFirstResume,
		},
	});

const select = (dispatch) => (id) =>
	dispatch({
		type: actionDefinition.SELECT_DETAIL,
		payload: id,
	});

const getArea = (dispatch) => (endpoint, id) => {
	const url =
		endpoint + (id ? `/${id}` : '') + `?p_auth=${window.Liferay.authToken}`;

	dispatch({
		type: actionDefinition.GET_AREA_PENDING,
	});

	return fetch(url)
		.then((response) => response.json())
		.then((data) =>
			dispatch({
				type: actionDefinition.GET_AREA_FULFILLED,
				payload: data,
			})
		)
		.catch((err) =>
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
