/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';
import {mkdir, readFile, writeFile} from 'fs/promises';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {listTypeDefinitionsPagesTest} from '../../../fixtures/listTypeDefinitionsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {ListTypeDefinitionsPage} from '../../../pages/object-web/list-type/ListTypeDefinitionsPage';
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
	page: Page,
	listTypeDefinitionPage: ListTypeDefinitionsPage,
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
	page: Page,
	listTypeDefinitionPage: ListTypeDefinitionsPage,
	filePath: string,
	picklistName: string
) {
	await listTypeDefinitionPage.goto();

	await page.waitForLoadState('networkidle');

	await expect(page.getByText('Picklists')).toBeVisible({timeout: 30000});

	await page.locator('button[aria-haspopup="true"]').first().click();

	await page
		.getByRole('menuitem', {exact: true, name: 'Import Picklist'})
		.or(page.getByRole('menuitem', {exact: true, name: 'Import'}))
		.click();

	await page.getByLabel('Name').first().fill(picklistName);

	const hiddenFileInput = page.locator('input[type="file"]');

	await hiddenFileInput.setInputFiles(filePath);

	await expect(
		page
			.getByLabel('External Reference Code')
			.or(page.locator('#externalReferenceCode'))
	).not.toBeEmpty({timeout: 5000});

	await page.getByRole('button', {exact: true, name: 'Import'}).click();
}

async function importPicklistFromFileWithWarning(
	page: Page,
	listTypeDefinitionPage: ListTypeDefinitionsPage,
	filePath: string,
	picklistName: string
) {
	await importPicklistFromFile(
		page,
		listTypeDefinitionPage,
		filePath,
		picklistName
	);

	await expect(
		page.getByRole('heading', {name: 'Update Existing Picklist'})
	).toBeVisible();

	await expect(
		page.getByText(
			'Another picklist has the same external reference code. Continue to replace the existing picklist with the imported one. This action is permanent and can result in data loss if the imported picklist is missing information.'
		)
	).toBeVisible();

	await page.getByRole('button', {name: 'Continue'}).click();
}

test(
	'can add entry with picklist field imported (required or not)',
	{tag: '@LPS-167536'},
	async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		viewObjectEntriesPage,
	}) => {
		const picklistItem1 = 'item1' + getRandomInt();
		const picklistItem2 = 'item2' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: picklistItem1,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: picklistItem1},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: picklistItem2,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: picklistItem2},
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
					required: false,
				},
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

		const picklistOptionalFieldName = objectFields[0].name as string;

		const picklistRequiredFieldName = objectFields[1].name as string;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[picklistOptionalFieldName]: {
					key: picklistItem1,
					name: picklistItem1,
				},
				[picklistRequiredFieldName]: {
					key: picklistItem2,
					name: picklistItem2,
				},
			},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByRole('cell', {exact: true, name: picklistItem1})
		).toBeVisible();

		await expect(
			page.getByRole('cell', {exact: true, name: picklistItem2})
		).toBeVisible();
	}
);

test(
	'can add entry with state of picklist imported',
	{tag: '@LPS-167536'},
	async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		viewObjectEntriesPage,
	}) => {
		const picklistItem = 'item' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: picklistItem,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: picklistItem},
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
					objectFieldSettings: [
						{
							name: 'defaultValueType',
							value: 'inputAsValue',
						},
						{
							name: 'defaultValue',
							value: picklistItem,
						},
					],
					required: true,
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
				[picklistFieldName]: {key: picklistItem, name: picklistItem},
			},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByRole('cell', {exact: true, name: picklistItem})
		).toBeVisible();
	}
);

test(
	'can add entry with translation of picklist imported',
	{tag: '@LPS-167536'},
	async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		viewObjectEntriesPage,
	}) => {
		const picklistItem = 'item' + getRandomInt();
		const picklistItemPT = 'entrada' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: picklistItem,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: picklistItem, pt_BR: picklistItemPT},
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
				[picklistFieldName]: {key: picklistItem, name: picklistItem},
			},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className, 'pt');

		await expect(
			page.getByRole('cell', {exact: true, name: picklistItemPT})
		).toBeVisible();
	}
);

test(
	'can export picklist as JSON',
	{tag: '@LPS-167536'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		const picklistItem1 = 'item1' + getRandomInt();
		const picklistItem2 = 'item2' + getRandomInt();

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: picklistItem1,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: picklistItem1},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: picklistItem2,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: picklistItem2},
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

		const entryKeys = jsonContent.listTypeEntries.map((entry) => entry.key);

		expect(entryKeys).toContain(picklistItem1);
		expect(entryKeys).toContain(picklistItem2);
	}
);

test(
	'cannot import wrong picklist JSON file',
	{tag: '@LPS-167536'},
	async ({listTypeDefinitionPage, page}) => {
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

		await page.locator('button[aria-haspopup="true"]').first().click();

		await page
			.getByRole('menuitem', {exact: true, name: 'Import Picklist'})
			.click();

		await page.getByLabel('Name').fill('ImportedPicklist' + getRandomInt());

		const hiddenFileInput = page.locator('input[type="file"]');

		await hiddenFileInput.setInputFiles(tempFilePath);

		await page.getByRole('button', {exact: true, name: 'Import'}).click();

		await expect(page.locator('.alert-danger')).toHaveText(
			/The picklist failed to import/
		);
	}
);

test(
	'can overwrite picklist when ERC is duplicated',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: 'item1' + getRandomInt(),
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: 'item1' + getRandomInt()},
		});

		const {jsonContent} = await exportPicklistAsJSON(
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

		await expect(
			page.getByRole('link', {name: modifiedName})
		).toBeVisible();
	}
);
