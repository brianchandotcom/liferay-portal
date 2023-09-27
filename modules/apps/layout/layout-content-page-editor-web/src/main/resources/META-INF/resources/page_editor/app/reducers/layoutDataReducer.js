/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	DELETE_ITEM,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	UPDATE_COLLECTION_DISPLAY_COLLECTION,
	UPDATE_COL_SIZE,
	UPDATE_FORM_ITEM_CONFIG,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_ITEM_LOCAL_CONFIG,
	UPDATE_PREVIEW_IMAGE,
	UPDATE_ROW_COLUMNS,
} from '../actions/types';
import {setIn} from '../utils/setIn';

export const INITIAL_STATE = {
	items: {},
};

export default function layoutDataReducer(layoutData = INITIAL_STATE, action) {
	switch (action.type) {
		case UPDATE_COL_SIZE:
		case UPDATE_COLLECTION_DISPLAY_COLLECTION:
		case ADD_FRAGMENT_ENTRY_LINKS:
		case ADD_ITEM:
		case DELETE_ITEM:
		case DUPLICATE_ITEM:
		case MOVE_ITEM:
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION:
		case UPDATE_ROW_COLUMNS:
			return action.layoutData;

		case UPDATE_FORM_ITEM_CONFIG:
		case UPDATE_ITEM_CONFIG: {
			const {itemId, layoutData: nextLayoutData} = action;

			const nextItem = nextLayoutData.items[itemId] || {};
			const previousItem = layoutData.items[itemId] || {};

			return {
				...nextLayoutData,
				items: {
					...nextLayoutData.items,
					[itemId]: {
						...nextItem,
						config: {
							...(action.overridePreviousConfig
								? {}
								: previousItem.config),
							...nextItem.config,
						},
					},
				},
			};
		}

		case UPDATE_ITEM_LOCAL_CONFIG: {
			const {itemConfig, itemId} = action;

			const item = layoutData.items[itemId] || {};

			return {
				...layoutData,
				items: {
					...layoutData.items,
					[itemId]: {
						...item,
						config: {
							...(action.overridePreviousConfig
								? {}
								: item.config),
							...itemConfig,
						},
					},
				},
			};
		}

		case UPDATE_PREVIEW_IMAGE: {
			const newItems = Object.fromEntries(
				Object.entries(layoutData.items).map(([key, value]) => {
					const newValue =
						value.config?.styles?.backgroundImage?.classPK ===
						action.fileEntryId
							? setIn(
									value,
									[
										'config',
										'styles',
										'backgroundImage',
										'url',
									],
									action.previewURL
							  )
							: value;

					return [key, newValue];
				})
			);

			return {
				...layoutData,
				items: newItems,
			};
		}

		default:
			return layoutData;
	}
}
