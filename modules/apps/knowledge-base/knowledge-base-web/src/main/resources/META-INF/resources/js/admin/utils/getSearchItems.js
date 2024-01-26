/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getSearchItems(items) {
	if (items) {
		return items.reduce(function reducer(acc, item) {
			acc.push({
				href: item.href,
				id: item.id,
				name: item.name,
				type: item.type,
			});

			if (item.children) {
				item.children.reduce(reducer, acc);
			}

			return acc;
		}, []);
	}
}
