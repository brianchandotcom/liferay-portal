/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

import {OrderBy, TFilter} from '../../utils/filter';
import {TFormattedItems, TItem, TTableRequestParams} from './types';

export function serializeTableRequestParams({
	filter: {type, value},
	keywords,
	pagination: {page, pageSize},
}: TTableRequestParams): string {
	const params: Record<string, string | number> = {
		keywords,
		page,
		pageSize,
		sort: `${value}:${type}`,
	};

	const arrs = Object.keys(params).map((key) => [key, String(params[key])]);
	const path = new URLSearchParams(arrs);

	return decodeURIComponent(path.toString());
}

export function getOrderBy({type}: TFilter): OrderBy {
	return type === OrderBy.Asc ? OrderBy.Desc : OrderBy.Asc;
}

export function getOrderBySymbol({type}: TFilter): string {
	return type === OrderBy.Asc ? 'order-list-up' : 'order-list-down';
}

export function getResultsLanguage(totalCount: number) {
	if (totalCount > 1) {
		return sub(
			Liferay.Language.get('x-results-for').toLowerCase(),
			totalCount
		);
	}

	return sub(Liferay.Language.get('x-result-for').toLowerCase(), totalCount);
}

export function getGlobalChecked(formattedItems: TFormattedItems): boolean {
	const items = Object.values(formattedItems).filter(
		({disabled}) => !disabled
	);

	if (!items.length) {
		return false;
	}

	return items.every(({checked}) => checked);
}

export function updateFormattedItems(
	formattedItems: TFormattedItems,
	checked: boolean
): TFormattedItems {
	const updatedItems: TFormattedItems = {};

	Object.entries(formattedItems).forEach(([id, item]) => {
		if (item.disabled) {
			updatedItems[id] = item;
		}
		else {
			updatedItems[id] = {
				...item,
				checked,
			};
		}
	});

	return updatedItems;
}

export function formattingItems(items: TItem[]): TFormattedItems {
	const formattedItems: TFormattedItems = {};

	for (let i = 0; i < items.length; i++) {
		const item = items[i];

		formattedItems[item.id] = item;
	}

	return formattedItems;
}

export function selectFormattedItems(
	formattedItems: TFormattedItems,
	rows: string[]
): TFormattedItems {
	let selectedFormattedItems = {};

	rows.forEach((rowId) => {
		selectedFormattedItems = {
			...selectedFormattedItems,
			[rowId]: formattedItems[rowId],
		};
	});

	return selectedFormattedItems;
}

export function getIds(items: TFormattedItems, initialIds: number[]): number[] {
	const ids = new Set(initialIds);

	Object.values(items).forEach(({checked, id}) => {
		if (checked) {
			ids.add(Number(id));
		}
		else {
			ids.delete(Number(id));
		}
	});

	return [...ids];
}
