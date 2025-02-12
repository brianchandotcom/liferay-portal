/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionApi,
	ObjectField,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {getRandomInt} from '../../utils/getRandomInt';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import {getTempDir} from '../../utils/temp';
import {readFileFromZip} from '../../utils/zip';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	companyExportImportPageTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true, system: true},
	}),
	loginTest(),
	productMenuPageTest
);

test('cannot export site scoped custom object entries at instance level', async ({
	apiHelpers,
	applicationsMenuPage,
	page,
}) => {
	const objectActionApiClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectActionApiClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'test',
			label: {
				en_US: 'Test',
			},
			name: 'Test',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'Name',
					},
					name: 'name',
					required: true,
				},
			],
			pluralLabel: {
				en_US: 'Tests',
			},
			portlet: true,
			scope: 'site',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests/scopes/Guest'
	);

	await applicationsMenuPage.goToExport();

	await page.getByTestId('creationMenuNewButton').nth(1).click();

	await expect(page.getByLabel('Tests 1 Items')).toBeHidden();
});

test('can export new default and custom task name', async ({
	apiHelpers,
	companyExportImportPage,
}) => {
	const objectActionApiClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectActionApiClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'test',
			label: {
				en_US: 'Test',
			},
			name: 'Test',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'Name',
					},
					name: 'name',
					required: true,
				},
			],
			pluralLabel: {
				en_US: 'Tests',
			},
			portlet: true,
			scope: 'company',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	const defaultExportFilePath =
		await companyExportImportPage.export('Tests 1 Items');

	expect(defaultExportFilePath).toMatch(
		new RegExp(`^${getTempDir()}Export-`)
	);

	const taskName = 'CustomTaskName';

	const customExportFilePath = await companyExportImportPage.export(
		'Tests 1 Items',
		false,
		taskName
	);

	expect(customExportFilePath).toMatch(
		new RegExp(`^${getTempDir()}${taskName}-`)
	);
});

test('can export custom object entries at instance level with permissions', async ({
	apiHelpers,
	companyExportImportPage,
}) => {
	const objectActionApiClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectActionApiClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'test',
			label: {
				en_US: 'Test',
			},
			name: 'Test',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'Name',
					},
					name: 'name',
					required: true,
				},
			],
			pluralLabel: {
				en_US: 'Tests',
			},
			portlet: true,
			scope: 'company',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests'
	);

	const exportFilePath = await companyExportImportPage.export(
		'Tests 1 Items',
		true
	);

	const content = await readFileFromZip('C_Test.json', exportFilePath);

	const json = JSON.parse(content);

	expect(json.length).toBe(1);
	expect(json[0]).toHaveProperty('permissions');
});

test('can see corresponding elements at instance level', async ({
	companyExportImportPage,
}) => {
	await companyExportImportPage.applicationsMenuPage.goToExport();
	await companyExportImportPage.page
		.getByTestId('creationMenuNewButton')
		.nth(1)
		.click();

	await expect(
		companyExportImportPage.page.getByText('Comments, Ratings')
	).not.toBeVisible();
});

test('Can/not view Export menu item in Application menu depending on permissions', async ({
	apiHelpers,
	applicationsMenuPage,
	companyExportImportPage,
	page,
}) => {
	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	const roleWithPermissions = await apiHelpers.headlessAdminUser.postRole({
		name: 'role' + getRandomInt(),
		rolePermissions: [
			{
				actionIds: ['VIEW_CONTROL_PANEL'],
				primaryKey: companyId,
				resourceName: '90',
				scope: 1,
			},
			{
				actionIds: ['ACCESS_IN_CONTROL_PANEL'],
				primaryKey: companyId,
				resourceName:
					'com_liferay_exportimport_web_portlet_CompanyExportPortlet',
				scope: 1,
			},
		],
	});

	const roleWithoutPermissions = await apiHelpers.headlessAdminUser.postRole({
		name: 'role' + getRandomInt(),
		rolePermissions: [
			{
				actionIds: ['VIEW_CONTROL_PANEL'],
				primaryKey: companyId,
				resourceName: '90',
				scope: 1,
			},
		],
	});

	const user1 = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user1.alternateName] = {
		name: user1.givenName,
		password: 'test',
		surname: user1.familyName,
	};

	await apiHelpers.headlessAdminUser.assignUserToRole(
		roleWithPermissions.externalReferenceCode,
		user1.id
	);

	const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user2.alternateName] = {
		name: user2.givenName,
		password: 'test',
		surname: user2.familyName,
	};

	await apiHelpers.headlessAdminUser.assignUserToRole(
		roleWithoutPermissions.externalReferenceCode,
		user2.id
	);

	await performLogout(page);

	await performLogin(page, user1.alternateName);

	await applicationsMenuPage.goToApplicationsMenu();

	const exportUrl =
		await applicationsMenuPage.exportMenuItem.getAttribute('href');

	await expect(applicationsMenuPage.exportMenuItem).toBeVisible();

	await applicationsMenuPage.goToExport();

	await expect(
		companyExportImportPage.exportImportPage.newExportButton
	).toBeVisible();

	await performLogout(page);

	await performLogin(page, user2.alternateName);

	await expect(applicationsMenuPage.applicationsMenuTabButton).toBeHidden();

	// Try to access the Export page directly using the stored URL

	await page.goto(exportUrl);

	await expect(
		companyExportImportPage.exportImportPage.newExportButton
	).toBeHidden();
});
