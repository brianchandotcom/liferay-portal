/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountSettingsPagesTest} from '../../fixtures/accountSettingsPagesTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(accountSettingsPagesTest, loginTest());

test('LPD-15689 roles in account settings should have no save button', async ({
	accountSettingsPage,
}) => {
	await accountSettingsPage.goToAccountSettingsRoles();

	await expect(accountSettingsPage.saveButton).not.toBeVisible();
});
