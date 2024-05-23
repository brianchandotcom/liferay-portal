/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../fixtures/systemSettingsPageTest';

export const test = mergeTests(loginTest(), systemSettingsPageTest);

test('@LPD-26435 Icon menu should close when another icon menu is open', async ({
	page,
}) => {
	await test.step('Go to wiki', async () => {
		const openProductMenu = page.getByLabel('Open Product Menu');

		const contentAndData = page.getByRole('menuitem', {
			name: 'Content & Data',
		});

		if (await openProductMenu.isVisible()) {
			openProductMenu.click();
		}

		await contentAndData.waitFor({state: 'visible'});
		await contentAndData.click();

		const wiki = page.getByRole('menuitem', {name: 'Wiki'});

		await wiki.waitFor({state: 'visible'});
		wiki.click();
	});

	await test.step('Create new wiki', async () => {
		const newWikiButton = page.getByRole('link', {name: 'Add Wiki'});

		await newWikiButton.waitFor({state: 'visible'});
		await newWikiButton.click();

		const nameInput = page.getByLabel('Name');

		await nameInput.waitFor({state: 'visible'});
		await nameInput.click();
		await nameInput.fill('test');

		await page.getByRole('button', {name: 'Save'}).click();
	});

	await test.step('Check menu gets closed', async () => {
		const menuOneButton = page.locator(
			'[id="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_1_menu"]'
		);

		await menuOneButton.waitFor({state: 'visible'});
		await menuOneButton.click();

		const menuOne = page.locator(
			'[aria-labelledby="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_1_menu"]'
		);

		await menuOne.waitFor({state: 'visible'});

		const menuTwoButton = page.locator(
			'[id="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_2_menu"]'
		);

		await menuTwoButton.click();

		const menuTwo = page.locator(
			'[aria-labelledby="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_2_menu"]'
		);

		await menuTwo.waitFor({state: 'visible'});

		await expect(menuOne).toBeHidden();
	});
});
