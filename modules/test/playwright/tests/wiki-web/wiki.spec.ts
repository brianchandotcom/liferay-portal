/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {wikiPagesTest} from '../../fixtures/wikiPagesTest';

export const test = mergeTests(loginTest(), wikiPagesTest);

test('LPD-26435 Icon menu should close when another icon menu is open', async ({
	page,
	wikiPage,
}) => {
	await wikiPage.goto();

	await wikiPage.createNewWikiNode('Wiki Node Title');

	const wikiNodeMenu = await page.locator(
		'[id="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_1_menu"]'
	);

	await test.step('Check menu gets closed', async () => {
		await wikiNodeMenu.click();

		const menuOne = await page.locator(
			'[aria-labelledby="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_1_menu"]'
		);

		await expect(menuOne).toBeVisible();

		await page
			.locator(
				'[id="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_2_menu"]'
			)
			.click();

		const menuTwo = await page.locator(
			'[aria-labelledby="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_2_menu"]'
		);

		await expect(menuTwo).toBeVisible();

		await expect(menuOne).toBeHidden();
	});

	await test.step('Wiki node is deleted', async () => {
		await wikiNodeMenu.click();

		await page.getByRole('link', {name: 'Delete'}).click();
	});
});
