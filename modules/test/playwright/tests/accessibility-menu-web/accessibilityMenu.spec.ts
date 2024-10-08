/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../fixtures/accessibilityMenuPagesTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {instanceSettingsPagesTest} from '../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import {featureFlagPagesTest} from '../feature-flag-web/fixtures/featureFlagPagesTest';

const test = mergeTests(
	accessibilityMenuPagesTest,
	apiHelpersTest,
	featureFlagPagesTest,
	instanceSettingsPagesTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test('Verify that the default value is displayed when the user has never changed the accessibility setting, regardless of the login status', async ({
	accessibilityMenuPage,
	apiHelpers,
	instanceSettingsPage,
	page,
}) => {
	await instanceSettingsPage.goToInstanceSetting(
		'Accessibility',
		'Accessibility Menu'
	);

	await accessibilityMenuPage.enableAccessibilityMenu();

	const userAccount = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[userAccount.alternateName] = {
		name: userAccount.givenName,
		password: 'test',
		surname: userAccount.familyName,
	};

	try {
		await test.step('Verify that the "underlined links" toggle is off when logged out, then turn it on', async () => {
			await performLogout(page);

			await accessibilityMenuPage.openAccessibilityMenu();

			await expect(
				accessibilityMenuPage.underlinedLinksToggle
			).not.toBeChecked();

			await accessibilityMenuPage.toggleUnderlinedLinks(true);
		});

		await test.step('Login as a new user and assert that the "underlined links" guest preference is copied to the user', async () => {
			await performLogin(page, userAccount.alternateName);

			await accessibilityMenuPage.openAccessibilityMenu();

			await expect(
				accessibilityMenuPage.underlinedLinksToggle
			).toBeChecked();
		});

		await test.step('Verify that the "underlined links" toggle is on when logged out, then turn it off', async () => {
			await performLogout(page);

			await accessibilityMenuPage.openAccessibilityMenu();

			await expect(
				accessibilityMenuPage.underlinedLinksToggle
			).toBeChecked();

			await accessibilityMenuPage.toggleUnderlinedLinks(false);
		});

		await test.step('Confirm that the "underlined links" toggle is off when logged in, since user did not change the preference', async () => {
			await performLogin(page, userAccount.alternateName);

			await accessibilityMenuPage.openAccessibilityMenu();

			await expect(
				accessibilityMenuPage.underlinedLinksToggle
			).not.toBeChecked();
		});

		await test.step('Then, change the "underlined links" preference for that user for the first time', async () => {
			await accessibilityMenuPage.toggleUnderlinedLinks(true);
		});

		await test.step('Ensure that the "underlined links" toggle is off after logging out', async () => {
			await performLogout(page);

			await accessibilityMenuPage.openAccessibilityMenu();

			await expect(
				accessibilityMenuPage.underlinedLinksToggle
			).not.toBeChecked();
		});

		await test.step('Confirm that the "underlined links" toggle is on after logging in', async () => {
			await performLogin(page, userAccount.alternateName);

			await accessibilityMenuPage.openAccessibilityMenu();

			await expect(
				accessibilityMenuPage.underlinedLinksToggle
			).toBeChecked();
		});
	}
	finally {
		await test.step('Delete new user', async () => {
			await apiHelpers.headlessAdminUser.deleteUserAccount(
				Number(userAccount.id)
			);
		});
	}
});
