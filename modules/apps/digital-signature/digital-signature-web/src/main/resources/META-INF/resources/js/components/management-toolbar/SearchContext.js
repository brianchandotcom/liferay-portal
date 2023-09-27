/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createContext} from 'react';

const reducer = (state, action) => {
	switch (action.type) {
		case 'CHANGE_PAGE':
			return {
				...state,
				page: action.page,
			};
		case 'CHANGE_PAGE_SIZE':
			return {
				...state,
				page: 1,
				pageSize: action.pageSize,
			};
		case 'CLEAR':
			return {
				...state,
				filters: {},
				keywords: '',
			};
		case 'REMOVE_FILTER': {
			const {filterKey} = action;
			const updatedFilters = {...state.filters};

			delete updatedFilters[filterKey];

			return {
				...state,
				filters: updatedFilters,
			};
		}
		case 'SEARCH':
			return {
				...state,
				keywords: action.keywords,
				page: 1,
			};
		case 'SORT':
			return {
				...state,
				sort: action.sort,
			};
		case 'UPDATE_FILTERS_AND_SORT':
			return {
				...state,
				filters: action.filters,
				page: 1,
				sort: action.sort,
			};
		default:
			return state;
	}
};

const SearchContext = createContext();

export {reducer};
export default SearchContext;
