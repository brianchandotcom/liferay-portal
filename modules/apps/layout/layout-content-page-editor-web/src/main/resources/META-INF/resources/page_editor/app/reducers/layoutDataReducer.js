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
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

const LAYOUT_DATA_INSERT_INNER = {
	[LAYOUT_DATA_ITEM_TYPES.column]: [LAYOUT_DATA_ITEM_TYPES.row],
	[LAYOUT_DATA_ITEM_TYPES.container]: [LAYOUT_DATA_ITEM_TYPES.root],
	[LAYOUT_DATA_ITEM_TYPES.fragment]: [
		LAYOUT_DATA_ITEM_TYPES.column,
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.root,
		LAYOUT_DATA_ITEM_TYPES.row
	],
	[LAYOUT_DATA_ITEM_TYPES.root]: [],
	[LAYOUT_DATA_ITEM_TYPES.row]: [
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.root
	]
};

function addItem(items, action) {
	const {config, itemId, itemType, siblingId} = action;
	const defaultConfig = LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[itemType];
	const siblingItem = items[siblingId];

	if (itemId in items) {
		return items;
	}

	let currentItem;

	if (LAYOUT_DATA_INSERT_INNER[itemType].includes(siblingItem.type)) {
		currentItem = siblingItem;
	} else {
		currentItem = items[siblingItem.parentId];
	}

	if (process.env.NODE_ENV === 'development') {
		if (!currentItem) {
			console.error(`Parent item "${currentItem.itemId}" does not exist`);
		} else if (
			!LAYOUT_DATA_ALLOWED_PARENT_TYPES[itemType].includes(
				currentItem.type
			)
		) {
			console.error(
				`Item of type "${itemType}" shouldn't be placed inside "${currentItem.type}"`
			);
		}
	}

	const children = [...currentItem.children];
	const {position = currentItem.children.length} = action;
	const siblingIndex = children.findIndex(child => child === siblingId);

	children.splice(siblingIndex + position, 0, itemId);

	return {
		...items,

		[itemId]: {
			children: [],
			config: {
				...defaultConfig,
				...config
			},
			itemId,
			parentId: currentItem.itemId,
			type: itemType
		},

		[currentItem.itemId]: {
			...currentItem,
			children
		}
	};
}

function moveItem(items, action) {
	const {itemId, position, siblingId} = action;

	const item = items[itemId];
	const itemParentId = items[item.parentId];

	if (itemParentId.children.includes(itemId)) {
		const itemIndex = itemParentId.children.findIndex(
			child => child === itemId
		);

		itemParentId.children.splice(itemIndex, 1);
	}

	const siblingItem = items[siblingId];
	let currentItem;

	if (LAYOUT_DATA_INSERT_INNER[item.type].includes(siblingItem.type)) {
		currentItem = siblingItem;
	} else {
		currentItem = items[siblingItem.parentId];
	}

	const children = [...currentItem.children];
	const siblingIndex = children.findIndex(child => child === siblingId);

	children.splice(siblingIndex + position, 0, itemId);

	return {
		...items,

		[itemId]: {
			...item,
			parentId: currentItem.itemId
		},

		[currentItem.itemId]: {
			...currentItem,
			children
		}
	};
}

function removeItem(items, action) {
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
					items: addItem(state.layoutData.items, action)
				}
			};

			break;

		case TYPES.MOVE_ITEM:
			nextState = {
				...state,
				layoutData: {
					...state.layoutData,
					items: moveItem(state.layoutData.items, action)
				}
			};

			break;

		case TYPES.REMOVE_ITEM:
			nextState = {
				...state,
				layoutData: {
					...state.layoutData,
					items: removeItem(state.layoutData.items, action)
				}
			};
			break;

		case TYPES.ADD_FRAGMENT_ENTRY_LINK:
			nextState = {
				...state,
				layoutData: {
					...state.layoutData,
					items: addItem(state.layoutData.items, {
						config: {
							fragmentEntryLinkId:
								action.fragmentEntryLink.fragmentEntryLinkId
						},
						itemId: action.itemId,
						itemType: LAYOUT_DATA_ITEM_TYPES.fragment,
						position: action.position,
						siblingId: action.siblingId
					})
				}
			};
			break;

		default:
			break;
	}

	return nextState;
}
