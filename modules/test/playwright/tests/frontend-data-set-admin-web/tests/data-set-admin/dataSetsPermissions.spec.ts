/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {liferayConfig} from '../../../../liferay.config';
import getRandomString from '../../../../utils/getRandomString';
import performLogin, {
	performLogout,
	performUserSwitch,
} from '../../../../utils/performLogin';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {setupUserRoleAndLoginAsUser} from '../../utils/setupUserRoleAndLoginAsUser';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';

export const test = mergeTests(
	dataApiHelpersTest,
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest()
);

const createdDataSetERCs = [];
const createdRoleIds = [];
const createdUserIds = [];
const dataSetUserRoleName = `ds_user_${getRandomString()}`;

const blogPostsDataSetConfig = {
	name: 'BlogPost',
	restApplication: '/headless-delivery/v1.0',
	restEndpoint: '/v1.0/sites/{siteId}/blog-postings',
	restSchema: 'BlogPosting',
};

async function openActionsDropdown({page, text}: {page: Page; text: string}) {
	const table: Locator = page.locator('.data-set-content-wrapper');

	const row = table.locator('.dnd-tbody .dnd-tr').filter({
		has: page.getByText(text, {exact: true}).first(),
	});

	const actionsButton = row.locator('.cell-item-actions button');

	await expect(actionsButton).toBeInViewport();

	await actionsButton.click();
}

test.beforeEach(async ({page}) => {

	// Unsure why this happens, but sometimes this can start off as not
	// signed in, so this attempts to log in again or else the headless
	// requests will fail.

	if (
		await page
			.getByRole('button', {
				name: 'Sign In',
			})
			.isVisible()
	) {
		await test.step('Sign in as admin', async () => {
			await performLogin(page, 'test');
		});
	}
});

test.afterEach(async ({apiHelpers, dataSetManagerApiHelpers, page}) => {
	if (await page.getByLabel('Test Test User Profile').isHidden()) {
		if (
			await page
				.getByRole('button', {
					name: 'Sign In',
				})
				.isHidden()
		) {
			await performLogout(page);
		}

		await performLogin(page, 'test');
	}

	for (const erc of createdDataSetERCs) {
		await dataSetManagerApiHelpers.deleteDataSet({
			erc,
		});
	}

	createdDataSetERCs.length = 0;

	for (const id of createdRoleIds) {
		await apiHelpers.headlessAdminUser.deleteRole(id);
	}

	createdRoleIds.length = 0;

	for (const id of createdUserIds) {
		await apiHelpers.headlessAdminUser.deleteUserAccount(id);
	}

	createdUserIds.length = 0;
});

test('A user with "View" and "Permissions" permission', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
}) => {
	await test.step('Create a data set', async () => {
		const blogPostDataSetERC = getRandomString();
		createdDataSetERCs.push(blogPostDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...blogPostsDataSetConfig,
			erc: blogPostDataSetERC,
			label: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Setup user role and login as user', async () => {
		const userRoleAndAccount = await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['PERMISSIONS', 'VIEW'],
					resourceName: 'Data Set',
				},
			],
			dataSetUserRoleName,
			page,
		});

		createdRoleIds.push(userRoleAndAccount.dataSetUserRole.id);
		createdUserIds.push(userRoleAndAccount.userAccount.id);
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Check that the "Permissions" button action is visible', async () => {
		await expect(
			dataSetsPage.dataSetPermissionsButton.first()
		).toBeVisible();
	});

	await test.step('Open Permissions modal', async () => {
		await dataSetsPage.dataSetPermissionsButton.first().click();

		await expect(
			dataSetsPage.permissionsModal.locator('#guest_ACTION_VIEW')
		).not.toBeChecked();
	});

	await test.step('Enable "View" permission for "User" role', async () => {
		await dataSetsPage.permissionsModal
			.locator('#guest_ACTION_VIEW')
			.setChecked(true);

		await expect(
			dataSetsPage.permissionsModal.locator('#guest_ACTION_VIEW')
		).toBeChecked();
	});

	await test.step('Save Permissions modal', async () => {
		await dataSetsPage.permissionsModal
			.getByRole('button', {name: 'Save'})
			.click();

		await waitForAlert(dataSetsPage.permissionsModal);
	});

	await test.step('Click "Cancel" in the Permissions modal', async () => {
		await dataSetsPage.permissionsModal
			.getByRole('button', {name: 'Cancel'})
			.click();
	});

	await test.step('Check that the Permissions modal is closed', async () => {
		await expect(
			page.getByRole('heading', {name: 'Permissions'})
		).not.toBeVisible();
	});

	await test.step('Open Permissions modal', async () => {
		await dataSetsPage.dataSetPermissionsButton.first().click();
	});

	await test.step('Confirm "View" permission is persisted', async () => {
		await expect(
			dataSetsPage.permissionsModal.locator('#guest_ACTION_VIEW')
		).toBeChecked();
	});
});

test('A user with only "View" permission', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
}) => {
	await test.step('Create a data set', async () => {
		const blogPostDataSetERC = getRandomString();
		createdDataSetERCs.push(blogPostDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...blogPostsDataSetConfig,
			erc: blogPostDataSetERC,
			label: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Setup user role and login as user', async () => {
		const userRoleAndAccount = await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['VIEW'],
					resourceName: 'Data Set',
				},
			],
			dataSetUserRoleName,
			page,
		});

		createdRoleIds.push(userRoleAndAccount.dataSetUserRole.id);
		createdUserIds.push(userRoleAndAccount.userAccount.id);
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Check that there is no actions dropdown', async () => {
		const table: Locator = page.locator('.data-set-content-wrapper');

		const row = table.locator('.dnd-tbody .dnd-tr').filter({
			has: page
				.getByText(blogPostsDataSetConfig.name, {exact: true})
				.first(),
		});

		const actionsButton = row.locator('.cell-item-actions button');

		await expect(actionsButton).not.toBeInViewport();
	});

	await test.step('Check that "Permissions" is not visible', async () => {
		await expect(dataSetsPage.dataSetPermissionsButton).not.toBeVisible();
		await expect(dataSetsPage.dataSetPermissionsMenuItem).not.toBeVisible();
	});

	await test.step('Check that "Delete" is not visible', async () => {
		await expect(dataSetsPage.dataSetDeleteButton).not.toBeVisible();
		await expect(dataSetsPage.dataSetDeleteMenuItem).not.toBeVisible();
	});
});

test('A user without "View" permission on Data Set items', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
}) => {
	await test.step('Create a data set', async () => {
		const blogPostDataSetERC = getRandomString();
		createdDataSetERCs.push(blogPostDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...blogPostsDataSetConfig,
			erc: blogPostDataSetERC,
			label: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Setup user role and login as user', async () => {
		const userRoleAndAccount = await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetUserRoleName,
			page,
		});

		createdRoleIds.push(userRoleAndAccount.dataSetUserRole.id);
		createdUserIds.push(userRoleAndAccount.userAccount.id);
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Assert that no data sets appear on the table', async () => {
		await expect(dataSetsPage.dataSetsEmptyState).toBeVisible();
	});
});

test('A user with "Delete" permission', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
}) => {
	await test.step('Create a data set', async () => {
		const blogPostDataSetERC = getRandomString();
		createdDataSetERCs.push(blogPostDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...blogPostsDataSetConfig,
			erc: blogPostDataSetERC,
			label: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Setup user role and login as user', async () => {
		const userRoleAndAccount = await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['DELETE', 'VIEW'],
					resourceName: 'Data Set',
				},
			],
			dataSetUserRoleName,
			page,
		});

		createdRoleIds.push(userRoleAndAccount.dataSetUserRole.id);
		createdUserIds.push(userRoleAndAccount.userAccount.id);
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Open actions dropdown', async () => {
		await openActionsDropdown({
			page,
			text: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Check that "Delete" is visible', async () => {
		await expect(dataSetsPage.dataSetDeleteButton.first()).toBeVisible();
	});
});

test('Check "Edit" permission', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
}) => {
	const blogPostDataSetERC = getRandomString();
	let userAccount;

	await test.step('Create a data set', async () => {
		createdDataSetERCs.push(blogPostDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...blogPostsDataSetConfig,
			erc: blogPostDataSetERC,
			label: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Setup user role and login as user', async () => {
		const userRoleAndAccount = await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['VIEW'],
					resourceName: 'Data Set',
				},
			],
			dataSetUserRoleName,
			page,
		});

		userAccount = userRoleAndAccount.userAccount;
		createdRoleIds.push(userRoleAndAccount.dataSetUserRole.id);
		createdUserIds.push(userRoleAndAccount.userAccount.id);
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Check that there is no actions dropdown', async () => {
		const table: Locator = page.locator('.data-set-content-wrapper');

		const row = table.locator('.dnd-tbody .dnd-tr').filter({
			has: page
				.getByText(blogPostsDataSetConfig.name, {exact: true})
				.first(),
		});

		const actionsButton = row.locator('.cell-item-actions button');

		await expect(actionsButton).not.toBeInViewport();
	});

	await test.step('Check that the user can not enter to Data Set details pages', async () => {
		const dataSetRows = page
			.locator('.data-set-content-wrapper .dnd-tbody .dnd-tr')
			.filter({
				hasText: blogPostsDataSetConfig.name,
			});

		await dataSetRows
			.first()
			.getByText(blogPostsDataSetConfig.name)
			.first()
			.click();

		await expect(
			page.getByRole('button', {name: 'Details'})
		).not.toBeVisible();

		await page.goto(
			`${liferayConfig.environment.baseUrl}/group/guest/~/control_panel/manage?p_p_id=com_liferay_frontend_data_set_admin_web_internal_portlet_FDSAdminPortlet&p_p_lifecycle=0&_com_liferay_frontend_data_set_admin_web_internal_portlet_FDSAdminPortlet_mvcRenderCommandName=%2Ffrontend_data_set_admin%2Fedit_data_set&_com_liferay_frontend_data_set_admin_web_internal_portlet_FDSAdminPortlet_dataSetERC=${blogPostDataSetERC}&_com_liferay_frontend_data_set_admin_web_internal_portlet_FDSAdminPortlet_dataSetLabel=${blogPostsDataSetConfig.name}`
		);
		await waitForAlert(page, 'Error:Your request failed to complete.', {
			type: 'danger',
		});
	});

	await test.step('Do logout and login as administrator', async () => {
		await performUserSwitch(page, 'test');
	});

	await test.step('Grant Data Sets "Update" permission for the new role', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});

		await openActionsDropdown({
			page,
			text: blogPostsDataSetConfig.name,
		});

		await dataSetsPage.dataSetPermissionsMenuItem.click();

		await expect(
			dataSetsPage.permissionsModal.locator(
				`#${dataSetUserRoleName}_ACTION_UPDATE`
			)
		).not.toBeChecked();

		await dataSetsPage.permissionsModal
			.locator(`#${dataSetUserRoleName}_ACTION_UPDATE`)
			.setChecked(true);

		await dataSetsPage.permissionsModal
			.getByRole('button', {name: 'Save'})
			.click();

		await waitForAlert(dataSetsPage.permissionsModal);
	});

	await test.step('Do logout and login with the new user', async () => {
		await performUserSwitch(page, userAccount.alternateName);
	});

	await test.step('Navigate to Data Set page', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Check that the user has only "Edit" option on actions menu', async () => {
		await expect(dataSetsPage.dataSetEditButton.first()).toBeVisible();
	});

	await test.step('Check that the user can now edit the data set', async () => {
		await dataSetsPage.dataSetEditButton.first().click();

		await expect(
			page.getByRole('heading', {name: 'Details'})
		).toBeVisible();
	});
});

test('A user with "Add Object Entry" permission', async ({
	apiHelpers,
	dataSetsPage,
	page,
}) => {
	await test.step('Setup user role and login as user with "Add Object Entry"', async () => {
		const userRoleAndAccount = await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['ADD_OBJECT_ENTRY'],
					resourceName: 'Data Sets',
				},
			],
			dataSetUserRoleName,
			page,
		});

		createdRoleIds.push(userRoleAndAccount.dataSetUserRole.id);
		createdUserIds.push(userRoleAndAccount.userAccount.id);
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Confirm that the user can create a Data Set', async () => {
		await expect(dataSetsPage.newDataSetButton).toBeVisible();

		await dataSetsPage.createDataSet(blogPostsDataSetConfig);

		await waitForAlert(page);
	});

	await test.step('Delete Data Set', async () => {
		await openActionsDropdown({
			page,
			text: blogPostsDataSetConfig.name,
		});

		await dataSetsPage.dataSetDeleteMenuItem.click();

		const deleteModal = dataSetsPage.page.getByRole('dialog');

		await deleteModal.getByRole('button', {name: 'Delete'}).click();
	});
});

test('A user without "Add Object Entry" permission', async ({
	apiHelpers,
	dataSetsPage,
	page,
}) => {
	await test.step('Setup user role and login as user with "View" permission', async () => {
		const userRoleAndAccount = await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['VIEW'],
					resourceName: 'Data Set',
				},
			],
			dataSetUserRoleName,
			page,
		});

		createdRoleIds.push(userRoleAndAccount.dataSetUserRole.id);
		createdUserIds.push(userRoleAndAccount.userAccount.id);
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Confirm that the user can not create a Data Set', async () => {
		await expect(dataSetsPage.newDataSetButton).not.toBeVisible();
	});
});
