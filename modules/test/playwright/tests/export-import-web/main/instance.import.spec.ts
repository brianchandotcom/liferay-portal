/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionAPI,
	ObjectRelationshipAPI,
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
import {ApiHelpers, DataApiHelpers} from '../../../helpers/ApiHelpers';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../utils/getRandomInt';
import {normalizeRestPath} from '../../../utils/normalizeRestPath';
import performLogin, {
	performLoginViaApi,
	performLogout,
	performUserSwitch,
	userData,
} from '../../../utils/performLogin';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {readFileFromZip} from '../../../utils/zip';
import {generateObjectEntryValues} from '../../object-web/main/utils/generateObjectEntry';
import {generateObjectFields} from '../../object-web/main/utils/generateObjectFields';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {portletExportImportPageTest} from './fixtures/portletExportImportPageTest';
import {stagingPageTest} from './fixtures/stagingPageTest';
import {createUserAssignRolesAndLogin} from './utils/createUserAssignRolesAndLogin';
import {toDateRangeDate, toDateRangeTime} from './utils/dateRangeUtil';

export const test = mergeTests(
	applicationsMenuPageTest,
	companyExportImportPageTest,
	dataApiHelpersTest,
	depotAdminPageTest,
	documentLibraryPagesTest,
	exportImportPagesTest,
	featureFlagsTest({
		'LPD-35013': {enabled: true},
		'LPD-35443': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	pageEditorPagesTest,
	pageTemplatesPagesTest,
	portletExportImportPageTest,
	stagingPageTest,
	wikiPagesTest
);

test('Can export and import custom object entries at instance level', async ({
	apiHelpers,
	applicationsMenuPage,
	companyExportImportPage,
	exportImportPage,
}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', textField: objectDefinition.name},
		`${normalizeRestPath(objectDefinition.restContextPath)}`
	);

	await applicationsMenuPage.goToExport();

	const exportFilePath = await exportImportPage.export({
		portletLabels: [`${objectDefinition.name} 1 Items`],
	});

	const content = await readFileFromZip(
		`${objectDefinition.externalReferenceCode}.json`,
		exportFilePath
	);

	const json = JSON.parse(content);

	expect(json.length).toBe(1);
	expect(json[0]).not.toHaveProperty('permissions');

	expect(
		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${normalizeRestPath(objectDefinition.restContextPath)}/${objectEntry.id}`
		)
	).toBeOK();

	await companyExportImportPage.import({
		filePath: exportFilePath,
	});

	expect(
		await apiHelpers.get(
			`${apiHelpers.baseUrl}${normalizeRestPath(objectDefinition.restContextPath)}/by-external-reference-code/${objectEntry.externalReferenceCode}`
		)
	).toEqual(
		expect.objectContaining({
			externalReferenceCode: objectEntry.externalReferenceCode,
			textField: objectEntry.textField,
		})
	);
});

test('Can import account restricted entry when account does and does not exist in environment', async ({
	apiHelpers,
	applicationsMenuPage,
	companyExportImportPage,
	exportImportPage,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({
		id: account.id,
		type: 'account',
	});

	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
		ObjectRelationshipAPI
	);

	const {body: objectRelationship} =
		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			'L_ACCOUNT',
			{
				label: {
					en_US: 'objectRelationshipLabel' + getRandomInt(),
				},
				name: 'objectRelationshipName' + Math.floor(Math.random() * 99),
				objectDefinitionExternalReferenceCode1: 'L_ACCOUNT',
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				type: 'oneToMany',
			}
		);

	apiHelpers.data.push({
		id: objectRelationship.id,
		type: 'objectRelationship',
	});

	const accountEntryERC = `r_${objectRelationship.name}_accountEntryERC`;
	const accountEntryId = `r_${objectRelationship.name}_accountEntryId`;
	const applicationName = `${normalizeRestPath(objectDefinition.restContextPath)}`;

	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	await objectDefinitionAPIClient.patchObjectDefinition(objectDefinition.id, {
		accountEntryRestricted: true,
		accountEntryRestrictedObjectFieldName: accountEntryId,
	});

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{
			[accountEntryERC]: account.externalReferenceCode.toString(),
			[accountEntryId]: account.id.toString(),
		},
		applicationName
	);

	await applicationsMenuPage.goToExport();

	const exportFilePath = await exportImportPage.export({
		portletLabels: [`${objectDefinition.name} 1 Items`],
	});

	await test.step('assert entry is imported with account relationship properties when it exists', async () => {
		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntry.id}`
		);

		expect(
			await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode({
				applicationName,
				externalReferenceCode: objectEntry.externalReferenceCode,
			})
		).toEqual({status: 'NOT_FOUND'});

		await companyExportImportPage.import({
			filePath: exportFilePath,
		});

		const importedObjectEntry = await apiHelpers.get(
			`${apiHelpers.baseUrl}${applicationName}/by-external-reference-code/${objectEntry.externalReferenceCode}`
		);

		expect(importedObjectEntry).toMatchObject({
			[accountEntryERC]: account.externalReferenceCode,
			[accountEntryId]: account.id,
		});

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${importedObjectEntry.id}`
		);

		expect(
			await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode({
				applicationName,
				externalReferenceCode:
					importedObjectEntry.externalReferenceCode,
			})
		).toEqual({status: 'NOT_FOUND'});
	});

	await test.step('assert entry is imported with account relationship properties when it does not exist', async () => {
		await apiHelpers.headlessAdminUser.deleteAccount(account.id);

		expect(
			await apiHelpers.headlessAdminUser.getAccountByName(account.name)
		).toBe(undefined);

		await companyExportImportPage.import({
			filePath: exportFilePath,
			taskStatus: 'completedWithErrors',
		});

		const newImportedObjectEntry = await apiHelpers.get(
			`${apiHelpers.baseUrl}${applicationName}/by-external-reference-code/${objectEntry.externalReferenceCode}`
		);

		const importedAccount =
			await apiHelpers.headlessAdminUser.getAccountByName(account.name);

		expect(newImportedObjectEntry).toMatchObject({
			[accountEntryERC]: importedAccount.externalReferenceCode,
			[accountEntryId]: importedAccount.id,
		});
	});
});

test('Can import custom and system objects entries at instance level using date filter', async ({
	apiHelpers,
	applicationsMenuPage,
	companyExportImportPage,
	exportImportPage,
	page,
}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', textField: objectDefinition.name},
		`${normalizeRestPath(objectDefinition.restContextPath)}`
	);

	const {
		items: cookiesObjectEntries,
		totalCount: cookiesObjectEntriesTotalCount,
	} = await apiHelpers.get(
		`${apiHelpers.baseUrl}functional-cookies-entries/`
	);

	const applicationName = `${normalizeRestPath(objectDefinition.restContextPath)}`;

	const {
		dateCreated: cookiesObjectEntryCreationDate,
		id: cookiesObjectEntryId,
	} = cookiesObjectEntries[0];

	await test.step('export functional cookie entries using date reange filter', async () => {
		await applicationsMenuPage.goToExport();

		const startDate = new Date(cookiesObjectEntryCreationDate);

		startDate.setUTCDate(startDate.getUTCDate() - 1);

		const endDate = new Date(cookiesObjectEntryCreationDate);

		endDate.setUTCMinutes(endDate.getUTCMinutes() + 1);

		await page.getByTestId('creationMenuNewButton').nth(1).click();

		await page.getByLabel('Export Individual Deletions:').check();

		await applicationsMenuPage.goToExport();

		const functionalCookieEntriesExportFilePath =
			await exportImportPage.export({
				dateFilter: {
					endDate: toDateRangeDate(endDate),
					endTime: toDateRangeTime(endDate),
					startDate: toDateRangeDate(startDate),
					startTime: toDateRangeTime(startDate),
				},
				portletLabels: [
					`Functional Cookie Entries ${cookiesObjectEntriesTotalCount} Items`,
					`${objectDefinition.name} 1 Items`,
				],
			});

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntry.id}`
		);

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}functional-cookies-entries/${cookiesObjectEntryId}`
		);

		await companyExportImportPage.import({
			filePath: functionalCookieEntriesExportFilePath,
		});

		const {totalCount: importedCookiesObjectEntriesTotalCount} =
			await apiHelpers.get(
				`${apiHelpers.baseUrl}functional-cookies-entries/`
			);

		const {totalCount: importedCustomObjectEntriesTotalCount} =
			await apiHelpers.get(
				`${apiHelpers.baseUrl}${normalizeRestPath(objectDefinition.restContextPath)}`
			);

		expect(importedCookiesObjectEntriesTotalCount).toBe(
			cookiesObjectEntriesTotalCount
		);

		expect(importedCustomObjectEntriesTotalCount).toBe(0);
	});

	await test.step('export all entries using last 12 hours filter', async () => {
		await apiHelpers.objectEntry.postObjectEntry(
			{externalReferenceCode: '', textField: objectDefinition.name},
			`${normalizeRestPath(objectDefinition.restContextPath)}`
		);

		await applicationsMenuPage.goToExport();

		const allEntriesExportFilePath = await exportImportPage.export({
			dateFilter: {rangeLast: '12 Hours'},
			portletLabels: [
				`Functional Cookie Entries ${cookiesObjectEntriesTotalCount} Items`,
				`${objectDefinition.name} 1 Items`,
			],
		});

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}functional-cookies-entries/${cookiesObjectEntryId}`
		);

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntry.id}`
		);
		await companyExportImportPage.import({
			filePath: allEntriesExportFilePath,
		});

		const {totalCount: importedCookiesObjectEntriesTotalCount} =
			await apiHelpers.get(
				`${apiHelpers.baseUrl}functional-cookies-entries/`
			);

		const {totalCount: importedCustomObjectEntriesTotalCount} =
			await apiHelpers.get(
				`${apiHelpers.baseUrl}${normalizeRestPath(objectDefinition.restContextPath)}`
			);

		expect(importedCookiesObjectEntriesTotalCount).toBe(
			cookiesObjectEntriesTotalCount
		);

		expect(importedCustomObjectEntriesTotalCount).toBe(1);
	});
});

test('Can import custom object entries at instance level with or without permissions based on selection', async ({
	apiHelpers,
	applicationsMenuPage,
	companyExportImportPage,
	exportImportPage,
}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	let objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{
			externalReferenceCode: '',
			permissions: [
				{
					actionIds: ['VIEW'],
					roleName: 'Guest',
				},
			],
			textField: 'test',
		},
		`${normalizeRestPath(objectDefinition.restContextPath)}`
	);

	// Export with permissions

	await applicationsMenuPage.goToExport();

	const exportFilePath = await exportImportPage.export({
		includePermissions: true,
		portletLabels: [`${objectDefinition.name} 1 Items`],
	});

	// Import with permissions

	await apiHelpers.delete(
		`${apiHelpers.baseUrl}${normalizeRestPath(objectDefinition.restContextPath)}/${objectEntry.id}`
	);

	expect(
		await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode({
			applicationName: `${normalizeRestPath(objectDefinition.restContextPath)}`,
			externalReferenceCode: objectEntry.externalReferenceCode,
		})
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import({
		filePath: exportFilePath,
		includePermissions: true,
	});

	objectEntry = await apiHelpers.get(
		`${apiHelpers.baseUrl}${normalizeRestPath(objectDefinition.restContextPath)}/by-external-reference-code/${objectEntry.externalReferenceCode}/?nestedFields=permissions`
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

	await apiHelpers.delete(
		`${apiHelpers.baseUrl}${normalizeRestPath(objectDefinition.restContextPath)}/${objectEntry.id}`
	);

	expect(
		await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode({
			applicationName: `${normalizeRestPath(objectDefinition.restContextPath)}`,
			externalReferenceCode: objectEntry.externalReferenceCode,
		})
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import({
		filePath: exportFilePath,
	});

	objectEntry = await apiHelpers.get(
		`${apiHelpers.baseUrl}${normalizeRestPath(objectDefinition.restContextPath)}/by-external-reference-code/${objectEntry.externalReferenceCode}/?nestedFields=permissions`
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
	'Can import custom object entries with current user as creator',
	{tag: '@LPD-43217'},
	async ({
		apiHelpers,
		applicationsMenuPage,
		companyExportImportPage,
		exportImportPage,
		page,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				panelCategoryKey: 'control_panel.object',
				status: {code: 0},
			});
		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});
		const user = await createUserAssignRolesAndLogin({apiHelpers, page});
		const textFieldContent = `${objectDefinition.name} entry by ${user.alternateName}`;
		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				externalReferenceCode: '',
				name: 'test',
				textField: textFieldContent,
			},
			`c/${objectDefinition.name.toLowerCase()}s`
		);

		await applicationsMenuPage.goToExport();

		const exportFilePath = await exportImportPage.export({
			portletLabels: [`${objectDefinition.name} 1 Items`],
		});

		const applicationName = `${normalizeRestPath(objectDefinition.restContextPath)}`;
		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntry.id}`
		);

		await performUserSwitch(page, 'test');

		await test.step('Import the file with useCurrentUser enabled and check the imported entry authorship', async () => {
			await companyExportImportPage.import({
				expectedUploadErrorMessage: null,
				filePath: exportFilePath,
				includePermissions: false,
				useCurrentUser: true,
			});
			await applicationsMenuPage.goToObjectDefinition(
				objectDefinition.name
			);

			const row = page.locator('tr', {hasText: textFieldContent});

			await expect(row).toContainText(
				`${userData.test.name} ${userData.test.surname}`
			);
		});
	}
);

test(
	'Can import custom object entries with original creator, and creator user exists in the current environment',
	{tag: '@LPD-43217'},
	async ({
		apiHelpers,
		applicationsMenuPage,
		companyExportImportPage,
		exportImportPage,
		page,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				panelCategoryKey: 'control_panel.object',
				status: {code: 0},
			});
		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});
		const user = await createUserAssignRolesAndLogin({apiHelpers, page});
		const textFieldContent = `${objectDefinition.name} entry by ${user.alternateName}`;
		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				externalReferenceCode: '',
				name: 'test',
				textField: textFieldContent,
			},
			`${normalizeRestPath(objectDefinition.restContextPath)}`
		);

		await applicationsMenuPage.goToExport();

		const exportFilePath = await exportImportPage.export({
			portletLabels: [`${objectDefinition.name} 1 Items`],
		});

		const applicationName = `${normalizeRestPath(objectDefinition.restContextPath)}`;
		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntry.id}`
		);

		await performUserSwitch(page, 'test');

		await test.step('Import the file and check the imported entry authorship', async () => {
			await companyExportImportPage.import({
				filePath: exportFilePath,
			});
			await applicationsMenuPage.goToObjectDefinition(
				objectDefinition.name
			);

			const row = page.locator('tr', {hasText: textFieldContent});
			await expect(row).toContainText(
				`${user.givenName} ${user.familyName}`
			);
		});
	}
);

test(
	'Can import custom object entries with original creator, but creator user does not exist in the current environment',
	{tag: '@LPD-43217'},
	async ({
		apiHelpers,
		applicationsMenuPage,
		companyExportImportPage,
		exportImportPage,
		page,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				panelCategoryKey: 'control_panel.object',
				status: {code: 0},
			});
		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});
		const user = await createUserAssignRolesAndLogin({apiHelpers, page});
		const textFieldContent = `${objectDefinition.name} entry by ${user.alternateName}`;
		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{
				externalReferenceCode: '',
				name: 'test',
				textField: textFieldContent,
			},
			`${normalizeRestPath(objectDefinition.restContextPath)}`
		);

		await applicationsMenuPage.goToExport();

		const exportFilePath = await exportImportPage.export({
			portletLabels: [`${objectDefinition.name} 1 Items`],
		});

		const applicationName = `${normalizeRestPath(objectDefinition.restContextPath)}`;
		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntry.id}`
		);

		await performUserSwitch(page, 'test');

		await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));

		await test.step('Import the file and check the authorship fallback to the current user', async () => {
			await companyExportImportPage.import({
				filePath: exportFilePath,
			});
			await applicationsMenuPage.goToObjectDefinition(
				objectDefinition.name
			);

			const row = page.locator('tr', {hasText: textFieldContent});

			await expect(row).toContainText(
				`${userData.test.name} ${userData.test.surname}`
			);
		});
	}
);

test(
	'Can import custom object entry values',
	{
		tag: '@LPD-66167',
	},
	async ({
		apiHelpers,
		applicationsMenuPage,
		companyExportImportPage,
		exportImportPage,
	}) => {
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

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName = `${normalizeRestPath(objectDefinition.restContextPath)}`;

		const {objectEntry: objectEntryValues} =
			await generateObjectEntryValues({
				objectEntryFormat: 'API',
				objectFields,
			});

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			objectEntryValues,
			applicationName
		);

		await applicationsMenuPage.goToExport();

		const exportFilePath = await exportImportPage.export({
			includePermissions: true,
			portletLabels: [`${objectDefinition.name} 1 Items`],
		});

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}${applicationName}/${objectEntry.id}`
		);

		expect(
			await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode({
				applicationName,
				externalReferenceCode: objectEntry.externalReferenceCode,
			})
		).toEqual({status: 'NOT_FOUND'});

		await companyExportImportPage.import({
			filePath: exportFilePath,
			includePermissions: true,
		});

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

test('Can import many to many entries', async ({
	apiHelpers,
	applicationsMenuPage,
	companyExportImportPage,
	exportImportPage,
}) => {
	const objectDefinition1 =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition1.id,
		type: 'objectDefinition',
	});

	const objectDefinition2 =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition2.id,
		type: 'objectDefinition',
	});

	const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
		ObjectRelationshipAPI
	);

	const {body: objectRelationship} =
		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			objectDefinition1.externalReferenceCode,
			{
				label: {
					en_US: 'objectRelationshipLabel' + getRandomInt(),
				},
				name: 'objectRelationshipName' + Math.floor(Math.random() * 99),
				objectDefinitionExternalReferenceCode1:
					objectDefinition1.externalReferenceCode,
				objectDefinitionExternalReferenceCode2:
					objectDefinition2.externalReferenceCode,
				type: 'manyToMany',
			}
		);

	apiHelpers.data.push({
		id: objectRelationship.id,
		type: 'objectRelationship',
	});

	const applicationName1 = 'c/' + objectDefinition1.name.toLowerCase() + 's';
	const applicationName2 = 'c/' + objectDefinition2.name.toLowerCase() + 's';

	const objectDefinition1ObjectEntry1 =
		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'objectDefinition1ObjectEntry1'},
			applicationName1
		);

	const objectDefinition1ObjectEntry2 =
		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'objectDefinition1ObjectEntry2'},
			applicationName1
		);

	const objectDefinition1ObjectEntry3 =
		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'objectDefinition1ObjectEntry3'},
			applicationName1
		);

	const objectDefinition2ObjectEntry1 =
		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'objectDefinition2ObjectEntry1'},
			applicationName2
		);

	await test.step('relate objectDefinition1ObjectEntry1 and objectDefinition1ObjectEntry2 to objectDefinition2ObjectEntry1 and assert their persistence', async () => {
		await apiHelpers.objectEntry.putByExternalReferenceCodeCurrentExternalReferenceCodeObjectRelationshipNameRelatedExternalReferenceCode(
			{
				applicationName: applicationName1,
				currentExternalReferenceCode:
					objectDefinition1ObjectEntry1.externalReferenceCode,
				objectRelationshipName: objectRelationship.name,
				relatedExternalReferenceCode:
					objectDefinition2ObjectEntry1.externalReferenceCode,
			}
		);

		await apiHelpers.objectEntry.putByExternalReferenceCodeCurrentExternalReferenceCodeObjectRelationshipNameRelatedExternalReferenceCode(
			{
				applicationName: applicationName1,
				currentExternalReferenceCode:
					objectDefinition1ObjectEntry2.externalReferenceCode,
				objectRelationshipName: objectRelationship.name,
				relatedExternalReferenceCode:
					objectDefinition2ObjectEntry1.externalReferenceCode,
			}
		);

		const objectEntry =
			await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode({
				applicationName: applicationName2,
				externalReferenceCode:
					objectDefinition2ObjectEntry1.externalReferenceCode,
				nestedField: objectRelationship.name,
			});

		expect(objectEntry[objectRelationship.name].length).toBe(2);
	});

	await applicationsMenuPage.goToExport();

	const exportFilePath1 = await exportImportPage.export({
		portletLabels: [`${objectDefinition1.name} 3 Items`],
	});

	await test.step("relate objectDefinition1ObjectEntry3 to objectDefinition2ObjectEntry1 and assert it's persistence", async () => {
		await apiHelpers.objectEntry.putByExternalReferenceCodeCurrentExternalReferenceCodeObjectRelationshipNameRelatedExternalReferenceCode(
			{
				applicationName: applicationName1,
				currentExternalReferenceCode:
					objectDefinition1ObjectEntry3.externalReferenceCode,
				objectRelationshipName: objectRelationship.name,
				relatedExternalReferenceCode:
					objectDefinition2ObjectEntry1.externalReferenceCode,
			}
		);

		const objectEntry =
			await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode({
				applicationName: applicationName2,
				externalReferenceCode:
					objectDefinition2ObjectEntry1.externalReferenceCode,
				nestedField: objectRelationship.name,
			});

		expect(objectEntry[objectRelationship.name].length).toBe(3);
	});

	await applicationsMenuPage.goToExport();

	const exportFilePath2 = await exportImportPage.export({
		portletLabels: [`${objectDefinition1.name} 3 Items`],
	});

	await test.step("import object entry where objectDefinition1ObjectEntry3 was still unrelated and assert it's persistence", async () => {
		await companyExportImportPage.import({filePath: exportFilePath1});

		const objectEntry =
			await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode({
				applicationName: applicationName2,
				externalReferenceCode:
					objectDefinition2ObjectEntry1.externalReferenceCode,
				nestedField: objectRelationship.name,
			});

		expect(objectEntry[objectRelationship.name].length).toBe(2);
	});

	await test.step("import object entry where objectDefinition1ObjectEntry3 was related to objectDefinition2ObjectEntry1 and assert it's persistence", async () => {
		await companyExportImportPage.import({filePath: exportFilePath2});

		const objectEntry =
			await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode({
				applicationName: applicationName2,
				externalReferenceCode:
					objectDefinition2ObjectEntry1.externalReferenceCode,
				nestedField: objectRelationship.name,
			});

		expect(objectEntry[objectRelationship.name].length).toBe(3);
	});
});

test('Can see corresponding elements at instance level', async ({
	apiHelpers,
	applicationsMenuPage,
	companyExportImportPage,
	exportImportPage,
	page,
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
		{externalReferenceCode: '', textField: objectDefinition.name},
		`${normalizeRestPath(objectDefinition.restContextPath)}`
	);

	await applicationsMenuPage.goToExport();

	const exportFilePath = await exportImportPage.export({
		portletLabels: [`${objectDefinition.name} 1 Items`],
	});

	await page.goto('/');

	await companyExportImportPage.goToImportOptions(exportFilePath);

	await expect(page.getByRole('group', {name: 'Pages'})).not.toBeVisible();

	await expect(page.getByText('Comments, Ratings')).not.toBeVisible();

	await expect(page.getByText(`${objectDefinition.name}`)).toBeVisible();

	await expect(
		page.getByText(`${objectDefinition.externalReferenceCode} Change`)
	).not.toBeVisible();

	await expect(page.getByLabel('Delete Application Data')).not.toBeVisible();

	await expect(
		page.getByText(
			'Mirror: All data and content inside the imported LAR is created as new the first time while maintaining a reference to the source. Subsequent imports from the same source update the entries instead of creating new entries.'
		)
	).toBeVisible();

	await expect(page.getByText('Mirror with overwriting:')).not.toBeVisible();

	await expect(page.getByText('Copy as New:')).not.toBeVisible();
});

test('Can/not view Import menu item in Application menu depending on permissions', async ({
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

	await expect(exportImportPage.newImportButton).toBeVisible();

	await performLogout(page);

	await performLogin(page, user2.alternateName);

	await expect(applicationsMenuPage.applicationsMenuTabButton).toBeHidden();

	// Try to access the Import page directly using the stored URL

	await page.goto(importUrl);

	await expect(exportImportPage.newImportButton).toBeHidden();
});

test('Cannot import a site scoped lar file', async ({
	companyExportImportPage,
	exportImportPage,
}) => {
	await exportImportPage.goToExport();

	const exportFilePath = await exportImportPage.export();

	await companyExportImportPage.import({
		expectedUploadErrorMessage:
			'The LAR file contains one or more entities with a different scope.',
		filePath: exportFilePath,
		includePermissions: false,
	});
});

test('Can import at instance level when LAR contains custom objects without existing definitions', async ({
	apiHelpers,
	applicationsMenuPage,
	companyExportImportPage,
	exportImportPage,
}) => {
	const objectDefinitionExternalReferenceCode = `ObjectDefinition${getRandomInt()}`;

	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			className: `com.liferay.object.model.ObjectDefinition#${objectDefinitionExternalReferenceCode}`,
			objectDefinitionExternalReferenceCode,
			status: {code: 0},
		});

	try {
		await apiHelpers.objectEntry.postObjectEntry(
			{externalReferenceCode: 'testERC', textField: 'test'},
			`${normalizeRestPath(objectDefinition.restContextPath)}`
		);
	}
	catch {

		// Ensure cleanup if test execution stops before removing the object definition.

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});
	}

	await applicationsMenuPage.goToExport();

	const exportFilePath = await exportImportPage.export({
		portletLabels: [`${objectDefinitionExternalReferenceCode} 1 Items`],
	});

	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	await objectDefinitionAPIClient.deleteObjectDefinition(objectDefinition.id);

	await companyExportImportPage.import({
		filePath: exportFilePath,
		taskStatus: 'completedWithErrors',
	});
});

test('Can import object with different classname via portlet', async ({
	apiHelpers,
	applicationsMenuPage,
	featureFlags,
	page,
	portletExportImportPage,
	viewObjectDefinitionsPage,
}) => {
	test.slow();
	let objectDefinition: ObjectDefinition;
	let objectDefinitionFilePath: string;
	let objectEntryFilePath: string;

	await test.step('Create and download Object Definition LAR', async () => {
		objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				panelCategoryKey: 'control_panel.object',
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectDefinitionsPage.goto();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: viewObjectDefinitionsPage.page
				.locator('.dropdown-menu')
				.getByRole('menuitem', {name: 'Export / Import'}),
			trigger: viewObjectDefinitionsPage.page.getByLabel('Options'),
		});

		objectDefinitionFilePath = await portletExportImportPage.exportLARFile(
			/Objects-\d+\.portlet\.lar/
		);
	});

	await test.step('Create and download Object Entry LAR', async () => {
		await apiHelpers.objectEntry.postObjectEntry(
			{externalReferenceCode: '', textField: objectDefinition.name},
			`${normalizeRestPath(objectDefinition.restContextPath)}`
		);
		await applicationsMenuPage.goToObjectDefinition(objectDefinition.name);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page
				.locator('.dropdown-menu')
				.getByRole('menuitem', {name: 'Export / Import'}),
			trigger: page.getByLabel('Options'),
		});

		objectEntryFilePath = await portletExportImportPage.exportLARFile(
			/ObjectDefinition\d+-\d+\.portlet\.lar/
		);
	});

	let virtualInstanceApiHelpers: ApiHelpers;

	await test.step('Create and Configure Virtual Instance (Able)', async () => {
		const virtualInstance =
			await apiHelpers.headlessPortalInstance.addVirtualInstance({
				domain: 'liferay.com',
				portalInstanceId: 'www.able.com',
				virtualHost: 'www.able.com',
			});

		apiHelpers.data.push({
			id: virtualInstance.portalInstanceId,
			type: 'virtual-instance',
		});

		await performLoginViaApi({
			loginUrl: 'http://www.able.com:8080',
			page,
			screenName: 'test',
		});

		virtualInstanceApiHelpers = new DataApiHelpers(
			page,
			'http://www.able.com:8080'
		);

		for (const featureFlag of featureFlags) {
			await virtualInstanceApiHelpers.featureFlag.updateFeatureFlag(
				featureFlag.key,
				featureFlag.enabled,
				'http://www.able.com:8080'
			);
		}
	});

	await test.step('Object Definition into Virtual Instance', async () => {
		await page.goto(
			`http://www.able.com:8080/group/guest${PORTLET_URLS.objects}`
		);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page
				.locator('.dropdown-menu')
				.getByRole('menuitem', {name: 'Export / Import'}),
			trigger: page.getByLabel('Options'),
		});

		await portletExportImportPage.importLARFile(objectDefinitionFilePath);
		await expect(
			portletExportImportPage.frame
				.getByRole('cell', {name: 'Successful'})
				.first()
		).toBeVisible();
	});

	await test.step('Import Object Entry into Virtual Instance & Verify', async () => {
		await page.goto(`http://www.able.com:8080`);
		await page.getByLabel('Open Applications MenuCtrl+').click();
		await page.getByRole('tab', {name: 'Control Panel'}).click();
		await page.getByRole('menuitem', {name: objectDefinition.name}).click();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page
				.locator('.dropdown-menu')
				.getByRole('menuitem', {name: 'Export / Import'}),
			trigger: page.getByLabel('Options'),
		});

		const {totalCount: beforeImportingTotalCount} =
			await virtualInstanceApiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(beforeImportingTotalCount).toBe(0);

		await portletExportImportPage.importLARFile(objectEntryFilePath);
		await expect(
			portletExportImportPage.frame
				.getByRole('cell', {name: 'Successful'})
				.first()
		).toBeVisible();

		const {totalCount: afterImportingTotalCount} =
			await virtualInstanceApiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(afterImportingTotalCount).toBe(1);
	});
});
