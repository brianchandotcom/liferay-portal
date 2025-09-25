/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {depotAdminPageTest} from '../../../fixtures/depotAdminPageTest';
import {documentLibraryPagesTest} from '../../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageTemplatesPagesTest} from '../../../fixtures/pageTemplatesPagesTest';
import {wikiPagesTest} from '../../../fixtures/wikiPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import performLogin, {
	performLogout,
	userData,
} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';
import {readFileFromZip} from '../../../utils/zip';
import {generateObjectEntryValues} from '../../object-web/main/utils/generateObjectEntry';
import {generateObjectFields} from '../../object-web/main/utils/generateObjectFields';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {stagingPageTest} from './fixtures/stagingPageTest';
import {objectDefitionRequestData} from './utils/objectDefitionRequestData';

export const test = mergeTests(
	applicationsMenuPageTest,
	companyExportImportPageTest,
	dataApiHelpersTest,
	depotAdminPageTest,
	documentLibraryPagesTest,
	exportImportPagesTest,
	featureFlagsTest({
		'LPD-35013': {enabled: true},
		'LPD-35914': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	pageEditorPagesTest,
	pageTemplatesPagesTest,
	stagingPageTest,
	wikiPagesTest
);

test('can export and import custom object entries at instance level', async ({
	apiHelpers,
	companyExportImportPage,
}) => {
	const objectActionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	const {body: objectDefinition} =
		await objectActionAPIClient.postObjectDefinition(
			objectDefitionRequestData()
		);

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

	expect(
		await apiHelpers.delete(
			`${apiHelpers.baseUrl}c/tests/${objectEntry.id}`
		)
	).toBeOK();

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
	const objectActionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	const {body: objectDefinition} =
		await objectActionAPIClient.postObjectDefinition(
			objectDefitionRequestData()
		);

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
					roleExternalReferenceCode: expect.any(String),
					roleName: 'Guest',
					roleType: 'regular',
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
					roleExternalReferenceCode: expect.any(String),
					roleName: 'Guest',
					roleType: 'regular',
				},
			],
		})
	);
});

test(
	'can import custom object entries with current user as creator',
	{
		tag: '@LPD-43217',
	},
	async ({
		apiHelpers,
		applicationsMenuPage,
		companyExportImportPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{externalReferenceCode: 'testERC', textField: 'test'},
			`c/${objectDefinition.name.toLowerCase()}s`
		);

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		let roles =
			await apiHelpers.headlessAdminUser.getRoles('Administrator');

		await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
			roles.items[0].id,
			Number(user.id)
		);

		roles = await apiHelpers.headlessAdminUser.getRoles('Power User');

		await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
			roles.items[0].id,
			Number(user.id)
		);

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await applicationsMenuPage.goToObjects();
		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinition.name
		);
		await page.getByLabel('Panel Link', {exact: true}).click();
		await page.getByRole('option', {name: 'Object'}).click();
		await page.getByRole('button', {name: 'Save'}).click();
		await page.waitForTimeout(2000);
		await applicationsMenuPage.goToObjectDefinition(objectDefinition.name);
		await page.locator('[data-testid="fdsCreationActionButton"]').click();
		await page.getByLabel('textField').fill('testText');
		await page.getByRole('button', {name: 'Save'}).click();
		await waitForAlert(
			page,
			'Success:Your request completed successfully.'
		);

		await applicationsMenuPage.goToObjectDefinition(objectDefinition.name);

		const objectEntryId = await page
			.locator('table tr:first-child td:first-child')
			.innerText();

		const exportFilePath = await companyExportImportPage.export(
			`${objectDefinition.name} 2 Items`
		);

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntryId}`
		);

		await performLogout(page);

		await performLogin(page, 'test');

		await companyExportImportPage.import(exportFilePath, false, null, true);

		await applicationsMenuPage.goToObjectDefinition(objectDefinition.name);
		await expect(page.getByRole('cell', {name: 'Test Test'})).toBeVisible();
	}
);

test(
	'can import custom object entries with original creator, and creator user does exist in the current environment',
	{
		tag: '@LPD-43217',
	},
	async ({
		apiHelpers,
		applicationsMenuPage,
		companyExportImportPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{externalReferenceCode: '', name: 'test'},
			`c/${objectDefinition.name.toLowerCase()}s`
		);

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		let roles =
			await apiHelpers.headlessAdminUser.getRoles('Administrator');

		await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
			roles.items[0].id,
			Number(user.id)
		);

		roles = await apiHelpers.headlessAdminUser.getRoles('Power User');

		await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
			roles.items[0].id,
			Number(user.id)
		);

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await applicationsMenuPage.goToObjects();
		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinition.name
		);
		await page.getByLabel('Panel Link', {exact: true}).click();
		await page.getByRole('option', {name: 'Object'}).click();
		await page.getByRole('button', {name: 'Save'}).click();
		await page.waitForTimeout(2000);
		await applicationsMenuPage.goToObjectDefinition(objectDefinition.name);
		await page.locator('[data-testid="fdsCreationActionButton"]').click();
		await page.getByLabel('textField').fill('testText');
		await page.getByRole('button', {name: 'Save'}).click();
		await waitForAlert(
			page,
			'Success:Your request completed successfully.'
		);

		await applicationsMenuPage.goToObjectDefinition(objectDefinition.name);

		const objectEntryId = await page
			.locator('table tr:first-child td:first-child')
			.innerText();

		const exportFilePath = await companyExportImportPage.export(
			`${objectDefinition.name} 2 Items`
		);

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntryId}`
		);

		await performLogout(page);

		await performLogin(page, 'test');

		await companyExportImportPage.import(exportFilePath);

		await applicationsMenuPage.goToObjectDefinition(objectDefinition.name);
		await expect(
			page.getByRole('cell', {
				name: user.givenName + ' ' + user.familyName,
			})
		).toBeVisible();
	}
);

test(
	'can import custom object entries with original creator, but creator user does not exist in the current environment',
	{
		tag: '@LPD-43217',
	},
	async ({
		apiHelpers,
		applicationsMenuPage,
		companyExportImportPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{externalReferenceCode: '', name: 'test'},
			`c/${objectDefinition.name.toLowerCase()}s`
		);

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		let roles =
			await apiHelpers.headlessAdminUser.getRoles('Administrator');

		await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
			roles.items[0].id,
			Number(user.id)
		);

		roles = await apiHelpers.headlessAdminUser.getRoles('Power User');

		await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
			roles.items[0].id,
			Number(user.id)
		);

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await applicationsMenuPage.goToObjects();
		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinition.name
		);
		await page.getByLabel('Panel Link', {exact: true}).click();
		await page.getByRole('option', {name: 'Object'}).click();
		await page.getByRole('button', {name: 'Save'}).click();
		await page.waitForTimeout(2000);
		await applicationsMenuPage.goToObjectDefinition(objectDefinition.name);
		await page.locator('[data-testid="fdsCreationActionButton"]').click();
		await page.getByLabel('textField').fill('testText');
		await page.getByRole('button', {name: 'Save'}).click();
		await waitForAlert(
			page,
			'Success:Your request completed successfully.'
		);

		await applicationsMenuPage.goToObjectDefinition(objectDefinition.name);

		const objectEntryId = await page
			.locator('table tr:first-child td:first-child')
			.innerText();

		const exportFilePath = await companyExportImportPage.export(
			`${objectDefinition.name} 2 Items`
		);

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntryId}`
		);

		await performLogout(page);
		await performLogin(page, 'test');
		await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));

		await companyExportImportPage.import(exportFilePath);

		await applicationsMenuPage.goToObjectDefinition(objectDefinition.name);
		await expect(page.getByRole('cell', {name: 'Test Test'})).toBeVisible();
	}
);

test(
	'can import custom object entry values',
	{
		tag: '@LPD-66167',
	},
	async ({apiHelpers, companyExportImportPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				'Boolean',
				'Date',
				'Decimal',
				'Integer',
				'LongInteger',
				'LongText',
				'PrecisionDecimal',
				'RichText',
				'Text',
			],
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const {body: objectDefinition} =
			await objectActionAPIClient.postObjectDefinition(
				objectDefitionRequestData({objectFields})
			);

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {objectEntry: objectEntryValues} =
			await generateObjectEntryValues({
				objectEntryFormat: 'API',
				objectFields,
			});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			objectEntryValues,
			applicationName
		);

		const exportFilePath = await companyExportImportPage.export(
			`Tests 1 Items`,
			true
		);

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntry.id}`
		);

		expect(
			await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode(
				applicationName,
				objectEntry.externalReferenceCode
			)
		).toEqual({status: 'NOT_FOUND'});

		await companyExportImportPage.import(exportFilePath, true);

		const importedObjectEntry = await apiHelpers.get(
			`${apiHelpers.baseUrl}${applicationName}/by-external-reference-code/${objectEntry.externalReferenceCode}`
		);

		// Exclude friendlyUrl properties until LPD-66545 is resolved

		delete objectEntry.friendlyUrlPath;
		delete objectEntry.friendlyUrlPath_i18n;

		expect(importedObjectEntry).toEqual(
			expect.objectContaining({
				...objectEntry,
				actions: expect.any(Object),
				dateCreated: expect.any(String),
				dateModified: expect.any(String),
				id: expect.any(Number),
			})
		);
	}
);

test('can only import custom object entries when their definitions are already in the system', async ({
	apiHelpers,
	companyExportImportPage,
}) => {
	const objectActionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	const objectDefinitionRequestBody: ObjectDefinition =
		objectDefitionRequestData({
			className:
				'com.liferay.object.model.ObjectDefinition#test_definition',
			externalReferenceCode: 'test-definition',
			objectFields: [
				{
					DBType: 'String',
					businessType: 'Text',
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'textField',
					},
					name: 'textField',
					required: true,
				},
			],
		});

	let {body: objectDefinition} =
		await objectActionAPIClient.postObjectDefinition(
			objectDefinitionRequestBody
		);

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: 'testERC', textField: 'test'},
		'c/tests'
	);

	const exportFilePath =
		await companyExportImportPage.export('Tests 1 Items');

	objectActionAPIClient.deleteObjectDefinition(objectDefinition.id);

	await companyExportImportPage.import(
		exportFilePath,
		false,
		'The Data Handler for the "Tests" portlet is missing from the system.'
	);

	({body: objectDefinition} =
		await objectActionAPIClient.postObjectDefinition(
			objectDefinitionRequestBody
		));

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await companyExportImportPage.import(exportFilePath);

	expect(
		await apiHelpers.get(
			`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}`
		)
	).toEqual(
		expect.objectContaining({
			externalReferenceCode: objectEntry.externalReferenceCode,
			textField: objectEntry.textField,
		})
	);
});

test('can see corresponding elements at instance level', async ({
	apiHelpers,
	companyExportImportPage,
}) => {
	const objectActionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	const {body: objectDefinition} =
		await objectActionAPIClient.postObjectDefinition(
			objectDefitionRequestData()
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests'
	);

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

	await expect(companyExportImportPage.page.getByText('Tests')).toBeVisible();

	await expect(
		companyExportImportPage.page.getByText('C_Tests Change')
	).not.toBeVisible();

	await expect(
		companyExportImportPage.page.getByLabel('Delete Application Data')
	).not.toBeVisible();

	await expect(
		companyExportImportPage.page.getByText(
			'Mirror: All data and content inside the imported LAR is created as new the first time while maintaining a reference to the source. Subsequent imports from the same source update the entries instead of creating new entries.'
		)
	).toBeVisible();

	await expect(
		companyExportImportPage.page.getByText('Mirror with overwriting:')
	).not.toBeVisible();

	await expect(
		companyExportImportPage.page.getByText('Copy as New:')
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

test('cannot import a site scoped lar file', async ({
	companyExportImportPage,
	exportImportPage,
}) => {
	await exportImportPage.goToExport();

	const taskName = 'MyExport-' + getRandomString();

	await exportImportPage.export(taskName);

	await expect(
		exportImportPage.page
			.locator('//h2[span[normalize-space()="' + taskName + '"]]')
			.first()
			.locator('../..')
			.getByText('Successful')
	).toBeVisible();

	const exportFilePath =
		await exportImportPage.downloadExportProcess(taskName);

	await companyExportImportPage.import(
		exportFilePath,
		false,
		'The LAR file contains one or more entities with a different scope.'
	);
});
