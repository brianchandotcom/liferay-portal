/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {getSearchParam} from '../util/params';

const initialState = {
	apiResponse: undefined,
	apiURL: '',
	categoryKey: getSearchParam('category'),
	categories: undefined,
	contentType: undefined,
	filter: getSearchParam('filter') || '',
	path: getSearchParam('path'),
	paths: undefined,
	method: getSearchParam('method'),
	requestBodyData: undefined,
	schemas: undefined,
	showSchemas: getSearchParam('show-schemas') || false,
};

const appStateReducer = (state, action) => {
	switch (action.type) {
		case 'LOAD_API_RESPONSE': {
			return {
				...state,
				apiURL: action.apiURL,
				apiResponse: action.response,
				contentType: action.contentType,
				requestBodyData: action.data,
			};
		}
		case 'LOAD_CATEGORIES': {
			const {categories} = action;

			return {
				...state,
				categoryKey: state.categoryKey || Object.keys(categories)[0],
				categories,
			};
		}
		case 'LOAD_CATEGORY': {
			const {category} = action;

			const {components, paths} = category;

			return {
				...state,
				paths,
				method: state.method || undefined,
				schemas: components.schemas,
			};
		}
		case 'SELECT_CATEGORY': {
			return {
				...state,
				apiResponse: undefined,
				categoryKey: action.categoryKey,
				contentType: undefined,
				method: undefined,
				path: undefined,
				requestBodyData: undefined,
			};
		}
		case 'SELECT_METHOD': {
			return {
				...state,
				apiResponse: undefined,
				contentType: undefined,
				method: action.method,
				requestBodyData: undefined,
			};
		}
		case 'SELECT_PATH': {
			const {path} = action;

			return {
				...state,
				apiResponse: undefined,
				contentType: undefined,
				method: Object.keys(state.paths[path])[0],
				path,
				requestBodyData: undefined,
			};
		}
		case 'SET_API_URL': {
			return {
				...state,
				apiURL: action.url,
			};
		}
		case 'SET_FILTER': {
			return {
				...state,
				filter: action.filter,
			};
		}
		case 'TOGGLE_SCHEMAS': {
			return {
				...state,
				showSchemas: !state.showSchemas,
			};
		}
		default:
			return state;
	}
};

export {initialState};

export default appStateReducer;
