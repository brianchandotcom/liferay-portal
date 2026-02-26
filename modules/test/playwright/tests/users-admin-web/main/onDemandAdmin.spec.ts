/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {virtualInstancesPagesTest} from '../../../fixtures/virtualInstancesPagesTest';
import {liferayConfig} from '../../../liferay.config';
import {EditUserPage} from '../../../pages/users-admin-web/EditUserPage';
import {UsersAndOrganizationsPage} from '../../../pages/users-admin-web/UsersAndOrganizationsPage';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	usersAndOrganizationsPagesTest,
	virtualInstancesPagesTest
);

const basePort = new URL(liferayConfig.environment.baseUrl).port || '8080';

test(
	'On demand admin',
	{tag: ['@LPD-70297', '@LPS-150388', '@LPS-156154']},
	async ({onDemandAdminPage, page, virtualInstancesPage}) => {
		test.setTimeout(240000);

		const DEFAULT_VIRTUAL_INSTANCE_NAME = 'www.able.com';

		let newPage: Page;

		try {
			await virtualInstancesPage.addNewVirtualInstance(
				DEFAULT_VIRTUAL_INSTANCE_NAME
			);

			await onDemandAdminPage.goto();

			await expect(async () => {
				await (
					await onDemandAdminPage.instancesTable.rowActions(
						DEFAULT_VIRTUAL_INSTANCE_NAME
					)
				).click();

				await expect(
					onDemandAdminPage.requestAdministratorAccessMenuItem
				).toBeVisible({timeout: 500});
			}).toPass({timeout: 5000});

			await onDemandAdminPage.requestAdministratorAccessMenuItem.click();

			await onDemandAdminPage.reasonFrameInput.fill(getRandomString());

			const pagePromise = page.waitForEvent('popup');

			await onDemandAdminPage.reasonFrameSaveButton.click();

			newPage = await pagePromise;

			await test.step('the portlet for On-Demand Admin is not available in a non-default instance', async () => {
				await newPage
					.getByRole('button', {name: 'Applications Menu'})
					.or(newPage.getByTestId('globalMenu'))
					.click();

				await newPage
					.getByRole('menuitem', {
						name: 'Control Panel',
					})
					.click();

				await expect(
					newPage.getByRole('menuitem', {
						exact: true,
						name: 'Users and Organizations',
					})
				).not.toBeVisible();
				await expect(
					newPage.getByRole('menuitem', {
						exact: true,
						name: 'On-Demand Admin',
					})
				).not.toBeVisible();
			});

			await newPage.goto(
				`http://${DEFAULT_VIRTUAL_INSTANCE_NAME}:${basePort}`
			);

			await test.step('can change instance administrator password', async () => {
				const newUsersAndOrganizationsPage =
					new UsersAndOrganizationsPage(newPage);
				const newEditUserPage = new EditUserPage(newPage);

				await newUsersAndOrganizationsPage.goToUsers(false);
				await (
					await newUsersAndOrganizationsPage.usersTableRowLink('test')
				).click();

				await newEditUserPage.passwordLink.click();

				const newPassword = String(getRandomInt());

				await newEditUserPage.passwordInput.click();
				await newEditUserPage.passwordInput.fill(newPassword);
				await newEditUserPage.passwordReenterInput.click();
				await newEditUserPage.passwordReenterInput.fill(newPassword);
				await newEditUserPage.saveButton.click();

				await waitForAlert(newPage);

				await newPage.goto(
					`http://${DEFAULT_VIRTUAL_INSTANCE_NAME}:${basePort}`
				);
				await newPage.getByTitle('User Profile Menu').click();
				await newPage.getByRole('menuitem', {name: 'Sign Out'}).click();
				await newPage.goto(
					`http://${DEFAULT_VIRTUAL_INSTANCE_NAME}:${basePort}`
				);

				const signInButton = newPage
					.getByRole('button', {name: 'Sign In'})
					.last();
				await signInButton.click();

				await newPage
					.getByLabel('Email Address')
					.fill(`test@${DEFAULT_VIRTUAL_INSTANCE_NAME}.com`);
				await newPage.getByLabel('Password').fill(newPassword);

				await signInButton.click();

				await expect(
					newPage.getByText('Welcome to Liferay')
				).toBeVisible();
			});
		}
		finally {
			if (newPage) {
				await newPage.close();
			}

			await virtualInstancesPage.deleteVirtualInstance(
				DEFAULT_VIRTUAL_INSTANCE_NAME
			);
		}
	}
);
