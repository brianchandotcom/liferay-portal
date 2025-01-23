/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import performLogin, {performLogout, userData} from '../../utils/performLogin';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	featureFlagsTest({
		'LPD-34938': {enabled: true},
	}),
	loginTest(),
	fragmentsPagesTest,
	pageEditorPagesTest,
	pageManagementSiteTest
);

test(
	'Show Marketplace Button and the related modal',
	{
		tag: ['@LPD-46101'],
	},
	async ({apiHelpers, fragmentsPage, page, site}) => {

		// Create a new user with admin role

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role.externalReferenceCode,
			user.id
		);

		// Log in with the new user

		await performLogout(page);

		await performLogin(page, user.alternateName);

		// Go to fragment administration and look for the badge in the marketplace button

		await fragmentsPage.goto(site.friendlyUrlPath);

		await expect(page.locator('[id$="marketplaceBadge"]')).toBeVisible();

		// Click the marketplace button and wait for the modal

		await page.getByTitle('Open Marketplace Explorer').click();

		await expect(
			page
				.getByRole('dialog')
				.getByRole('heading', {name: 'Marketplace is now in'})
		).toBeVisible();

		// Close the modal and check that the badge in the marketplace button is hide

		await page.getByRole('button', {name: 'Cancel'}).click();

		await expect(
			page.locator('[id$="marketplaceBadge"]')
		).not.toBeVisible();
	}
);
