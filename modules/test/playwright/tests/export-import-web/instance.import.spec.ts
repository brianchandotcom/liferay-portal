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
import {depotAdminPageTest} from '../../fixtures/depotAdminPageTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageTemplatesPagesTest} from '../../fixtures/pageTemplatesPagesTest';
import {wikiPagesTest} from '../../fixtures/wikiPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import {readFileFromZip} from '../../utils/zip';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {stagingPageTest} from './fixtures/stagingPageTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	companyExportImportPageTest,
	dataApiHelpersTest,
	depotAdminPageTest,
	documentLibraryPagesTest,
	exportImportPagesTest,
	featureFlagsTest({
		'LPD-35013': {enabled: true},
		'LPD-35914': {enabled: true, system: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pageTemplatesPagesTest,
	stagingPageTest,
	wikiPagesTest
);

test('can export and import custom object entries at instance level', async ({
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

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests'
	);

	const exportFilePath =
		await companyExportImportPage.export('Tests 1 Items');

	const content = await readFileFromZip('C_Test.json', exportFilePath);

	const json = JSON.parse(content);

	expect(json.length).toBe(1);
	expect(json[0]).not.toHaveProperty('permissions');

	await apiHelpers.delete(`${apiHelpers.baseUrl}c/tests/${objectEntry.id}`);

	expect(
		await apiHelpers.get(
			`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}`
		)
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import(exportFilePath);

	expect(
		await apiHelpers.get(
			`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}`
		)
	).toEqual(
		expect.objectContaining({
			externalReferenceCode: objectEntry.externalReferenceCode,
			name: objectEntry.name,
		})
	);
});

test('can import custom object entries at instance level with or without permissions based on selection', async ({
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

	let objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{
			externalReferenceCode: '',
			name: 'test',
			permissions: [
				{
					actionIds: ['VIEW'],
					roleName: 'Guest',
				},
			],
		},
		'c/tests'
	);

	// Export with permissions

	const exportFilePath = await companyExportImportPage.export(
		'Tests 1 Items',
		true
	);

	// Import with permissions

	await apiHelpers.delete(`${apiHelpers.baseUrl}c/tests/${objectEntry.id}`);

	expect(
		await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode(
			'c/tests',
			objectEntry.externalReferenceCode
		)
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import(exportFilePath, true);

	objectEntry = await apiHelpers.get(
		`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}/?nestedFields=permissions`
	);

	expect(objectEntry).toEqual(
		expect.objectContaining({
			permissions: [
				{
					actionIds: ['VIEW'],
					roleName: 'Guest',
				},
			],
		})
	);

	// Import without permissions

	await apiHelpers.delete(`${apiHelpers.baseUrl}c/tests/${objectEntry.id}`);

	expect(
		await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode(
			'c/tests',
			objectEntry.externalReferenceCode
		)
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import(exportFilePath);

	objectEntry = await apiHelpers.get(
		`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}/?nestedFields=permissions`
	);

	expect(objectEntry).not.toEqual(
		expect.objectContaining({
			permissions: [
				{
					actionIds: ['VIEW'],
					roleName: 'Guest',
				},
			],
		})
	);
});

test('can see corresponding elements at instance level', async ({
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

	const exportFilePath =
		await companyExportImportPage.export('Tests 1 Items');

	await companyExportImportPage.page.goto('/');

	await companyExportImportPage.goToImportOptions(exportFilePath);

	await expect(
		companyExportImportPage.page.getByRole('group', {name: 'Pages'})
	).not.toBeVisible();

	await expect(
		companyExportImportPage.page.getByText('Comments, Ratings')
	).not.toBeVisible();
});

test('Can/not view Import menu item in Application menu depending on permissions', async ({
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
					'com_liferay_exportimport_web_portlet_CompanyImportPortlet',
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

	const importUrl =
		await applicationsMenuPage.importMenuItem.getAttribute('href');

	await expect(applicationsMenuPage.importMenuItem).toBeVisible();

	await applicationsMenuPage.goToImport();

	await expect(
		companyExportImportPage.exportImportPage.newImportButton
	).toBeVisible();

	await performLogout(page);

	await performLogin(page, user2.alternateName);

	await expect(applicationsMenuPage.applicationsMenuTabButton).toBeHidden();

	// Try to access the Import page directly using the stored URL

	await page.goto(importUrl);

	await expect(
		companyExportImportPage.exportImportPage.newImportButton
	).toBeHidden();
});
