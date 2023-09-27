/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getDescendantsCount(items = [], itemId) {
	let descendantCount = 0;

	const countChildren = (items, itemId) => {
		const children = items.filter(
			(item) => item.parentSiteNavigationMenuItemId === itemId
		);

		descendantCount += children.length;

		children.forEach((child) => {
			countChildren(items, child.siteNavigationMenuItemId);
		});
	};

	countChildren(items, itemId);

	return descendantCount;
}
