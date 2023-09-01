/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {DeletedLayoutDataItem, LayoutData} from '../../types/LayoutData';
import type {FragmentEntryLinkMap} from './addFragmentEntryLinks';
export default function updateFormItemConfig({
	addedFragmentEntryLinks,
	deletedItems,
	isMapping,
	itemId,
	layoutData,
	overridePreviousConfig,
	removedFragmentEntryLinkIds,
	restoredFragmentEntryLinkIds,
}: {
	addedFragmentEntryLinks?: FragmentEntryLinkMap | null;
	deletedItems?: DeletedLayoutDataItem[];
	isMapping: boolean;
	itemId: string;
	layoutData: LayoutData;
	overridePreviousConfig?: boolean;
	removedFragmentEntryLinkIds?: string[];
	restoredFragmentEntryLinkIds?: string[];
}): {
	readonly addedFragmentEntryLinks: FragmentEntryLinkMap | null;
	readonly deletedItems: DeletedLayoutDataItem[];
	readonly isMapping: boolean;
	readonly itemId: string;
	readonly layoutData: LayoutData;
	readonly overridePreviousConfig: boolean;
	readonly removedFragmentEntryLinkIds: string[];
	readonly restoredFragmentEntryLinkIds: string[];
	readonly type: 'UPDATE_FORM_ITEM_CONFIG';
};
