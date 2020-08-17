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
	UPDATE_BREADCRUMBS: 'updateBreadcrumbs',
	SET_ERROR: 'setError',
	SET_LOADING: 'setLoading',
	SET_SPRITEMAP: 'setSpritemap',
	INITIALIZE_APP_DATA: 'initializeAppData',
};

const initializeAppData = (dispatch) => (data) =>
	dispatch({
		type: actionDefinition.INITIALIZE_APP_DATA,
		payload: {
			spritemap: data.spritemap,
			areaApiUrl: data.areaApiUrl,
			productApiUrl: data.productApiUrl,
			areaId: data.areaId,
		},
	});

const setError = (dispatch) => (error) =>
	dispatch({
		type: actionDefinition.SET_ERROR,
		payload: error,
	});

const setLoading = (dispatch) => (loading) =>
	dispatch({
		type: actionDefinition.SET_LOADING,
		payload: loading,
	});

const setSpritemap = (dispatch) => (spritemap) =>
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
