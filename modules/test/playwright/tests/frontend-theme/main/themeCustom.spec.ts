/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {
	CLASSIC_BACKGROUND_COLOR,
	TEST_THEME_BACKGROUND_COLOR,
} from './constants';
import {frontendThemePagesTest} from './fixtures/frontendThemePagesTest';

const test = mergeTests(
	frontendThemePagesTest,
	loginTest(),
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	})
);

test.beforeEach(async ({themeHelper}) => {
	await themeHelper.installTestTheme();
});

test.afterEach(async ({themeHelper}) => {
	await themeHelper.uninstallTestTheme();
});

test(
	'Verifies test theme background color',
	{tag: '@LPD-70288'},
	async ({pageHelper, themeHelper}) => {
		const sitePageName = getRandomString();
		const sitePage = await pageHelper.createPage(sitePageName);

		await pageHelper.goToPage(sitePage);

		await pageHelper.expectBodyToHaveBackgroundColor(
			CLASSIC_BACKGROUND_COLOR
		);

		await themeHelper.changePageThemeToTestTheme(sitePageName);

		await themeHelper.publishPage(sitePageName);

		await pageHelper.expectBodyToHaveBackgroundColor(
			TEST_THEME_BACKGROUND_COLOR
		);
	}
);

test(
	'A custom theme can be deactivated and reactivated',
	{tag: '@LPD-70288'},
	async ({pageHelper, themeHelper}) => {
		const [sitePageName, sitePage] =
			await test.step('Create site page', async () => {
				const sitePageName = getRandomString();

				const sitePage = await pageHelper.createPage(sitePageName);

				return [sitePageName, sitePage];
			});

		await test.step('Set page theme to test theme', async () => {
			await themeHelper.changePageThemeToTestTheme(sitePageName);

			await themeHelper.publishPage(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectBodyToHaveBackgroundColor(
				TEST_THEME_BACKGROUND_COLOR
			);
		});

		await test.step('Deactivates test theme', async () => {
			await themeHelper.deactivateTestTheme(sitePageName);

			await themeHelper.expectCurrentThemeToBeClassic(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectBodyToHaveBackgroundColor(
				CLASSIC_BACKGROUND_COLOR
			);
		});

		await test.step('Reactivates test theme', async () => {
			await themeHelper.activateTestTheme(sitePageName);

			await themeHelper.expectCurrentThemeToBeTestTheme(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectBodyToHaveBackgroundColor(
				TEST_THEME_BACKGROUND_COLOR
			);
		});
	}
);

test(
	'A custom theme can be uninstalled and reinstalled',
	{tag: '@LPD-70288'},
	async ({pageHelper, themeHelper}) => {
		const [sitePageName, sitePage] =
			await test.step('Create site page', async () => {
				const sitePageName = getRandomString();

				const sitePage = await pageHelper.createPage(sitePageName);

				return [sitePageName, sitePage];
			});

		await test.step('Set page theme to test theme', async () => {
			await themeHelper.changePageThemeToTestTheme(sitePageName);

			await themeHelper.publishPage(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectBodyToHaveBackgroundColor(
				TEST_THEME_BACKGROUND_COLOR
			);
		});

		await test.step('Uninstall test theme', async () => {
			await themeHelper.uninstallTestTheme(sitePageName);

			await themeHelper.expectCurrentThemeToBeClassic(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectBodyToHaveBackgroundColor(
				CLASSIC_BACKGROUND_COLOR
			);
		});

		await test.step('Redeploy test theme', async () => {
			await themeHelper.reinstallTestTheme(sitePageName);

			await themeHelper.expectCurrentThemeToBeTestTheme(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectBodyToHaveBackgroundColor(
				TEST_THEME_BACKGROUND_COLOR
			);
		});
	}
);
