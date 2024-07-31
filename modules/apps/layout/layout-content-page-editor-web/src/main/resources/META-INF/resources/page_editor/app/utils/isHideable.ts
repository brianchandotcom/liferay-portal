/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData, LayoutDataItem} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {FRAGMENT_ENTRY_TYPES} from '../config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import isRemovable from './isRemovable';

export default function isHideable(
	item: LayoutDataItem,
	fragmentEntryLinks: FragmentEntryLinkMap,
	layoutData: LayoutData
) {
	if (!isRemovable(item, layoutData)) {
		return false;
	}

	if (item.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
		return true;
	}

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	return fragmentEntryLink.fragmentEntryType !== FRAGMENT_ENTRY_TYPES.input;
}
