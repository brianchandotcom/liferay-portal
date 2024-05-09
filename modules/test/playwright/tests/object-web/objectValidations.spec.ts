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

let objectDefinition1: ObjectDefinition;
let objectDefinition2: ObjectDefinition;

test.beforeEach(async ({apiHelpers}) => {
	const newObjectDefinition1 =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectFolderExternalReferenceCode: 'default',
			status: {code: 0},
		});

	const newObjectDefinition2 =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectFolderExternalReferenceCode: 'default',
			status: {code: 0},
		});

	objectDefinition1 = newObjectDefinition1;
	objectDefinition2 = newObjectDefinition2;
});

test.afterEach(async ({apiHelpers}) => {
	await apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition1.id);
	await apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition2.id);
});

test.describe('Object Unique Composite Key Validation', () => {
	test('can create and use a object unique composite key validation', async ({
		apiHelpers,
		editObjectValidationPage,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
		viewObjectEntriesPage,
	}) => {
		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition1.externalReferenceCode,
			{
				DBType: 'Integer',
				businessType: 'Integer',
				externalReferenceCode: 'integerField',
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: 'integerField'},
				listTypeDefinitionId: 0,
				localized: false,
				name: 'integerField',
				readOnly: 'false',
				required: false,
				state: false,
				system: false,
			}
		);

		objectValidationsPage.goto(objectDefinition1.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

	const objectValidationLabel =
		'UniqueCompositeKeyValidation' + getRandomInt();

	await modalAddObjectValidationPage.fillObjectValidationInputs(
		objectValidationLabel,
		'Composite Key'
	);

	const newValidationLink = page.getByText(objectValidationLabel);

		await newValidationLink.click();

		await editObjectValidationPage.uniqueCompositeKeyTab.click();

		await editObjectValidationPage.addObjectFieldsButton.click();

		await editObjectValidationPage.clickSelectAllFields();

		await editObjectValidationPage.saveObjectValidationButton.click();

		const applicationName =
			'c/' + objectDefinition1.name.toLowerCase() + 's';

		const textObjectEntry = {
			textField: 'entry',
		};

		await apiHelpers.objectEntry.postObjectEntry(
			textObjectEntry,
			applicationName
		);

		await viewObjectEntriesPage.goto(objectDefinition1.id);

		await viewObjectEntriesPage.addObjectEntryButton.click();

		await viewObjectEntriesPage.fillObjectEntry('integerField', '0');
		await viewObjectEntriesPage.fillObjectEntry('textField', 'entry');

		await viewObjectEntriesPage.saveObjectEntryButton.click();
		await viewObjectEntriesPage.assertErrorWithDuplicateEntryValue();

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.addObjectEntryButton.click();

		await viewObjectEntriesPage.fillObjectEntry('integerField', '123');
		await viewObjectEntriesPage.fillObjectEntry('textField', 'entry 2');

		await viewObjectEntriesPage.saveObjectEntryButton.click();
		await expect(viewObjectEntriesPage.successMessage).toBeVisible();
	});

	test('check if only specific object field business types (AutoIncrement, Integer, Picklist, Relationship, Text) will be accepted in unique composite key validation', async ({
		apiHelpers,
		editObjectValidationPage,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
	}) => {
		const autoIncrementFieldName = 'autoIncrementField' + getRandomInt();
		const dateFieldName = 'dateField' + getRandomInt();
		const integerFieldName = 'integerField' + getRandomInt();
		const objectRelationshipLabel =
			'objectRelationshipLabel' + getRandomInt();
		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);
		const picklistFieldName = 'picklistField' + getRandomInt();

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition1.externalReferenceCode,
			{
				DBType: 'String',
				businessType: 'AutoIncrement',
				externalReferenceCode: autoIncrementFieldName,
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: autoIncrementFieldName},
				listTypeDefinitionId: 0,
				localized: false,
				name: autoIncrementFieldName,
				objectFieldSettings: [
					{
						name: 'initialValue',
						value: '1234',
					},
				],
				readOnly: 'false',
				required: false,
				state: false,
				system: false,
			}
		);

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition1.externalReferenceCode,
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

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition1.externalReferenceCode,
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

		const objectRelationshipData: Partial<ObjectRelationship> = {
			label: {
				en_US: objectRelationshipLabel,
			},
			name: objectRelationshipName,
			objectDefinitionExternalReferenceCode1:
				objectDefinition2.externalReferenceCode,
			objectDefinitionExternalReferenceCode2:
				objectDefinition1.externalReferenceCode,
			objectDefinitionId1: objectDefinition2.id,
			objectDefinitionId2: objectDefinition1.id,
			objectDefinitionName2: objectDefinition1.name,
			type: 'oneToMany' as ObjectRelationshipType,
		};

		await apiHelpers.objectAdmin.postObjectRelationship(
			objectRelationshipData
		);

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition1.externalReferenceCode,
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

		objectValidationsPage.goto(objectDefinition1.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

	const objectValidationLabel =
		'UniqueCompositeKeyValidation' + getRandomInt();

	await modalAddObjectValidationPage.fillObjectValidationInputs(
		objectValidationLabel,
		'Composite Key'
	);

	const newValidationLink = page.getByText(objectValidationLabel);

		await expect(newValidationLink).toBeVisible();

		await newValidationLink.click();

		await editObjectValidationPage.uniqueCompositeKeyTab.click();

		await editObjectValidationPage.addObjectFieldsButton.click();

		await expect(page.getByText(autoIncrementFieldName)).toBeVisible();
		await expect(page.getByText(dateFieldName)).not.toBeVisible();
		await expect(page.getByText(integerFieldName)).toBeVisible();
		await expect(page.getByText(objectRelationshipLabel)).toBeVisible();
		await expect(page.getByText(picklistFieldName)).toBeVisible();

	// Clean Up

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			listTypeDefinition.id
		);
	});

	test('cannot select a object field that already has an entry in a new composite key validation', async ({
		apiHelpers,
		editObjectValidationPage,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
	}) => {
		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			objectDefinition1.externalReferenceCode,
			{
				DBType: 'Integer',
				businessType: 'Integer',
				externalReferenceCode: 'integerField',
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: 'integerField'},
				listTypeDefinitionId: 0,
				localized: false,
				name: 'integerField',
				readOnly: 'false',
				required: false,
				state: false,
				system: false,
			}
		);

		const applicationName =
			'c/' + objectDefinition1.name.toLowerCase() + 's';

		const textObjectEntry = {
			textField: 'entry',
		};

		await apiHelpers.objectEntry.postObjectEntry(
			textObjectEntry,
			applicationName
		);

		objectValidationsPage.goto(objectDefinition1.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

	const objectValidationLabel =
		'UniqueCompositeKeyValidation' + getRandomInt();

	await modalAddObjectValidationPage.fillObjectValidationInputs(
		objectValidationLabel,
		'Composite Key'
	);

	const newValidationLink = page.getByText(objectValidationLabel);

		await expect(newValidationLink).toBeVisible();

		await newValidationLink.click();

		await editObjectValidationPage.uniqueCompositeKeyTab.click();

		await editObjectValidationPage.addObjectFieldsButton.click();

		await editObjectValidationPage.clickSelectAllFields();

	await editObjectValidationPage.saveObjectValidationButton.click();

		await expect(
			editObjectValidationPage.getObjectFieldAlreadyHasEntryErrorLocator(
				'textField, integerField'
			)
		).toBeVisible();
	});

	test('cannot add unique composite key validation with just one field', async ({
		editObjectValidationPage,
		modalAddObjectValidationPage,
		objectValidationsPage,
		page,
	}) => {
		objectValidationsPage.goto(objectDefinition1.label['en_US']);

		await objectValidationsPage.addObjectValidationButton.click();

		const validationLabel = 'UniqueCompositeKeyValidation' + getRandomInt();

		await modalAddObjectValidationPage.fillObjectValidationInputs(
			validationLabel,
			'Composite Key'
		);

		const newValidationLink = page.getByText(validationLabel);

		await expect(newValidationLink).toBeVisible();

		await newValidationLink.click();

		await editObjectValidationPage.uniqueCompositeKeyTab.click();

		await editObjectValidationPage.addObjectFieldsButton.click();

		await editObjectValidationPage.clickSelectAllFields();

	await editObjectValidationPage.saveObjectValidationButton.click();

	await expect(
		editObjectValidationPage.addTwoObjectFieldsErrorMessage
	).toBeVisible();
});
