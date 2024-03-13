/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {lockedItemsPagesTest} from './fixtures/lockedItemsPagesTest';

const test = mergeTests(
	loginTest(),
	apiHelpersTest,
	featureFlagsTest({
		'LPD-11003': true,
	}),
	lockedItemsPagesTest
);

test('the Locked Items page is shown', async ({lockedItemsPage}) => {
	await lockedItemsPage.goto();

	await expect(lockedItemsPage.pageTitle).toBeVisible();
	await expect(lockedItemsPage.lockedPagesMenuItem).toBeVisible();
});

test('the locked Pages page is shown', async ({lockedItemsPage}) => {
	await lockedItemsPage.goto();
	await lockedItemsPage.goToLockedPages();

	await expect(lockedItemsPage.lockedPagesTitle).toBeVisible();
});
