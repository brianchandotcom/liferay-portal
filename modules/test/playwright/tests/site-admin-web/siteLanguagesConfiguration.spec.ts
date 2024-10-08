/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../fixtures/siteSettingsPagesTest';
import getRandomString from '../../utils/getRandomString';
import {localizationPagesTest} from '../site-admin-web/fixtures/localizationPagesTest';

const test = mergeTests(
	applicationsMenuPageTest,
	dataApiHelpersTest,
	isolatedSiteTest,
	localizationPagesTest,
	loginTest(),
	siteSettingsPagesTest
);

test(
	'Check current site locales based on instance locales',
	{
		tag: '@LPD-37997',
	},
	async ({
		apiHelpers,
		applicationsMenuPage,
		localizationInstanceSettingsPage,
		page,
		siteSettingsLocalizationPage,
	}) => {
		const site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		await localizationInstanceSettingsPage.goto('Language');

		let currentInstanceLanguages =
			await localizationInstanceSettingsPage.currentLanguages.allInnerTexts();

		currentInstanceLanguages = currentInstanceLanguages[0].split('\n');

		let defaultInstanceLanguage =
			await localizationInstanceSettingsPage.defaultLanguage.textContent();

		defaultInstanceLanguage = defaultInstanceLanguage.replace(
			/[\n\t]/g,
			''
		);

		for (let i = 0; i < currentInstanceLanguages.length; i++) {
			await expect
				.soft(
					page.getByLabel('Current').getByRole('option', {
						name: currentInstanceLanguages[i],
					})
				)
				.toBeVisible();
		}

		await applicationsMenuPage.goToSite(site.name);

		await siteSettingsLocalizationPage.goto();

		for (let i = 0; i < currentInstanceLanguages.length; i++) {
			await expect
				.soft(siteSettingsLocalizationPage.availableLanguages)
				.toContainText(currentInstanceLanguages[i]);
		}

		currentInstanceLanguages = currentInstanceLanguages.filter(
			(item) => item !== defaultInstanceLanguage
		);

		await localizationInstanceSettingsPage.goto('Language');

		for (let i = 0; i < currentInstanceLanguages.length; i++) {
			await page.waitForTimeout(500);
			await page
				.getByLabel('Current')
				.selectOption(currentInstanceLanguages[i]);
			await page
				.getByRole('button', {
					name: 'Move selected items from Current to Available',
				})
				.click({force: true});
		}

		await page.getByRole('button', {name: 'Save'}).click();

		await page.waitForTimeout(500);

		await applicationsMenuPage.goToSite(site.name);

		await siteSettingsLocalizationPage.goto();

		await expect
			.soft(siteSettingsLocalizationPage.availableLanguages)
			.toContainText(defaultInstanceLanguage);

		for (let i = 0; i < currentInstanceLanguages.length; i++) {
			await expect
				.soft(siteSettingsLocalizationPage.availableLanguages)
				.not.toContainText(currentInstanceLanguages[i]);
		}

		await localizationInstanceSettingsPage.goto('Language');

		for (let i = 0; i < currentInstanceLanguages.length; i++) {
			await page.waitForTimeout(500);
			await page
				.getByLabel('Available')
				.selectOption(currentInstanceLanguages[i]);
			await page
				.getByRole('button', {
					name: 'Move selected items from Available to Current',
				})
				.click({force: true});
		}

		await page.getByRole('button', {name: 'Save'}).click();
	}
);
