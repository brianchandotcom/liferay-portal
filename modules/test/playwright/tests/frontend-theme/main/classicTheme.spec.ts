/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {pagesPagesTest} from '../../layout-admin-web/main/fixtures/pagesPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	pageEditorPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginTest(),
	pagesPagesTest,
	pagesAdminPagesTest,
	pageViewModePagesTest
);

const CUSTOM_BACKGROUND_COLOR = 'rgb(66, 244, 197)';
const MENU_DISPLAY_NAME = 'Menu Display';
const PAGE_NAME = getRandomString();

const togglePortletOptions = async (portletName: string, page: Page) => {
	await page
		.locator('.portlet-topper', {hasText: portletName})
		.getByLabel('Options')
		.click();
};

const assertPortletOptionsVisible = async (
	portletName: string,
	actions: string[],
	page: Page
) => {
	await togglePortletOptions(portletName, page);

	for (const action of actions) {
		await expect(
			page.getByRole('menuitem', {exact: true, name: action})
		).toBeVisible();
	}

	await togglePortletOptions(portletName, page);
};

test('Verify custom look and feel settings can be applied to page.', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pageEditorPage,
	pagesAdminPage,
	site,
	widgetPagePage,
}) => {
	const layout =
		await test.step('Given a page with classic theme applied.', async () => {
			const layout = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: PAGE_NAME,
			});

			return layout;
		});

	await test.step('When look and feel settings and CSS is edited.', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.goToDesignTabConfiguration(PAGE_NAME);

		await pagesAdminPage.defineCustomThemeRadio.click();

		await page
			.getByRole('checkbox', {
				exact: true,
				name: 'Show Header Search',
			})
			.uncheck();

		await page
			.getByRole('checkbox', {
				exact: true,
				name: 'Show Maximize/Minimize Application Links',
			})
			.check();

		await page
			.getByRole('textbox', {exact: true, name: 'CSS'})
			.fill(
				`body, #wrapper {background-color: ${CUSTOM_BACKGROUND_COLOR};}`
			);

		await pageConfigurationPage.save();

		await expect(
			page.getByText(
				'These design configurations are now saved in a draft'
			)
		).toBeVisible();

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.publishPage();
	});

	await test.step('Assert that the custom CSS is present.', async () => {
		await page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath || layout.friendlyURL}`
		);

		await expect(page.locator('body')).toHaveCSS(
			'background-color',
			CUSTOM_BACKGROUND_COLOR
		);

		await expect(page.locator('#wrapper')).toHaveCSS(
			'background-color',
			CUSTOM_BACKGROUND_COLOR
		);
	});

	await test.step('Assert that the header search bar is not present.', async () => {
		await expect(
			page.locator('.portlet-topper', {hasText: 'Search Bar'})
		).toBeHidden();
	});

	await test.step('Assert that the menu display can be minimized/maximized.', async () => {
		await assertPortletOptionsVisible(
			MENU_DISPLAY_NAME,
			['Maximize', 'Minimize'],
			page
		);

		await widgetPagePage.clickOnAction(MENU_DISPLAY_NAME, 'Minimize');

		await expect(
			page.locator('.portlet-topper', {hasText: MENU_DISPLAY_NAME})
		).toBeVisible();

		await expect(
			page
				.locator('.portlet-content', {hasText: PAGE_NAME})
				.locator('.portlet-body')
		).toBeHidden();
	});

	await test.step('Then restore menu display.', async () => {
		await widgetPagePage.clickOnAction(MENU_DISPLAY_NAME, 'Restore');

		await expect(
			page
				.locator('.portlet-content', {hasText: PAGE_NAME})
				.locator('.portlet-body')
		).toBeVisible();
	});
});
