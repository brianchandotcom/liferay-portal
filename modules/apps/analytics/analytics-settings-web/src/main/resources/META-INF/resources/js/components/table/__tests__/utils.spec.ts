/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DEFAULT_FILTER, OrderBy} from '../../../utils/filter';
import {DEFAULT_PAGINATION} from '../../../utils/pagination';
import {TFormattedItems, TTableRequestParams} from '../types';
import {
	formattingItems,
	getGlobalChecked,
	getIds,
	getOrderBy,
	getOrderBySymbol,
	getResultsLanguage,
	serializeTableRequestParams,
	updateFormattedItems,
} from '../utils';

describe('serializeTableRequestParams', () => {
	it('serialize the table request parameters correctly with default values', () => {
		const tableRequestParams: TTableRequestParams = {
			filter: DEFAULT_FILTER,
			keywords: 'keyword',
			pagination: DEFAULT_PAGINATION,
		};
		const expected = 'keywords=keyword&page=1&pageSize=20&sort=name:asc';
		const result = serializeTableRequestParams(tableRequestParams);

		expect(result).toEqual(expected);
	});

	it('serialize the table request parameters correctly', () => {
		const tableRequestParams: TTableRequestParams = {
			filter: {type: OrderBy.Asc, value: 'name'},
			keywords: 'keyword',
			pagination: {page: 1, pageSize: 10},
		};
		const expected = 'keywords=keyword&page=1&pageSize=10&sort=name:asc';
		const result = serializeTableRequestParams(tableRequestParams);

		expect(result).toEqual(expected);
	});

	it('serialize the table request parameters correctly with empty values', () => {
		const tableRequestParams: TTableRequestParams = {
			filter: {type: OrderBy.Asc, value: ''},
			keywords: '',
			pagination: {page: 0, pageSize: 0},
		};
		const expected = 'keywords=&page=0&pageSize=0&sort=:asc';
		const result = serializeTableRequestParams(tableRequestParams);

		expect(result).toEqual(expected);
	});
});

describe('getOrderBy', () => {
	it('returns the reverse OrderBy value ASC -> DESC', () => {
		expect(getOrderBy({type: OrderBy.Asc, value: 'name'})).toEqual(
			OrderBy.Desc
		);
	});

	it('returns the reverse OrderBy value DESC -> ASC', () => {
		expect(getOrderBy({type: OrderBy.Desc, value: 'name'})).toEqual(
			OrderBy.Asc
		);
	});
});

describe('getOrderBySymbol', () => {
	it('returns OrderBy symbol based on value ASC', () => {
		expect(
			getOrderBySymbol({
				type: OrderBy.Asc,
				value: 'name',
			})
		).toEqual('order-list-up');
	});

	it('returns OrderBy symbol based on value DESC', () => {
		expect(
			getOrderBySymbol({
				type: OrderBy.Desc,
				value: 'name',
			})
		).toEqual('order-list-down');
	});
});

describe('getResultsLanguage', () => {
	it('returns results language on singular', () => {
		expect(getResultsLanguage(1)).toEqual('x-result-for');
	});

	it('returns results language on plural', () => {
		expect(getResultsLanguage(2)).toEqual('x-results-for');
	});
});

describe('getGlobalChecked', () => {
	it('returns false if there are no items', () => {
		expect(getGlobalChecked({})).toBe(false);
	});

	it('returns false if all items are disabled', () => {
		const formattedItems: TFormattedItems = {
			item1: {checked: false, columns: [], disabled: true, id: 'item1'},
			item2: {checked: false, columns: [], disabled: true, id: 'item2'},
		};
		expect(getGlobalChecked(formattedItems)).toBe(false);
	});

	it('returns true if all items are checked', () => {
		const formattedItems: TFormattedItems = {
			item1: {checked: true, columns: [], disabled: false, id: 'item1'},
			item2: {checked: true, columns: [], disabled: false, id: 'item2'},
		};
		expect(getGlobalChecked(formattedItems)).toBe(true);
	});

	it('returns false if some items are checked and some are not', () => {
		const formattedItems: TFormattedItems = {
			item1: {checked: true, columns: [], disabled: false, id: 'item1'},
			item2: {checked: false, columns: [], disabled: false, id: 'item2'},
		};
		expect(getGlobalChecked(formattedItems)).toBe(false);
	});
});

describe('updateFormattedItems', () => {
	it('returns items formatted with the checked value updated to true', () => {
		expect(
			updateFormattedItems(
				{
					123: {
						checked: false,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: false,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: false,
						id: '456',
					},
					789: {
						checked: false,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				true
			)
		).toEqual({
			123: {
				checked: true,
				columns: [
					{
						id: 'SiteA',
						value: 'Site A',
					},
				],
				disabled: false,
				id: '123',
			},
			456: {
				checked: true,
				columns: [
					{
						id: 'SiteB',
						value: 'Site B',
					},
				],
				disabled: false,
				id: '456',
			},
			789: {
				checked: true,
				columns: [
					{
						id: 'SiteC',
						value: 'Site C',
					},
				],
				disabled: false,
				id: '789',
			},
		});
	});

	it('returns items formatted with the checked value updated to false', () => {
		expect(
			updateFormattedItems(
				{
					123: {
						checked: true,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: true,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: false,
						id: '456',
					},
					789: {
						checked: true,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				false
			)
		).toEqual({
			123: {
				checked: false,
				columns: [
					{
						id: 'SiteA',
						value: 'Site A',
					},
				],
				disabled: false,
				id: '123',
			},
			456: {
				checked: false,
				columns: [
					{
						id: 'SiteB',
						value: 'Site B',
					},
				],
				disabled: false,
				id: '456',
			},
			789: {
				checked: false,
				columns: [
					{
						id: 'SiteC',
						value: 'Site C',
					},
				],
				disabled: false,
				id: '789',
			},
		});
	});

	it('returns formatted items without changing the checked value if the item is disabled', () => {
		expect(
			updateFormattedItems(
				{
					123: {
						checked: true,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: true,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: true,
						id: '456',
					},
					789: {
						checked: true,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				false
			)
		).toEqual({
			123: {
				checked: false,
				columns: [
					{
						id: 'SiteA',
						value: 'Site A',
					},
				],
				disabled: false,
				id: '123',
			},
			456: {
				checked: true,
				columns: [
					{
						id: 'SiteB',
						value: 'Site B',
					},
				],
				disabled: true,
				id: '456',
			},
			789: {
				checked: false,
				columns: [
					{
						id: 'SiteC',
						value: 'Site C',
					},
				],
				disabled: false,
				id: '789',
			},
		});
	});
});

describe('formattingItems', () => {
	it('returns formatted items', () => {
		expect(
			formattingItems([
				{
					checked: false,
					columns: [
						{
							id: 'SiteA',
							value: 'Site A',
						},
					],
					disabled: false,
					id: '123',
				},
				{
					checked: true,
					columns: [
						{
							id: 'SiteB',
							value: 'Site B',
						},
					],
					disabled: true,
					id: '456',
				},
				{
					checked: false,
					columns: [
						{
							id: 'SiteC',
							value: 'Site C',
						},
					],
					disabled: false,
					id: '789',
				},
			])
		).toEqual({
			123: {
				checked: false,
				columns: [
					{
						id: 'SiteA',
						value: 'Site A',
					},
				],
				disabled: false,
				id: '123',
			},
			456: {
				checked: true,
				columns: [
					{
						id: 'SiteB',
						value: 'Site B',
					},
				],
				disabled: true,
				id: '456',
			},
			789: {
				checked: false,
				columns: [
					{
						id: 'SiteC',
						value: 'Site C',
					},
				],
				disabled: false,
				id: '789',
			},
		});
	});

	it('returns empty formatted items', () => {
		expect(formattingItems([])).toEqual({});
	});
});

describe('getIds', () => {
	it('returns ids with all checked items', () => {
		expect(
			getIds(
				{
					123: {
						checked: true,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: true,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: false,
						id: '456',
					},
					789: {
						checked: true,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				[]
			)
		).toEqual([123, 456, 789]);

		expect(
			getIds(
				{
					123: {
						checked: false,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: false,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: false,
						id: '456',
					},
					789: {
						checked: true,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				[]
			)
		).toEqual([789]);
	});

	it('returns ids with all checked items and old ones', () => {
		expect(
			getIds(
				{
					123: {
						checked: true,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: true,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: false,
						id: '456',
					},
					789: {
						checked: true,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				[111, 222, 333, 789]
			)
		).toEqual([111, 222, 333, 789, 123, 456]);

		expect(
			getIds(
				{
					123: {
						checked: true,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: true,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: false,
						id: '456',
					},
					789: {
						checked: true,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				[111, 222, 333, 123, 456]
			)
		).toEqual([111, 222, 333, 123, 456, 789]);
	});

	it('returns ids with all checked items and not includes unchecked items', () => {
		expect(
			getIds(
				{
					123: {
						checked: false,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: false,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: false,
						id: '456',
					},
					789: {
						checked: false,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				[]
			)
		).toEqual([]);

		expect(
			getIds(
				{
					123: {
						checked: false,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: true,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: false,
						id: '456',
					},
					789: {
						checked: false,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				[]
			)
		).toEqual([456]);
	});

	it('returns ids with all checked items and remove unchecked items', () => {
		expect(
			getIds(
				{
					123: {
						checked: false,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: false,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: false,
						id: '456',
					},
					789: {
						checked: false,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				[123, 456, 789]
			)
		).toEqual([]);

		expect(
			getIds(
				{
					123: {
						checked: false,
						columns: [
							{
								id: 'SiteA',
								value: 'Site A',
							},
						],
						disabled: false,
						id: '123',
					},
					456: {
						checked: false,
						columns: [
							{
								id: 'SiteB',
								value: 'Site B',
							},
						],
						disabled: false,
						id: '456',
					},
					789: {
						checked: false,
						columns: [
							{
								id: 'SiteC',
								value: 'Site C',
							},
						],
						disabled: false,
						id: '789',
					},
				},
				[111, 222, 333]
			)
		).toEqual([111, 222, 333]);
	});
});
