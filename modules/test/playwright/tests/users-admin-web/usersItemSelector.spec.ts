/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {UsersAdminItemSelectorPageTest} from '../../fixtures/usersAdminItemSelectorPagesTest';

export const test = mergeTests(loginTest(), UsersAdminItemSelectorPageTest);

test('LPD-1288 users item selector table clickable', async ({
	usersAdminItemSelectorPage,
}) => {
	const userName = 'Test Test';

	await usersAdminItemSelectorPage.goToOauth2Administration();
	await usersAdminItemSelectorPage.creationMenuNewButton.click();

	await expect(
		usersAdminItemSelectorPage.clientCredentialUserNameTextbox
	).toHaveValue('test');

	await usersAdminItemSelectorPage.selectUserButton.click();

	await expect(
		usersAdminItemSelectorPage.usersFrameSearchButton
	).toBeEnabled();

	await usersAdminItemSelectorPage.usersFrameTableRow(userName).click();

	await usersAdminItemSelectorPage
		.usersFrameTableRow(userName)
		.waitFor({state: 'detached'});

	await expect(
		usersAdminItemSelectorPage.clientCredentialUserNameTextbox
	).toHaveValue(userName);
});
