/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getItemPath(itemId, items) {
	if (itemId === '0') {
		return ['0'];
	}

	const item = items.find((item) => item.siteNavigationMenuItemId === itemId);

	return item.parentSiteNavigationMenuItemId !== '0'
		? [...getItemPath(item.parentSiteNavigationMenuItemId, items), itemId]
		: [itemId];
}
