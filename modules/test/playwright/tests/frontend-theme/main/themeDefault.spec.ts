/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {frontendThemePagesTest} from './fixtures/frontendThemePagesTest';

const CLASSIC_FOOTER_COLOR = 'rgb(48, 49, 63)';
const DIALECT_FOOTER_COLOR = 'rgb(51, 43, 74)';

const test = mergeTests(
	frontendThemePagesTest,
	loginTest(),
	featureFlagsTest({
		'LPD-30204': {enabled: true},
		'LPS-178052': {enabled: true},
	})
);

test(
	'Verifies dialect theme primary color',
	{tag: '@LPD-70288'},
	async ({pageHelper, themeHelper}) => {
		const sitePageName = getRandomString();
		const sitePage = await pageHelper.createPage(sitePageName);

		await pageHelper.goToPage(sitePage);

		await pageHelper.expectFooterToHaveBackgroundColor(
			CLASSIC_FOOTER_COLOR
		);

		await themeHelper.changePageThemeToDialect(sitePageName);

		await themeHelper.publishPage(sitePageName);

		await pageHelper.expectFooterToHaveBackgroundColor(
			DIALECT_FOOTER_COLOR
		);
	}
);

test(
	'A theme can be deactivated and reactivated',
	{tag: '@LPD-70288'},
	async ({pageHelper, themeHelper}) => {
		const [sitePage, sitePageName] =
			await test.step('Create site page', async () => {
				const sitePageName = getRandomString();
				const sitePage = await pageHelper.createPage(sitePageName);

				return [sitePage, sitePageName];
			});

		await test.step('Set page theme to dialect theme', async () => {
			await themeHelper.changePageThemeToDialect(sitePageName);

			await themeHelper.publishPage(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectFooterToHaveBackgroundColor(
				DIALECT_FOOTER_COLOR
			);
		});

		await test.step('Deactivates dialect theme', async () => {
			await themeHelper.deactivateDialectTheme(sitePageName);

			await themeHelper.expectCurrentThemeToBeClassic(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectFooterToHaveBackgroundColor(
				CLASSIC_FOOTER_COLOR
			);
		});

		await test.step('Reactivates dialect theme', async () => {
			await themeHelper.activateDialectTheme(sitePageName);

			await themeHelper.expectCurrentThemeToBeDialect(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectFooterToHaveBackgroundColor(
				DIALECT_FOOTER_COLOR
			);
		});
	}
);

test(
	'A theme can be uninstalled and reinstalled',
	{tag: '@LPD-70288'},
	async ({pageHelper, themeHelper}) => {
		const [sitePage, sitePageName] =
			await test.step('Create site page', async () => {
				const sitePageName = getRandomString();
				const sitePage = await pageHelper.createPage(sitePageName);

				return [sitePage, sitePageName];
			});

		await test.step('Set page theme to dialect theme', async () => {
			await themeHelper.changePageThemeToDialect(sitePageName);

			await themeHelper.publishPage(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectFooterToHaveBackgroundColor(
				DIALECT_FOOTER_COLOR
			);
		});

		await test.step('Uninstall dialect theme', async () => {
			await themeHelper.uninstallDialectTheme(sitePageName);

			await themeHelper.expectCurrentThemeToBeClassic(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectFooterToHaveBackgroundColor(
				CLASSIC_FOOTER_COLOR
			);
		});

		await test.step('Redeploy dialect theme', async () => {
			await themeHelper.reinstallDialectTheme(sitePageName);

			await themeHelper.expectCurrentThemeToBeDialect(sitePageName);

			await pageHelper.goToPage(sitePage);

			await pageHelper.expectFooterToHaveBackgroundColor(
				DIALECT_FOOTER_COLOR
			);
		});
	}
);
