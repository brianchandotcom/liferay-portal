/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {filterItemsByQuery} from '../../src/main/resources/META-INF/resources/js/useSideNavigationFilter';

describe('Single layer items filtering', () => {
	const singleLayerItems = [
		{id: '1', label: 'Blogs'},
		{id: '2', label: 'Wiki'},
		{id: '3', label: 'Web Content'},
		{id: '4', label: 'Content Templates'},
	];

	it('returns all items when query is empty', () => {
		const result = filterItemsByQuery(singleLayerItems, '');

		expect(result.items).toHaveLength(singleLayerItems.length);
		expect(result.items).toBe(singleLayerItems);
	});

	it('returns matching leaf items', () => {
		const result = filterItemsByQuery(singleLayerItems, 'blo');

		expect(result.items).toHaveLength(1);
		expect(result.items[0].label).toBe('Blogs');
	});

	it('returns empty when nothing matches', () => {
		const result = filterItemsByQuery(singleLayerItems, 'calendar');

		expect(result.items).toHaveLength(0);
	});

	it('is case-sensitive (expects query to be lower case and trimmed)', () => {
		const lowerCaseResult = filterItemsByQuery(singleLayerItems, 'wiki');

		expect(lowerCaseResult.items).toHaveLength(1);
		expect(lowerCaseResult.items[0].label).toBe('Wiki');

		const upperCaseResult = filterItemsByQuery(singleLayerItems, 'WIKI');

		expect(upperCaseResult.items).toHaveLength(0);

		const leadingTrailingSpacesResult = filterItemsByQuery(
			singleLayerItems,
			' wiki '
		);

		expect(leadingTrailingSpacesResult.items).toHaveLength(0);
	});

	it('returns all items when query is a common substring', () => {
		const result = filterItemsByQuery(singleLayerItems, 'content');

		expect(result.items).toHaveLength(2);
	});
});

describe('Multi layer items filtering', () => {
	const multiLayerItems = [
		{
			id: 'parent1',
			items: [
				{id: 'child1', label: 'Blogs'},
				{id: 'child2', label: 'Wiki'},
			],
			label: 'Content',
		},
		{
			id: 'parent2',
			items: [
				{id: 'child3', label: 'Web Content'},
				{id: 'child4', label: 'Content Templates'},
			],
			label: 'Assets',
		},
	];

	it('returns all items when query is empty', () => {
		const result = filterItemsByQuery(multiLayerItems, '');

		expect(result.items).toBe(multiLayerItems);
		expect(result.expandedKeys).toBeUndefined();
	});

	it('returns empty when nothing matches', () => {
		const result = filterItemsByQuery(multiLayerItems, 'calendar');

		expect(result.items).toHaveLength(0);
		expect(result.expandedKeys).toBeDefined();
		expect(result.expandedKeys?.size).toBe(0);
	});

	it('filters nested items and expands parent', () => {
		const result = filterItemsByQuery(multiLayerItems, 'blog');

		expect(result.items).toHaveLength(1);
		expect(result.items[0].id).toBe('parent1');
		expect(result.items[0].items).toHaveLength(1);
		expect(result.items[0].items![0].label).toBe('Blogs');
		expect(result.expandedKeys?.has('parent1')).toBe(true);
		expect(result.expandedKeys?.has('parent2')).toBe(false);
	});

	it('matches the whole parent item if the query matches the parent label', () => {
		const result = filterItemsByQuery(multiLayerItems, 'assets');

		expect(result.items).toHaveLength(1);
		expect(result.items[0].id).toBe('parent2');
		expect(result.items[0].items).toHaveLength(2);
		expect(result.items[0].items![0].label).toBe('Web Content');
		expect(result.items[0].items![1].label).toBe('Content Templates');
		expect(result.expandedKeys?.has('parent2')).toBe(true);
		expect(result.expandedKeys?.has('parent1')).toBe(false);
	});
});
