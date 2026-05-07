/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {renderHook, waitFor} from '@testing-library/react';
import {fetch} from 'frontend-js-web';

import useTaxonomyCategoryTreeNodes from '../src/main/resources/META-INF/resources/item_selector/useTaxonomyCategoryTreeNodes';

jest.mock('frontend-js-web', () => {
	const actualPackage = jest.requireActual('frontend-js-web') as any;

	return {
		...actualPackage,
		fetch: jest.fn(),
	};
});

const mockedFetch = fetch as jest.Mock;

function pageOf<T>(items: T[]) {
	const headers = new Headers();
	headers.set('Content-Type', 'application/json');

	return Promise.resolve({
		headers,
		json: () =>
			Promise.resolve({
				items,
				lastPage: 1,
				page: 1,
				totalCount: items.length,
			}),
		ok: true,
		status: 200,
	});
}

describe('useTaxonomyCategoryTreeNodes', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('uses the flatten path and rebuilds the tree client-side', async () => {
		mockedFetch.mockImplementation((url: string) => {
			if (url.includes('/taxonomy-vocabularies/42/')) {
				return pageOf([
					{
						id: 100,
						name: 'Root',
						numberOfTaxonomyCategories: 1,
					},
					{
						id: 200,
						name: 'Child',
						numberOfTaxonomyCategories: 0,
						parentTaxonomyCategory: {id: 100, name: 'Root'},
					},
				]);
			}

			return pageOf([]);
		});

		const {result} = renderHook(() => useTaxonomyCategoryTreeNodes(['42']));

		await waitFor(() => {
			expect(result.current.loading).toBe(false);
		});

		expect(result.current.error).toBeNull();
		expect(result.current.nodes).toHaveLength(1);

		const root = result.current.nodes[0];

		expect(root.id).toBe('100');
		expect(root.name).toBe('Root');
		expect(root.children).toHaveLength(1);
		expect(root.children?.[0].id).toBe('200');
		expect(root.children?.[0].hasChildren).toBe(false);

		expect(mockedFetch).toHaveBeenCalledTimes(1);

		const [url] = mockedFetch.mock.calls[0] as [string];

		expect(url).toContain(
			'/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/42/taxonomy-categories'
		);
		expect(url).toContain('flatten=true');
	});

	it('returns an error when the request fails', async () => {
		mockedFetch.mockImplementation(() =>
			Promise.resolve({
				headers: new Headers(),
				json: () => Promise.resolve({}),
				ok: false,
				status: 500,
			})
		);

		const {result} = renderHook(() => useTaxonomyCategoryTreeNodes(['42']));

		await waitFor(() => {
			expect(result.current.loading).toBe(false);
		});

		expect(result.current.error).not.toBeNull();
		expect(result.current.nodes).toEqual([]);
	});

	it('does not fetch when no vocabulary IDs are provided', async () => {
		const {result} = renderHook(() => useTaxonomyCategoryTreeNodes([]));

		await waitFor(() => {
			expect(result.current.loading).toBe(false);
		});

		expect(mockedFetch).not.toHaveBeenCalled();
		expect(result.current.nodes).toEqual([]);
	});
});
