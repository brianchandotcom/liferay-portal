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
	INITIALIZE: 'initalize',
	SET_BASE_PATH_URL: 'setBasePathUrl',
	SET_BASENAME: 'setBasename',
	SET_ERROR: 'setError',
	SET_HISTORY: 'setHistory',
	SET_LOADING: 'setLoading',
	SET_SPRITEMAP: 'setSpritemap',
	UPDATE_BREADCRUMBS: 'updateBreadcrumbs',
};

const updateBreadcrumbs = (dispatch) => (breadcrumbs) =>
	dispatch({
		payload: breadcrumbs,
		type: actionDefinition.UPDATE_BREADCRUMBS,
	});

const setBasePathUrl = (dispatch) => (path) =>
	dispatch({
		payload: path,
		type: actionDefinition.SET_BASE_PATH_URL,
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

const setBasename = (dispatch) => (basename) =>
	dispatch({
		payload: basename,
		type: actionDefinition.SET_BASENAME,
	});

const setHistory = (dispatch) => (basename) =>
	dispatch({
		payload: basename,
		type: actionDefinition.SET_HISTORY,
	});

const initialize = (dispatch) => (settings) =>
	dispatch({
		payload: settings,
		type: actionDefinition.INITIALIZE,
	});

export const actions = {
	initialize,
	setBasePathUrl,
	setBasename,
	setError,
	setHistory,
	setLoading,
	setSpritemap,
	updateBreadcrumbs,
};
