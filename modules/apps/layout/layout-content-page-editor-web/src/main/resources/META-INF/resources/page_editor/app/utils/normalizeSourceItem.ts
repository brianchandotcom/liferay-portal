/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FragmentLayoutDataItem} from '../../types/layout_data/FragmentLayoutDataItem';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import selectLayoutDataItemLabel from '../selectors/selectLayoutDataItemLabel';
import isItemWidget from './isItemWidget';

import type {LayoutData} from '../../types/layout_data/LayoutData';

export default function normalizeSourceItem(
	item: FragmentLayoutDataItem,
	layoutData: LayoutData,
	fragmentEntryLinks: FragmentEntryLinkMap
) {
	const isWidget = isItemWidget(item, fragmentEntryLinks);
	const name = selectLayoutDataItemLabel(
		{
			fragmentEntryLinks,
			layoutData,
		},
		item
	);

	let fragmentEntryLink = null;

	if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
		fragmentEntryLink =
			fragmentEntryLinks[item.config?.fragmentEntryLinkId];
	}

	return {
		...item,
		fieldTypes: fragmentEntryLink?.fieldTypes ?? [],
		fragmentEntryType: fragmentEntryLink?.fragmentEntryType ?? null,
		isWidget,
		name,
	};
}
