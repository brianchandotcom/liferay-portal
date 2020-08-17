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
	GET_FOLDER_FULFILLED: 'getFolderFulfilled',
	GET_FOLDER_REJECTED: 'getFolderRejected',
	GET_FOLDER_PENDING: 'getFolderPending',
};

const getFolder = (dispatch) => (endpoint, id) => {
	const url =
		endpoint +
		(id ? `/${id}` : '/0') +
		`?p_auth=${window.Liferay.authToken}`;

	dispatch({
		type: actionDefinition.GET_FOLDER_PENDING,
	});

	return fetch(url)
		.then((response) => response.json())
		.then((data) =>
			dispatch({
				type: actionDefinition.GET_FOLDER_FULFILLED,
				payload: data,
			})
		)
		.catch((err) =>
			dispatch({
				type: actionDefinition.GET_FOLDER_REJECTED,
				payload: err,
			})
		);
};

export const actions = {
	getFolder,
};
