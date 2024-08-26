/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export function hasFieldType({
	fieldTypes,
	fragmentEntryLinks,
	itemId,
	layoutData,
	requiredFieldType,
}: {
	fieldTypes?: string[];
	fragmentEntryLinks: FragmentEntryLinkMap;
	itemId: string;
	layoutData: LayoutData;
	requiredFieldType: string;
}): boolean {
	if (fieldTypes) {
		return fieldTypes.includes(requiredFieldType);
	}

	const item = layoutData.items[itemId];

	if (!item || item.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
		return false;
	}

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	if (!fragmentEntryLink) {
		return false;
	}

	return fragmentEntryLink.fieldTypes.includes(requiredFieldType);
}
