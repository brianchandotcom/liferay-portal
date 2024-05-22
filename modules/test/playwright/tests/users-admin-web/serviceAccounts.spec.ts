/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test('LPS-204541 check export/import menu visibility', async ({
	serviceAccountsPage,
}) => {
	await serviceAccountsPage.goto();
	await (
		await serviceAccountsPage.serviceAccountActionMenu(
			'default-service-account'
		)
	).click();
	await expect(serviceAccountsPage.impersonateUserMenuItem).not.toBeVisible();
});
