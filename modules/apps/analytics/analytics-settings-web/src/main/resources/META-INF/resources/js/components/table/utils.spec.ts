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

import {DEFAULT_FILTER, OrderBy} from '../../utils/filter';
import {DEFAULT_PAGINATION} from '../../utils/pagination';
import {
	getFormattedItems,
	getGlobalChecked,
	getIds,
	getOrderBy,
	getOrderBySymbol,
	getResultsLanguage,
	serializeTableRequestParams,
	updateFormattedItems,
} from './utils';

describe('serializeTableRequestParams', () => {
	it('returns a serialized table request params', () => {
		expect(
			serializeTableRequestParams({
				filter: DEFAULT_FILTER,
				keywords: '',
				pagination: DEFAULT_PAGINATION,
			})
		).toEqual('page=1&pageSize=20&sort=name:asc');
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
		expect(getResultsLanguage(['Site A'])).toEqual('x-result-for');
	});

	it('returns results language on plural', () => {
		expect(getResultsLanguage(['Site A', 'Site B'])).toEqual(
			'x-results-for'
		);
	});
});

describe('getGlobalChecked', () => {
	it('returns true if all items have checked value as true', () => {
		const items = {
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
		};

		expect(getGlobalChecked(items)).toBeTruthy();
	});

	it('returns false if at least one item has value checked as false', () => {
		const items = {
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
		};

		expect(getGlobalChecked(items)).toBeFalsy();
	});

	it('returns false if there are no items', () => {
		const items = {};

		expect(getGlobalChecked(items)).toBeFalsy();
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

describe('getFormattedItems', () => {
	it('returns formatted items', () => {
		expect(
			getFormattedItems([
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
		expect(getFormattedItems([])).toEqual({});
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
				[111, 222, 333]
			)
		).toEqual([111, 222, 333, 123, 456, 789]);

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
