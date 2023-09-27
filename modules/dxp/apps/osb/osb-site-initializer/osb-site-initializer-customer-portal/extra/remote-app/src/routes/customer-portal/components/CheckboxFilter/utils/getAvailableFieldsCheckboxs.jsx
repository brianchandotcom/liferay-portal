/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getAvailableFieldsCheckboxs(items, getItem) {
	return items
		?.reduce((accumulatorItems, currentItem) => {
			const item = getItem(currentItem);

			if (!item || accumulatorItems.includes(item)) {
				return accumulatorItems;
			}

			return [...accumulatorItems, item];
		}, [])
		.sort((previousItem, nextItem) => previousItem - nextItem);
}
