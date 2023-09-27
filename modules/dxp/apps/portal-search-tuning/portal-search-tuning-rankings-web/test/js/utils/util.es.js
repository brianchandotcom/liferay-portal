/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	buildUrl,
	isNil,
	move,
	resultsDataToMap,
	toggleListItem,
	updateDataMap,
} from '../../../src/main/resources/META-INF/resources/js/utils/util.es';

const RESULTS_LIST = [
	{
		author: 'Test Test',
		clicks: 289,
		date: 'Apr 18 2018, 11:04 AM',
		description:
			'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod',
		icon: 'web-content',
		id: 102,
		title: 'This is a Web Content Example with Long Title',
		type: 'Web Content',
	},
	{
		author: 'Test Test',
		clicks: 8,
		date: 'Apr 18 2018, 11:04 AM',
		icon: 'documents-and-media',
		id: 103,
		title: 'This is an Image Example',
		type: 'Document',
	},
];

const TEST_BASE_URL = 'https://liferay.com/';

const TEST_LIST = [{id: 1}, {id: 2}, {id: 3}, {id: 4}, {id: 5}];

describe('utils', () => {
	describe('buildUrl', () => {
		it('adds a single parameter to the url', () => {
			expect(buildUrl(TEST_BASE_URL, {testParam: 'testValue'})).toEqual(
				TEST_BASE_URL + '?testParam=testValue'
			);
		});

		it('adds multiple parameters to the url', () => {
			expect(
				buildUrl(TEST_BASE_URL, {
					testParam1: 'testValue1',
					testParam2: 'testValue2',
				})
			).toEqual(
				TEST_BASE_URL + '?testParam1=testValue1&testParam2=testValue2'
			);
		});

		it('replaces an existing parameter in the url', () => {
			const baseUrl = TEST_BASE_URL + '?baseParam=baseValue';
			const params = {baseParam: 'testValue'};

			const url = buildUrl(baseUrl, params);

			expect(url).toEqual(TEST_BASE_URL + '?baseParam=testValue');
		});

		it('returns the same url if no parameters are passed', () => {
			const url = buildUrl(TEST_BASE_URL);

			expect(url).toEqual(TEST_BASE_URL);
		});
	});

	describe('isNil', () => {
		it('returns false for a defined variable', () => {
			const definedVariable = 'test';

			expect(isNil(definedVariable)).not.toBeTruthy();
			expect(isNil('test')).not.toBeTruthy();
			expect(isNil('')).not.toBeTruthy();
			expect(isNil(1)).not.toBeTruthy();
			expect(isNil(0)).not.toBeTruthy();
			expect(isNil(-1)).not.toBeTruthy();
		});

		it('returns true for an undefined or null variable', () => {
			expect(isNil(null)).toBeTruthy();
			expect(isNil(undefined)).toBeTruthy();
		});
	});

	describe('move', () => {
		it('returns an array with an item moved downward', () => {
			expect(move(TEST_LIST, 0, 2)).toEqual([
				{id: 2},
				{id: 1},
				{id: 3},
				{id: 4},
				{id: 5},
			]);

			expect(move(TEST_LIST, 1, 4)).toEqual([
				{id: 1},
				{id: 3},
				{id: 4},
				{id: 2},
				{id: 5},
			]);
		});

		it('returns an array with an item moved upward', () => {
			expect(move(TEST_LIST, 2, 0)).toEqual([
				{id: 3},
				{id: 1},
				{id: 2},
				{id: 4},
				{id: 5},
			]);

			expect(move(TEST_LIST, 4, 2)).toEqual([
				{id: 1},
				{id: 2},
				{id: 5},
				{id: 3},
				{id: 4},
			]);
		});

		it('moves items in a string list', () => {
			expect(move(['one', 'two', 'three'], 0, 2)).toEqual([
				'two',
				'one',
				'three',
			]);
		});

		it('moves items in a number list', () => {
			expect(move([1, 2, 3], 0, 2)).toEqual([2, 1, 3]);
		});

		it('moves an item to the end of the to index exceeds the list length', () => {
			expect(move(TEST_LIST, 0, 99)).toEqual([
				{id: 2},
				{id: 3},
				{id: 4},
				{id: 5},
				{id: 1},
			]);

			expect(move(TEST_LIST, 3, 10)).toEqual([
				{id: 1},
				{id: 2},
				{id: 3},
				{id: 5},
				{id: 4},
			]);
		});
	});

	describe('resultsDataToMap', () => {
		it('returns a mapped set of data', () => {
			expect(resultsDataToMap(RESULTS_LIST)).toEqual({
				102: RESULTS_LIST.filter(({id}) => id === 102)[0],
				103: RESULTS_LIST.filter(({id}) => id === 103)[0],
			});
		});

		it('does not replace existing ids', () => {
			const newResults = [
				{
					id: 103,
					pinned: true,
					title: 'Different Title',
				},
			];

			const originalDataMap = resultsDataToMap(RESULTS_LIST);

			expect(resultsDataToMap(newResults, originalDataMap)).toEqual(
				originalDataMap
			);
		});

		it('returns an empty set if data is empty', () => {
			expect(resultsDataToMap([])).toEqual({});
		});
	});

	describe('toggleListItem', () => {
		it('returns a new list', () => {
			expect(toggleListItem([102, 103, 104], 102)).toEqual([103, 104]);
			expect(toggleListItem([102, 103, 104], 103)).toEqual([102, 104]);
			expect(toggleListItem([102, 103, 104], 105)).toEqual([
				102,
				103,
				104,
				105,
			]);
		});
	});

	describe('updateDataMap', () => {
		it('returns a data map object with the updated properties', () => {
			const initialDataMap = {
				101: {
					id: 101,
					pinned: false,
				},
				102: {
					id: 102,
					pinned: false,
				},
			};

			const updatedDataMap = {
				101: {
					id: 101,
					pinned: false,
				},
				102: {
					id: 102,
					pinned: true,
				},
			};

			expect(
				updateDataMap(initialDataMap, [102], {pinned: true})
			).toEqual(updatedDataMap);
		});

		it('returns multiple items from a data map object', () => {
			const initialDataMap = {
				101: {
					id: 101,
					pinned: false,
				},
				102: {
					id: 102,
					pinned: false,
				},
			};

			const updatedDataMap = {
				101: {
					id: 101,
					pinned: true,
				},
				102: {
					id: 102,
					pinned: true,
				},
			};

			expect(
				updateDataMap(initialDataMap, [101, 102], {pinned: true})
			).toEqual(updatedDataMap);
		});

		it('returns a data map object with the multiple updated properties', () => {
			const initialDataMap = {
				101: {
					hidden: false,
					id: 101,
					pinned: false,
				},
				102: {
					hidden: false,
					id: 102,
					pinned: false,
				},
			};

			const updatedDataMap = {
				101: {
					hidden: false,
					id: 101,
					pinned: false,
				},
				102: {
					hidden: true,
					id: 102,
					pinned: true,
				},
			};

			expect(
				updateDataMap(initialDataMap, [102], {
					hidden: true,
					pinned: true,
				})
			).toEqual(updatedDataMap);
		});

		it('ignores ids that do not exist in the map', () => {
			const initialDataMap = {
				101: {
					addedResult: false,
					hidden: false,
					id: 101,
				},
				102: {
					addedResult: true,
					hidden: false,
					id: 102,
				},
			};

			const updatedDataMap = {
				101: {
					addedResult: false,
					hidden: false,
					id: 101,
				},
				102: {
					addedResult: false,
					hidden: false,
					id: 102,
				},
			};

			expect(
				updateDataMap(initialDataMap, [102, 103], {
					addedResult: false,
				})
			).toEqual(updatedDataMap);
		});
	});
});
