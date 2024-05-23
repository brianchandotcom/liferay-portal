/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import sortItems from '../../main/resources/META-INF/resources/js/utils/sortItems';
import {IOrderable} from '../../main/resources/META-INF/resources/js/utils/types';

/* items to order.
	- id: allows itemOrder to refer to items in this array
	- dateCreated: additional data that can be used to sort items
*/
const items = [
	{
		dateCreated: '2024-06-05T10:49:04Z',
		id: 4,
	},
	{
		dateCreated: '2024-06-05T10:49:03Z',
		id: '3',
	},
	{
		dateCreated: '2024-06-05T10:49:02Z',
		id: 2,
	},
	{
		dateCreated: '2024-06-05T10:49:01Z',
		id: '1',
	},
] as IOrderable[];

/* This type represents expected order result pairs
  - keys contain itemsOrder values, will be used to call sortItems()
  - values contain the expected order of id property after calling sortItems()
  */
type orderResult = {
	[Property: string]: string;
};

const totalOrderResult: orderResult = {
	'1,2,3,4': JSON.stringify([1, 2, 3, 4]),
	'4,1,3,2': JSON.stringify([4, 1, 3, 2]),
};

/* items not in itemsOrder keep their relative order */
const partialOrderNoDateResult: orderResult = {
	'1,2': JSON.stringify([1, 2, 4, 3]),
	'3,2': JSON.stringify([3, 2, 4, 1]),
};

/* items not in itemsOrder are sorted by dateCreated */
const partialOrderDateResult: orderResult = {
	'1,2': JSON.stringify([1, 2, 3, 4]),
	'4,3': JSON.stringify([4, 3, 1, 2]),
};

const testLoop = (expected: orderResult, useCreationDate?: boolean) =>
	Object.keys(expected).forEach((itemsOrder) =>
		expect(
			JSON.stringify(
				sortItems(items, itemsOrder, useCreationDate).map((item) =>
					Number(item.id)
				)
			)
		).toBe(expected[itemsOrder])
	);

describe('sortItems()', () => {
	it('Sorts over a total order', () => testLoop(totalOrderResult));

	it('Sorts over a partial order, no dates', () =>
		testLoop(partialOrderNoDateResult, false));

	it('Sorts over a partial order, with dates', () =>
		testLoop(partialOrderDateResult, true));
});
