/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {serverAdministrationPageTest} from '../../fixtures/serverAdministrationPageTest';
import {userGroupsPageTest} from '../../fixtures/userGroupsPageTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {virtualInstancesPagesTest} from '../../fixtures/virtualInstancesPagesTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {userData} from '../../utils/performLogin';

export const test = mergeTests(
	applicationsMenuPageTest,
	serverAdministrationPageTest,
	userGroupsPageTest,
	usersAndOrganizationsPagesTest,
	virtualInstancesPagesTest
);

test('LPD-52714 Checking what is the first page loaded if default.admin.password is blank and the admin user is set when we create a virtual instance but not available when we edit it', async ({
	editVirtualInstancePage,
	page,
	virtualInstancesPage,
}) => {
	await page.goto(liferayConfig.environment.baseUrl);
	await expect(page.getByRole('heading', {name: 'Set Password'})).toBeVisible(
		{
			timeout: 10 * 1000,
		}
	);

	const {name, password, surname} = userData['test'];

	await page.getByLabel('Password', {exact: true}).fill(password);

	await page.getByLabel('Reenter Password').fill(password);

	const signInButton = page.getByRole('button', {name: 'Save'});
	await signInButton.click();

	await expect(page.getByLabel(`${name} ${surname}`)).toBeVisible({
		timeout: 30 * 1000,
	});

	const nameInstance = getRandomString();

	await virtualInstancesPage.addNewVirtualInstanceAndSetupAdminUser(
		nameInstance,
		name,
		name + '@liferay.com',
		password
	);

	await editVirtualInstancePage.checkEditVirtualInstanceFields(nameInstance);
});
