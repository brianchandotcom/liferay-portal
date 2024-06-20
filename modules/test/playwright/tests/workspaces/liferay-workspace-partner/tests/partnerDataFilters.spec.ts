/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { expect, mergeTests } from '@playwright/test';

import { partnerSiteFixture } from '../fixtures/partnerSite';

export const test = mergeTests(partnerSiteFixture);

test('Check if date filter fields are cleared', async ({page}) => {

	await page.getByRole('link', {name: 'MDFs'}).click();

	await page.getByRole('button', { name: 'Filter' }).click();
	
    await page.getByRole('button', { name: 'Activity Period' }).click();
	
    await page.locator('div').filter({ hasText: /^Activity Date On Or After$/ }).locator('#basicInputText').fill('2024-06-01');
	
    await page.locator('div').filter({ hasText: /^Activity Date On Or Before$/ }).locator('#basicInputText').fill('2024-06-08');
	
    await page.getByRole('button', { name: 'Apply' }).click();
	
    await page.getByText('Clear All Filters').click();
	
    await page.getByRole('button', { name: 'Filter' }).click();
	
    await expect(page.locator('div').filter({ hasText: /^Activity Date On Or After$/ }).locator('#basicInputText')).toBeEmpty();
	
});


