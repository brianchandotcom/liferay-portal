/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {readFile} from 'fs/promises';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {UsersAndOrganizationsPage} from '../../../pages/users-admin-web/UsersAndOrganizationsPage';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	instanceSettingsPagesTest,
	isolatedSiteTest,
	loginTest(),
	siteSettingsPagesTest
);

test('Asserts that a user can manage factory configurations', async ({
	instanceSettingsPage,
	page,
}) => {
	const providerNames = [getRandomString(), getRandomString()];

	await test.step('Add factory configurations', async () => {
		await instanceSettingsPage.goToInstanceSetting(
			'SSO',
			'OpenID Connect Provider Connection'
		);

		for (const providerName of providerNames) {
			await page.getByRole('link', {exact: true, name: 'Add'}).click();

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
		}
	});

	await test.step('Assert that the factory configurations were created successfully', async () => {
		for (const providerName of providerNames) {
			await expect(page.getByText(providerName)).toBeVisible();
		}
	});

	await test.step('Assert that a single factory configuration can be exported', async () => {
		const downloadPromise = page.waitForEvent('download');

		await instanceSettingsPage.exportFactoryEntry(providerNames[0]);

		const download = await downloadPromise;

		expect(download.suggestedFilename()).toEqual(
			expect.stringMatching(
				'com.liferay.portal.security.sso.openid.connect.internal.configuration.OpenIdConnectProviderConfiguration.scoped~(.*).config'
			)
		);

		const path = await download.path();

		const fileContent = await readFile(path, 'utf-8');

		expect(fileContent).toContain(providerNames[0]);
	});

	await test.step('Assert that multiple factory configuration entries can be exported', async () => {
		const downloadPromise = page.waitForEvent('download');

		await instanceSettingsPage.exportFactoryEntries();

		const download = await downloadPromise;

		expect(download.suggestedFilename()).toEqual(
			expect.stringMatching(
				'com.liferay.portal.security.sso.openid.connect.internal.configuration.OpenIdConnectProviderConfiguration.zip'
			)
		);
	});

	await test.step('Assert that factory configurations can be edited', async () => {
		await instanceSettingsPage.editFactoryEntry(providerNames[0]);

		const newProviderName = getRandomString();

		await page.getByLabel('Provider Name').fill(newProviderName);

		await instanceSettingsPage.saveAndWaitForAlert({
			autoClose: true,
			type: 'success',
		});

		expect(
			page.locator(`tbody tr:has-text("${providerNames[0]}")`)
		).toBeHidden();

		providerNames[0] = newProviderName;

		expect(
			page.locator(`tbody tr:has-text("${newProviderName}")`)
		).toBeVisible();
	});

	await test.step('Assert that factory configurations can be deleted', async () => {
		for (const providerName of providerNames) {
			await instanceSettingsPage.deleteFactoryEntry(providerName);

			await waitForAlert(page);
		}
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
