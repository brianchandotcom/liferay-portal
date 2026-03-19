/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';

const test = mergeTests(instanceSettingsPagesTest, loginTest());

test(
	'View portal settings',
	{tag: '@LPD-81993'},
	async ({defaultUserAssociationsPage, emailInstanceSettingsPage}) => {
		await test.step('View Default User Associations', async () => {
			await defaultUserAssociationsPage.goto();

			await expect(
				defaultUserAssociationsPage.applyToExistingUsersCheckbox
			).toBeVisible();
			await expect(
				defaultUserAssociationsPage.organizationSitesInput
			).toBeVisible();
			await expect(
				defaultUserAssociationsPage.regularRolesInput
			).toBeVisible();
			await expect(defaultUserAssociationsPage.sitesInput).toBeVisible();
			await expect(
				defaultUserAssociationsPage.userGroupsInput
			).toBeVisible();
		});

		await test.step('View Email Sender', async () => {
			await emailInstanceSettingsPage.goToEmailSender();

			await expect(
				emailInstanceSettingsPage.senderAddressInput
			).toBeVisible();
			await expect(
				emailInstanceSettingsPage.senderAddressLabel
			).toBeVisible();
			await expect(
				emailInstanceSettingsPage.senderNameInput
			).toBeVisible();
			await expect(
				emailInstanceSettingsPage.senderNameLabel
			).toBeVisible();
		});

		await test.step('View Account Created Notification', async () => {
			await emailInstanceSettingsPage.goToAccountCreatedNotification();

			await expect(
				emailInstanceSettingsPage.accountCreatedNotificationBodyWithoutPasswordLabel
			).toBeVisible();
			await expect(
				emailInstanceSettingsPage.accountCreatedNotificationEnabledCheckbox
			).toBeVisible();
			await expect(
				emailInstanceSettingsPage.accountCreatedNotificationSubjectInput
			).toHaveValue('[$PORTAL_URL$]: Your New Account');
			await expect(
				emailInstanceSettingsPage.accountCreatedNotificationSubjectLabel
			).toBeVisible();

			await expect(
				emailInstanceSettingsPage.accountCreatedNotificationBodyWithPasswordLinkLabel
			).toBeVisible();
		});
	}
);

test(
	'Can add additional mail host name',
	{tag: '@LPD-81993'},
	async ({instanceSettingsPage, mailHostNamesInstanceSettingsPage}) => {
		try {
			await mailHostNamesInstanceSettingsPage.goto();

			await expect(
				mailHostNamesInstanceSettingsPage.mailHostNamesInput
			).toBeVisible();

			await mailHostNamesInstanceSettingsPage.mailHostNamesInput.fill(
				'newhostname.com'
			);

			await instanceSettingsPage.saveAndWaitForAlert();

			await mailHostNamesInstanceSettingsPage.goto();

			await expect(
				mailHostNamesInstanceSettingsPage.mailHostNamesInput
			).toHaveValue('newhostname.com');
		}
		finally {
			await mailHostNamesInstanceSettingsPage.goto();

			await mailHostNamesInstanceSettingsPage.mailHostNamesInput.clear();

			await instanceSettingsPage.saveAndWaitForAlert();
		}
	}
);

test(
	'Can add contact information in instance configuration',
	{tag: '@LPD-81993'},
	async ({contactInformationInstanceSettingsPage, instanceSettingsPage}) => {
		try {
			await contactInformationInstanceSettingsPage.goto();

			await contactInformationInstanceSettingsPage.postalCodeInput.clear();
			await contactInformationInstanceSettingsPage.countrySelect.selectOption(
				{label: 'China'}
			);
			await contactInformationInstanceSettingsPage.regionSelect.selectOption(
				{label: 'Liaoning Sheng'}
			);
			await contactInformationInstanceSettingsPage.addressTypeSelect.selectOption(
				{label: 'Shipping'}
			);
			await contactInformationInstanceSettingsPage.street1Input.fill(
				'Street1'
			);
			await contactInformationInstanceSettingsPage.street2Input.fill(
				'Street2'
			);
			await contactInformationInstanceSettingsPage.street3Input.fill(
				'Street3'
			);
			await contactInformationInstanceSettingsPage.cityInput.fill('City');
			await contactInformationInstanceSettingsPage.postalCodeInput.fill(
				'116023'
			);
			await contactInformationInstanceSettingsPage.numberInput.fill(
				'041188120855'
			);
			await contactInformationInstanceSettingsPage.extensionInput.fill(
				'86'
			);
			await contactInformationInstanceSettingsPage.emailAddressInput.fill(
				'testsample@liferay.com'
			);
			await contactInformationInstanceSettingsPage.urlInput.fill(
				'http://liferay.com'
			);

			await instanceSettingsPage.saveAndWaitForAlert();

			await contactInformationInstanceSettingsPage.goto();

			await expect(
				contactInformationInstanceSettingsPage.cityInput
			).toHaveValue('City');
			await expect(
				contactInformationInstanceSettingsPage.emailAddressInput
			).toHaveValue('testsample@liferay.com');
			await expect(
				contactInformationInstanceSettingsPage.street1Input
			).toHaveValue('Street1');
			await expect(
				contactInformationInstanceSettingsPage.street2Input
			).toHaveValue('Street2');
			await expect(
				contactInformationInstanceSettingsPage.street3Input
			).toHaveValue('Street3');
			await expect(
				contactInformationInstanceSettingsPage.urlInput
			).toHaveValue('http://liferay.com');
		}
		finally {
			await contactInformationInstanceSettingsPage.goto();

			await contactInformationInstanceSettingsPage.countrySelect.selectOption(
				{label: ''}
			);
			await contactInformationInstanceSettingsPage.cityInput.fill('-');
			await contactInformationInstanceSettingsPage.emailAddressInput.clear();
			await contactInformationInstanceSettingsPage.extensionInput.clear();
			await contactInformationInstanceSettingsPage.numberInput.clear();
			await contactInformationInstanceSettingsPage.postalCodeInput.clear();
			await contactInformationInstanceSettingsPage.street1Input.fill('-');
			await contactInformationInstanceSettingsPage.street2Input.clear();
			await contactInformationInstanceSettingsPage.street3Input.clear();
			await contactInformationInstanceSettingsPage.urlInput.clear();

			await instanceSettingsPage.saveAndWaitForAlert();
		}
	}
);
