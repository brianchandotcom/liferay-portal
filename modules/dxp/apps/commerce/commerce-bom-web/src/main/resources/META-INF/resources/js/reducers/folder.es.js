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

import {actionDefinition} from '../actions/folder.es';

export const initialState = {
	brands: null,
	items: null,
	loading: null,
};

export default function reducer(state = initialState, action) {
	switch (action.type) {
		case actionDefinition.GET_FOLDER_FULFILLED:
			return {
				...state,
				brands: action.payload.data.brands,
				items: action.payload.data.items,
				loading: false,
			};
		case actionDefinition.GET_FOLDER_PENDING:
			return {
				...state,
				loading: true,
			};
		default:
			return state;
	}
}
