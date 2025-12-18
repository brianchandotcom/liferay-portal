/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionAPI,
	ObjectRelationship,
	ObjectRelationshipAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {uiElementsPageTest} from '../../../fixtures/uiElementsTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import performLogin, {
	performLogout,
	userData,
} from '../../../utils/performLogin';
import {getTempDir} from '../../../utils/temp';
import {readFileFromZip} from '../../../utils/zip';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {toDateRangeDate, toDateRangeTime} from './utils/dateRangeUtil';
import {objectDefitionRequestData} from './utils/objectDefitionRequestData';

export const test = mergeTests(
	applicationsMenuPageTest,
	companyExportImportPageTest,
	dataApiHelpersTest,
	exportImportPagesTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true},
	}),
	loginTest(),
	productMenuPageTest,
	uiElementsPageTest
);

const rootModelTest = mergeTests(
	test,
	featureFlagsTest({
		'LPD-34594': {enabled: true},
		'LPD-35914': {enabled: true},
	})
);

rootModelTest.describe(
	'Manage export and import of root model object definitions',
	() => {
		rootModelTest(
			'can distinguish root model object definitions in export/import',
			async ({
				apiHelpers,
				applicationsMenuPage,
				exportImportPage,
				page,
			}) => {
				const objectRelationships: ObjectRelationship[] = [];
				const objectRelationshipAPIClient =
					await apiHelpers.buildRestClient(ObjectRelationshipAPI);

				try {
					const objectDefinitionA =
						await apiHelpers.objectAdmin.postRandomObjectDefinition(
							{
								status: {code: 0},
							}
						);

					const objectDefinitionB =
						await apiHelpers.objectAdmin.postRandomObjectDefinition(
							{
								status: {code: 0},
							}
						);

					const objectDefinitionC =
						await apiHelpers.objectAdmin.postRandomObjectDefinition(
							{
								status: {code: 0},
							}
						);

					apiHelpers.data.push({
						id: objectDefinitionA.id,
						type: 'objectDefinition',
					});
					apiHelpers.data.push({
						id: objectDefinitionB.id,
						type: 'objectDefinition',
					});
					apiHelpers.data.push({
						id: objectDefinitionC.id,
						type: 'objectDefinition',
					});

					const {body: objectRelationshipAB} =
						await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
							objectDefinitionA.externalReferenceCode,
							{
								edge: true,
								label: {
									en_US:
										'objectRelationshipABLabel' +
										getRandomInt(),
								},
								name:
									'objectRelationshipABName' +
									Math.floor(Math.random() * 99),
								objectDefinitionExternalReferenceCode1:
									objectDefinitionA.externalReferenceCode,
								objectDefinitionExternalReferenceCode2:
									objectDefinitionB.externalReferenceCode,
								objectDefinitionId1: objectDefinitionA.id,
								objectDefinitionId2: objectDefinitionB.id,
								objectDefinitionName2: objectDefinitionB.name,
								type: 'oneToMany',
							}
						);

					const {body: objectRelationshipAC} =
						await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
							objectDefinitionA.externalReferenceCode,
							{
								edge: true,
								label: {
									en_US:
										'objectRelationshipBCLabel' +
										getRandomInt(),
								},
								name:
									'objectRelationshipBCName' +
									Math.floor(Math.random() * 99),
								objectDefinitionExternalReferenceCode1:
									objectDefinitionA.externalReferenceCode,
								objectDefinitionExternalReferenceCode2:
									objectDefinitionC.externalReferenceCode,
								objectDefinitionId1: objectDefinitionA.id,
								objectDefinitionId2: objectDefinitionC.id,
								objectDefinitionName2: objectDefinitionC.name,
								type: 'oneToMany',
							}
						);

					objectRelationships.push(
						objectRelationshipAB,
						objectRelationshipAC
					);

					apiHelpers.data.push({
						id: objectRelationshipAB.id,
						type: 'objectRelationship',
					});
					apiHelpers.data.push({
						id: objectRelationshipAC.id,
						type: 'objectRelationship',
					});

					const objectEntryA =
						await apiHelpers.objectEntry.postObjectEntry(
							{textField: 'entryA'},
							'c/' + objectDefinitionA.name.toLowerCase() + 's'
						);

					const objectEntryB =
						await apiHelpers.objectEntry.postObjectEntry(
							{textField: 'entryB'},
							'c/' + objectDefinitionB.name.toLowerCase() + 's'
						);

					await apiHelpers.objectEntry.postObjectEntry(
						{
							[`r_${objectRelationshipAB.name}_c_${objectDefinitionA.name[0].toLowerCase() + objectDefinitionA.name.substring(1)}Id`]:
								objectEntryA.id.toString(),
							[`r_${objectRelationshipAC.name}_c_${objectDefinitionB.name[0].toLowerCase() + objectDefinitionB.name.substring(1)}Id`]:
								objectEntryB.id.toString(),
							textField: 'entryC',
						},
						'c/' + objectDefinitionC.name.toLowerCase() + 's'
					);

					const objectDefinitionRootCheckbox = page.getByLabel(
						new RegExp(
							`${objectDefinitionA.label.en_US}\\s*Root Object`
						)
					);

					await applicationsMenuPage.goToExport();

					await exportImportPage.newExportButton.click();

					await expect(objectDefinitionRootCheckbox).toBeVisible();

					await expect(
						page.getByText(
							`${objectDefinitionB.label.en_US}, ${objectDefinitionC.label.en_US}`
						)
					).toBeVisible();

					await applicationsMenuPage.goToExport();

					const filePath = await exportImportPage.export({
						portletLabels: [
							`${objectDefinitionA.name} Root Object 1 Items`,
						],
					});

					await applicationsMenuPage.goToImport();

					await exportImportPage.newImportButton.click();

					await page
						.locator('input[type="file"]')
						.setInputFiles(filePath);

					await exportImportPage.continueButton.click();

					await expect(objectDefinitionRootCheckbox).toBeChecked();

					await expect(
						page.getByText(
							`${objectDefinitionB.label.en_US}, ${objectDefinitionC.label.en_US}`
						)
					).toBeVisible();
				}
				finally {
					for (const objectRelationship of objectRelationships) {
						await objectRelationshipAPIClient.putObjectRelationship(
							objectRelationship.id,
							{
								...objectRelationship,
								edge: false,
							}
						);
					}
				}
			}
		);
	}
);

test('cannot export site scoped custom object entries at instance level', async ({
	apiHelpers,
	applicationsMenuPage,
	page,
}) => {
	const objectActionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	const {body: objectDefinition} =
		await objectActionAPIClient.postObjectDefinition(
			objectDefitionRequestData({scope: 'site'})
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests/scopes/Guest'
	);

	await applicationsMenuPage.goToExport();

	await page.getByTestId('creationMenuNewButton').nth(1).click();

	await expect(page.getByLabel('Tests')).toBeHidden();
});

test('can export custom object entries at instance level with date filter', async ({
	apiHelpers,
	applicationsMenuPage,
	exportImportPage,
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

	await applicationsMenuPage.goToExport();

	const exportFilePath1 = await exportImportPage.export({
		portletLabels: ['Tests 1 Items'],
	});

	const content1 = await readFileFromZip('C_Test.json', exportFilePath1);

	const json1 = JSON.parse(content1);

	expect(json1.length).toBe(1);

	const endDate = new Date();

	endDate.setDate(endDate.getDate() - 1);

	const startDate = new Date();

	startDate.setDate(startDate.getDate() - 2);

	await applicationsMenuPage.goToExport();

	const exportFilePath2 = await exportImportPage.export({
		dateFilter: {
			endDate: toDateRangeDate(endDate),
			endTime: toDateRangeTime(endDate),
			startDate: toDateRangeDate(startDate),
			startTime: toDateRangeTime(startDate),
		},
		portletLabels: ['Tests 1 Items'],
	});

	const content2 = await readFileFromZip('C_Test.json', exportFilePath2);

	const json2 = JSON.parse(content2);

	expect(json2.length).toBe(0);

	await applicationsMenuPage.goToExport();

	const exportFilePath3 = await exportImportPage.export({
		dateFilter: {rangeLast: '12 Hours'},
		portletLabels: ['Tests 1 Items'],
	});

	const content3 = await readFileFromZip('C_Test.json', exportFilePath3);

	const json3 = JSON.parse(content3);

	expect(json3.length).toBe(1);
});

test('can export new default and custom task name', async ({
	apiHelpers,
	applicationsMenuPage,
	exportImportPage,
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

	await applicationsMenuPage.goToExport();

	const defaultExportFilePath = await exportImportPage.export({
		portletLabels: ['Tests 1 Items'],
	});

	expect(defaultExportFilePath).toMatch(
		new RegExp(`^${getTempDir()}Export-`)
	);

	const taskName = 'CustomTaskName';

	await applicationsMenuPage.goToExport();

	const customExportFilePath = await exportImportPage.export({
		portletLabels: ['Tests 1 Items'],
		taskName,
	});

	expect(customExportFilePath).toMatch(
		new RegExp(`^${getTempDir()}${taskName}-`)
	);
});

test('can export custom object entries at instance level with permissions', async ({
	apiHelpers,
	applicationsMenuPage,
	exportImportPage,
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

	await applicationsMenuPage.goToExport();

	const exportFilePath = await exportImportPage.export({
		includePermissions: true,
		portletLabels: ['Tests 1 Items'],
	});

	const content = await readFileFromZip('C_Test.json', exportFilePath);

	const json = JSON.parse(content);

	expect(json.length).toBe(1);
	expect(json[0]).toHaveProperty('permissions');
});

test('can see corresponding elements at instance level', async ({
	apiHelpers,
	applicationsMenuPage,
	companyExportImportPage,
	uiElementsPage,
}) => {
	const objectActionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);
	const {body: objectDefinition} =
		await objectActionAPIClient.postObjectDefinition(
			objectDefitionRequestData()
		);
	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await apiHelpers.objectEntry.postObjectEntry({name: 'test'}, 'c/tests');

	await applicationsMenuPage.goToExport();
	await uiElementsPage.clickNewButton();
	await expect(
		companyExportImportPage.page.getByText('Comments, Ratings')
	).not.toBeVisible();

	await expect(
		companyExportImportPage.page.getByText('Tests 1 Items')
	).not.toBeVisible();

	await companyExportImportPage.page.getByLabel('Tests').click();

	await expect(
		companyExportImportPage.page.getByText('C_Test Change')
	).not.toBeVisible();

	await expect(
		companyExportImportPage.page.getByRole('link', {name: 'Refresh Counts'})
	).toBeVisible();
});

test(
	'can see the Deletions label at the instance level',
	{tag: ['@LPD-37317']},
	async ({applicationsMenuPage, exportImportPage, uiElementsPage}) => {
		await applicationsMenuPage.goToExport();
		await uiElementsPage.clickNewButton();

		const deletionsLabelText =
			await exportImportPage.deletionsLabel.textContent();

		expect(deletionsLabelText?.replace(/\s+/g, ' ').trim()).toBe(
			'Export Individual Deletions: If this is checked, the delete operations performed will be exported in the LAR file.'
		);
	}
);

test('Can/not view Export menu item in Application menu depending on permissions', async ({
	apiHelpers,
	applicationsMenuPage,
	exportImportPage,
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

	await expect(exportImportPage.newExportButton).toBeVisible();

	await performLogout(page);

	await performLogin(page, user2.alternateName);

	await expect(applicationsMenuPage.applicationsMenuTabButton).toBeHidden();

	// Try to access the Export page directly using the stored URL

	await page.goto(exportUrl);

	await expect(exportImportPage.newExportButton).toBeHidden();
});
