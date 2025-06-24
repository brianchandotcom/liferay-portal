/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {headlessDiscoveryPagesTest} from './fixtures/headlessDiscoveryPagesTest';

export const test = mergeTests(headlessDiscoveryPagesTest, loginTest());

test.use({
	permissions: ['clipboard-write', 'clipboard-read'],
});

test(
	'Show help popover and Filterable Fields copy behavior',
	{tag: '@LPD-54844'},
	async ({apiExplorer, page}) => {
		const operationBlock = apiExplorer.getOperationBlock(
			'getSiteBlogPostingsPage'
		);
		const filterRow = await operationBlock.getByRole('row', {
			name: 'filter',
		});

		await apiExplorer.goto();
		await operationBlock.getByRole('button').click();

		await test.step('Open help popover', async () => {
			await filterRow.getByRole('button').click();
			await expect(
				apiExplorer.helpPopover.getByRole('link', {
					name: 'Use this filter to narrow',
				})
			).toBeVisible();
		});

		await test.step('Copy Filterable Field', async () => {
			const filterableName = 'creatorId';

			await apiExplorer.helpPopover
				.locator('li')
				.filter({hasText: filterableName})
				.getByLabel('Copy to Clipboard')
				.click();
			await expect(
				await page.evaluate('navigator.clipboard.readText()')
			).toBe(filterableName);
		});
	}
);
