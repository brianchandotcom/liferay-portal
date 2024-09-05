/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {uiElementsPageTest} from '../../fixtures/uiElementsTest';
import getRandomString from '../../utils/getRandomString';

const baseTest = mergeTests(isolatedSiteTest, loginTest());

export const test = mergeTests(
	baseTest,
	pagesAdminPagesTest,
	pageViewModePagesTest,
	productMenuPageTest,
	uiElementsPageTest
);

test(
	'Checks center text alignment in Look & Feel',
	{
		tag: '@LPD-31641',
	},
	async ({page, pagesAdminPage, site, widgetPagePage}) => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		const name = getRandomString();
		await pagesAdminPage.addWidgetPage({name});

		await pagesAdminPage.goto(site.friendlyUrlPath);
		await page.getByLabel(name, {exact: true}).click();

		await widgetPagePage.addPortlet('Asset Publisher');
		await page
			.locator('.portlet-asset-publisher')
			.first()
			.getByLabel('Options')
			.click();
		await page
			.getByRole('menuitem', {name: 'Look and Feel Configuration'})
			.click();
		const lookAndFeelFrame = page.frameLocator(
			'iframe[title="Look and Feel Configuration"]'
		);
		await lookAndFeelFrame.getByRole('tab', {name: 'Text Styles'}).click();
		await lookAndFeelFrame.getByLabel('Alignment').selectOption('center');
		await lookAndFeelFrame.getByRole('button', {name: 'Save'}).click();

		await expect(lookAndFeelFrame.getByLabel('Alignment')).toHaveValue(
			'center'
		);
	}
);
