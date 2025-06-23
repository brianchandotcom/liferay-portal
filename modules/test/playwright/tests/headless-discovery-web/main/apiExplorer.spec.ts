/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {headlessDiscoveryPagesTest} from './fixtures/headlessDiscoveryPagesTest';

export const test = mergeTests(headlessDiscoveryPagesTest, loginTest());

test('Show help popover', async ({apiExplorer, page}) => {
	await apiExplorer.goto();

	await page
		.getByLabel('get /v1.0/sites/{siteId}/blog-postings', {exact: true})
		.click();
	const filterRow = await page.getByRole('row', {
		name: 'filter string (query)',
	});
	const helpPopover = page.locator('.popover-body');

	await filterRow.getByRole('button').click();
	await expect(
		helpPopover.getByRole('link', {name: 'Use this filter to narrow'})
	).toBeVisible();
});
