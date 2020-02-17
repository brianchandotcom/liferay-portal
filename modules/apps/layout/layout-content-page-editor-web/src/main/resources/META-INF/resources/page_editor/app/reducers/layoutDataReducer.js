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

import {
	ADD_FRAGMENT_ENTRY_LINK,
	UPDATE_COL_SIZE,
	UPDATE_LAYOUT_DATA
} from '../actions/types';

export const INITIAL_STATE = {
	items: {}
};

export default function layoutDataReducer(layoutData = INITIAL_STATE, action) {
	switch (action.type) {
		case UPDATE_COL_SIZE: {
			let items = layoutData.items;

			if (action.itemId in items) {
				items = {
					...items,
					[action.itemId]: {
						...items[action.itemId],
						config: {
							...items[action.itemId].config,
							size: action.size
						}
					}
				};

				if (action.nextColumnItemId in items) {
					items = {
						...items,
						[action.nextColumnItemId]: {
							...items[action.nextColumnItemId],
							config: {
								...items[action.nextColumnItemId].config,
								size: action.nextColumnSize
							}
						}
					};
				}
			}

			return {
				...layoutData,
				items
			};
		}

		case UPDATE_LAYOUT_DATA:
		case ADD_FRAGMENT_ENTRY_LINK:
			return action.layoutData;

		default:
			return layoutData;
	}
}
