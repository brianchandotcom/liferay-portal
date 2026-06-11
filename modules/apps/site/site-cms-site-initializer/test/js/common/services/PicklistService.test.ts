/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PicklistService from '../../../../src/main/resources/META-INF/resources/js/common/services/PicklistService';
import {mockFetch} from '../../__mocks__/frontend-js-web';

describe('PicklistService.getPicklists', () => {
	afterEach(() => {
		mockFetch.mockReset();
	});

	it('requests pageSize=-1 and returns every picklist across all pages, not just the first page of 20', async () => {
		const maxId = 21;
		const page1 = Array.from({length: 20}, (_, i) => ({id: i + 1}));
		const page2 = [{id: maxId}];

		const getMockResponse = (page: {id: number}[]) => {
			return {
				json: async () => ({items: page, lastPage: 2}),
				ok: true,
				status: 200,
			} as Response;
		};

		mockFetch
			.mockResolvedValueOnce(getMockResponse(page1))
			.mockResolvedValueOnce(getMockResponse(page2));

		const result = await PicklistService.getPicklists();

		expect(mockFetch).toHaveBeenCalledWith(
			expect.stringContaining(
				'/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=-1'
			),
			expect.anything()
		);
		expect(result).toHaveLength(maxId);
		expect(result).toContainEqual({id: maxId});
	});

	it('rejects when a page request returns an error', async () => {
		mockFetch.mockResolvedValue({
			json: async () => ({title: 'an-unexpected-error-occurred'}),
			ok: false,
			status: 500,
		} as Response);

		await expect(PicklistService.getPicklists()).rejects.toBe(
			'an-unexpected-error-occurred'
		);
	});
});
