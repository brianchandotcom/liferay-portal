/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-187854': true,
	}),
	loginTest(),
	objectPagesTest
);

interface CreatedEntities {
	listTypeDefinitionIds: number[];
	objectDefinition: ObjectDefinition;
}

const createdEntities = {
	listTypeDefinitionIds: [],
	objectDefinition: {},
} as CreatedEntities;

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

		await viewObjectDefinitionsPage.viewInModelBuilder();

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

	test('all picklist definitions are listed during object field creation', async ({
		apiHelpers,
		modelBuilderPage,
	}) => {
		const {listTypeDefinitionIds, objectDefinition} = createdEntities;

		const listTypeDefinitions = await Promise.all(
			Array(22)
				.fill(null)
				.map(() =>
					apiHelpers.listTypeAdmin.postRandomListTypeDefinition()
				)
		);

		listTypeDefinitions.forEach(({id}) => listTypeDefinitionIds.push(id));

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderPage.openNewFieldModal(objectDefinition.name);

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

		await expect(listTypeDefinitionBox.getByRole('listitem')).toHaveCount(
			22
		);
	});

	test('can show and hide object fields in the object definition node', async ({
		apiHelpers,
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

		await modelBuilderPage.clickLeftSideBarItem(
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
		modelBuilderPage,
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

		await modelBuilderPage.leftSidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await modelBuilderPage.clickShowAllFieldsButton(objectDefinition.name);

		await page.getByText(integerFieldName).click();

		await modelBuilderPage.deleteButton.click();

		await expect(page.getByText('Deletion Not Allowed')).toBeVisible();
		await expect(
			page.getByText(
				`The object field "${integerFieldName}" cannot be deleted because it is used in a unique composite key validation. To remove this object field, you must first delete the associated unique composite key validation.`
			)
		).toBeVisible();
	});

	test('can delete object field', async ({apiHelpers, modelBuilderPage}) => {
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

		await modelBuilderPage.leftSidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await modelBuilderPage.clickShowAllFieldsButton(objectDefinition.name);

		await modelBuilderPage.objectDefinitionNodes
			.filter({hasText: objectDefinition.name})
			.getByText('integer', {exact: true})
			.click();

		await modelBuilderPage.deleteTrashButton.click();

		await modelBuilderPage.modalDeleteObjectDefinitionConfirmationButton.click();

		await expect(
			modelBuilderPage.objectDefinitionNodes
				.filter({hasText: objectDefinition.name})
				.getByText('intField')
		).toBeHidden();
	});
});

test.describe('Manage objectFields through Objects Admin UI', () => {
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
