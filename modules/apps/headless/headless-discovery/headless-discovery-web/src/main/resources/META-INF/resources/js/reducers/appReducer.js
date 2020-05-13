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
	categories: undefined,
	categoryKey: getSearchParam('category'),
	contentType: undefined,
	filter: getSearchParam('filter') || '',
	method: getSearchParam('method'),
	path: getSearchParam('path'),
	paths: undefined,
	requestBodyData: undefined,
	schemas: undefined,
	showSchemas: getSearchParam('show-schemas') || false,
};

const appStateReducer = (state, action) => {
	switch (action.type) {
		case 'ADD_HEADERS': {
			const {headers} = action;

			return {
				...state,
				headers,
			};
		}
		case 'LOAD_API_RESPONSE': {
			return {
				...state,
				apiResponse: action.response,
				apiURL: action.apiURL,
				contentType: action.contentType,
				requestBodyData: action.data,
			};
		}
		case 'LOAD_CATEGORIES': {
			const {categories} = action;

			return {
				...state,
				categories,
				categoryKey: state.categoryKey || Object.keys(categories)[0],
			};
		}
		case 'LOAD_CATEGORY': {
			const {category} = action;

			const {components, info, paths} = category;

			return {
				...state,
				info,
				method: state.method || undefined,
				paths,
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
