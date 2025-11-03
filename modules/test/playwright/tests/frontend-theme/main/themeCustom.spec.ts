/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {
	CLASSIC_BACKGROUND_COLOR,
	TEST_THEME_BACKGROUND_COLOR,
} from './constants';
import {themesTest} from './extensions/themesTest';

const test = mergeTests(
	themesTest,
	loginTest(),
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	})
);

test(
	'Verifies test theme background color',
	{tag: '@LPD-70288'},
	async ({page, pageFixture, themeFixture}) => {
		const sitePageName = getRandomString();
		const sitePage = await pageFixture.createPage(sitePageName);

		await pageFixture.goToPage(sitePage);

		expect(page.locator('body')).toHaveCSS(
			'background-color',
			CLASSIC_BACKGROUND_COLOR
		);

		await themeFixture.addTestTheme();
		await themeFixture.changePageThemeToTestTheme(sitePageName);
		await themeFixture.publishPage(sitePageName);

		await expect(page.locator('body')).toHaveCSS(
			'background-color',
			TEST_THEME_BACKGROUND_COLOR
		);
	}
);

test(
	'A custom theme can be deactivated and reactivated',
	{tag: '@LPD-70288'},
	async ({page, pageFixture, themeFixture}) => {
		const [sitePageName, sitePage] =
			await test.step('Create site page', async () => {
				const sitePageName = getRandomString();
				const sitePage = await pageFixture.createPage(sitePageName);

				return [sitePageName, sitePage];
			});

		await test.step('Set page theme to test theme', async () => {
			await themeFixture.addTestTheme();
			await themeFixture.changePageThemeToTestTheme(sitePageName);
			await themeFixture.publishPage(sitePageName);

			await pageFixture.goToPage(sitePage);

			await expect(page.locator('body')).toHaveCSS(
				'background-color',
				TEST_THEME_BACKGROUND_COLOR
			);
		});

		await test.step('Deactivates test theme', async () => {
			await themeFixture.deactivateTestTheme(sitePageName);
			await themeFixture.expectCurrentThemeToBeClassic(sitePageName);
			await pageFixture.goToPage(sitePage);

			await expect(page.locator('body')).toHaveCSS(
				'background-color',
				CLASSIC_BACKGROUND_COLOR
			);
		});

		await test.step('Reactivates test theme', async () => {
			await themeFixture.activateTestTheme(sitePageName);
			await themeFixture.expectCurrentThemeToBeTestTheme(sitePageName);
			await pageFixture.goToPage(sitePage);

			await expect(page.locator('body')).toHaveCSS(
				'background-color',
				TEST_THEME_BACKGROUND_COLOR
			);
		});
	}
);

test(
	'A custom theme can be uninstalled and redeployed',
	{tag: '@LPD-70288'},
	async ({page, pageFixture, themeFixture}) => {
		const [sitePageName, sitePage] =
			await test.step('Create site page', async () => {
				const sitePageName = getRandomString();
				const sitePage = await pageFixture.createPage(sitePageName);

				return [sitePageName, sitePage];
			});

		await test.step('Set page theme to test theme', async () => {
			await themeFixture.addTestTheme();
			await themeFixture.changePageThemeToTestTheme(sitePageName);
			await themeFixture.publishPage(sitePageName);

			await pageFixture.goToPage(sitePage);

			await expect(page.locator('body')).toHaveCSS(
				'background-color',
				TEST_THEME_BACKGROUND_COLOR
			);
		});

		await test.step('Uninstall test theme', async () => {
			await themeFixture.uninstallTestTheme(sitePageName);
			await themeFixture.expectCurrentThemeToBeClassic(sitePageName);
			await pageFixture.goToPage(sitePage);

			await expect(page.locator('body')).toHaveCSS(
				'background-color',
				CLASSIC_BACKGROUND_COLOR
			);
		});

		await test.step('Redeploy test theme', async () => {
			await themeFixture.redeployTestTheme(sitePageName);
			await themeFixture.expectCurrentThemeToBeTestTheme(sitePageName);
			await pageFixture.goToPage(sitePage);

			await expect(page.locator('body')).toHaveCSS(
				'background-color',
				TEST_THEME_BACKGROUND_COLOR
			);
		});
	}
);
