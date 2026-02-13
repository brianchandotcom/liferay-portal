/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {liferayConfig} from '../../../liferay.config';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {closeProductMenu, openProductMenu} from '../../../utils/productMenu';

const test = mergeTests(loginTest(), productMenuPageTest);

test('The Product Menu panel should be focused when Product menu is opened', async ({
	page,
	productMenuPage,
}) => {
	await page.goto('/');

	if (await productMenuPage.contentAndDataButton.isVisible()) {
		productMenuPage.closeProductMenuButton.press('Enter');
	}

	productMenuPage.openProductMenuButton.press('Enter');

	await expect(page.getByLabel('Product Menu', {exact: true})).toBeFocused();
});

function expectPropertyToBeGreaterThan(property: string, threshold: number) {
	expect(parseFloat(property.replace('px', ''))).toBeGreaterThan(threshold);
}

async function getComputedStyle(locator: Locator) {
	return await locator.evaluate((element) => {
		return window.getComputedStyle(element);
	});
}

test(
	'The Product Menu displays correct spacing for categories and portlets and the company logo in the sticker',
	{tag: '@LPD-66981'},
	async ({page, productMenuPage}) => {
		await test.step('Open Product Menu', async () => {
			await page.goto('/');

			await productMenuPage.openProductMenuIfClosed();
		});

		await test.step('View the company logo is displayed in sticker', async () => {
			await expect(
				page
					.getByRole('navigation', {name: 'Product Menu'})
					.locator('div.sticker')
			).toHaveCSS('background-image', /company_logo|layout_set_logo/);
		});

		const category = page.getByRole('menuitem', {
			exact: true,
			expanded: false,
			name: 'Design',
		});

		await test.step('Assert the padding settings of a category', async () => {
			const {paddingBottom, paddingLeft, paddingRight, paddingTop} =
				await getComputedStyle(category);

			const horizontalThreshold = 20;

			expectPropertyToBeGreaterThan(paddingLeft, horizontalThreshold);
			expectPropertyToBeGreaterThan(paddingRight, horizontalThreshold);

			const verticalThreshold = 10;

			expectPropertyToBeGreaterThan(paddingTop, verticalThreshold);
			expectPropertyToBeGreaterThan(paddingBottom, verticalThreshold);
		});

		const portlet = page.getByRole('menuitem', {
			exact: true,
			name: 'Style Books',
		});

		await test.step('Expand the test category', async () => {
			await clickAndExpectToBeVisible({
				target: portlet,
				trigger: category,
			});
		});

		await test.step('Assert the padding settings of a portlet', async () => {
			const {paddingBottom, paddingLeft, paddingRight, paddingTop} =
				await getComputedStyle(portlet);

			const horizontalThreshold = 14;

			expectPropertyToBeGreaterThan(paddingLeft, horizontalThreshold);
			expectPropertyToBeGreaterThan(paddingRight, horizontalThreshold);

			const verticalThreshold = 8;

			expectPropertyToBeGreaterThan(paddingTop, verticalThreshold);
			expectPropertyToBeGreaterThan(paddingBottom, verticalThreshold);
		});
	}
);

test('Control menu on home page is styled correctly after refreshing page.', async ({
	page,
}) => {
	const expectControlMenuStyles = async () => {
		const controlMenu = page.locator('#controlMenu');
		await expect(controlMenu).toHaveCSS('display', 'flex');
		await expect(controlMenu).toHaveCSS('list-style', 'outside none none');
	};

	await test.step('Navigate to home page and close product menu', async () => {
		await page.goto(liferayConfig.environment.baseUrl);
		await closeProductMenu(page);
	});

	await test.step('Verify styles after refresh with menu closed', async () => {
		await page.reload();
		await expectControlMenuStyles();
	});

	await test.step('Open product menu and verify styles after refresh', async () => {
		await openProductMenu(page);
		await page.reload();
		await expectControlMenuStyles();
	});
});
