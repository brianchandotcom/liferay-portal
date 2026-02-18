/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';

const test = mergeTests(loginTest(), productMenuPageTest);

test(
	'The global site shows the correct default logo instead of falling back to the default company logo',
	{tag: '@LPD-77440'},
	async ({page, productMenuPage}) => {
		await test.step('Open Product Menu', async () => {
			await page.goto('/');

			await productMenuPage.openProductMenuIfClosed();
		});

		await test.step('Verify that the default global site logo is displayed in the Select Site modal', async () => {
			const frame = page.frameLocator('iframe[title="Select Site"]');

			const allSitesTab = frame.getByRole('link', {
				name: 'All Sites',
			});

			await clickAndExpectToBeVisible({
				target: allSitesTab,
				trigger: page.getByRole('button', {
					name: 'Go to Other Site',
				}),
			});

			await allSitesTab.click();

			const globalSiteCard = frame.locator('.card-body', {
				hasText: 'Global',
			});

			await expect(
				globalSiteCard.locator('.sticker-img')
			).toHaveAttribute('src', /company_group_logo/);

			await globalSiteCard.locator('.card-title').click();
		});

		await test.step('Validate that the default global site logo is displayed in the Product Menu', async () => {
			await expect(
				page
					.getByRole('navigation', {name: 'Product Menu'})
					.locator('div.sticker')
			).toHaveCSS('background-image', /company_group_logo/);
		});
	}
);
