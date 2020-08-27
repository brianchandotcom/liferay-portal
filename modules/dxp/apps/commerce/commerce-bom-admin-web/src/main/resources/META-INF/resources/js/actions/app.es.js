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
	INITIALIZE_APP_DATA: 'initializeAppData',
	SET_ERROR: 'setError',
	SET_LOADING: 'setLoading',
	SET_SPRITEMAP: 'setSpritemap',
	UPDATE_BREADCRUMBS: 'updateBreadcrumbs',
};

const initializeAppData = (dispatch) => (data) =>
	dispatch({
		payload: {
			areaApiUrl: data.areaApiUrl,
			areaId: data.areaId,
			productApiUrl: data.productApiUrl,
			spritemap: data.spritemap,
		},
		type: actionDefinition.INITIALIZE_APP_DATA,
	});

const setError = (dispatch) => (error) =>
	dispatch({
		payload: error,
		type: actionDefinition.SET_ERROR,
	});

const setLoading = (dispatch) => (loading) =>
	dispatch({
		payload: loading,
		type: actionDefinition.SET_LOADING,
	});

const setSpritemap = (dispatch) => (spritemap) =>
	dispatch({
		payload: spritemap,
		type: actionDefinition.SET_SPRITEMAP,
	});

export const actions = {
	initializeAppData,
	setError,
	setLoading,
	setSpritemap,
};

export default actions;
