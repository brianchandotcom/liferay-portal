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

import {createContext} from 'react';

const reducer = (state, action) => {
	switch (action.type) {
		case 'CHANGE_PAGE':
			return {
				...state,
				page: action.page
			};
		case 'CHANGE_PAGE_SIZE':
			return {
				...state,
				page: 1,
				pageSize: action.pageSize
			};
		case 'SEARCH':
			return {
				...state,
				keywords: action.keywords,
				page: 1
			};
		case 'SORT':
			return {
				...state,
				sort: action.sort
			};
		default:
			return state;
	}
};

const SearchContext = createContext();

export {reducer};
export default SearchContext;
