/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {rolesPagesTest} from '../../../../fixtures/rolesPagesTest';
import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../../liferay.config';
import {RoleDefinePermissionsPage} from '../../../../pages/roles-admin-web/RoleDefinePermissionsPage';
import {RolePage} from '../../../../pages/roles-admin-web/RolePage';
import {RolesPage} from '../../../../pages/roles-admin-web/RolesPage';
import getRandomString from '../../../../utils/getRandomString';
import performLogin, {
	performLogout,
	userData,
} from '../../../../utils/performLogin';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';

export const test = mergeTests(
	dataApiHelpersTest,
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	rolesPagesTest,
	loginTest()
);

const createdDataSetERCs = [];
const createdRoleIds = [];
const createdUserIds = [];
const dataSetUserRoleName = `ds_user_${getRandomString()}`;

let loggedInAsAdmin = true;
let dataSetUserRole;
let userAccount: TUserAccount;

const blogPostsDataSetConfig = {
	name: 'BlogPost',
	restApplication: '/headless-delivery/v1.0',
	restEndpoint: '/v1.0/sites/{siteId}/blog-postings',
	restSchema: 'BlogPosting',
};

async function assertTableRowsCount(page: Page, rowsCount: number) {
	await test.step(`Assert table has ${rowsCount} rows`, async () => {
		const rows = await page.locator('.dnd-table > .dnd-tbody > .dnd-tr');

		expect(rows).toHaveCount(rowsCount);
	});
}

async function openActionsDropdown({page, text}: {page: Page; text: string}) {
	const table: Locator = page.locator('.data-set-content-wrapper');

	const row = table.locator('.dnd-tbody .dnd-tr').filter({
		has: page.getByText(text, {exact: true}).first(),
	});

	const actionsButton = row.locator('.cell-item-actions button');

	await expect(actionsButton).toBeInViewport();

	await actionsButton.click();
}

async function setupUserRoleAndLoginAsUser({
	apiHelpers,
	dataSetResourcePermissions,
	page,
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
}: {
	apiHelpers: DataApiHelpers;
	dataSetResourcePermissions?: {
		actions: string[];
		name: string;
	}[];
	page: Page;
	roleDefinePermissionsPage: RoleDefinePermissionsPage;
	rolePage: RolePage;
	rolesPage: RolesPage;
}) {
	await test.step('Create Data Set user role', async () => {
		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		dataSetUserRole = await apiHelpers.headlessAdminUser.postRole({
			name: dataSetUserRoleName,
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_frontend_data_set_admin_web_internal_portlet_FDSAdminPortlet',
					scope: 1,
				},
			],
			roleType: 'regular',
		});

		createdRoleIds.push(dataSetUserRole.id);
	});

	await test.step('Create a new user', async () => {
		userAccount = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		createdUserIds.push(userAccount.id);
	});

	await test.step('Assign new role to user', async () => {
		await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
			dataSetUserRole.id,
			Number(userAccount.id)
		);

		apiHelpers.data.push({
			id: `${dataSetUserRole.id}_${userAccount.id}`,
			type: 'roleUserAccountAssociation',
		});
	});

	// Enable Data Set roles through the UI since the Data Set Object created
	// is given a random resource name (For example: com.liferay.object.model.ObjectDefinition#E0X3).

	if (dataSetResourcePermissions) {
		await test.step('Go to roles admin page', async () => {
			await rolesPage.goto();
		});

		await test.step('Navigate to role edit page', async () => {
			await page
				.getByRole('link', {exact: true, name: dataSetUserRoleName})
				.click();
		});

		await test.step('Navigate to "Define Permissions" > "Data Set" section', async () => {
			await rolePage.definePermissionsLink.click();
			await roleDefinePermissionsPage.searchInput.click();
			await roleDefinePermissionsPage.searchInput.fill('Data Set');

			await page
				.getByRole('menuitem', {exact: true, name: 'Data Set'})
				.click();
		});

		for (const dataSetResourcePermission of dataSetResourcePermissions) {
			await test.step('Enable role checkboxes', async () => {
				const dataSetRolesTable = page
					.locator('.sheet-tertiary-title')
					.getByText(dataSetResourcePermission.name, {exact: true})
					.locator('~ .lfr-search-container');

				for (const action of dataSetResourcePermission.actions) {
					await dataSetRolesTable
						.getByRole('row', {name: action})
						.getByRole('checkbox')
						.setChecked(true);
				}
			});
		}

		await test.step('Save roles', async () => {
			await page.getByRole('button', {name: 'Save'}).click();

			await waitForAlert(
				page,
				'Success:The role permissions were updated.'
			);
		});
	}

	await test.step('Do login with the new user', async () => {
		await performLogout(page);
		await performLogin(page, userAccount.alternateName);

		loggedInAsAdmin = false;
	});
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
	if (!loggedInAsAdmin) {
		if (
			!(await page
				.getByRole('button', {
					name: 'Sign In',
				})
				.isVisible())
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
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
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
		await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['Permissions', 'View'],
					name: 'Data Set',
				},
			],
			page,
			roleDefinePermissionsPage,
			rolePage,
			rolesPage,
		});
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

	await test.step('Check that "Permissions" is visible', async () => {
		await expect(dataSetsPage.dataSetPermissionsAction).toBeVisible();
	});

	await test.step('Open Permissions modal', async () => {
		await dataSetsPage.dataSetPermissionsAction.click();

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

	await test.step('Open actions dropdown', async () => {
		await openActionsDropdown({
			page,
			text: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Open Permissions modal', async () => {
		await dataSetsPage.dataSetPermissionsAction.click();
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
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
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
		await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['View'],
					name: 'Data Set',
				},
			],
			page,
			roleDefinePermissionsPage,
			rolePage,
			rolesPage,
		});
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

	await test.step('Check that "Permissions" is not visible', async () => {
		await expect(dataSetsPage.dataSetPermissionsAction).not.toBeVisible();
	});

	await test.step('Check that "Delete" is not visible', async () => {
		await expect(dataSetsPage.dataSetPermissionsAction).not.toBeVisible();
	});
});

test('A user without "View" permission on Data Set items', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
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
		await setupUserRoleAndLoginAsUser({
			apiHelpers,
			page,
			roleDefinePermissionsPage,
			rolePage,
			rolesPage,
		});
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Assert that no data sets appear on the table', async () => {
		assertTableRowsCount(page, 0);
	});
});

test('A user with "Delete" permission', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
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
		await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['Delete', 'View'],
					name: 'Data Set',
				},
			],
			page,
			roleDefinePermissionsPage,
			rolePage,
			rolesPage,
		});
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
		await expect(dataSetsPage.dataSetDeleteAction).toBeVisible();
	});
});

test('Check "Edit" permission', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
}) => {
	const blogPostDataSetERC = getRandomString();

	await test.step('Create a data set', async () => {
		createdDataSetERCs.push(blogPostDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...blogPostsDataSetConfig,
			erc: blogPostDataSetERC,
			label: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Setup user role and login as user', async () => {
		await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['View'],
					name: 'Data Set',
				},
			],
			page,
			roleDefinePermissionsPage,
			rolePage,
			rolesPage,
		});
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

	await test.step('Check that "Edit" is not visible', async () => {
		await expect(dataSetsPage.dataSetEditAction).not.toBeVisible();
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
		await performLogout(page);
		await performLogin(page, 'test');
	});

	await test.step('Grant Data Sets "Update" permission for the new role', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});

		await openActionsDropdown({
			page,
			text: blogPostsDataSetConfig.name,
		});

		await dataSetsPage.dataSetPermissionsAction.click();

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
		await performLogout(page);
		await performLogin(page, userAccount.alternateName);
	});

	await test.step('Navigate to Data Set page', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Check that the user has only "Edit" option on actions menu', async () => {
		await openActionsDropdown({
			page,
			text: blogPostsDataSetConfig.name,
		});

		await expect(dataSetsPage.dataSetEditAction).toBeVisible();
	});

	await test.step('Check that the user can now edit the data set', async () => {
		await openActionsDropdown({
			page,
			text: blogPostsDataSetConfig.name,
		});

		await dataSetsPage.dataSetEditAction.click();

		await expect(
			page.getByRole('heading', {name: 'Details'})
		).toBeVisible();
	});
});

test('A user with "Add Object Entry" permission', async ({
	apiHelpers,
	dataSetsPage,
	page,
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
}) => {
	await test.step('Setup user role and login as user with "Add Object Entry"', async () => {
		await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['Add Object Entry'],
					name: 'Data Sets',
				},
			],
			page,
			roleDefinePermissionsPage,
			rolePage,
			rolesPage,
		});
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

		await dataSetsPage.dataSetDeleteAction.click();

		const deleteModal = dataSetsPage.page.getByRole('dialog');

		await deleteModal.getByRole('button', {name: 'Delete'}).click();
	});
});

test('A user without "Add Object Entry" permission', async ({
	apiHelpers,
	dataSetsPage,
	page,
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
}) => {
	await test.step('Setup user role and login as user with "View" permission', async () => {
		await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['View'],
					name: 'Data Set',
				},
			],
			page,
			roleDefinePermissionsPage,
			rolePage,
			rolesPage,
		});
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Confirm that the user can not create a Data Set', async () => {
		await expect(dataSetsPage.newDataSetButton).not.toBeVisible();
	});
});
