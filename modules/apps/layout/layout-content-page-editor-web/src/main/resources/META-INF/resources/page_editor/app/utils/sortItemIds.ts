/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';

/**
 * Sort items ids based on layout data tree.
 *
 * First the item ids of the layout data are sorted according to the order
 * of the layout data tree with getSortedLayoutDataItemIds function, and based
 * on that, the itemIds received as parameters in the sortItemIds function are
 * sorted.
 */

function getSortedLayoutDataItemIds(layoutData: LayoutData) {
	const root = layoutData.items[layoutData.rootItems.main];
	const sortedItemIds: string[] = [];

	const addItemIds = (itemId: string) => {
		sortedItemIds.push(itemId);

		layoutData.items[itemId]?.children.forEach((childId) =>
			addItemIds(childId)
		);
	};

	addItemIds(root.itemId);

	return sortedItemIds;
}

export default function sortItemIds(itemIds: string[], layoutData: LayoutData) {
	const sortedLayoutDataItemIds = getSortedLayoutDataItemIds(layoutData);

	return itemIds.sort(
		(a, b) =>
			sortedLayoutDataItemIds.indexOf(a) -
			sortedLayoutDataItemIds.indexOf(b)
	);
}
