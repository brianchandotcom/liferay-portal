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

export const actionTypes = {
	UPDATE_ACTIVATION_KEYS: 'UPDATE_ACTIVATION_KEYS',
	UPDATE_ACTIVATION_KEYS_FILTERED_BY_CONDITIONS:
		'UPDATE_ACTIVATION_KEYS_FILTERED_BY_CONDITIONS',
	UPDATE_TO_SERACH_AND_FILTER_KEYS: '	UPDATE_TO_SERACH_AND_FILTER_KEYS',
	UPDATE_WAS_FILTERED: 'UPDATE_WAS_FILTERED',
	UPDATE_WAS_SEARCHED: 'UPDATE_WAS_SEARCHED',
};

const reducer = (state, action) => {
	switch (action.type) {
		case actionTypes.UPDATE_ACTIVATION_KEYS: {
			return {
				...state,
				activationKeys: action.payload,
			};
		}
		case actionTypes.UPDATE_ACTIVATION_KEYS_FILTERED_BY_CONDITIONS: {
			return {
				...state,
				activationKeysFilteredByConditions: action.payload,
			};
		}
		case actionTypes.UPDATE_WAS_FILTERED: {
			return {
				...state,
				wasFiltered: action.payload,
			};
		}
		case actionTypes.UPDATE_WAS_SEARCHED: {
			return {
				...state,
				wasSearched: action.payload,
			};
		}
		case actionTypes.UPDATE_TO_SERACH_AND_FILTER_KEYS: {
			return {
				...state,
				toSearchAndFilterKeys: action.payload,
			};
		}
		default: {
			return state;
		}
	}
};

export default reducer;
