/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

import type {
	LayoutData,
	LayoutDataItem,
} from '../../types/layout_data/LayoutData';
import type {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';

export default function selectLayoutDataItemLabel(
	{
		fragmentEntryLinks,
		layoutData,
	}: {
		fragmentEntryLinks: FragmentEntryLinkMap;
		layoutData: LayoutData;
	},
	item: LayoutDataItem,
	{useCustomName = true} = {}
) {
	if (useCustomName && item.config?.name) {
		return item.config.name;
	}

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
		fragmentEntryLinks[item.config?.fragmentEntryLinkId]?.name
	) {
		return fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
	}

	if (item.type === LAYOUT_DATA_ITEM_TYPES.formStep) {
		const parent = layoutData.items?.[item.parentId];

		if (parent) {
			const index = parent.children.indexOf(item.itemId);

			return sub(Liferay.Language.get('step-x'), [index + 1]);
		}
	}

	return (
		LAYOUT_DATA_ITEM_TYPE_LABELS[item.type] ||
		item.type ||
		Liferay.Language.get('element')
	);
}
