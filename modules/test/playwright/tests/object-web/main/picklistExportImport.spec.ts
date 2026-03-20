/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {mkdir, readFile, writeFile} from 'fs/promises';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {listTypeDefinitionsPagesTest} from '../../../fixtures/listTypeDefinitionsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {getTempDir} from '../../../utils/temp';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	listTypeDefinitionsPagesTest,
	loginTest(),
	objectPagesTest
);

async function exportPicklistAsJSON(
	page,
	listTypeDefinitionPage,
	picklistName: string
) {
	await listTypeDefinitionPage.goto();

	const downloadPromise = page.waitForEvent('download');

	const row = page.getByRole('row', {name: picklistName});

	await row.getByRole('button', {name: 'Actions'}).click();

	await page.getByRole('menuitem', {name: 'Export as JSON'}).click();

	const download = await downloadPromise;

	const filePath = path.join(getTempDir(), download.suggestedFilename());

	await download.saveAs(filePath);

	const content = await readFile(filePath, 'utf-8');

	return {content, filePath, jsonContent: JSON.parse(content)};
}

async function importPicklistFromFile(
	page,
	listTypeDefinitionPage,
	filePath: string,
	picklistName: string
) {
	await listTypeDefinitionPage.goto();

	await page.waitForLoadState('networkidle');

	await expect(page.getByText('Picklists')).toBeVisible({timeout: 30000});

	await page.locator('button[aria-haspopup="true"]').first().click();

	await page
		.getByRole('menuitem', {name: 'Import Picklist', exact: true})
		.or(page.getByRole('menuitem', {name: 'Import', exact: true}))
		.click();

	await page.getByLabel('Name').first().fill(picklistName);

	const hiddenFileInput = page.locator('input[type="file"]');

	await hiddenFileInput.setInputFiles(filePath);

	await expect(
		page.getByLabel('External Reference Code').or(
			page.locator('#externalReferenceCode')
		)
	).not.toBeEmpty({timeout: 5000});

	await page.getByRole('button', {name: 'Import', exact: true}).click();
}

async function importPicklistFromFileWithWarning(
	page,
	listTypeDefinitionPage,
	filePath: string,
	picklistName: string
) {
	await importPicklistFromFile(
		page,
		listTypeDefinitionPage,
		filePath,
		picklistName
	);

	await page.getByRole('button', {name: 'Continue'}).click();
}

test(
	'LPD-78504 Can add entry with mandatory picklist field imported',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		site,
		viewObjectEntriesPage,
	}) => {
		// Corresponds to Poshi test: CanAddEntryWithMandatoryPicklistFieldImported (stub)

		const entryName1 = 'entry1' + getRandomInt();
		const entryName2 = 'entry2' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName1,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName1},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName2,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName2},
		});

		const {filePath, jsonContent} = await exportPicklistAsJSON(
			page,
			listTypeDefinitionPage,
			listTypeDefinition.name
		);

		const dataIndex = apiHelpers.data.findIndex(
			(item) =>
				item.id === listTypeDefinition.id &&
				item.type === 'listTypeDefinition'
		);

		apiHelpers.data.splice(dataIndex, 1);

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			listTypeDefinition.id
		);

		const importedPicklistName = 'ImportedPicklist' + getRandomInt();

		await importPicklistFromFile(
			page,
			listTypeDefinitionPage,
			filePath,
			importedPicklistName
		);

		await page.waitForLoadState('networkidle');

		await listTypeDefinitionPage.goto();

		const [importedListTypeDefinition] =
			await apiHelpers.listTypeAdmin.getFilteredListTypeDefinition(
				'name',
				importedPicklistName
			);

		expect(importedListTypeDefinition).toBeTruthy();

		apiHelpers.data.push({
			id: importedListTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				jsonContent.externalReferenceCode,
			objectFieldBusinessTypes: [
				{
					businessType: 'Picklist',
					required: true,
				},
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

		const picklistFieldName = objectFields[0].name as string;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[picklistFieldName]: {key: entryName1, name: entryName1},
			},
			'c/' + objectDefinition.name!.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(
			objectDefinition.className!,
			'en',
			site.friendlyUrlPath
		);

		await expect(
			page.locator('td').getByText(entryName1, {exact: true})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can add entry with picklist and custom object imported',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		site,
		viewObjectEntriesPage,
	}) => {
		// Corresponds to Poshi test: CanAddEntryWithPicklistAndCustomObjectImported

		const entryName1 = 'entry1' + getRandomInt();
		const entryName2 = 'entry2' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName1,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName1},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName2,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName2},
		});

		const {filePath, jsonContent} = await exportPicklistAsJSON(
			page,
			listTypeDefinitionPage,
			listTypeDefinition.name
		);

		const dataIndex = apiHelpers.data.findIndex(
			(item) =>
				item.id === listTypeDefinition.id &&
				item.type === 'listTypeDefinition'
		);

		apiHelpers.data.splice(dataIndex, 1);

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			listTypeDefinition.id
		);

		const importedPicklistName = 'ImportedPicklist' + getRandomInt();

		await importPicklistFromFile(
			page,
			listTypeDefinitionPage,
			filePath,
			importedPicklistName
		);

		await page.waitForLoadState('networkidle');

		const [importedListTypeDefinition] =
			await apiHelpers.listTypeAdmin.getFilteredListTypeDefinition(
				'name',
				importedPicklistName
			);

		expect(importedListTypeDefinition).toBeTruthy();

		apiHelpers.data.push({
			id: importedListTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				jsonContent.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist'],
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

		const picklistFieldName = objectFields[0].name as string;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[picklistFieldName]: {key: entryName1, name: entryName1},
			},
			'c/' + objectDefinition.name!.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(
			objectDefinition.className!,
			'en',
			site.friendlyUrlPath
		);

		await expect(
			page.locator('td').getByText(entryName1, {exact: true})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can add entry with picklist imported',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		site,
		viewObjectEntriesPage,
	}) => {
		// Corresponds to Poshi test: CanAddEntryWithPicklistImported

		const entryName1 = 'entry1' + getRandomInt();
		const entryName2 = 'entry2' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName1,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName1},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName2,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName2},
		});

		const {filePath, jsonContent} = await exportPicklistAsJSON(
			page,
			listTypeDefinitionPage,
			listTypeDefinition.name
		);

		const dataIndex = apiHelpers.data.findIndex(
			(item) =>
				item.id === listTypeDefinition.id &&
				item.type === 'listTypeDefinition'
		);

		apiHelpers.data.splice(dataIndex, 1);

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			listTypeDefinition.id
		);

		const importedPicklistName = 'ImportedPicklist' + getRandomInt();

		await importPicklistFromFile(
			page,
			listTypeDefinitionPage,
			filePath,
			importedPicklistName
		);

		await page.waitForLoadState('networkidle');

		const [importedListTypeDefinition] =
			await apiHelpers.listTypeAdmin.getFilteredListTypeDefinition(
				'name',
				importedPicklistName
			);

		expect(importedListTypeDefinition).toBeTruthy();

		apiHelpers.data.push({
			id: importedListTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				jsonContent.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist'],
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

		const picklistFieldName = objectFields[0].name as string;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[picklistFieldName]: {key: entryName1, name: entryName1},
			},
			'c/' + objectDefinition.name!.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(
			objectDefinition.className!,
			'en',
			site.friendlyUrlPath
		);

		await expect(
			page.locator('td').getByText(entryName1, {exact: true})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can add entry with state of picklist imported',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		site,
		viewObjectEntriesPage,
	}) => {
		// Corresponds to Poshi test: CanAddEntryWithStateOfPicklistImported

		test.fixme(
			true,
			'HTTP 500 when creating object definition with state picklist field via API - state field configuration may require additional setup'
		);

		const entryName1 = 'entry1' + getRandomInt();
		const entryName2 = 'entry2' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName1,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName1},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName2,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName2},
		});

		const {filePath, jsonContent} = await exportPicklistAsJSON(
			page,
			listTypeDefinitionPage,
			listTypeDefinition.name
		);

		const dataIndex = apiHelpers.data.findIndex(
			(item) =>
				item.id === listTypeDefinition.id &&
				item.type === 'listTypeDefinition'
		);

		apiHelpers.data.splice(dataIndex, 1);

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			listTypeDefinition.id
		);

		const importedPicklistName = 'ImportedPicklist' + getRandomInt();

		await importPicklistFromFile(
			page,
			listTypeDefinitionPage,
			filePath,
			importedPicklistName
		);

		await page.waitForLoadState('networkidle');

		const [importedListTypeDefinition] =
			await apiHelpers.listTypeAdmin.getFilteredListTypeDefinition(
				'name',
				importedPicklistName
			);

		expect(importedListTypeDefinition).toBeTruthy();

		apiHelpers.data.push({
			id: importedListTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				jsonContent.externalReferenceCode,
			objectFieldBusinessTypes: [
				{
					businessType: 'Picklist',
					state: true,
				},
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

		const picklistFieldName = objectFields[0].name as string;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[picklistFieldName]: {key: entryName1, name: entryName1},
			},
			'c/' + objectDefinition.name!.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(
			objectDefinition.className!,
			'en',
			site.friendlyUrlPath
		);

		await expect(
			page.locator('td').getByText(entryName1, {exact: true})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can add entry with translation of picklist imported',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		site,
		viewObjectEntriesPage,
	}) => {
		// Corresponds to Poshi test: CanAddEntryWithTranslationPicklistImported

		const entryName1 = 'entry1' + getRandomInt();
		const entryName1PtBR = 'entrada1' + getRandomInt();
		const entryName2 = 'entry2' + getRandomInt();
		const entryName2PtBR = 'entrada2' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName1,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName1, pt_BR: entryName1PtBR},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName2,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName2, pt_BR: entryName2PtBR},
		});

		const {filePath, jsonContent} = await exportPicklistAsJSON(
			page,
			listTypeDefinitionPage,
			listTypeDefinition.name
		);

		const dataIndex = apiHelpers.data.findIndex(
			(item) =>
				item.id === listTypeDefinition.id &&
				item.type === 'listTypeDefinition'
		);

		apiHelpers.data.splice(dataIndex, 1);

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			listTypeDefinition.id
		);

		const importedPicklistName = 'ImportedPicklist' + getRandomInt();

		await importPicklistFromFile(
			page,
			listTypeDefinitionPage,
			filePath,
			importedPicklistName
		);

		await page.waitForLoadState('networkidle');

		const [importedListTypeDefinition] =
			await apiHelpers.listTypeAdmin.getFilteredListTypeDefinition(
				'name',
				importedPicklistName
			);

		expect(importedListTypeDefinition).toBeTruthy();

		apiHelpers.data.push({
			id: importedListTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				jsonContent.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist'],
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

		const picklistFieldName = objectFields[0].name as string;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[picklistFieldName]: {key: entryName1, name: entryName1},
			},
			'c/' + objectDefinition.name!.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(
			objectDefinition.className!,
			'pt',
			site.friendlyUrlPath
		);

		await expect(
			page.locator('td').getByText(entryName1PtBR, {exact: true})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can export picklist as JSON',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page, site}) => {
		// Corresponds to Poshi test: CanExportPicklist

		const entryName1 = 'entry1' + getRandomInt();
		const entryName2 = 'entry2' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName1,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName1},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName2,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName2},
		});

		const {jsonContent} = await exportPicklistAsJSON(
			page,
			listTypeDefinitionPage,
			listTypeDefinition.name
		);

		expect(jsonContent).toBeTruthy();
		expect(jsonContent.externalReferenceCode).toBe(
			listTypeDefinition.externalReferenceCode
		);
		expect(jsonContent.listTypeEntries).toBeTruthy();
		expect(jsonContent.listTypeEntries.length).toBe(2);

		const entryKeys = jsonContent.listTypeEntries.map(
			(entry) => entry.key
		);

		expect(entryKeys).toContain(entryName1);
		expect(entryKeys).toContain(entryName2);
	}
);

test(
	'LPD-78504 Cannot import wrong picklist JSON file',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page, site}) => {
		// Corresponds to Poshi test: CannotImportWrongPicklist

		const tempDir = getTempDir();

		await mkdir(tempDir, {recursive: true});

		const tempFilePath = path.join(tempDir, 'wrong_format_picklist.json');

		await writeFile(
			tempFilePath,
			JSON.stringify({
				objectFields: [{name: 'field1', type: 'String'}],
				objectName: 'WrongObject',
			})
		);

		await listTypeDefinitionPage.goto();

		await page.waitForLoadState('networkidle');

		await expect(page.getByText('Picklists')).toBeVisible({timeout: 30000});

		await page.locator('button[aria-haspopup="true"]').first().click();

		await page
			.getByRole('menuitem', {name: 'Import Picklist', exact: true})
			.or(page.getByRole('menuitem', {name: 'Import', exact: true}))
			.click();

		await page
			.getByLabel('Name')
			.first()
			.fill('ImportedPicklist' + getRandomInt());

		const hiddenFileInput = page.locator('input[type="file"]');

		await hiddenFileInput.setInputFiles(tempFilePath);

		await page
			.getByRole('button', {name: 'Import', exact: true})
			.click();

		await expect(
			page.locator('.alert-danger').first()
		).toBeVisible({timeout: 10000});
	}
);

test(
	'LPD-78504 Can overwrite picklist when ERC is duplicated',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page, site}) => {
		// Corresponds to Poshi test: CanOverwritePicklistWhenERCisDuplicated

		const entryName1 = 'entry1' + getRandomInt();
		const entryName2 = 'entry2' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName1,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName1},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: entryName2,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: entryName2},
		});

		const {filePath, jsonContent} = await exportPicklistAsJSON(
			page,
			listTypeDefinitionPage,
			listTypeDefinition.name
		);

		const modifiedName = 'ModifiedPicklist' + getRandomInt();

		jsonContent.name = modifiedName;
		jsonContent.name_i18n = {en_US: modifiedName};

		const modifiedFilePath = path.join(
			getTempDir(),
			'modified_picklist.json'
		);

		await writeFile(modifiedFilePath, JSON.stringify(jsonContent));

		await importPicklistFromFileWithWarning(
			page,
			listTypeDefinitionPage,
			modifiedFilePath,
			modifiedName
		);

		await page.waitForLoadState('networkidle');

		await listTypeDefinitionPage.goto();

		await expect(
			page.getByRole('link', {name: modifiedName})
		).toBeVisible();
	}
);
