/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {LayoutData} from '../../types/LayoutData';
import type {FragmentEntryLink} from './addFragmentEntryLinks';
export default function duplicateItem({
	addedFragmentEntryLinks,
	itemId,
	layoutData,
	restrictedItemIds,
}: {
	addedFragmentEntryLinks?: FragmentEntryLink[];
	itemId: string;
	layoutData: LayoutData;
	restrictedItemIds: string[];
}): {
	readonly addedFragmentEntryLinks: FragmentEntryLink<string, string>[];
	readonly itemId: string;
	readonly layoutData: LayoutData;
	readonly restrictedItemIds: string[];
	readonly type: 'DUPLICATE_ITEM';
};
