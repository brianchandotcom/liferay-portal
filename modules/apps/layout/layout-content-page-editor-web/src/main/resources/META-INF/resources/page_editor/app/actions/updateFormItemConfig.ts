/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_FORM_ITEM_CONFIG} from './types';

import type {
	DeletedLayoutDataItem,
	LayoutData,
} from '../../types/layout_data/LayoutData';
import type {FragmentEntryLinkMap} from './addFragmentEntryLinks';

export default function updateFormItemConfig({
	addedFragmentEntryLinks = null,
	deletedItems = [],
	isMapping,
	itemId,
	layoutData,
	removedFragmentEntryLinkIds = [],
	restoredFragmentEntryLinkIds = [],
}: {
	addedFragmentEntryLinks?: FragmentEntryLinkMap | null;
	deletedItems?: DeletedLayoutDataItem[];
	isMapping: boolean;
	itemId: string;
	layoutData: LayoutData;
	removedFragmentEntryLinkIds?: string[];
	restoredFragmentEntryLinkIds?: string[];
}) {
	return {
		addedFragmentEntryLinks,
		deletedItems,
		isMapping,
		itemId,
		layoutData,
		removedFragmentEntryLinkIds,
		restoredFragmentEntryLinkIds,
		type: UPDATE_FORM_ITEM_CONFIG,
	} as const;
}
