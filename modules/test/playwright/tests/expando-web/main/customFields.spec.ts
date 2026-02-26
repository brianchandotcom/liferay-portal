/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {customFieldsPagesTest} from '../../../fixtures/customFieldsPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {TCustomField} from '../../../helpers/CustomFieldTypesHelper';

export const test = mergeTests(
	apiHelpersTest,
	customFieldsPagesTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	usersAndOrganizationsPagesTest
);

test(
	`Can edit Custom Field User text field`,
	{
		tag: '@LPD-67622',
	},
	async ({
		addCustomFieldPage,
		apiHelpers,
		page,
		usersAndOrganizationsPage,
	}) => {
		const customField: TCustomField = {
			fieldName: 'Text Field',
			fieldType: 'inputField',
			resource: 'User',
		};

		await addCustomFieldPage.addCustomField(customField);

		await page.locator(`a:has-text('Text Field')`).click();

		await page.getByLabel('Starting Value').fill('Nickname');

		await addCustomFieldPage.saveButton.click();
		await expect(addCustomFieldPage.successMessage).toBeVisible();

		const user = await apiHelpers.headlessAdminUser.postUserAccount({
			familyName: 'A',
			givenName: 'User',
		});

		await usersAndOrganizationsPage.goToUsers();

		await usersAndOrganizationsPage.goToUser(user.name);

		await expect(page.getByText('Text Field')).toBeVisible();

		await expect(
			page.locator(
				'[id="_com_liferay_users_admin_web_portlet_UsersAdminPortlet_pqai___Text_20_Field"]'
			)
		).toHaveValue('Nickname');
	}
);
