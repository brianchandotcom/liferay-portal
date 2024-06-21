/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import sortItems from '../../src/main/resources/META-INF/resources/js/utils/sortItems';
import {IOrderable} from '../../src/main/resources/META-INF/resources/js/utils/types';

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

interface IOrderResult {
	[Property: string]: number[];
}

const completeSortCases: IOrderResult = {
	'1,2,3,4': [1, 2, 3, 4],
	'4,1,3,2': [4, 1, 3, 2],
};

const partialSortCases: IOrderResult = {
	'1,2': [1, 2, 4, 3],
	'3,2': [3, 2, 4, 1],
};

const creationDatePartialSortCases: IOrderResult = {
	'1,2': [1, 2, 3, 4],
	'4,3': [4, 3, 1, 2],
};

const testCases = (expected: IOrderResult, useCreationDate?: boolean) =>
	Object.keys(expected).forEach((itemsOrder) =>
		expect(
			JSON.stringify(
				sortItems(items, itemsOrder, useCreationDate).map((item) =>
					Number(item.id)
				)
			)
		).toBe(JSON.stringify(expected[itemsOrder]))
	);

describe('sortItems', () => {
	it('sorts over a total order, using complete sort cases', () =>
		testCases(completeSortCases));

	it('sorts over a partial order, without dates', () =>
		testCases(partialSortCases, false));

	it('sorts over a partial order, with dates', () =>
		testCases(creationDatePartialSortCases, true));
});
