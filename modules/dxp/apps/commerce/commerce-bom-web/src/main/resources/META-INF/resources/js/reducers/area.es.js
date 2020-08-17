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

import {actionDefinition} from '../actions/area.es';

export const initialState = {
	highlightedDetail: null,
	imageUrl: null,
	name: null,
	products: [],
	spots: [],
};

export default function reducer(state = initialState, action) {
	switch (action.type) {
		case actionDefinition.HIGHLIGHT_DETAIL:
			return {
				...state,
				highlightedDetail: action.payload,
			};
		case actionDefinition.GET_AREA_FULFILLED:
			return {
				...state,
				imageUrl: action.payload.data.imageUrl,
				name: action.payload.data.name,
				products: action.payload.data.products
					? action.payload.data.products
					: [],
				spots: action.payload.data.spots
					? action.payload.data.spots
					: [],
			};
		default:
			return state;
	}
}
