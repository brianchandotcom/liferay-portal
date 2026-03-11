/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {ListTypeDefinitionsPage} from '../../../pages/object-web/list-type/ListTypeDefinitionsPage';
import {getRandomInt} from '../../../utils/getRandomInt';
import {performUserSwitch, userData} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

async function createUserWithPermissions(apiHelpers, rolePermissions) {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const company =
		await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
			'liferay.com'
		);

	const role = await apiHelpers.headlessAdminUser.postRole({
		name: 'role' + getRandomInt(),
		rolePermissions: rolePermissions.map((rolePermission) => ({
			...rolePermission,
			primaryKey: String(company.companyId),
			scope: 1,
		})),
	});

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	return user;
}

async function grantPermissionsToUserRole(page, checked: boolean) {
	const permissionIframe = page.frameLocator('iframe[title="Permissions"]');

	const permissionsCheckbox = permissionIframe.locator(
		'#user_ACTION_PERMISSIONS'
	);

	await page.waitForTimeout(500);

	await permissionsCheckbox.check({trial: true});

	if (checked) {
		await permissionsCheckbox.check();
	}
	else {
		await permissionsCheckbox.uncheck();
	}

	await permissionIframe.getByRole('button', {name: 'Save'}).click();

	await waitForAlert(permissionIframe);

	await page.locator('.modal').getByLabel('Close', {exact: true}).click();
}

test('Can only update Object Definition permissions when PERMISSIONS permission is granted', async ({
	apiHelpers,
	page,
	viewObjectDefinitionsPage,
}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	await test.step('As admin, grant PERMISSIONS on the object definition to the User role', async () => {
		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
			objectDefinition.label['en_US']
		);

		await page.getByRole('menuitem', {name: 'Permissions'}).click();

		await grantPermissionsToUserRole(page, true);
	});

	await test.step('Switch to limited user and verify Permissions is available', async () => {
		const user = await createUserWithPermissions(apiHelpers, [
			{
				actionIds: ['ACCESS_IN_CONTROL_PANEL'],
				resourceName:
					'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
			},
			{
				actionIds: ['VIEW'],
				resourceName: 'com.liferay.object.model.ObjectDefinition',
			},
		]);

		await performUserSwitch(page, user.alternateName);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
			objectDefinition.label['en_US']
		);

		await page.getByRole('menuitem', {name: 'Permissions'}).click();
	});

	await test.step('Remove PERMISSIONS from User role', async () => {
		await grantPermissionsToUserRole(page, false);
	});

	await test.step('Verify Permissions menu item is no longer available', async () => {
		await page.reload();

		await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
			objectDefinition.label['en_US']
		);

		await expect(
			page.getByRole('menuitem', {name: 'Permissions'})
		).toBeHidden();
	});
});

test('Can only update Object Entry permissions when PERMISSIONS permission is granted', async ({
	apiHelpers,
	page,
	viewObjectEntriesPage,
}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	await apiHelpers.objectEntry.postObjectEntry(
		{},
		'c/' + objectDefinition.name.toLowerCase() + 's'
	);

	await test.step('As admin, grant PERMISSIONS on the object entry to the User role', async () => {
		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await viewObjectEntriesPage.frontendDatasetPermissionsAction.click();

		await grantPermissionsToUserRole(page, true);
	});

	await test.step('Switch to limited user and verify Permissions is available', async () => {
		const user = await createUserWithPermissions(apiHelpers, [
			{
				actionIds: ['ACCESS_IN_CONTROL_PANEL'],
				resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
			},
			{
				actionIds: ['VIEW'],
				resourceName: objectDefinition.className,
			},
		]);

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await viewObjectEntriesPage.frontendDatasetPermissionsAction.click();
	});

	await test.step('Remove PERMISSIONS from User role', async () => {
		await grantPermissionsToUserRole(page, false);
	});

	await test.step('Verify actions dropdown is no longer available', async () => {
		await page.reload();

		await expect(viewObjectEntriesPage.frontendDatasetActions).toBeHidden();
	});
});

test('Can only update Picklist permissions when PERMISSIONS permission is granted', async ({
	apiHelpers,
	page,
}) => {
	const picklist =
		await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

	apiHelpers.data.push({
		id: picklist.id,
		type: 'listTypeDefinition',
	});

	const listTypeDefinitionsPage = new ListTypeDefinitionsPage(page);

	await test.step('As admin, grant PERMISSIONS on the picklist to the User role', async () => {
		await listTypeDefinitionsPage.goto();

		await page
			.getByRole('row', {name: picklist.name})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Permissions'}).click();

		await grantPermissionsToUserRole(page, true);
	});

	await test.step('Switch to limited user and verify Permissions is available', async () => {
		const user = await createUserWithPermissions(apiHelpers, [
			{
				actionIds: ['ACCESS_IN_CONTROL_PANEL'],
				resourceName:
					'com_liferay_object_web_internal_list_type_portlet_portlet_ListTypeDefinitionsPortlet',
			},
			{
				actionIds: ['VIEW'],
				resourceName: 'com.liferay.list.type.model.ListTypeDefinition',
			},
		]);

		await performUserSwitch(page, user.alternateName);

		await listTypeDefinitionsPage.goto();

		await page
			.getByRole('row', {name: picklist.name})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Permissions'}).click();
	});

	await test.step('Remove PERMISSIONS from User role', async () => {
		await grantPermissionsToUserRole(page, false);
	});

	await test.step('Verify Permissions menu item is no longer available', async () => {
		await page.reload();

		await page
			.getByRole('row', {name: picklist.name})
			.getByRole('button')
			.click();

		await expect(
			page.getByRole('menuitem', {name: 'Permissions'})
		).toBeHidden();
	});
});
