/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {sub} from 'frontend-js-web';

import {OrderBy, TFilter} from '../../utils/filter';
import {TFormattedItems, TItem, TTableRequestParams} from './types';

export function serializeTableRequestParams({
	filter: {type, value},
	keywords,
	pagination: {page, pageSize},
}: TTableRequestParams): string {
	let params: any = {
		page,
		pageSize,
		sort: `${value}:${type}`,
	};

	if (keywords) {
		params = {
			keywords,
			page: 1,
			pageSize,
			sort: `${value}:${type}`,
		};
	}

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

export function getResultsLanguage(rows: string[]) {
	if (rows.length > 1) {
		return sub(
			Liferay.Language.get('x-results-for').toLowerCase(),
			rows.length
		);
	}

	return sub(Liferay.Language.get('x-result-for').toLowerCase(), rows.length);
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
	return Object.values(formattedItems).reduce(
		(accumulator: TFormattedItems, item) => {
			if (item.disabled) {
				return {
					...accumulator,
					[item.id]: item,
				};
			}

			return {
				...accumulator,
				[item.id]: {
					...item,
					checked,
				},
			};
		},
		{}
	);
}

export function getFormattedItems(items: TItem[]): TFormattedItems {
	return items.reduce((accumulator: TFormattedItems, item) => {
		return {
			...accumulator,
			[item.id]: item,
		};
	}, {});
}

export function getIds(items: TFormattedItems, initialIds: number[]): number[] {
	const ids = [...initialIds];

	Object.values(items).forEach((item) => {
		if (ids.length) {
			ids.forEach((id, index) => {
				if (id === Number(item.id) && !item.checked) {
					ids.splice(index, 1);
				} else if (id !== Number(item.id) && item.checked) {
					ids.push(Number(item.id));
				}
			});
		} else if (item.checked) {
			ids.push(Number(item.id));
		}
	});

	return [...new Set(ids)];
}
