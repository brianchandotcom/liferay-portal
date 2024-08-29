/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(apiHelpersTest, loginTest(), objectPagesTest);

const createdEntities = {
	listTypeDefinitionIds: [],
	objectDefinitions: [],
} as {
	listTypeDefinitionIds: number[];
	objectDefinitions: ObjectDefinition[];
};

test.beforeEach(async ({apiHelpers}) => {
	const newObjectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectFolderExternalReferenceCode: 'default',
			status: {code: 0},
		});

	createdEntities.objectDefinitions.push(newObjectDefinition);
});

test.afterEach(async ({apiHelpers}) => {
	if (createdEntities.objectDefinitions.length) {
		await Promise.all(
			createdEntities.objectDefinitions.map(async (objectDefinition) => {
				await apiHelpers.objectAdmin.deleteObjectDefinition(
					objectDefinition.id
				);
			})
		);

		createdEntities.objectDefinitions = [];
	}

	if (createdEntities.listTypeDefinitionIds.length) {
		await Promise.all(
			createdEntities.listTypeDefinitionIds.map(
				async (listTypeDefinitionId) =>
					await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
						listTypeDefinitionId
					)
			)
		);

		createdEntities.listTypeDefinitionIds = [];
	}
});

test.describe('Manage object fields through Model Builder', () => {
	test.beforeEach(({page}) => {
		page.setViewportSize({height: 1080, width: 1920});
	});

	test('all picklist definitions are listed during object field creation', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderObjectDefinitionNodePage,
	}) => {
		const {listTypeDefinitionIds, objectDefinitions} = createdEntities;

		const [objectDefinition] = objectDefinitions;

		const existingListTypeDefinitions = (
			await apiHelpers.listTypeAdmin.getListTypeDefinitions()
		).items;

		const allListTypeDefinitions = existingListTypeDefinitions.concat(
			await Promise.all(
				Array(22)
					.fill(null)
					.map(
						async () =>
							await apiHelpers.listTypeAdmin.postRandomListTypeDefinition()
					)
			)
		);

		allListTypeDefinitions.forEach(({id}) =>
			listTypeDefinitionIds.push(id)
		);

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		await modelBuilderObjectDefinitionNodePage.openAddNewObjectFieldOrRelationshipModal(
			objectDefinition.name,
			modelBuilderDiagramPage.objectDefinitionNodes,
			modelBuilderObjectDefinitionNodePage.addObjectFieldButton
		);

		await modelBuilderObjectDefinitionNodePage.fillObjectFieldLabelInput(
			'objectFieldLabel' + getRandomInt()
		);

		await modelBuilderObjectDefinitionNodePage.selectNewObjectFieldBusinessTypeOption(
			'Picklist'
		);

		await modelBuilderObjectDefinitionNodePage.objectFieldPicklistSelect.click();

		const listTypeDefinitionBox =
			modelBuilderDiagramPage.page.getByRole('listbox');

		await expect(listTypeDefinitionBox).toBeVisible();

		await expect(listTypeDefinitionBox.getByRole('option')).toHaveCount(
			allListTypeDefinitions.length
		);
	});

	test('can add picklist object field to object definition node', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderObjectDefinitionNodePage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const {listTypeDefinitionIds, objectDefinitions} = createdEntities;

		const [objectDefinition] = objectDefinitions;

		await page.goto('/');

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		listTypeDefinitionIds.push(listTypeDefinition.id);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder('default');

		await viewObjectDefinitionsPage.viewInModelBuilderButton.click();

		const objectFieldLabel = 'objectFieldLabel' + getRandomInt();

		await modelBuilderObjectDefinitionNodePage.createObjectField({
			listTypeDefinitionName: listTypeDefinition.name,
			mandatory: false,
			objectDefinitionLabel: objectDefinition.label['en_US'],
			objectDefinitionNodes:
				modelBuilderDiagramPage.objectDefinitionNodes,
			objectFieldBusinessType: 'Picklist',
			objectFieldLabel,
		});

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes
				.filter({hasText: objectDefinition.label['en_US']})
				.getByText(objectFieldLabel)
		).toBeVisible();
	});

	test('can delete object field', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
		modelBuilderObjectDefinitionNodePage,
		modelBuilderRightSidebarPage,
	}) => {
		const [objectDefinition] = createdEntities.objectDefinitions;

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition.externalReferenceCode,
			{
				DBType: 'Integer',
				label: {
					en_US: 'intField',
				},

				listTypeDefinitionId: 0,
				localized: false,
				name: 'intField',
				objectFieldSettings: [],
				readOnly: 'false',
				readOnlyConditionExpression: '',
				required: false,
				state: false,
				system: false,
			}
		);

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.sidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await modelBuilderObjectDefinitionNodePage.clickShowAllFieldsButton(
			objectDefinition.label['en_US'],
			modelBuilderDiagramPage.objectDefinitionNodes
		);

		await modelBuilderDiagramPage.objectDefinitionNodes
			.filter({hasText: objectDefinition.name})
			.getByText('integer', {exact: true})
			.click();

		await modelBuilderRightSidebarPage.deleteTrashButton.click();

		await modelBuilderObjectDefinitionNodePage.modalDeleteObjectDefinitionConfirmationButton.click();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes
				.filter({hasText: objectDefinition.name})
				.getByText('intField')
		).toBeHidden();
	});

	test('can edit picklist object field from draft object definition', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
		modelBuilderObjectDefinitionNodePage,
		page,
	}) => {
		const {listTypeDefinitionIds} = createdEntities;

		const draftObjectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 2},
			});

		createdEntities.objectDefinitions.push(draftObjectDefinition);

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		listTypeDefinitionIds.push(listTypeDefinition.id);

		let picklistFieldName = 'picklistField' + getRandomInt();

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			draftObjectDefinition.externalReferenceCode,
			{
				DBType: 'String',
				businessType: 'Picklist',
				externalReferenceCode: picklistFieldName,
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: picklistFieldName},
				listTypeDefinitionExternalReferenceCode:
					listTypeDefinition.externalReferenceCode,
				listTypeDefinitionId: listTypeDefinition.id,
				localized: false,
				name: picklistFieldName,
				readOnly: 'false',
				required: false,
				state: false,
				system: false,
			}
		);

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.clickSideBarItem(
			draftObjectDefinition.label['en_US']
		);

		await modelBuilderObjectDefinitionNodePage.clickShowAllFieldsButton(
			draftObjectDefinition.label['en_US'],
			modelBuilderDiagramPage.objectDefinitionNodes
		);

		await page.getByText(picklistFieldName).click();

		picklistFieldName = 'picklistField' + getRandomInt();

		await page
			.getByPlaceholder('Text to translate...')
			.fill(picklistFieldName);

		await modelBuilderLeftSidebarPage.clickSideBarItem(
			draftObjectDefinition.label['en_US']
		);

		await expect(page.getByText(picklistFieldName)).toBeVisible();
	});

	test('can show and hide object fields in the object definition node', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
		modelBuilderObjectDefinitionNodePage,
		page,
	}) => {
		const [objectDefinition] = createdEntities.objectDefinitions;

		const dateFieldName = 'dateField' + getRandomInt();
		const integerFieldName = 'integerField' + getRandomInt();

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition.externalReferenceCode,
			{
				DBType: 'Integer',
				businessType: 'Integer',
				externalReferenceCode: integerFieldName,
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: integerFieldName},
				listTypeDefinitionId: 0,
				localized: false,
				name: integerFieldName,
				readOnly: 'false',
				required: false,
				state: false,
				system: false,
			}
		);

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition.externalReferenceCode,
			{
				DBType: 'Date',
				businessType: 'Date',
				externalReferenceCode: dateFieldName,
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: dateFieldName},
				listTypeDefinitionId: 0,
				localized: false,
				name: dateFieldName,
				readOnly: 'false',
				required: false,
				state: false,
				system: false,
			}
		);

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.clickSideBarItem(
			objectDefinition.label['en_US']
		);

		await expect(page.getByText(integerFieldName)).not.toBeVisible();
		await expect(page.getByText(dateFieldName)).not.toBeVisible();

		await modelBuilderObjectDefinitionNodePage.clickShowAllFieldsButton(
			objectDefinition.label['en_US'],
			modelBuilderDiagramPage.objectDefinitionNodes
		);

		await expect(page.getByText(integerFieldName)).toBeVisible();
		await expect(page.getByText(dateFieldName)).toBeVisible();

		await modelBuilderObjectDefinitionNodePage.clickHideFieldsButton(
			objectDefinition.label['en_US'],
			modelBuilderDiagramPage.objectDefinitionNodes
		);

		await expect(page.getByText(integerFieldName)).not.toBeVisible();
		await expect(page.getByText(dateFieldName)).not.toBeVisible();
	});

	test('cannot delete an objectField that belongs to a unique composite key validation through Model Builder', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
		modelBuilderObjectDefinitionNodePage,
		modelBuilderRightSidebarPage,
		page,
	}) => {
		const [objectDefinition] = createdEntities.objectDefinitions;

		const integerFieldName = 'integerField' + getRandomInt();

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition.externalReferenceCode,
			{
				DBType: 'Integer',
				businessType: 'Integer',
				externalReferenceCode: integerFieldName,
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: integerFieldName},
				listTypeDefinitionId: 0,
				localized: false,
				name: integerFieldName,
				readOnly: 'false',
				required: false,
				state: false,
				system: false,
			}
		);

		const objectValidationName =
			'Unique Composite Key Object Validation' + getRandomInt();

		await apiHelpers.objectAdmin.postObjectValidation(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'compositeKey',
				engineLabel: 'Composite Key',
				errorLabel: {
					en_US: 'Unique composite key object validation error',
				},
				name: {
					en_US: objectValidationName,
				},
				objectValidationRuleSettings: [
					{
						name: 'compositeKeyObjectFieldExternalReferenceCode',
						value: 'textField',
					},
					{
						name: 'compositeKeyObjectFieldExternalReferenceCode',
						value: integerFieldName,
					},
				],
				outputType: 'fullValidation',
				script: '',
				system: false,
			}
		);

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.sidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await modelBuilderObjectDefinitionNodePage.clickShowAllFieldsButton(
			objectDefinition.name,
			modelBuilderDiagramPage.objectDefinitionNodes
		);

		await page.getByText(integerFieldName).click();

		await modelBuilderRightSidebarPage.deleteButton.click();

		await expect(page.getByText('Deletion Not Allowed')).toBeVisible();
		await expect(
			page.getByText(
				`The object field "${integerFieldName}" cannot be deleted because it is used in a unique composite key validation. To remove this object field, you must first delete the associated unique composite key validation.`
			)
		).toBeVisible();
	});

	test('cannot delete only custom object field of an published object definition', async ({
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
		modelBuilderObjectDefinitionNodePage,
		modelBuilderRightSidebarPage,
		page,
	}) => {
		const [objectDefinition] = createdEntities.objectDefinitions;

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.sidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await modelBuilderObjectDefinitionNodePage.clickShowAllFieldsButton(
			objectDefinition.name,
			modelBuilderDiagramPage.objectDefinitionNodes
		);

		await page.getByText('textField').click();

		await modelBuilderRightSidebarPage.deleteButton.click();

		await expect(page.getByText('Deletion Not Allowed')).toBeVisible();
		await expect(
			page.getByText(
				`The object field "textField" cannot be deleted because it is the only custom object field of the published object definition.`
			)
		).toBeVisible();
	});
});

test.describe('Manage objectFields through Objects Admin UI', () => {
	test('can create object fields of multiple types (except AutoIncrement, Date and Time, Encrypted and Aggregation)', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		objectFieldsPage,
		page,
	}) => {
		const {listTypeDefinitionIds, objectDefinitions} = createdEntities;

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [],
				objectFolderExternalReferenceCode: 'default',
				status: {code: 1},
			});

		objectDefinitions.push(objectDefinition);

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		listTypeDefinitionIds.push(listTypeDefinition.id);

		await objectFieldsPage.goto(objectDefinition.label['en_US']);

		const objectFieldsMock = [
			{
				objectFieldBusinessType: 'Attachment',
				objectFieldLabel: `attachment${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Boolean',
				objectFieldLabel: `boolean${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Date',
				objectFieldLabel: `date${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Decimal',
				objectFieldLabel: `decimal${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Integer',
				objectFieldLabel: `integer${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Long Integer',
				objectFieldLabel: `longInteger${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Long Text',
				objectFieldLabel: `longText${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Multiselect Picklist',
				objectFieldLabel: `multiselectPicklist${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Picklist',
				objectFieldLabel: `picklist${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Precision Decimal',
				objectFieldLabel: `precisionDecimal${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Rich Text',
				objectFieldLabel: `richText${getRandomInt()}`,
			},
			{
				objectFieldBusinessType: 'Text',
				objectFieldLabel: `text${getRandomInt()}`,
			},
		] as {
			objectFieldBusinessType: string;
			objectFieldLabel: string;
		}[];

		for (const objectField of objectFieldsMock) {
			const {objectFieldBusinessType, objectFieldLabel} =
				objectField;

			if (objectFieldBusinessType === 'Attachment') {
				await objectFieldsPage.addObjectField({
					attachmentSource: 'Upload Directly from the User',
					objectDefinitionNodes:
						modelBuilderDiagramPage.objectDefinitionNodes,
					objectFieldBusinessType,
					objectFieldLabel,
				});

				continue;
			}

			if (
				objectFieldBusinessType === 'Picklist' ||
				objectFieldBusinessType === `Multiselect Picklist`
			) {
				await objectFieldsPage.addObjectField({
					listTypeDefinitionName: listTypeDefinition.name,
					objectDefinitionNodes:
						modelBuilderDiagramPage.objectDefinitionNodes,
					objectFieldBusinessType,
					objectFieldLabel,
				});

				continue;
			}

			await objectFieldsPage.addObjectField({
				objectDefinitionNodes:
					modelBuilderDiagramPage.objectDefinitionNodes,
				objectFieldBusinessType,
				objectFieldLabel,
			});
		}

		while (
			(await page.locator('.dnd-tbody > .dnd-tr').all()).length !==
			objectDefinition.objectFields.length + objectFieldsMock.length
		) {
			await page.waitForTimeout(1000);
		}

		const objectFieldTableRows = await page
			.locator('.dnd-tbody > .dnd-tr')
			.all();

		const asyncFilter = async (
			array: Locator[],
			predicate: (value: Locator) => Promise<boolean>
		) => {
			const results = await Promise.all(array.map(predicate));

			return array.filter((_, index) => results[index]);
		};

		const objectFieldTableCustomRows = await asyncFilter(
			objectFieldTableRows,
			async (objectFieldTableRow: Locator) => {
				return (await objectFieldTableRow.textContent()).includes(
					'Custom'
				);
			}
		);

		for (let i = 0; i < objectFieldsMock.length; i++) {
			const {objectFieldBusinessType, objectFieldLabel} =
				objectFieldsMock[i];

			await expect(
				objectFieldTableCustomRows[i].getByText(objectFieldLabel, {
					exact: true,
				})
			).toBeVisible();

			await expect(
				objectFieldTableCustomRows[i].getByText(
					objectFieldBusinessType,
					{exact: true}
				)
			).toBeVisible();
		}
	});

	test('cannot delete an objectField that belongs to a unique composite key validation through Objects Admin UI', async ({
		apiHelpers,
		objectFieldsPage,
		page,
	}) => {
		const [objectDefinition] = createdEntities.objectDefinitions;
		const integerFieldName = 'integerField' + getRandomInt();

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition.externalReferenceCode,
			{
				DBType: 'Integer',
				businessType: 'Integer',
				externalReferenceCode: integerFieldName,
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: integerFieldName},
				listTypeDefinitionId: 0,
				localized: false,
				name: integerFieldName,
				readOnly: 'false',
				required: false,
				state: false,
				system: false,
			}
		);

		const objectValidationName =
			'Unique Composite Key Object Validation' + getRandomInt();

		await apiHelpers.objectAdmin.postObjectValidation(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'compositeKey',
				engineLabel: 'Composite Key',
				errorLabel: {
					en_US: 'Unique composite key object validation error',
				},
				name: {
					en_US: objectValidationName,
				},
				objectValidationRuleSettings: [
					{
						name: 'compositeKeyObjectFieldExternalReferenceCode',
						value: 'textField',
					},
					{
						name: 'compositeKeyObjectFieldExternalReferenceCode',
						value: integerFieldName,
					},
				],
				outputType: 'fullValidation',
				script: '',
				system: false,
			}
		);

		await objectFieldsPage.goto(objectDefinition.label['en_US']);

		await objectFieldsPage.deleteObjectField(-1);

		await expect(page.getByText('Deletion Not Allowed')).toBeVisible();
		await expect(
			page.getByText(
				`The object field "${integerFieldName}" cannot be deleted because it is used in a unique composite key validation. To remove this object field, you must first delete the associated unique composite key validation.`
			)
		).toBeVisible();
	});
});
