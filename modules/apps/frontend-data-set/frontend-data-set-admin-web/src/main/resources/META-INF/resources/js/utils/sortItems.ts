/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IOrderable} from './types';

/**
 * Sorts the provided items array according to the itemsOrder comma-separated list.
 * When orderByERC is false, itemsOrder is a list of ids; when true, a list of externalReferenceCodes.
 * If array contains items not included in the list, then those are appended after.
 * Optionally, not included items can be sorted by creation date.
 *
 * @param items {IOrderable[]}
 * @param itemsOrder {string} - CSV of ids or externalReferenceCodes
 * @param useCreationDate {boolean}
 * @param orderByERC {boolean} - when true, itemsOrder is CSV of ERCs; when false, CSV of ids
 * @returns {Array}
 */
export default function sortItems(
	items: IOrderable[],
	itemsOrder: string,
	useCreationDate: boolean = false,
	orderByERC: boolean = false
): IOrderable[] {
	const itemsOrderArray = itemsOrder?.split(',') || ([] as string[]);

	const getItemKey = (item: IOrderable) =>
		orderByERC
			? String(item.externalReferenceCode ?? '')
			: String(Number(item.id));

	const getOrderKey = (orderKey: string) =>
		orderByERC ? String(orderKey ?? '') : String(Number(orderKey));

	const orderKeysSet = new Set(itemsOrderArray.map(getOrderKey));

	const included = itemsOrderArray
		.map((orderKey) =>
			items.find((item) => getItemKey(item) === getOrderKey(orderKey))
		)
		.filter(Boolean) as IOrderable[];

	let notIncluded = items.filter(
		(item) => !orderKeysSet.has(getItemKey(item))
	);

	if (useCreationDate) {
		const creationDates = {} as {[key: number]: number};

		notIncluded.forEach((item) => {
			creationDates[item.id] = Date.parse(item.dateCreated);
		});

		notIncluded = notIncluded.sort(
			(item1, item2) => creationDates[item1.id] - creationDates[item2.id]
		);
	}

	return [...included, ...notIncluded];
}
