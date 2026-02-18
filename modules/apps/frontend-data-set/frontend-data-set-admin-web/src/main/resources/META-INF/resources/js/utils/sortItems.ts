/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IOrderable} from './types';

/**
 * Sorts the provided items array according to the itemsOrder comma-separated list of ERCs
 * If array contains items not included in the list of ERCs, then those are appended after.
 * Optionally, not included items can be sorted by creation date.
 *
 * @param items {IOrderable[]}
 * @param itemsOrder {string} - CSV of ids or externalReferenceCodes
 * @param useCreationDate {boolean}
 * @returns {Array}
 */
export default function sortItems(
	items: IOrderable[],
	itemsOrder: string,
	useCreationDate: boolean = false
): IOrderable[] {
	const itemsOrderArray = itemsOrder?.split(',') || ([] as string[]);

	const getItemKey = (item: IOrderable) =>
		String(item.externalReferenceCode ?? '');

	const included = itemsOrderArray
		.map((erc) => items.find((item) => getItemKey(item) === erc))
		.filter(Boolean) as IOrderable[];

	let notIncluded = items.filter(
		(item) => !itemsOrderArray.includes(getItemKey(item))
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
