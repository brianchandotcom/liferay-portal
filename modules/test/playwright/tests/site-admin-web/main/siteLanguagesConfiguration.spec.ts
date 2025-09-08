/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {localizationPagesTest} from './fixtures/localizationPagesTest';

const test = mergeTests(
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

		await siteSettingsLocalizationPage.goto(site.friendlyUrlPath);

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
				.getByLabel('Current', {exact: true})
				.selectOption(currentInstanceLanguages[i]);
			await page
				.getByRole('button', {
					name: 'Move selected items from Current to Available',
				})
				.click({force: true});
		}

		await page.getByRole('button', {name: 'Save'}).click();

		await page.waitForTimeout(500);

		await siteSettingsLocalizationPage.goto(site.friendlyUrlPath);

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
				.getByLabel('Available', {exact: true})
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

test('Add site name translation in site settings', async ({
	apiHelpers,
	page,
	site,
	siteSettingsPage,
}) => {
	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	await siteSettingsPage.goToSiteSetting(
		'Site Configuration',
		null,
		site.friendlyUrlPath
	);

	await page.waitForTimeout(300);

	await page
		.locator(
			'[id="_com_liferay_site_admin_web_portlet_SiteSettingsPortlet__com_liferay_site_admin_web_portlet_SiteSettingsPortlet_nameMenu"]'
		)
		.click();

	await page.getByRole('menuitem', {name: 'Spanish'}).click();

	const localizedSiteName = getRandomString();

	await page.getByLabel('Name').fill(localizedSiteName);

	await page.getByRole('button', {name: 'Save'}).click();

	await waitForAlert(page);

	await page.goto(`/es/web${site.friendlyUrlPath}`);

	await expect(page.getByText(localizedSiteName).first()).toBeVisible();

	await page.goto(`/en/web${site.friendlyUrlPath}`);

	await expect(page.getByText(site.name).first()).toBeVisible();
});

test('Cannot remove the site default language in instance settings', async ({
	localizationInstanceSettingsPage,
	page,
	site,
	siteSettingsLocalizationPage,
}) => {
	await siteSettingsLocalizationPage.setCustomDefaultLanguage(
		'Spanish (Spain)',
		site.friendlyUrlPath
	);

	await localizationInstanceSettingsPage.goto('Language');

	await page.waitForTimeout(500);
	await page
		.getByLabel('Current', {exact: true})
		.selectOption('Spanish (Spain)');
	await page
		.getByRole('button', {
			name: 'Move selected items from Current to Available',
		})
		.click({force: true});

	await page.getByRole('button', {name: 'Save'}).click();

	await waitForAlert(page, 'Your request failed to complete', {
		type: 'danger',
	});

	await expect(page.getByText(site.name)).toBeVisible();
});
