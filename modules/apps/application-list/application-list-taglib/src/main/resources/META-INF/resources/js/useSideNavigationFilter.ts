/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useMemo, useState} from 'react';

import {type SideNavigationItem} from './SideNavigationItem';

interface SideNavigationFilter {
	expandedKeys?: Set<React.Key>;
	items: Array<SideNavigationItem>;
}

const EMPTY_KEYS_SET = new Set<React.Key>();
const EMPTY_FILTER = {expandedKeys: EMPTY_KEYS_SET, items: []};

export function filterItemsByQuery(
	items: Array<SideNavigationItem>,
	query: string
): SideNavigationFilter {
	if (!query) {
		return {items};
	}

	return items.reduce<Required<SideNavigationFilter>>((result, item) => {
		if (item.items && item.items.length) {
			const {expandedKeys, items} = filterItemsByQuery(item.items, query);

			if (items.length) {
				return {
					expandedKeys: new Set([
						...result.expandedKeys,
						...(expandedKeys ?? EMPTY_KEYS_SET),
						item.id,
					]),

					items: result.items.concat({
						...item,
						items,
					}),
				};
			}

			if (item.label.toLowerCase().includes(query)) {
				return {
					expandedKeys: result.expandedKeys.add(item.id),
					items: result.items.concat(item),
				};
			}
		}
		else if (item.label.toLowerCase().includes(query)) {
			return {
				expandedKeys: result.expandedKeys,
				items: result.items.concat(item),
			};
		}

		return result;
	}, EMPTY_FILTER);
}

export function useSideNavigationFilter(items: Array<SideNavigationItem>) {
	const [query, setQuery] = useState('');

	const filter = useMemo(
		() => filterItemsByQuery(items, query),
		[items, query]
	);

	const updateQuery = useCallback((query: string) => {
		setQuery(query.trim().toLowerCase());
	}, []);

	return {
		expandedKeys: filter.expandedKeys,
		isFilterActive: !!query,
		items: filter.items,
		setQuery: updateQuery,
	};
}
