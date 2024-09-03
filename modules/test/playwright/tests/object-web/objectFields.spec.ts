/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(apiHelpersTest, loginTest(), objectPagesTest);

const createdEntities = {
	listTypeDefinitionIds: [],
	objectDefinition: {},
} as {
	listTypeDefinitionIds: number[];
	objectDefinition: ObjectDefinition;
};

test.beforeEach(async ({apiHelpers}) => {
	const newObjectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectFolderExternalReferenceCode: 'default',
			status: {code: 0},
		});

	createdEntities.objectDefinition = newObjectDefinition;
});

test.afterEach(async ({apiHelpers}) => {
	await apiHelpers.objectAdmin.deleteObjectDefinition(
		createdEntities.objectDefinition.id
	);

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
		modelBuilderPage,
	}) => {
		const {listTypeDefinitionIds, objectDefinition} = createdEntities;

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

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderPage.openAddNewObjectFieldModal(objectDefinition.name);

		await modelBuilderPage.fillNewObjectFieldLabel(
			'objectFieldLabel' + getRandomInt()
		);

		await modelBuilderPage.selectNewObjectFieldBusinessTypeOption(
			'Picklist'
		);

		await modelBuilderPage.newObjectFieldSelectPicklist.click();

		const listTypeDefinitionBox =
			modelBuilderPage.page.getByRole('listbox');

		await expect(listTypeDefinitionBox).toBeVisible();

		await expect(listTypeDefinitionBox.getByRole('option')).toHaveCount(
			allListTypeDefinitions.length
		);
	});

	test('can add picklist object field to object definition node', async ({
		apiHelpers,
		modelBuilderPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const {listTypeDefinitionIds, objectDefinition} = createdEntities;

		await page.goto('/');

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		listTypeDefinitionIds.push(listTypeDefinition.id);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder('default');

		await viewObjectDefinitionsPage.viewInModelBuilderButton.click();

		const objectFieldLabel = 'objectFieldLabel' + getRandomInt();

		await modelBuilderPage.createObjectField({
			listTypeDefinitionName: listTypeDefinition.name,
			mandatory: false,
			objectDefinitionName: objectDefinition.name,
			objectFieldBusinessType: 'Picklist',
			objectFieldLabel,
		});

		await expect(
			modelBuilderPage.objectDefinitionNodes
				.filter({hasText: objectDefinition.label['en_US']})
				.getByText(objectFieldLabel)
		).toBeVisible();
	});

	test('can delete object field', async ({
		apiHelpers,
		modelBuilderLeftSidebarPage,
		modelBuilderPage,
		modelBuilderRightSidebarPage,
	}) => {
		const {objectDefinition} = createdEntities;

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

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.sidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await modelBuilderPage.clickShowAllFieldsButton(objectDefinition.name);

		await modelBuilderPage.objectDefinitionNodes
			.filter({hasText: objectDefinition.name})
			.getByText('integer', {exact: true})
			.click();

		await modelBuilderRightSidebarPage.deleteTrashButton.click();

		await modelBuilderPage.modalDeleteObjectDefinitionConfirmationButton.click();

		await expect(
			modelBuilderPage.objectDefinitionNodes
				.filter({hasText: objectDefinition.name})
				.getByText('intField')
		).toBeHidden();
	});

	test('can edit picklist object field from draft object definition', async ({
		apiHelpers,
		modelBuilderLeftSidebarPage,
		modelBuilderPage,
		page,
	}) => {
		const {listTypeDefinitionIds} = createdEntities;

		const draftObjectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 2},
			});

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

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.clickSideBarItem(
			draftObjectDefinition.label['en_US']
		);

		await modelBuilderPage.clickShowAllFieldsButton(
			draftObjectDefinition.label['en_US']
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

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			draftObjectDefinition.id
		);
	});

	test('can show and hide object fields in the object definition node', async ({
		apiHelpers,
		modelBuilderLeftSidebarPage,
		modelBuilderPage,
		page,
	}) => {
		const {objectDefinition} = createdEntities;
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

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.clickSideBarItem(
			objectDefinition.label['en_US']
		);

		await expect(page.getByText(integerFieldName)).not.toBeVisible();
		await expect(page.getByText(dateFieldName)).not.toBeVisible();

		await modelBuilderPage.clickShowAllFieldsButton(
			objectDefinition.label['en_US']
		);

		await expect(page.getByText(integerFieldName)).toBeVisible();
		await expect(page.getByText(dateFieldName)).toBeVisible();

		await modelBuilderPage.clickHideFieldsButton(
			objectDefinition.label['en_US']
		);

		await expect(page.getByText(integerFieldName)).not.toBeVisible();
		await expect(page.getByText(dateFieldName)).not.toBeVisible();
	});

	test('cannot delete an objectField that belongs to a unique composite key validation through Model Builder', async ({
		apiHelpers,
		modelBuilderLeftSidebarPage,
		modelBuilderPage,
		modelBuilderRightSidebarPage,
		page,
	}) => {
		const {objectDefinition} = createdEntities;

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

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.sidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await modelBuilderPage.clickShowAllFieldsButton(objectDefinition.name);

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
		modelBuilderLeftSidebarPage,
		modelBuilderPage,
		modelBuilderRightSidebarPage,
		page,
	}) => {
		const {objectDefinition} = createdEntities;

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.sidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await modelBuilderPage.clickShowAllFieldsButton(objectDefinition.name);

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
		objectFieldsPage,
		page,
	}) => {
		const {listTypeDefinitionIds, objectDefinition} = createdEntities;

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		listTypeDefinitionIds.push(listTypeDefinition.id);

		await objectFieldsPage.goto(objectDefinition.label['en_US']);

		const objectFieldsMock = [
			{
				objectFieldBusinessType: 'Attachment',
				objectFieldLabel: 'Custom Attachment',
			},
			{
				objectFieldBusinessType: 'Boolean',
				objectFieldLabel: 'Custom Boolean',
			},
			{
				objectFieldBusinessType: 'Date',
				objectFieldLabel: 'Custom Date',
			},
			{
				objectFieldBusinessType: 'Decimal',
				objectFieldLabel: 'Custom Decimal',
			},
			{
				objectFieldBusinessType: 'Integer',
				objectFieldLabel: 'Custom Integer',
			},
			{
				objectFieldBusinessType: 'Long Integer',
				objectFieldLabel: 'Custom Long Integer',
			},
			{
				objectFieldBusinessType: 'Long Text',
				objectFieldLabel: 'Custom Long Text',
			},
			{
				objectFieldBusinessType: 'Multiselect Picklist',
				objectFieldLabel: 'Custom Multiselect Picklist',
			},
			{
				objectFieldBusinessType: 'Picklist',
				objectFieldLabel: 'Custom Picklist',
			},

			{
				objectFieldBusinessType: 'Precision Decimal',
				objectFieldLabel: 'Custom Precision Decimal',
			},
			{
				objectFieldBusinessType: 'Rich Text',
				objectFieldLabel: 'Custom Rich Text',
			},
			{
				objectFieldBusinessType: 'Text',
				objectFieldLabel: 'Custom Text',
			},
		] as {
			objectFieldBusinessType: string;
			objectFieldLabel: string;
		}[];

		for (let i = 0; i < objectFieldsMock.length; i++) {
			const {objectFieldBusinessType, objectFieldLabel} =
				objectFieldsMock[i];

			if (objectFieldBusinessType === 'Attachment') {
				await objectFieldsPage.addObjectField({
					attachmentSource: 'Upload Directly from the User',
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
					objectFieldBusinessType,
					objectFieldLabel,
				});

				continue;
			}

			await objectFieldsPage.addObjectField({
				objectFieldBusinessType,
				objectFieldLabel,
			});
		}

		for (let i = 0; i < objectFieldsMock.length; i++) {
			const {objectFieldLabel} = objectFieldsMock[i];

			await expect(page.getByText(objectFieldLabel)).toBeVisible();
		}
	});

	test('cannot delete an objectField that belongs to a unique composite key validation through Objects Admin UI', async ({
		apiHelpers,
		objectFieldsPage,
		page,
	}) => {
		const {objectDefinition} = createdEntities;
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
