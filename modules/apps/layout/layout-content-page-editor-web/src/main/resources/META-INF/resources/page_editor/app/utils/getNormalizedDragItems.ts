/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FragmentLayoutDataItem} from '../../types/layout_data/FragmentLayoutDataItem';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import normalizeSourceItem from './normalizeSourceItem';

import type {LayoutData} from '../../types/layout_data/LayoutData';

type SourceItem = FragmentLayoutDataItem & {
	fieldTypes: string[];
	fragmentEntryType: string;
	isWidget: boolean;
	name: string;
};

export default function getNormalizedDragItems(
	sourceItem: SourceItem,
	activeItemIds: string[],
	layoutData: LayoutData,
	fragmentEntryLinks: FragmentEntryLinkMap
) {
	const normalizedItems = [];

	const sourceItemIds = activeItemIds.filter(
		(itemId) => itemId !== sourceItem.itemId
	);

	for (const itemId of sourceItemIds) {
		const item = layoutData.items[itemId] as FragmentLayoutDataItem;

		if (item) {
			normalizedItems.push(
				normalizeSourceItem(item, layoutData, fragmentEntryLinks)
			);
		}
	}

	return [
		...normalizedItems,
		normalizeSourceItem(sourceItem, layoutData, fragmentEntryLinks),
	];
}
