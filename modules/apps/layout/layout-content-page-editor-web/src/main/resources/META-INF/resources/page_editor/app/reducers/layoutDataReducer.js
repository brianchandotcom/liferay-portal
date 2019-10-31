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
import {LAYOUT_DATA_ALLOWED_PARENT_TYPES} from '../config/constants/layoutDataAllowedParentTypes';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../config/constants/layoutDataItemDefaultConfigurations';

function addItemReducer(items, action) {
	const {config, itemId, itemType, parentId} = action;
	const defaultConfig = LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[itemType];
	const parentItem = items[parentId];

	if (itemId in items) {
		return items;
	}

	if (process.env.NODE_ENV === 'development') {
		if (!parentItem) {
			console.error(`Parent item "${parentId}" does not exist`);
		} else if (
			!LAYOUT_DATA_ALLOWED_PARENT_TYPES[itemType].includes(
				parentItem.type
			)
		) {
			console.error(
				`Item of type "${itemType}" shouldn't be placed inside "${parentItem.type}"`
			);
		}
	}

	const {position = parentItem.children.length} = action;

	return {
		...items,

		[itemId]: {
			children: [],
			config: {
				...defaultConfig,
				...config
			},
			itemId,
			parentId,
			type: itemType
		},

		[parentId]: {
			...parentItem,
			children: [
				...parentItem.children.slice(0, position),
				itemId,
				...parentItem.children.slice(position)
			]
		}
	};
}

function removeItemReducer(items, action) {
	const {itemId} = action;
	let newItems = items;

	if (itemId in items) {
		const item = items[itemId];
		const parentItem = items[item.parentId];

		if (parentItem) {
			newItems = {
				...newItems,

				[parentItem.itemId]: {
					...parentItem,
					children: parentItem.children.filter(id => id !== itemId)
				}
			};
		} else {
			newItems = {...newItems};
		}

		delete newItems[itemId];
	}

	return newItems;
}

export default function layoutDataReducer(state, action) {
	let nextState = state;

	switch (action.type) {
		case TYPES.ADD_ITEM:
			nextState = {
				...state,
				layoutData: {
					...state.layoutData,
					items: addItemReducer(state.layoutData.items, action)
				}
			};

			break;

		case TYPES.REMOVE_ITEM:
			nextState = {
				...state,
				layoutData: {
					...state.layoutData,
					items: removeItemReducer(state.layoutData.items, action)
				}
			};

			break;

		default:
			break;
	}

	return nextState;
}
