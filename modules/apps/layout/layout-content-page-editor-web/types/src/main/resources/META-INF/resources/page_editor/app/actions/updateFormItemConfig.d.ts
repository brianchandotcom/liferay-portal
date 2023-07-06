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

import type {DeletedLayoutDataItem, LayoutData} from '../../types/LayoutData';
import type {FragmentEntryLink} from './addFragmentEntryLinks';
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
	addedFragmentEntryLinks?: FragmentEntryLink[] | null;
	deletedItems?: DeletedLayoutDataItem[];
	isMapping: boolean;
	itemId: string;
	layoutData: LayoutData;
	overridePreviousConfig?: boolean;
	removedFragmentEntryLinkIds?: string[];
	restoredFragmentEntryLinkIds?: string[];
}): {
	readonly addedFragmentEntryLinks:
		| FragmentEntryLink<string, string>[]
		| null;
	readonly deletedItems: DeletedLayoutDataItem[];
	readonly isMapping: boolean;
	readonly itemId: string;
	readonly layoutData: LayoutData;
	readonly overridePreviousConfig: boolean;
	readonly removedFragmentEntryLinkIds: string[];
	readonly restoredFragmentEntryLinkIds: string[];
	readonly type: 'UPDATE_FORM_ITEM_CONFIG';
};
