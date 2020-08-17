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
	SET_BASENAME: 'setBasename',
	SET_BASE_PATH_URL: 'setBasePathUrl',
	SET_HISTORY: 'setHistory',
	INITIALIZE: 'initalize',
};

const updateBreadcrumbs = (dispatch) => (breadcrumbs) =>
	dispatch({
		type: actionDefinition.UPDATE_BREADCRUMBS,
		payload: breadcrumbs,
	});

const setBasePathUrl = (dispatch) => (path) =>
	dispatch({
		type: actionDefinition.SET_BASE_PATH_URL,
		payload: path,
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

const setBasename = (dispatch) => (basename) =>
	dispatch({
		type: actionDefinition.SET_BASENAME,
		payload: basename,
	});

const setHistory = (dispatch) => (basename) =>
	dispatch({
		type: actionDefinition.SET_HISTORY,
		payload: basename,
	});

const initialize = (dispatch) => (settings) =>
	dispatch({
		type: actionDefinition.INITIALIZE,
		payload: settings,
	});

export const actions = {
	updateBreadcrumbs,
	setError,
	setLoading,
	setSpritemap,
	setBasename,
	setBasePathUrl,
	setHistory,
	initialize,
};
