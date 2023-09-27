/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addItem, {PageContent} from '../actions/addItem';
import deleteItem from '../actions/deleteItem';
import {
	ADD_ITEM,
	DELETE_ITEM,
	UPDATE_COLLECTION_DISPLAY_COLLECTION,
	UPDATE_EDITABLE_VALUES,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_PAGE_CONTENTS,
	UPDATE_PREVIEW_IMAGE,
	UPDATE_ROW_COLUMNS,
} from '../actions/types';
import updateCollectionDisplayCollection from '../actions/updateCollectionDisplayCollection';
import updateEditableValues from '../actions/updateEditableValues';
import updateFragmentEntryLinkConfiguration from '../actions/updateFragmentEntryLinkConfiguration';
import updateItemConfig from '../actions/updateItemConfig';
import updatePageContents from '../actions/updatePageContents';
import updatePreviewImage from '../actions/updatePreviewImage';
import updateRowColumns from '../actions/updateRowColumns';

const INITIAL_STATE: PageContent[] = [];

export default function pageContentsReducer(
	pageContents = INITIAL_STATE,
	action: ReturnType<
		| typeof addItem
		| typeof deleteItem
		| typeof updateCollectionDisplayCollection
		| typeof updateEditableValues
		| typeof updateFragmentEntryLinkConfiguration
		| typeof updateItemConfig
		| typeof updatePageContents
		| typeof updatePreviewImage
		| typeof updateRowColumns
	>
) {
	switch (action.type) {
		case ADD_ITEM:
		case DELETE_ITEM:
		case UPDATE_COLLECTION_DISPLAY_COLLECTION:
		case UPDATE_EDITABLE_VALUES:
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION:
		case UPDATE_ITEM_CONFIG:
		case UPDATE_PAGE_CONTENTS:
		case UPDATE_ROW_COLUMNS:
			return [...action.pageContents];

		case UPDATE_PREVIEW_IMAGE: {
			const nextPageContents = pageContents.map((pageContent) => {
				if (pageContent.classPK === action.fileEntryId) {
					return {
						...pageContent,
						actions: {
							...pageContent.actions,
							editImage: {
								...pageContent.actions.editImage,
								previewURL: action.previewURL,
							},
						},
					};
				}
				else {
					return pageContent;
				}
			});

			return nextPageContents;
		}

		default:
			return pageContents;
	}
}
