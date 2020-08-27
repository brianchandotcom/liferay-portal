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

import {actionDefinition as appActionDefinition} from '../actions/app.es';
import {actionDefinition} from '../actions/area.es';

export const initialState = {
	availableProducts: [],
	highlightedDetail: null,
	id: null,
	imageUrl: null,
	mappedProducts: [],
	name: null,
	spotFormData: null,
	spots: [],
};

export default function reducer(state = initialState, action) {
	switch (action.type) {
		case appActionDefinition.INITIALIZE_APP_DATA:
			return {
				...state,
				id: action.payload.areaId,
			};
		case actionDefinition.HIGHLIGHT_DETAIL:
			return {
				...state,
				highlightedDetail: action.payload,
			};
		case actionDefinition.RESET_FORM_DATA:
			return {
				...state,
				spotFormData: null,
			};
		case actionDefinition.SELECT_SPOT: {
			const {productId, ...spotData} = state.spots.reduce(
				(found, spot) => found || (spot.id === action.payload && spot),
				null
			);
			const relatedProduct = state.mappedProducts.reduce(
				(acc, el) => acc || (el.id === productId && el),
				null
			);

			const spotFormData = {
				id: action.payload,
				number: spotData.number,
				position: spotData.position,
				productId: relatedProduct.id,
				query: relatedProduct.name,
			};

			return {
				...state,
				spotFormData: {
					changed: false,
					originalData: spotFormData,
					state: 'edit',
					...spotFormData,
				},
			};
		}
		case actionDefinition.UPDATE_FORM_VALUE: {
			const key = action.payload.key;
			const value = action.payload.value;

			const changed =
				state.spotFormData.state === 'edit'
					? state.spotFormData.originalData[key] !== value
					: true;

			return {
				...state,
				spotFormData: {
					...state.spotFormData,
					changed,
					[key]: value,
				},
			};
		}
		case actionDefinition.UNSELECT_SPOT:
			return {
				...state,
				spotFormData: null,
			};
		case actionDefinition.CREATE_SPOT:
			return {
				...state,
				spotFormData: {
					position: action.payload,
					state: 'create',
				},
			};
		case actionDefinition.GET_AREA_FULFILLED: {
			const {imageUrl, name, products, spots} = action.payload.data;

			return {
				...state,
				imageUrl,
				mappedProducts: products || [],
				name,
				spots: spots || [],
			};
		}
		case actionDefinition.GET_PRODUCTS_FULFILLED:
			return {
				...state,
				availableProducts: action.payload || [],
			};
		case actionDefinition.RESET_PRODUCTS:
			return {
				...state,
				availableProducts: [],
			};
		case actionDefinition.DELETE_SPOT_REJECTED:
		case actionDefinition.SUBMIT_NEW_SPOT_REJECTED:
		case actionDefinition.SUBMIT_SPOT_CHANGES_REJECTED:
			return {
				...state,
				spotFormData: null,
			};
		case actionDefinition.DELETE_SPOT_FULFILLED:
		case actionDefinition.SUBMIT_NEW_SPOT_FULFILLED:
		case actionDefinition.SUBMIT_SPOT_CHANGES_FULFILLED:
			return {
				...state,
				spotFormData: null,
			};
		default:
			return state;
	}
}
