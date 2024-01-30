/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';
import addFragmentEntryLinks from '../actions/addFragmentEntryLinks';
import addItem from '../actions/addItem';
import addRule from '../actions/addRule';
import deleteItem from '../actions/deleteItem';
import deleteRule from '../actions/deleteRule';
import duplicateItem from '../actions/duplicateItem';
import moveItem from '../actions/moveItem';
import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	ADD_RULE,
	DELETE_ITEM,
	DELETE_RULE,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	UPDATE_COLLECTION_DISPLAY_COLLECTION,
	UPDATE_COL_SIZE,
	UPDATE_FORM_ITEM_CONFIG,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_PREVIEW_IMAGE,
	UPDATE_ROW_COLUMNS,
	UPDATE_RULE,
} from '../actions/types';
import updateColSize from '../actions/updateColSize';
import updateCollectionDisplayCollection from '../actions/updateCollectionDisplayCollection';
import updateFormItemConfig from '../actions/updateFormItemConfig';
import updateFragmentEntryLinkConfiguration from '../actions/updateFragmentEntryLinkConfiguration';
import updateItemConfig from '../actions/updateItemConfig';
import updatePreviewImage from '../actions/updatePreviewImage';
import updateRowColumns from '../actions/updateRowColumns';
import updateRule from '../actions/updateRule';
import {setIn} from '../utils/setIn';

export const INITIAL_STATE: LayoutData = {
	deletedItems: [],
	items: {},
	pageRules: [],
	rootItems: {dropZone: '', main: ''},
	version: '0',
};

export default function layoutDataReducer(
	layoutData = INITIAL_STATE,
	action:
		| ReturnType<typeof addFragmentEntryLinks>
		| ReturnType<typeof addItem>
		| ReturnType<typeof addRule>
		| ReturnType<typeof deleteItem>
		| ReturnType<typeof deleteRule>
		| ReturnType<typeof duplicateItem>
		| ReturnType<typeof moveItem>
		| ReturnType<typeof updateCollectionDisplayCollection>
		| ReturnType<typeof updateColSize>
		| ReturnType<typeof updateFormItemConfig>
		| ReturnType<typeof updateFragmentEntryLinkConfiguration>
		| ReturnType<typeof updateItemConfig>
		| ReturnType<typeof updatePreviewImage>
		| ReturnType<typeof updateRowColumns>
		| ReturnType<typeof updateRule>
): LayoutData {
	switch (action.type) {
		case ADD_FRAGMENT_ENTRY_LINKS:
		case ADD_ITEM:
		case ADD_RULE:
		case DELETE_ITEM:
		case DELETE_RULE:
		case DUPLICATE_ITEM:
		case MOVE_ITEM:
		case UPDATE_COL_SIZE:
		case UPDATE_COLLECTION_DISPLAY_COLLECTION:
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION:
		case UPDATE_ROW_COLUMNS:
		case UPDATE_RULE:
			return action.layoutData;

		case UPDATE_FORM_ITEM_CONFIG:
		case UPDATE_ITEM_CONFIG: {
			const {itemId, layoutData: nextLayoutData} = action;

			const nextItem = nextLayoutData.items[itemId] || {};
			const previousItem = layoutData.items[itemId] || {};

			return setIn(nextLayoutData, ['items', itemId, 'config'], {
				...(action.overridePreviousConfig ? {} : previousItem.config),
				...nextItem.config,
			});
		}

		case UPDATE_PREVIEW_IMAGE: {
			const newItems = Object.fromEntries(
				Object.entries(layoutData.items).map(([itemId, item]) => {
					if (!('styles' in item.config) || !item.config.styles) {
						return [itemId, item];
					}

					if (
						item.config.styles.backgroundImage?.classPK !==
						action.fileEntryId
					) {
						return [itemId, item];
					}

					const nextItem = setIn(
						item,
						['config', 'styles', 'backgroundImage', 'url'],
						action.previewURL
					);

					return [itemId, nextItem];
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
