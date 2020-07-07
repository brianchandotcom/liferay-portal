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

import {actionsDefinition} from '../actions/index';

export const initialState = {
	filters: [],
	onFiltersChange: null,
};

function reducer(state = initialState, action) {
	switch (action.type) {
		case actionsDefinition.UPDATE_FILTER_STATE:
			return {
				...state,
				filters: state.filters.map((element) => ({
					...element,
					...(element.id === action.payload.id ? action.payload : {}),
				})),
			};
		case actionsDefinition.RESET_FILTERS_VALUE:
			return {
				...state,
				filters: state.filters.map((element) => ({
					...element,
					additionalData: null,
					odataFilterString: null,
					resumeCustomLabel: null,
					value: null,
				})),
			};
		default:
			return state;
	}
}

export default reducer;
