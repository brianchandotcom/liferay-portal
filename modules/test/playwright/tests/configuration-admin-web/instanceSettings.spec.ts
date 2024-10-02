/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {readFile} from 'fs/promises';

import {loginTest} from '../../fixtures/loginTest';
import {InstanceSettingsPage} from '../../pages/configuration-admin-web/InstanceSettingsPage';
import {UsersAndOrganizationsPage} from '../../pages/users-admin-web/UsersAndOrganizationsPage';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(loginTest());

test('Asserts that a user can export a configuration', async ({page}) => {
	const emailDomainValidationSwitcher = page.getByRole('switch', {
		name: 'Enable Email Domain Validation',
	});

	const instanceSettingsPage = new InstanceSettingsPage(page);

	await instanceSettingsPage.goToInstanceSetting('Accounts', 'Email Domains');

	try {
		await emailDomainValidationSwitcher.check();

		await instanceSettingsPage.saveButton.click();

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

		await instanceSettingsPage.saveButton.click();
	}
});

test('LPD-35562 Enter reserved screen name', async ({page}) => {
	const instanceSettingsPage = new InstanceSettingsPage(page);

	const emailAddress = getRandomString() + '@liferay.com';
	const firstName = getRandomString();
	const lastName = getRandomString();
	const reservedScreenName = getRandomString();

	await instanceSettingsPage.goToInstanceSetting(
		'User Authentication',
		'Reserved Credentials'
	);

	await page.getByLabel('Screen Names').fill(reservedScreenName);

	await instanceSettingsPage.saveAndWaitForAlert(
		'Success:Your request completed successfully.',
		{autoClose: true, type: 'success'}
	);

	const usersAndOrganizationsPage = new UsersAndOrganizationsPage(page);

	await usersAndOrganizationsPage.goToUsers();

	await page.getByRole('link', {name: 'Add User'}).click();

	await page.getByLabel('Screen Name').fill(reservedScreenName);

	await page.getByLabel('Email Address').fill(emailAddress);

	await page.getByLabel('First Name').fill(firstName);

	await page.getByLabel('Last Name').fill(lastName);

	await instanceSettingsPage.saveAndWaitForAlert(
		'Error:The screen name you requested is reserved.',
		{autoClose: false, type: 'danger'}
	);
});
