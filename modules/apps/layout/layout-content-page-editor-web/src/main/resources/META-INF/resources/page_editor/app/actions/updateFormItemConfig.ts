/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_FORM_ITEM_CONFIG} from './types';

import type {LayoutData} from '../../types/layout_data/LayoutData';
import type {FragmentEntryLinkMap} from './addFragmentEntryLinks';

export default function updateFormItemConfig({
	addedFragmentEntryLinks = null,
	addedItemIds = [],
	isMapping,
	itemIds,
	layoutData,
	movedItemIds = [],
	removedFragmentEntryLinkIds = [],
	removedItemIds = [],
	restoredFragmentEntryLinkIds = [],
	triggerItemId,
}: {
	addedFragmentEntryLinks?: FragmentEntryLinkMap | null;
	addedItemIds: string[];
	isMapping: boolean;
	itemIds: string[];
	layoutData: LayoutData;
	movedItemIds: {itemId: string; parentId: string}[];
	removedFragmentEntryLinkIds?: string[];
	removedItemIds?: string[];
	restoredFragmentEntryLinkIds?: string[];
	triggerItemId?: string;
}) {
	return {
		addedFragmentEntryLinks,
		addedItemIds,
		isMapping,
		itemIds,
		layoutData,
		movedItemIds,
		removedFragmentEntryLinkIds,
		removedItemIds,
		restoredFragmentEntryLinkIds,
		triggerItemId,
		type: UPDATE_FORM_ITEM_CONFIG,
	} as const;
}
