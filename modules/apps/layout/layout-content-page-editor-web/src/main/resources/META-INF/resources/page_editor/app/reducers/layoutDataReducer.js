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

function updateColSize(items, action) {
	const {itemId, nextColumnItemId, nextColumnSize, size} = action;

	if (itemId in items) {
		if (nextColumnItemId in items) {
			return {
				...items,
				[itemId]: {
					...items[itemId],
					config: {
						...items[itemId].config,
						size
					}
				},
				[nextColumnItemId]: {
					...items[nextColumnItemId],
					config: {
						...items[nextColumnItemId].config,
						size: nextColumnSize
					}
				}
			};
		}

		return {
			...items,
			[itemId]: {
				...items[itemId],
				config: {
					...items[itemId].config,
					size
				}
			}
		};
	}
}

export default function layoutDataReducer(state, action) {
	switch (action.type) {
		case TYPES.UPDATE_COL_SIZE:
			return {
				...state,
				items: updateColSize(state.items, action)
			};

		case TYPES.UPDATE_LAYOUT_DATA:
		case TYPES.ADD_FRAGMENT_ENTRY_LINK:
			return action.layoutData;

		default:
			break;
	}

	return state;
}
