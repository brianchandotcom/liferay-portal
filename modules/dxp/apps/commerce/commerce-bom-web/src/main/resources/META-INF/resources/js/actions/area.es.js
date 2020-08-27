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
	GET_AREA_FULFILLED: 'getAreaFulfilled',
	GET_AREA_PENDING: 'getAreaPending',
	GET_AREA_REJECTED: 'getAreaRejected',
	HIGHLIGHT_DETAIL: 'highlightDetail',
	SELECT_DETAIL: 'selectDetail',
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
	const url = endpoint + (id ? `/${id}` : '');

	dispatch({
		type: actionDefinition.GET_AREA_PENDING,
	});

	return fetch(url)
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

export const actions = {
	getArea,
	highlightDetail,
	select,
};
