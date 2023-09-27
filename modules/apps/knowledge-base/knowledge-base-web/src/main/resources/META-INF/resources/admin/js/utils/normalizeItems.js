/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import KBDropdownPropsTransformer from '../KBDropdownPropsTransformer';

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

function normalizeItems(items = [], portletNamespace) {
	const transformedItems = KBDropdownPropsTransformer({
		items: items.map((item) => {
			return {
				...item,
				items: item.items?.map(({icon, ...props}) => ({
					...props,
					symbolLeft: icon,
				})),
			};
		}),
		portletNamespace,
	}).items;

	const filteredItems = filterEmptyGroups(transformedItems);

	return addSeparators(filteredItems);
}

export default function normalizeDropdownItems(items, portletNamespace) {
	if (items) {
		return items.map((item) => {
			return {
				...item,
				actions: normalizeItems(item.actions, portletNamespace),
				children: normalizeDropdownItems(
					item.children,
					portletNamespace
				),
			};
		});
	}
}
