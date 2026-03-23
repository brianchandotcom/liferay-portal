/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectActionAPI,
	ObjectDefinitionAPI,
	ObjectRelationshipAPI,
	ObjectValidationRuleAPI,
	ObjectViewAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';
import {readFile, writeFile} from 'fs/promises';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {getTempDir} from '../../../utils/temp';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

async function exportObjectDefinitionAsJSON(
	page,
	viewObjectDefinitionsPage,
	objectDefinitionLabel: string
) {
	await viewObjectDefinitionsPage.goto();

	await viewObjectDefinitionsPage.page
		.getByPlaceholder('Search')
		.fill(objectDefinitionLabel.replace(/ /g, ''));

	await viewObjectDefinitionsPage.page.keyboard.press('Enter');

	const downloadPromise = page.waitForEvent('download');

	const row = page.getByRole('row', {name: objectDefinitionLabel});

	await row.getByRole('button', {name: 'Actions'}).click();

	await page
		.getByRole('menuitem', {name: 'Export Object Definition'})
		.click();

	const download = await downloadPromise;

	const filePath = path.join(getTempDir(), download.suggestedFilename());

	await download.saveAs(filePath);

	const content = await readFile(filePath, 'utf-8');

	return {content, filePath, jsonContent: JSON.parse(content)};
}

async function importObjectDefinitionFromFile(
	page,
	viewObjectDefinitionsPage,
	filePath: string,
	objectName: string
): Promise<number | null> {
	await viewObjectDefinitionsPage.goto();

	await viewObjectDefinitionsPage.objectFolderActions.click();

	await page
		.getByRole('menuitem', {name: 'Import Object Definition'})
		.click();

	await page.getByLabel('Name').first().fill(objectName);

	const hiddenFileInput = page.locator('input[type="file"]');

	await hiddenFileInput.setInputFiles(filePath);

	await expect(
		page
			.getByLabel('External Reference Code')
			.or(page.locator('#externalReferenceCode'))
	).not.toBeEmpty({timeout: 10000});

	let importedObjectId: number | null = null;

	page.on('response', async (response) => {
		if (
			response.url().includes('/object-admin') &&
			response.request().method() === 'POST' &&
			response.status() === 200
		) {
			try {
				const body = await response.json();

				if (body.id && !importedObjectId) {
					importedObjectId = body.id;
				}
			}
			catch {
				// Ignore non-JSON responses
			}
		}
	});

	await page.getByRole('button', {name: 'Import'}).click();

	await page.locator('.modal-body').waitFor({state: 'hidden', timeout: 60000});

	return importedObjectId;
}

async function deleteObjectDefinitionFromData(apiHelpers, objectDefinitionId) {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	const dataIndex = apiHelpers.data.findIndex(
		(item) =>
			item.id === objectDefinitionId &&
			item.type === 'objectDefinition'
	);

	if (dataIndex !== -1) {
		apiHelpers.data.splice(dataIndex, 1);
	}

	await objectDefinitionAPIClient.deleteObjectDefinition(
		objectDefinitionId
	);
}

test(
	'LPD-78504 Field values persist for object entries with 100 fields after export and import',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage, viewObjectEntriesPage}) => {
		test.setTimeout(300000);

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		for (const statusName of ['open', 'review', 'closed']) {
			await apiHelpers.listTypeAdmin.postListTypeEntry({
				key: statusName,
				listTypeDefinitionExternalReferenceCode:
					listTypeDefinition.externalReferenceCode,
				name_i18n: {en_US: statusName},
			});
		}

		const fieldTypes = [
			'Text',
			'Integer',
			'PrecisionDecimal',
			'Boolean',
			'Date',
			'DateTime',
			'LongText',
			'Decimal',
			'LongInteger',
			'RichText',
		];

		const objectFieldBusinessTypes: any[] = [];

		for (const fieldType of fieldTypes) {
			for (let i = 0; i < 10; i++) {
				objectFieldBusinessTypes.push(fieldType);
			}
		}

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: objectFieldBusinessTypes.slice(0, 97),
		});

		const picklistFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: [
				'Picklist',
				{
					businessType: 'Picklist',
					objectFieldSettings: [
						{
							name: 'defaultValueType',
							value: 'inputAsValue' as any,
						},
						{
							name: 'defaultValue',
							value: 'open',
						},
					],
					required: true,
					state: true,
				},
				'MultiselectPicklist',
			],
		});

		const allObjectFields = [...objectFields, ...picklistFields];

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: allObjectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const importedObjectName = 'ImportedObject' + getRandomInt();

		const importedObjectId = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePath,
			importedObjectName
		);

		if (importedObjectId) {
			apiHelpers.data.push({
				id: importedObjectId,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.goto();

		await page
			.getByPlaceholder('Search')
			.fill(objectDefinition.label['en_US']);

		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: objectDefinition.label['en_US']})
		).toBeVisible();

		if (importedObjectId) {
			const objectDefinitionAPIClient =
				await apiHelpers.buildRestClient(ObjectDefinitionAPI);

			await objectDefinitionAPIClient.postObjectDefinitionPublish(
				importedObjectId
			);

			const importedObjectDefinition =
				await objectDefinitionAPIClient.getObjectDefinition(
					importedObjectId
				);

			await viewObjectEntriesPage.goto(
				importedObjectDefinition.body.className
			);

			const textFieldLabel = allObjectFields[0].label['en_US'];
			const entryValue = 'Test field value';

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldLabel: textFieldLabel,
				objectFieldValue: entryValue,
			});

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await waitForAlert(page);

			await viewObjectEntriesPage.backButton.click();

			await expect(
				page.locator('td').getByText(entryValue)
			).toBeVisible();
		}
	}
);

test(
	'LPD-78504 Can cancel importing an object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.objectFolderActions.click();

		await page
			.getByRole('menuitem', {name: 'Import Object Definition'})
			.click();

		await page
			.getByLabel('Name')
			.first()
			.fill('CancelledImport' + getRandomInt());

		const hiddenFileInput = page.locator('input[type="file"]');

		await hiddenFileInput.setInputFiles(filePath);

		await expect(
			page
				.getByLabel('External Reference Code')
				.or(page.locator('#externalReferenceCode'))
		).not.toBeEmpty({timeout: 10000});

		await page.getByRole('button', {name: 'Cancel'}).click();

		await expect(page.locator('.modal-body')).toBeHidden();

		await viewObjectDefinitionsPage.goto();

		await page
			.getByPlaceholder('Search')
			.fill(objectDefinition.label['en_US']);

		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {
				exact: true,
				name: objectDefinition.label['en_US'],
			})
		).toHaveCount(1);
	}
);

test(
	'LPD-78504 Can clear the JSON file on the import dialog',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.objectFolderActions.click();

		await page
			.getByRole('menuitem', {name: 'Import Object Definition'})
			.click();

		const hiddenFileInput = page.locator('input[type="file"]');

		await hiddenFileInput.setInputFiles(filePath);

		await expect(
			page
				.getByLabel('External Reference Code')
				.or(page.locator('#externalReferenceCode'))
		).not.toBeEmpty({timeout: 10000});

		await page.getByRole('button', {name: 'Clear'}).click();

		await expect(
			page
				.getByLabel('External Reference Code')
				.or(page.locator('#externalReferenceCode'))
		).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can export an object with Actions',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				label: {
					en_US: 'Action Label',
				},
				name: 'actionName' + getRandomInt(),
				objectActionExecutorKey: 'webhook',
				objectActionTriggerKey: 'onAfterAdd',
				parameters: {
					secret: '',
					url: 'http://localhost:8080',
				},
			}
		);

		const {jsonContent} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		expect(jsonContent).toBeTruthy();
		expect(jsonContent.objectActions).toBeTruthy();
		expect(jsonContent.objectActions.length).toBeGreaterThanOrEqual(1);
	}
);

test(
	'LPD-78504 Can export an object with Aggregation field',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		objectFieldsPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 2},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const relationshipName =
			'relationship' + Math.floor(Math.random() * 99);

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			objectDefinition.externalReferenceCode,
			{
				label: {
					en_US: 'Relationship',
				},
				name: relationshipName,
				objectDefinitionExternalReferenceCode1:
					objectDefinition.externalReferenceCode,
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				objectDefinitionId1: objectDefinition.id,
				objectDefinitionId2: objectDefinition.id,
				objectDefinitionName2: objectDefinition.name,
				type: 'oneToMany',
			}
		);

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		await objectDefinitionAPIClient.postObjectDefinitionPublish(
			objectDefinition.id
		);

		await objectFieldsPage.goto(objectDefinition.label['en_US']);

		await objectFieldsPage.addObjectField({
			aggregationField: 'ID',
			aggregationFieldFunction: 'Max',
			aggregationFieldRelationship: 'Relationship',
			objectFieldBusinessType: 'Aggregation',
			objectFieldLabel: 'Custom Aggregation',
		});

		const {jsonContent} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		expect(jsonContent).toBeTruthy();

		const aggregationField = jsonContent.objectFields?.find(
			(field) => field.businessType === 'Aggregation'
		);

		expect(aggregationField).toBeTruthy();
	}
);

test(
	'LPD-78504 Can import and export Custom Views structure',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
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

		const objectViewAPIClient =
			await apiHelpers.buildRestClient(ObjectViewAPI);

		await objectViewAPIClient.postObjectDefinitionObjectView(
			objectDefinition.id,
			{
				defaultObjectView: true,
				name: {en_US: 'Custom View'},
				objectViewColumns: [
					{
						objectFieldName: objectFields[0].name,
						priority: 0,
					},
				],
				objectViewSortColumns: [
					{
						objectFieldName: objectFields[0].name,
						priority: 0,
						sortOrder: 'asc',
					},
				],
			}
		);

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const importedObjectName = 'ImportedCustomViews' + getRandomInt();

		const importedObjectId = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePath,
			importedObjectName
		);

		if (importedObjectId) {
			apiHelpers.data.push({
				id: importedObjectId,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.goto();
		await page.getByPlaceholder('Search').fill(objectDefinition.label['en_US']);
		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: objectDefinition.label['en_US']})
		).toBeVisible();

		const {jsonContent: reExportedJson} =
			await exportObjectDefinitionAsJSON(
				page,
				viewObjectDefinitionsPage,
				objectDefinition.label['en_US']
			);

		expect(reExportedJson).toBeTruthy();
		expect(reExportedJson.objectViews).toBeTruthy();
		expect(reExportedJson.objectViews.length).toBeGreaterThanOrEqual(1);
	}
);

test(
	'LPD-78504 Can import and export State Manager structure',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		test.setTimeout(300000);
		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const picklistItemName = 'PicklistItem' + getRandomInt();

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: picklistItemName,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: picklistItemName},
		});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: [
				{
					businessType: 'Picklist',
					objectFieldSettings: [
						{
							name: 'defaultValueType',
							value: 'inputAsValue' as any,
						},
						{
							name: 'defaultValue',
							value: picklistItemName.toLowerCase(),
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

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const importedObjectName = 'ImportedState' + getRandomInt();

		const importedObjectId = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePath,
			importedObjectName
		);

		if (importedObjectId) {
			apiHelpers.data.push({
				id: importedObjectId,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.goto();
		await page.getByPlaceholder('Search').fill(objectDefinition.label['en_US']);
		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: objectDefinition.label['en_US']})
		).toBeVisible();

		const {jsonContent: reExportedJson} =
			await exportObjectDefinitionAsJSON(
				page,
				viewObjectDefinitionsPage,
				objectDefinition.label['en_US']
			);

		expect(reExportedJson).toBeTruthy();

		const stateField = reExportedJson.objectFields?.find(
			(field) => field.state === true
		);

		expect(stateField).toBeTruthy();
	}
);

test(
	'LPD-78504 Can import and export Validation structure',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
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

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'ddm',
				engineLabel: 'Expression Builder',
				errorLabel: {
					en_US: 'Validation error message',
				},
				name: {
					en_US: 'Validation Rule ' + getRandomInt(),
				},
				objectValidationRuleSettings: [] as any,
				outputType: 'fullValidation',
				script: 'isEmailAddress(textField)',
				system: false,
			}
		);

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const importedObjectName = 'ImportedValidation' + getRandomInt();

		const importedObjectId = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePath,
			importedObjectName
		);

		if (importedObjectId) {
			apiHelpers.data.push({
				id: importedObjectId,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.goto();
		await page.getByPlaceholder('Search').fill(objectDefinition.label['en_US']);
		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: objectDefinition.label['en_US']})
		).toBeVisible();

		const {jsonContent: reExportedJson} =
			await exportObjectDefinitionAsJSON(
				page,
				viewObjectDefinitionsPage,
				objectDefinition.label['en_US']
			);

		expect(reExportedJson).toBeTruthy();
		expect(reExportedJson.objectValidationRules).toBeTruthy();
		expect(
			reExportedJson.objectValidationRules.length
		).toBeGreaterThanOrEqual(1);
	}
);

test(
	'LPD-78504 Can import data structure to custom objects',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const importedObjectName = 'ImportedSimple' + getRandomInt();

		const importedObjectId = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePath,
			importedObjectName
		);

		if (importedObjectId) {
			apiHelpers.data.push({
				id: importedObjectId,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.goto();
		await page.getByPlaceholder('Search').fill(objectDefinition.label['en_US']);
		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: objectDefinition.label['en_US']})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can import and maintain Fields after importing an Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const customFieldLabel = objectFields[0].label['en_US'];

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const importedObjectName = 'ImportedWithField' + getRandomInt();

		const importedObjectId = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePath,
			importedObjectName
		);

		if (importedObjectId) {
			apiHelpers.data.push({
				id: importedObjectId,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinition.label['en_US']
		);

		await page
			.locator('.nav-item .nav-link')
			.filter({hasText: 'Fields'})
			.click();

		await expect(
			page.getByRole('cell').getByText(customFieldLabel, {exact: true})
		).toBeVisible();

		await expect(
			page.getByRole('cell').getByText('Text', {exact: true}).first()
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can import and maintain Layouts after importing an Object',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		objectLayoutsPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
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

		const layoutName = 'Layout' + getRandomInt();

		await objectLayoutsPage.goto(objectDefinition.label['en_US']);

		await objectLayoutsPage.createObjectLayout(layoutName);

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const importedObjectName = 'ImportedWithLayout' + getRandomInt();

		const importedObjectId = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePath,
			importedObjectName
		);

		if (importedObjectId) {
			apiHelpers.data.push({
				id: importedObjectId,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinition.label['en_US']
		);

		await page.getByRole('link', {name: 'Layouts'}).click();

		await expect(
			page.getByRole('link', {name: layoutName})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can import an object with Actions',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const actionLabel = 'ImportedAction' + getRandomInt();

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				label: {
					en_US: actionLabel,
				},
				name: 'actionName' + getRandomInt(),
				objectActionExecutorKey: 'webhook',
				objectActionTriggerKey: 'onAfterAdd',
				parameters: {
					secret: '',
					url: 'http://localhost:8080',
				},
			}
		);

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const importedObjectName = 'ImportedWithAction' + getRandomInt();

		const importedObjectId = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePath,
			importedObjectName
		);

		if (importedObjectId) {
			apiHelpers.data.push({
				id: importedObjectId,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinition.label['en_US']
		);

		await page.getByRole('link', {name: 'Actions'}).click();

		await expect(
			page.getByRole('cell').getByText(actionLabel, {exact: true})
		).toBeVisible();

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can import the same object more than once',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const jsonContent = JSON.parse(await readFile(filePath, 'utf-8'));

		const importedObjectNameA = 'ImportedObjectA' + getRandomInt();
		const importedObjectNameB = 'ImportedObjectB' + getRandomInt();

		const filePathA = path.join(
			getTempDir(),
			importedObjectNameA + '.json'
		);

		const filePathB = path.join(
			getTempDir(),
			importedObjectNameB + '.json'
		);

		const makeMinimalJson = (
			objectName: string,
			label: Record<string, string>
		) => ({
			externalReferenceCode: objectName,
			label,
			name: objectName,
			objectFields: [],
			objectFolderExternalReferenceCode:
				jsonContent.objectFolderExternalReferenceCode ?? 'default',
			panelCategoryKey: jsonContent.panelCategoryKey ?? '',
			pluralLabel: {en_US: objectName + 's'},
			scope: jsonContent.scope ?? 'company',
			status: {code: 2},
			titleObjectFieldName: jsonContent.titleObjectFieldName ?? 'id',
		});

		await writeFile(
			filePathA,
			JSON.stringify(
				makeMinimalJson(
					importedObjectNameA,
					jsonContent.label as Record<string, string>
				)
			)
		);

		await writeFile(
			filePathB,
			JSON.stringify(
				makeMinimalJson(
					importedObjectNameB,
					jsonContent.label as Record<string, string>
				)
			)
		);

		const importedObjectIdA = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePathA,
			importedObjectNameA
		);

		if (importedObjectIdA) {
			apiHelpers.data.push({
				id: importedObjectIdA,
				type: 'objectDefinition',
			});
		}

		const importedObjectIdB = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePathB,
			importedObjectNameB
		);

		if (importedObjectIdB) {
			apiHelpers.data.push({
				id: importedObjectIdB,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.goto();

		await page
			.getByPlaceholder('Search')
			.fill(objectDefinition.label['en_US']);

		await page.keyboard.press('Enter');

		const objectDefinitionLinks = page.getByRole('link', {
			exact: true,
			name: objectDefinition.label['en_US'],
		});

		await expect(objectDefinitionLinks).toHaveCount(2);
	}
);

test(
	'LPD-78504 Can import and maintain Scope after importing an Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				scope: 'company',
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const importedObjectName = 'ImportedWithScope' + getRandomInt();

		const importedObjectId = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePath,
			importedObjectName
		);

		if (importedObjectId) {
			apiHelpers.data.push({
				id: importedObjectId,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.goto();
		await page.getByPlaceholder('Search').fill(objectDefinition.label['en_US']);
		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: objectDefinition.label['en_US']})
		).toBeVisible();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinition.label['en_US']
		);

		await page.getByRole('link', {name: 'Details'}).click();

		await expect(page.getByText('Company', {exact: true})).toBeVisible();
	}
);

test(
	'LPD-78504 Imported custom object is created with Draft status',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 2},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {filePath} = await exportObjectDefinitionAsJSON(
			page,
			viewObjectDefinitionsPage,
			objectDefinition.label['en_US']
		);

		await deleteObjectDefinitionFromData(
			apiHelpers,
			objectDefinition.id
		);

		const importedObjectName = 'ImportedDraft' + getRandomInt();

		const importedObjectId = await importObjectDefinitionFromFile(
			page,
			viewObjectDefinitionsPage,
			filePath,
			importedObjectName
		);

		if (importedObjectId) {
			apiHelpers.data.push({
				id: importedObjectId,
				type: 'objectDefinition',
			});
		}

		await viewObjectDefinitionsPage.goto();
		await page.getByPlaceholder('Search').fill(objectDefinition.label['en_US']);
		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: objectDefinition.label['en_US']})
		).toBeVisible();

		const row = page.getByRole('row', {
			name: objectDefinition.label['en_US'],
		});

		await expect(row.getByText('Draft')).toBeVisible();
	}
);
