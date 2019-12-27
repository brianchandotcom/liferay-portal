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

import {TYPES} from '../actions/index';

function updateItemConfig(items, action) {
	const {config, itemId} = action;

	let newItems = items;

	if (itemId in items) {
		newItems = {
			...items,
			[itemId]: {
				...items[itemId],
				config: {
					...items[itemId].config,
					...config
				}
			}
		};
	}

	return newItems;
}

export default function layoutDataReducer(state, action) {
	let nextState = state;

	switch (action.type) {
		case TYPES.UPDATE_ITEM_CONFIG:
			nextState = {
				...state,
				layoutData: {
					...state.layoutData,
					items: updateItemConfig(state.layoutData.items, action)
				}
			};
			break;

		case TYPES.UPDATE_LAYOUT_DATA:
		case TYPES.ADD_FRAGMENT_ENTRY_LINK:
			nextState = {
				...state,
				layoutData: action.layoutData
			};
			break;

		default:
			break;
	}

	return nextState;
}
