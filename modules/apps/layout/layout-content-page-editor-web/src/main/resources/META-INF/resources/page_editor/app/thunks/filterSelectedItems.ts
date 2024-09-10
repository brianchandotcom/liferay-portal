/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';

/**
 * Filters all selected items.
 *
 * If the selected items contain parents and its children, filter out all the items to
 * keep only the parent items.
 */

export default function filterSelectedItems(
	itemIds: string[],
	layoutDataItems: LayoutData['items']
): string[] {
	const hasParentSelected = (itemId: string): boolean => {
		const parentId = layoutDataItems[itemId].parentId;

		if (!parentId) {
			return false;
		}

		if (itemIds.includes(parentId)) {
			return true;
		}

		return hasParentSelected(parentId);
	};

	return itemIds.filter((itemId) => !hasParentSelected(itemId));
}
