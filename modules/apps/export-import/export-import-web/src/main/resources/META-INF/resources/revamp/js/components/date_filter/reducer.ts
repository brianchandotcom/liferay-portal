/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FilterAction, FilterState, FilterType, ModifiedLastType} from './types';
import {mapEditingToFilterValues} from './utils';

export const INITIAL_STATE: FilterState = {
	applied: {filterType: FilterType.All},
	editing: {
		filterType: FilterType.All,
		fromDate: '',
		modifiedLast: ModifiedLastType.H12,
		toDate: '',
	},
	touchedFields: {
		fromDate: false,
		toDate: false,
	},
};

export function filterReducer(
	state: FilterState,
	action: FilterAction
): FilterState {
	switch (action.type) {
		case 'UPDATE_FILTER':
			return {
				...state,
				editing: {...state.editing, ...action.payload},
			};
		case 'UPDATE_TOUCHED':
			return {
				...state,
				touchedFields: {...state.touchedFields, ...action.payload},
			};
		case 'SET_TOUCH_ALL':
			return {
				...state,
				touchedFields: {fromDate: true, toDate: true},
			};
		case 'APPLY':
			return {
				...state,
				applied: mapEditingToFilterValues(state.editing),
			};
		case 'RESET':
			return INITIAL_STATE;
		default:
			return state;
	}
}
