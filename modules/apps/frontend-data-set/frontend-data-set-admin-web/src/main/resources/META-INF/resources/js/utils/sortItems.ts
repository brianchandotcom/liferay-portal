/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IOrderable} from './types';

/**
 * sorts the provided items array according to the itemsOrder comma-separated list of ids.
 * If array contains items not included in the list of ids, then those are appended after
 * Example:
 * 		items = [ {id: 1}, {id: 4}, {id: 2}, {id: 3} ]
 * 		itemsOrder = "2, 3, 1"
 * 		output is [ {id: 2}, {id: 3}, {id: 1}, {id: 4} ]
 * Optionally, not included items can be sorted by creation date
 *
 * @param items {IOrderable[]}
 * @param itemsOrder {string}
 * @param useCreationDate {boolean}
 * @returns {Array}
 */
export default function sortItems(
	items: IOrderable[],
	itemsOrder: string,
	useCreationDate: boolean = false
): IOrderable[] {
	const itemsOrderArray = itemsOrder?.split(',') || ([] as string[]);

	let included: IOrderable[] = [];
	let notIncluded: IOrderable[] = [];

	included = itemsOrderArray
		.map((itemId) =>
			items.find((item) => Number(item.id) === Number(itemId))
		)
		.filter(Boolean) as IOrderable[];

	notIncluded = items.filter(
		(item) => !itemsOrderArray.includes(String(item.id))
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
