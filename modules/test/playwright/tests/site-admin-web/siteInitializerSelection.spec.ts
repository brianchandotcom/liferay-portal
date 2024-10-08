/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {localizationSiteSettingsPageTest} from '../../fixtures/localizationSiteSettingsPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {checkAccessibility} from '../../utils/checkAccessibility';
import getRandomString from '../../utils/getRandomString';
import {localizationPagesTest} from '../site-admin-web/fixtures/localizationPagesTest';
import {selectSiteInitializerPagesTest} from './fixtures/selectSiteInitializerPagesTest';

const test = mergeTests(
	applicationsMenuPageTest,
	dataApiHelpersTest,
	isolatedSiteTest,
	localizationPagesTest,
	loginTest(),
	selectSiteInitializerPagesTest,
	localizationSiteSettingsPageTest
);

test('Check select site initializers accessibility', async ({
	page,
	selectSiteInitializerPage,
	site,
}) => {

	// Go to site initializers selection page

	await selectSiteInitializerPage.goto(site.friendlyUrlPath);

	// Check all of them have correct label

	const cards = await page.locator('.card').all();

	for (const card of cards) {
		await expect(card.getByLabel('Select Template:')).toBeVisible();
	}

	// Check accessibility

	await checkAccessibility({
		page,
		selectors: ['.portlet-content-container'],
	});
});

test('Check current site locales based on instance locales', async ({
	apiHelpers,
	applicationsMenuPage,
	localizationInstanceSettingsPage,
	localizationSiteSettingsPage,
	page,
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

	defaultInstanceLanguage = defaultInstanceLanguage.replace(/[\n\t]/g, '');

	for (let i = 0; i < currentInstanceLanguages.length; i++) {
		await expect
			.soft(
				page
					.getByLabel('Current')
					.getByRole('option', {name: currentInstanceLanguages[i]})
			)
			.toBeVisible();
	}

	await applicationsMenuPage.goToSite(site.name);

	await localizationSiteSettingsPage.goto();

	for (let i = 0; i < currentInstanceLanguages.length; i++) {
		await expect
			.soft(localizationSiteSettingsPage.availableLanguages)
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

	await localizationSiteSettingsPage.goto();

	await expect
		.soft(localizationSiteSettingsPage.availableLanguages)
		.toContainText(defaultInstanceLanguage);

	for (let i = 0; i < currentInstanceLanguages.length; i++) {
		await expect
			.soft(localizationSiteSettingsPage.availableLanguages)
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
});
