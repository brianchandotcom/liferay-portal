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
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../config/constants/layoutDataItemDefaultConfigurations';

function updateColumnsSize({items, newNumberOfColumns, rowItem}) {
	const MAX_COLUMNS = 12;
	const columnsSize = Math.floor(MAX_COLUMNS / newNumberOfColumns);

	const newItems = items;
	if (rowItem.children.length > 0) {
		rowItem.children.forEach((child, columnIndex) => {
			const columnConfig = newItems[child].config;
			if (columnConfig) {
				let newColumnSize = columnsSize;
				const middleColumnPosition =
					Math.ceil(newNumberOfColumns / 2) - 1;

				if (middleColumnPosition === columnIndex) {
					newColumnSize =
						MAX_COLUMNS - (newNumberOfColumns - 1) * columnsSize;
				}

				columnConfig['size'] = newColumnSize;
			}
		});
	}

	return newItems;
}

function createColumn(items, action) {
	const {config, itemId, itemType, newNumberOfColumns, rowItemId} = action;
	const defaultConfig = LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[itemType];

	if (itemId in items[rowItemId].children) {
		return items;
	}

	let newItems = items;

	const rowItem = newItems[rowItemId];

	newItems = {
		...newItems,
		[itemId]: {
			children: [],
			config: {
				...defaultConfig,
				...config
			},
			itemId,
			parentId: rowItemId,
			type: itemType
		},

		[rowItemId]: {
			...rowItem,
			children: [...rowItem.children, itemId]
		}
	};

	newItems = updateColumnsSize({
		items: newItems,
		newNumberOfColumns,
		rowItem
	});

	return newItems;
}

function removeColumn(items, action) {
	const {newNumberOfColumns, rowItemId} = action;

	let newItems = items;

	if (rowItemId in items) {
		const rowItem = items[rowItemId];

		const children = rowItem.children;
		const columnItemIdToBeRemoved = children.pop();

		if (Array.isArray(children) && children.length) {
			newItems = {
				...newItems,
				[rowItemId]: {
					...rowItem,
					children
				}
			};

			newItems = updateColumnsSize({
				items: newItems,
				newNumberOfColumns,
				rowItem
			});
		} else {
			newItems = {...newItems};
		}

		delete newItems[columnItemIdToBeRemoved];
	}

	return newItems;
}

export default function layoutDataReducer(state, action) {
	switch (action.type) {
		case TYPES.UPDATE_ITEM_CONFIG:
			return {
				...state,
				items: {
					...state.items,
					[action.itemId]: {
						...state.items[action.itemId],
						config: {
							...state.items[action.itemId].config,
							...action.config
						}
					}
				}
			};

		case TYPES.UPDATE_LAYOUT_DATA:
		case TYPES.ADD_FRAGMENT_ENTRY_LINK:
			return action.layoutData;

		case TYPES.CREATE_COLUMN:
			return {
				...state,
				layoutData: {
					...state.layoutData,
					items: createColumn(state.layoutData.items, {
						itemId: action.itemId,
						itemType: action.itemType,
						newNumberOfColumns: action.newNumberOfColumns,
						rowItemId: action.rowItemId
					})
				}
			};

		case TYPES.REMOVE_COLUMN:
			return {
				...state,
				layoutData: {
					...state.layoutData,
					items: removeColumn(state.layoutData.items, {
						newNumberOfColumns: action.newNumberOfColumns,
						rowItemId: action.rowItemId
					})
				}
			};

		default:
			break;
	}

	return state;
}
