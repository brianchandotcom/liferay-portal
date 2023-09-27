/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function isLayoutDataItemDeleted(layoutData, itemId) {
	const item = layoutData.items[itemId];

	if (!item) {
		return true;
	}
	else if (
		layoutData.deletedItems.some(
			(deletedItem) => deletedItem.itemId === itemId
		)
	) {
		return true;
	}
	else if (item.parentId) {
		return isLayoutDataItemDeleted(layoutData, item.parentId);
	}

	return false;
}
