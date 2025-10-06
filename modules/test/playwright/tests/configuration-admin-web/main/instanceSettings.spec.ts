/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {readFile} from 'fs/promises';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {UsersAndOrganizationsPage} from '../../../pages/users-admin-web/UsersAndOrganizationsPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	accessibilityMenuPagesTest,
	instanceSettingsPagesTest,
	isolatedSiteTest,
	loginTest(),
	siteSettingsPagesTest
);

test.describe('Factory Configuration Tests', () => {
	test('Asserts that a user can create multiple factory configurations', async ({
		instanceSettingsPage,
		page,
	}) => {
		let providerName = getRandomString();

		await instanceSettingsPage.goToInstanceSetting(
			'SSO',
			'OpenID Connect Provider Connection'
		);

		await page.getByRole('link', {name: 'Add'}).click();

		await page.getByLabel('Provider Name').fill(providerName);

		await page
			.getByLabel('OpenID Connect Client ID')
			.fill(getRandomString());

		await page
			.getByLabel('OpenID Connect Client Secret')
			.fill(getRandomString());

		await instanceSettingsPage.saveAndWaitForAlert({
			autoClose: true,
			type: 'success',
		});

		await expect(page.getByText(providerName)).toBeVisible();

		providerName = getRandomString();

		await page.getByRole('link', {name: 'Add'}).click();

		await page.getByLabel('Provider Name').fill(providerName);

		await page
			.getByLabel('OpenID Connect Client ID')
			.fill(getRandomString());

		await page
			.getByLabel('OpenID Connect Client Secret')
			.fill(getRandomString());

		await instanceSettingsPage.saveAndWaitForAlert({
			autoClose: true,
			type: 'success',
		});

		await expect(page.getByText(providerName)).toBeVisible();
	});

	test('Asserts that a user can delete a factory configuration', async ({
		instanceSettingsPage,
		page,
	}) => {
		await instanceSettingsPage.goToInstanceSetting(
			'SSO',
			'OpenID Connect Provider Connection'
		);
		const firstRow = page.locator('tbody tr:first-of-type');

		const providerName = await firstRow.innerText();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByText('Delete').first(),
			trigger: firstRow.getByRole('button'),
		});

		await expect(
			page.getByText('Success:Your request completed successfully.')
		).toBeVisible();

		await expect(
			await page.locator('tbody tr:first-of-type').innerText()
		).not.toBe(providerName);

		await instanceSettingsPage.goToInstanceSetting(
			'SSO',
			'OpenID Connect Provider Connection'
		);

		await expect(
			await page.locator('tbody tr:first-of-type').innerText()
		).not.toBe(providerName);
	});
});

test('Asserts that a user can export a configuration', async ({
	instanceSettingsPage,
	page,
}) => {
	const emailDomainValidationSwitcher = page.getByRole('switch', {
		name: 'Enable Email Domain Validation',
	});

	await instanceSettingsPage.goToInstanceSetting('Accounts', 'Email Domains');

	try {
		await emailDomainValidationSwitcher.check();

		await instanceSettingsPage.saveAndWaitForAlert();

		await expect(emailDomainValidationSwitcher).toBeChecked();

		page.on('download', async (download) => {
			expect(download.suggestedFilename()).toEqual(
				expect.stringMatching(
					'com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration.scoped~(.*).config'
				)
			);

			const path = await download.path();

			const fileContent = await readFile(path, 'utf-8');

			expect(
				fileContent.includes('enableEmailDomainValidation=B"true"')
			).toBeTruthy();
		});

		await instanceSettingsPage.exportInstanceSetting();
	}
	finally {
		await emailDomainValidationSwitcher.uncheck();

		await instanceSettingsPage.saveAndWaitForAlert();
	}
});

test('LPD-35562 Enter reserved screen name', async ({
	instanceSettingsPage,
	page,
}) => {
	const emailAddress = getRandomString() + '@liferay.com';
	const firstName = getRandomString();
	const lastName = getRandomString();
	const reservedScreenName = getRandomString();

	await instanceSettingsPage.goToInstanceSetting(
		'User Authentication',
		'Reserved Credentials'
	);

	await page.getByLabel('Screen Names').fill(reservedScreenName);

	await instanceSettingsPage.saveAndWaitForAlert({
		autoClose: true,
		type: 'success',
	});

	const usersAndOrganizationsPage = new UsersAndOrganizationsPage(page);

	await usersAndOrganizationsPage.goToUsers();

	await page.getByRole('link', {name: 'Add User'}).click();

	await page.getByLabel('Screen Name').fill(reservedScreenName);

	await page.getByLabel('Email Address').fill(emailAddress);

	await page.getByLabel('First Name').fill(firstName);

	await page.getByLabel('Last Name').fill(lastName);

	await instanceSettingsPage.saveAndWaitForAlert({
		autoClose: false,
		text: 'Error:The screen name you requested is reserved.',
		type: 'danger',
	});
});

test('LPD-38043 Assert that a configuration at the site scope can override a falsy configuration at the instance scope', async ({
	accessibilityMenuPage,
	instanceSettingsPage,
	page,
	site,
	siteSettingsPage,
}) => {
	await siteSettingsPage.goto(site.friendlyUrlPath);

	if (await accessibilityMenuPage.isAccessibilityMenuAttached()) {
		await test.step('Disable the instance scoped accessibility menu configuration', async () => {
			await instanceSettingsPage.goToInstanceSetting(
				'Accessibility',
				'Accessibility Menu'
			);

			await accessibilityMenuPage.enableAccessibilityMenuCheckbox.uncheck();

			await instanceSettingsPage.saveAndWaitForAlert();
		});
	}

	await test.step('Check that the accessibility menu is not accessible in the site scope', async () => {
		await siteSettingsPage.goToSiteSetting(
			'Accessibility',
			'Accessibility Menu',
			site.friendlyUrlPath
		);

		await page.waitForLoadState();

		await expect(
			accessibilityMenuPage.openAccessibilityMenuButton
		).not.toBeAttached();
	});

	await test.step('Enable the site accessibility menu configuration', async () => {
		await accessibilityMenuPage.enableAccessibilityMenu();
	});

	await test.step('Check that the accessibility menu is accessible in the site scope', async () => {
		await expect(
			accessibilityMenuPage.openAccessibilityMenuButton
		).toBeAttached();
	});

	await test.step('Check that the accessibility menu is not accessible in the instance scope', async () => {
		await instanceSettingsPage.goToInstanceSetting(
			'Accessibility',
			'Accessibility Menu'
		);

		await page.waitForLoadState();

		await expect(
			accessibilityMenuPage.openAccessibilityMenuButton
		).not.toBeAttached();
	});
});
