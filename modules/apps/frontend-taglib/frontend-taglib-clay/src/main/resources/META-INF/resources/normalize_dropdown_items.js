/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getDataAttributes from './get_data_attributes';

function addSeparators(items) {
	if (items.length < 2) {
		return items;
	}

	const separatedItems = [items[0]];

	for (let i = 1; i < items.length; i++) {
		const item = items[i];

		if (item.type === 'group' && item.separator) {
			separatedItems.push({type: 'divider'});
		}

		separatedItems.push(item);
	}

	return separatedItems.map((item) => {
		if (item.type === 'group') {
			return {
				...item,
				items: addSeparators(item.items),
			};
		}

		return item;
	});
}

function filterEmptyGroups(items) {
	return items
		.filter(
			(item) =>
				item.type !== 'group' ||
				(Array.isArray(item.items) && item.items.length)
		)
		.map((item) =>
			item.type === 'group'
				? {...item, items: filterEmptyGroups(item.items)}
				: item
		);
}

function updateItemProps(item) {
	const {data, ...rest} = item;

	const dataAttributes = getDataAttributes(data);

	const items = Array.isArray(item.items)
		? item.items.map(updateItemProps)
		: item.items;

	return {
		...dataAttributes,
		...rest,
		items,
		symbolLeft: item.icon,
	};
}

export default function normalizeDropdownItems(items) {
	if (!items) {
		return null;
	}

	const filteredItems = filterEmptyGroups(items);

	if (!filteredItems.length) {
		return null;
	}

	return addSeparators(filteredItems.map(updateItemProps));
}
