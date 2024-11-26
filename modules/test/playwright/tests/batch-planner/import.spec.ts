/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionApi,
	ObjectField,
	ObjectRelationship,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';
import * as path from 'path';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {dataMigrationCenterPagesTest} from './fixtures/dataMigrationCenterPagesTest';
import {OBJECT_ENTRY_ENTITY_TYPE} from './utils/constants';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'COMMERCE-8087': true,
	}),
	loginTest(),
	dataMigrationCenterPagesTest,
	objectPagesTest
);

const companyObjectDefinition: ObjectDefinition = {
	active: true,
	externalReferenceCode: 'Test',
	label: {'en-US': 'Test'},
	name: 'Test',
	objectFields: [
		{
			DBType: ObjectField.DBTypeEnum.String,
			businessType: ObjectField.BusinessTypeEnum.Aggregation,
			externalReferenceCode: 'Test-AggregationField',
			indexed: false,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testAggregationField'},
			listTypeDefinitionId: 0,
			name: 'testAggregationField',
			objectFieldSettings: [
				{
					name: 'objectRelationshipName',
					value: 'testRelationship',
				} as any,
				{name: 'function', value: 'COUNT'} as any,
			],
			required: false,
			system: false,
			type: ObjectField.TypeEnum.String,
		},
		{
			DBType: ObjectField.DBTypeEnum.String,
			businessType: ObjectField.BusinessTypeEnum.AutoIncrement,
			externalReferenceCode: 'Test-AutoIncrementField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testAutoIncrementField'},
			listTypeDefinitionId: 0,
			name: 'testAutoIncrementField',
			objectFieldSettings: [
				{name: 'prefix', value: 'prefix-'} as any,
				{name: 'initialValue', value: '1'} as any,
				{name: 'suffix', value: '-suffix'} as any,
			],
			required: false,
			system: false,
			type: ObjectField.TypeEnum.String,
		},
		{
			DBType: ObjectField.DBTypeEnum.Boolean,
			businessType: ObjectField.BusinessTypeEnum.Boolean,
			externalReferenceCode: 'Test-BooleanField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testBooleanField'},
			listTypeDefinitionId: 0,
			name: 'testBooleanField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Boolean,
		},
		{
			DBType: ObjectField.DBTypeEnum.Date,
			businessType: ObjectField.BusinessTypeEnum.Date,
			externalReferenceCode: 'Test-DateField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testDateField'},
			listTypeDefinitionId: 0,
			name: 'testDateField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Date,
		},
		{
			DBType: ObjectField.DBTypeEnum.DateTime,
			businessType: ObjectField.BusinessTypeEnum.DateTime,
			externalReferenceCode: 'Test-DateTimeField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testDateTimeField'},
			listTypeDefinitionId: 0,
			name: 'testDateTimeField',
			objectFieldSettings: [
				{name: 'timeStorage', value: 'convertToUTC'} as any,
			],
			required: false,
			system: false,
			type: ObjectField.TypeEnum.DateTime,
		},
		{
			DBType: ObjectField.DBTypeEnum.Double,
			businessType: ObjectField.BusinessTypeEnum.Decimal,
			externalReferenceCode: 'Test-DecimalField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testDecimalFiel-d'},
			listTypeDefinitionId: 0,
			name: 'testDecimalField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Double,
		},
		{
			DBType: ObjectField.DBTypeEnum.String,
			businessType: ObjectField.BusinessTypeEnum.Formula,
			externalReferenceCode: 'Test-FormulaField',
			indexed: false,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testFormulaField'},
			listTypeDefinitionId: 0,
			name: 'testFormulaField',
			objectFieldSettings: [
				{name: 'output', value: 'Integer'} as any,
				{name: 'script', value: 'id / id'} as any,
			],
			required: false,
			system: false,
			type: ObjectField.TypeEnum.String,
		},
		{
			DBType: ObjectField.DBTypeEnum.Integer,
			businessType: ObjectField.BusinessTypeEnum.Integer,
			externalReferenceCode: 'Test-IntegerField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testIntegerField'},
			listTypeDefinitionId: 0,
			name: 'testIntegerField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Integer,
		},
		{
			DBType: ObjectField.DBTypeEnum.Long,
			businessType: ObjectField.BusinessTypeEnum.LongInteger,
			externalReferenceCode: 'Test-LongIntegerField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testLongInteger'},
			listTypeDefinitionId: 0,
			name: 'testLongInteger',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Long,
		},
		{
			DBType: ObjectField.DBTypeEnum.Clob,
			businessType: ObjectField.BusinessTypeEnum.LongText,
			externalReferenceCode: 'Test-LongTextField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testLongTextField'},
			listTypeDefinitionId: 0,
			name: 'testLongTextField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Clob,
		},
		{
			DBType: ObjectField.DBTypeEnum.BigDecimal,
			businessType: ObjectField.BusinessTypeEnum.PrecisionDecimal,
			externalReferenceCode: 'Test-PrecisionDecimalField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testPrecisionDecimalField'},
			listTypeDefinitionId: 0,
			name: 'testPrecisionDecimalField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.BigDecimal,
		},
		{
			DBType: ObjectField.DBTypeEnum.Clob,
			businessType: ObjectField.BusinessTypeEnum.RichText,
			externalReferenceCode: 'Test-RichTextField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testRichTextField'},
			listTypeDefinitionId: 0,
			name: 'testRichTextField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Clob,
		},
		{
			DBType: ObjectField.DBTypeEnum.String,
			businessType: ObjectField.BusinessTypeEnum.Text,
			externalReferenceCode: 'Test-TextField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testTextField'},
			listTypeDefinitionId: 0,
			name: 'testTextField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.String,
		},
	],
	objectRelationships: [
		{
			deletionType: ObjectRelationship.DeletionTypeEnum.Cascade,
			externalReferenceCode: 'test-Relationship',
			label: {
				en_US: 'Test Relationship',
			},
			name: 'testRelationship',
			objectDefinitionExternalReferenceCode1: 'Test',
			objectDefinitionExternalReferenceCode2: 'Test',
			objectDefinitionName2: 'Test',
			parameterObjectFieldId: 0,
			parameterObjectFieldName: '',
			reverse: false,
			system: false,
			type: ObjectRelationship.TypeEnum.OneToMany,
		},
	],
	panelCategoryKey: 'control_panel.users',
	pluralLabel: {'en-US': 'Tests'},
	portlet: true,
	scope: 'company',
	status: {code: 0},
};

const siteObjectDefinition: ObjectDefinition = {
	active: true,
	externalReferenceCode: 'Test',
	label: {'en-US': 'Test'},
	name: 'Test',
	objectFields: [
		{
			DBType: ObjectField.DBTypeEnum.String,
			businessType: ObjectField.BusinessTypeEnum.Aggregation,
			externalReferenceCode: 'Test-AggregationField',
			indexed: false,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testAggregationField'},
			listTypeDefinitionId: 0,
			name: 'testAggregationField',
			objectFieldSettings: [
				{
					name: 'objectRelationshipName',
					value: 'testRelationship',
				} as any,
				{name: 'function', value: 'COUNT'} as any,
			],
			required: false,
			system: false,
			type: ObjectField.TypeEnum.String,
		},
		{
			DBType: ObjectField.DBTypeEnum.String,
			businessType: ObjectField.BusinessTypeEnum.AutoIncrement,
			externalReferenceCode: 'Test-AutoIncrementField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testAutoIncrementField'},
			listTypeDefinitionId: 0,
			name: 'testAutoIncrementField',
			objectFieldSettings: [
				{name: 'prefix', value: 'prefix-'} as any,
				{name: 'initialValue', value: '1'} as any,
				{name: 'suffix', value: '-suffix'} as any,
			],
			required: false,
			system: false,
			type: ObjectField.TypeEnum.String,
		},
		{
			DBType: ObjectField.DBTypeEnum.Boolean,
			businessType: ObjectField.BusinessTypeEnum.Boolean,
			externalReferenceCode: 'Test-BooleanField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testBooleanField'},
			listTypeDefinitionId: 0,
			name: 'testBooleanField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Boolean,
		},
		{
			DBType: ObjectField.DBTypeEnum.Date,
			businessType: ObjectField.BusinessTypeEnum.Date,
			externalReferenceCode: 'Test-DateField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testDateField'},
			listTypeDefinitionId: 0,
			name: 'testDateField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Date,
		},
		{
			DBType: ObjectField.DBTypeEnum.DateTime,
			businessType: ObjectField.BusinessTypeEnum.DateTime,
			externalReferenceCode: 'Test-DateTimeField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testDateTimeField'},
			listTypeDefinitionId: 0,
			name: 'testDateTimeField',
			objectFieldSettings: [
				{name: 'timeStorage', value: 'convertToUTC'} as any,
			],
			required: false,
			system: false,
			type: ObjectField.TypeEnum.DateTime,
		},
		{
			DBType: ObjectField.DBTypeEnum.Double,
			businessType: ObjectField.BusinessTypeEnum.Decimal,
			externalReferenceCode: 'Test-DecimalField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testDecimalField'},
			listTypeDefinitionId: 0,
			name: 'testDecimalField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Double,
		},
		{
			DBType: ObjectField.DBTypeEnum.String,
			businessType: ObjectField.BusinessTypeEnum.Formula,
			externalReferenceCode: 'Test-FormulaField',
			indexed: false,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testFormulaField'},
			listTypeDefinitionId: 0,
			name: 'testFormulaField',
			objectFieldSettings: [
				{name: 'output', value: 'Integer'} as any,
				{name: 'script', value: 'id / id'} as any,
			],
			required: false,
			system: false,
			type: ObjectField.TypeEnum.String,
		},
		{
			DBType: ObjectField.DBTypeEnum.Integer,
			businessType: ObjectField.BusinessTypeEnum.Integer,
			externalReferenceCode: 'Test-IntegerField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testIntegerField'},
			listTypeDefinitionId: 0,
			name: 'testIntegerField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Integer,
		},
		{
			DBType: ObjectField.DBTypeEnum.Long,
			businessType: ObjectField.BusinessTypeEnum.LongInteger,
			externalReferenceCode: 'Test-LongIntegerField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testLongInteger'},
			listTypeDefinitionId: 0,
			name: 'testLongInteger',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Long,
		},
		{
			DBType: ObjectField.DBTypeEnum.Clob,
			businessType: ObjectField.BusinessTypeEnum.LongText,
			externalReferenceCode: 'Test-LongTextField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testLongTextField'},
			listTypeDefinitionId: 0,
			name: 'testLongTextField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Clob,
		},
		{
			DBType: ObjectField.DBTypeEnum.BigDecimal,
			businessType: ObjectField.BusinessTypeEnum.PrecisionDecimal,
			externalReferenceCode: 'Test-PrecisionDecimalField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testPrecisionDecimalField'},
			listTypeDefinitionId: 0,
			name: 'testPrecisionDecimalField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.BigDecimal,
		},
		{
			DBType: ObjectField.DBTypeEnum.Clob,
			businessType: ObjectField.BusinessTypeEnum.RichText,
			externalReferenceCode: 'Test-RichTextField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testRichTextField'},
			listTypeDefinitionId: 0,
			name: 'testRichTextField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.Clob,
		},
		{
			DBType: ObjectField.DBTypeEnum.String,
			businessType: ObjectField.BusinessTypeEnum.Text,
			externalReferenceCode: 'Test-TextField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testTextField'},
			listTypeDefinitionId: 0,
			name: 'testTextField',
			required: false,
			system: false,
			type: ObjectField.TypeEnum.String,
		},
	],
	objectRelationships: [
		{
			deletionType: ObjectRelationship.DeletionTypeEnum.Cascade,
			externalReferenceCode: 'test-Relationship',
			label: {
				en_US: 'Test Relationship',
			},
			name: 'testRelationship',
			objectDefinitionExternalReferenceCode1: 'Test',
			objectDefinitionExternalReferenceCode2: 'Test',
			objectDefinitionName2: 'Test',
			parameterObjectFieldId: 0,
			parameterObjectFieldName: '',
			reverse: false,
			system: false,
			type: ObjectRelationship.TypeEnum.OneToMany,
		},
	],
	panelCategoryKey: 'site_administration.design',
	pluralLabel: {'en-US': 'Tests'},
	portlet: true,
	scope: 'site',
	status: {code: 0},
};

test('can handle OnlyAddNewRecords and UpdateChangedRecordFields import strategies with duplicate ERCs', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			companyObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(__dirname, '/dependencies/object_entries.csv'),
		'INSERT',
		'PARTIAL_UPDATE'
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	await page.getByRole('button', {exact: true, name: 'Close'}).click();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(__dirname, '/dependencies/object_entry_same_erc.csv'),
		'INSERT',
		'PARTIAL_UPDATE'
	);

	await expect(
		page.getByText(
			'com.liferay.object.exception.DuplicateObjectEntryExternalReferenceCodeException'
		)
	).toBeVisible();
});

test('can import CSV file with an unexisting field', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			companyObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(
			__dirname,
			'/dependencies/non_existing_field_object_entries.csv'
		),
		'UPSERT',
		'UPDATE'
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	expect(
		(
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/tests'
			)
		).items
	).toEqual([
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: expect.any(Number),
			keywords: [],
			r_testRelationship_c_testERC: '',
			r_testRelationship_c_testId: 0,
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testAggregationField: '0',
			testAutoIncrementField: 'prefix-1-suffix',
			testBooleanField: false,
			testDateField: '2024-01-05T00:00:00.000Z',
			testDateTimeField: '2024-01-05T15:00:00.000Z',
			testDecimalField: 10.2,
			testFormulaField: 1,
			testIntegerField: 100,
			testLongInteger: 123456789,
			testLongTextField: 'This is a long text to test testLongTextField',
			testPrecisionDecimalField: 321.123,
			testRelationshipERC: '',
			testRichTextField: '',
			testRichTextFieldRawText: '',
			testTextField: 'Test',
		},
	]);
});

test('can import CSV file with custom columns order', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			siteObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();
	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(
			__dirname,
			'/dependencies/custom_column_order_object_entries.csv'
		),
		'UPSERT',
		'UPDATE'
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	expect(
		(
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntriesByScope(
				'c/tests',
				'Guest'
			)
		).items
	).toEqual([
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: expect.any(Number),
			keywords: [],
			r_testRelationship_c_testERC: '',
			r_testRelationship_c_testId: 0,
			scopeKey: 'Guest',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testAggregationField: '0',
			testAutoIncrementField: 'prefix-1-suffix',
			testBooleanField: true,
			testDateField: '2024-01-05T00:00:00.000Z',
			testDateTimeField: '2024-01-05T15:00:00.000Z',
			testDecimalField: 10.2,
			testFormulaField: 1,
			testIntegerField: 100,
			testLongInteger: 123456789,
			testLongTextField: 'This is a long text to test testLongTextField',
			testPrecisionDecimalField: 321.123,
			testRelationshipERC: '',
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField',
			testTextField: 'Test',
		},
	]);
});

test('can import CSV file with multiple site scoped object entries', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			siteObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(__dirname, '/dependencies/two_entries_object_entries.csv'),
		'UPSERT',
		'UPDATE'
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	expect(
		(
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntriesByScope(
				'c/tests',
				'Guest'
			)
		).items
	).toEqual([
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: expect.any(Number),
			keywords: [],
			r_testRelationship_c_testERC: '',
			r_testRelationship_c_testId: 0,
			scopeKey: 'Guest',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testAggregationField: '0',
			testAutoIncrementField: 'prefix-1-suffix',
			testBooleanField: true,
			testDateField: '2024-01-05T00:00:00.000Z',
			testDateTimeField: '2024-01-05T15:00:00.000Z',
			testDecimalField: 10.2,
			testFormulaField: 1,
			testIntegerField: 100,
			testLongInteger: 123456789,
			testLongTextField:
				'This is a long text to test testLongTextField. The first entry',
			testPrecisionDecimalField: 321.123,
			testRelationshipERC: '',
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField. The first entry.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField. The first entry.',
			testTextField: 'Test_FirstEntry',
		},
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08273',
			id: expect.any(Number),
			keywords: [],
			r_testRelationship_c_testERC: '',
			r_testRelationship_c_testId: 0,
			scopeKey: 'Guest',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testAggregationField: '0',
			testAutoIncrementField: 'prefix-2-suffix',
			testBooleanField: false,
			testDateField: '2024-01-06T00:00:00.000Z',
			testDateTimeField: '2024-01-06T15:00:00.000Z',
			testDecimalField: 11.2,
			testFormulaField: 1,
			testIntegerField: 101,
			testLongInteger: 123456790,
			testLongTextField:
				'This is a long text to test testLongTextField. The second entry',
			testPrecisionDecimalField: 123.321,
			testRelationshipERC: '',
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField. The second entry.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField. The second entry.',
			testTextField: 'Test_SecondEntry',
		},
	]);
});

test('can import CSV file with new and existing site scoped object entries', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			siteObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(__dirname, '/dependencies/object_entries.csv'),
		'UPSERT',
		'UPDATE'
	);

	await page.getByRole('button', {exact: true, name: 'Close'}).click();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(
			__dirname,
			'/dependencies/two_entries_existing_nonmodified_object_entries.csv'
		),
		'UPSERT',
		'UPDATE'
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	expect(
		(
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntriesByScope(
				'c/tests',
				'Guest'
			)
		).items
	).toEqual([
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: expect.any(Number),
			keywords: [],
			r_testRelationship_c_testERC: '',
			r_testRelationship_c_testId: 0,
			scopeKey: 'Guest',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testAggregationField: '0',
			testAutoIncrementField: 'prefix-1-suffix',
			testBooleanField: false,
			testDateField: '2024-01-05T00:00:00.000Z',
			testDateTimeField: '2024-01-05T15:00:00.000Z',
			testDecimalField: 10.2,
			testFormulaField: 1,
			testIntegerField: 100,
			testLongInteger: 123456789,
			testLongTextField:
				'This is a long text to test testLongTextField. The first entry',
			testPrecisionDecimalField: 321.123,
			testRelationshipERC: '',
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField.',
			testTextField: 'Test',
		},
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08273',
			id: expect.any(Number),
			keywords: [],
			r_testRelationship_c_testERC: '',
			r_testRelationship_c_testId: 0,
			scopeKey: 'Guest',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testAggregationField: '0',
			testAutoIncrementField: 'prefix-2-suffix',
			testBooleanField: true,
			testDateField: '2024-01-06T00:00:00.000Z',
			testDateTimeField: '2024-01-06T15:00:00.000Z',
			testDecimalField: 11.2,
			testFormulaField: 1,
			testIntegerField: 101,
			testLongInteger: 123456790,
			testLongTextField:
				'This is a long text to test testLongTextField. The second entry',
			testPrecisionDecimalField: 123.321,
			testRelationshipERC: '',
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField. New entry.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField. New entry.',
			testTextField: 'Test_SecondEntry',
		},
	]);
});

test('can import CSV file with new and modified existing company scoped object entries', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			companyObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(__dirname, '/dependencies/object_entries.csv'),
		'UPSERT',
		'UPDATE'
	);

	await page.getByRole('button', {exact: true, name: 'Close'}).click();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(
			__dirname,
			'/dependencies/two_entries_existing_modified_object_entries.csv'
		),
		'UPSERT',
		'UPDATE'
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	expect(
		(
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/tests'
			)
		).items
	).toEqual([
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: expect.any(Number),
			keywords: [],
			r_testRelationship_c_testERC: '',
			r_testRelationship_c_testId: 0,
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testAggregationField: '0',
			testAutoIncrementField: 'prefix-1-suffix',
			testBooleanField: true,
			testDateField: '2024-01-05T00:00:00.000Z',
			testDateTimeField: '2024-01-05T15:00:00.000Z',
			testDecimalField: 10.2,
			testFormulaField: 1,
			testIntegerField: 100,
			testLongInteger: 123456789,
			testLongTextField:
				'This is a long text to test testLongTextField. The first entry',
			testPrecisionDecimalField: 321.123,
			testRelationshipERC: '',
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField. The modified entry.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField. The modified entry.',
			testTextField: 'Test_Modified',
		},
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08273',
			id: expect.any(Number),
			keywords: [],
			r_testRelationship_c_testERC: '',
			r_testRelationship_c_testId: 0,
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testAggregationField: '0',
			testAutoIncrementField: 'prefix-2-suffix',
			testBooleanField: false,
			testDateField: '2024-01-06T00:00:00.000Z',
			testDateTimeField: '2024-01-06T15:00:00.000Z',
			testDecimalField: 11.2,
			testFormulaField: 1,
			testIntegerField: 101,
			testLongInteger: 123456790,
			testLongTextField:
				'This is a long text to test testLongTextField. The second entry',
			testPrecisionDecimalField: 123.321,
			testRelationshipERC: '',
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField. The new entry.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField. The new entry.',
			testTextField: 'Test_NewEntry',
		},
	]);
});

test('can map all imported fields', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);
	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			siteObjectDefinition
		);
	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.selectEntityType(OBJECT_ENTRY_ENTITY_TYPE);

	await expect(page.getByText('externalReferenceCode')).toBeVisible();
	await expect(page.getByText('keywords', {exact: true})).toBeVisible();
	await expect(page.getByText('taxonomyCategoryIds')).toBeVisible();
	await expect(page.getByText('testAutoIncrementField')).toBeVisible();
	await expect(page.getByText('testBooleanField')).toBeVisible();
	await expect(page.getByText('testDateField')).toBeVisible();
	await expect(page.getByText('testDecimalField')).toBeVisible();
	await expect(page.getByText('testIntegerField')).toBeVisible();
	await expect(page.getByText('testLongInteger')).toBeVisible();
	await expect(page.getByText('testLongTextField')).toBeVisible();
	await expect(page.getByText('testPrecisionDecimalField')).toBeVisible();
	await expect(page.getByText('testRichTextField')).toBeVisible();
	await expect(page.getByText('testTextField')).toBeVisible();
});

test('can preview CSV file', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			siteObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.selectFile(
		path.join(__dirname, '/dependencies/object_entries.csv')
	);

	await dataMigrationCenterPage.selectEntityType(OBJECT_ENTRY_ENTITY_TYPE);

	await page.waitForTimeout(2000);

	await page.getByRole('button', {name: 'Next'}).click();

	await expect(
		page
			.getByLabel('Preview')
			.getByRole('cell', {name: 'externalReferenceCode'})
	).toBeVisible();
	await expect(
		page
			.getByLabel('Preview')
			.getByRole('cell', {exact: true, name: 'testAutoIncrementField'})
	).toBeVisible();
	await expect(
		page
			.getByLabel('Preview')
			.getByRole('cell', {exact: true, name: 'testBooleanField'})
	).toBeVisible();
	await expect(
		page.getByLabel('Preview').getByRole('cell', {name: 'testDateField'})
	).toBeVisible();
	await expect(
		page
			.getByLabel('Preview')
			.getByRole('cell', {name: 'testDateTimeField'})
	).toBeVisible();
	await expect(
		page.getByLabel('Preview').getByRole('cell', {name: 'testDecimalField'})
	).toBeVisible();
	await expect(
		page.getByLabel('Preview').getByRole('cell', {name: 'testIntegerField'})
	).toBeVisible();
	await expect(
		page.getByLabel('Preview').getByRole('cell', {name: 'testLongInteger'})
	).toBeVisible();
	await expect(
		page
			.getByLabel('Preview')
			.getByRole('cell', {exact: true, name: 'testLongTextField'})
	).toBeVisible();
	await expect(
		page
			.getByLabel('Preview')
			.getByRole('cell', {name: 'testPrecisionDecimalField'})
	).toBeVisible();
	await expect(
		page
			.getByLabel('Preview')
			.getByRole('cell', {exact: true, name: 'testRichTextField'})
	).toBeVisible();
	await expect(
		page
			.getByLabel('Preview')
			.getByRole('cell', {exact: true, name: 'testTextField'})
	).toBeVisible();
});

test('can show duplicate error message with CSV import existing entry and only add new record fields', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			companyObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(__dirname, '/dependencies/object_entries.csv'),
		'UPSERT',
		'UPDATE'
	);

	await page.getByRole('button', {exact: true, name: 'Close'}).click();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(__dirname, '/dependencies/object_entry_same_erc.csv'),
		'INSERT',
		'UPDATE'
	);

	await expect(
		page.getByText(
			'com.liferay.object.exception.DuplicateObjectEntryExternalReferenceCodeException'
		)
	).toBeVisible();
});

test('can show unique contraint error message with CSV import existing entry and only add new record fields', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			companyObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(__dirname, '/dependencies/object_entries.csv'),
		'UPSERT',
		'UPDATE'
	);

	await page.getByRole('button', {exact: true, name: 'Close'}).click();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(__dirname, '/dependencies/object_entries.csv'),
		'INSERT',
		'UPDATE'
	);

	await expect(
		page.getByText(
			'com.liferay.object.exception.ObjectEntryValuesException$UniqueValueConstraintViolation'
		)
	).toBeVisible();
});

test('cannot import CSV file without headers row', async ({
	dataMigrationCenterPage,
	page,
}) => {
	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.selectFile(
		path.join(__dirname, '/dependencies/no_headers_object_entries.csv')
	);

	await page.getByRole('button', {name: 'Next'}).click();

	await expect(page.getByText('Unexpected Error')).toBeVisible();
	await expect(
		page.getByText(
			'Error:Please upload a file and select the required columns before continuing.'
		)
	).toBeVisible();
});

test('cannot import CSV file with empty headers row', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			siteObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.selectFile(
		path.join(
			__dirname,
			'/dependencies/empty_header_values_object_entries.csv'
		)
	);

	await dataMigrationCenterPage.selectEntityType(OBJECT_ENTRY_ENTITY_TYPE);

	await page.waitForTimeout(2000);

	await page.getByRole('button', {name: 'Next'}).click();

	await expect(
		page.getByText(
			'Error:You must map at least one field and all required fields before continuing.'
		)
	).toBeVisible();
});

test('cannot import CSV file with object entry with UPSERT strategy', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			companyObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.importFile(
		OBJECT_ENTRY_ENTITY_TYPE,
		path.join(__dirname, '/dependencies/object_entries.csv'),
		'UPSERT',
		'PARTIAL_UPDATE'
	);

	await expect(
		page.getByText(
			'javax.ws.rs.NotSupportedException: Create strategy "UPSERT" is not supported for'
		)
	).toBeVisible();
});

test('cannot import empty CSV file', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			companyObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.selectFile(
		path.join(__dirname, '/dependencies/empty_object_entries.csv')
	);

	await dataMigrationCenterPage.selectEntityType(OBJECT_ENTRY_ENTITY_TYPE);

	await page.waitForTimeout(2000);

	await page.getByRole('button', {name: 'Next'}).click();

	await expect(page.getByText('Error:Please upload a file.')).toBeVisible();
});

test('can see correct custom object name in dropdown', async ({
	apiHelpers,
	dataMigrationCenterPage,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'stockERC',
			label: {
				en_US: 'stock',
			},
			name: 'Stock',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					externalReferenceCode: 'nameERC',
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'name',
					},
					name: 'name',
					required: true,
				},
			],
			pluralLabel: {
				en_US: 'stocks',
			},
			portlet: true,
			scope: 'company',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await apiHelpers.objectEntry.postObjectEntry(
		{
			externalReferenceCode: 'nameERC',
			name: 'Stock Entry',
		},
		'c/stocks'
	);

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	expect(
		await dataMigrationCenterPage.page
			.getByLabel('Entity Type')
			.textContent()
	).toContain('Stock (v1.0 - Liferay Object REST)');
});

test('can see ObjectDefinition entity type in dropdown', async ({
	dataMigrationCenterPage,
}) => {
	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	expect(
		await dataMigrationCenterPage.page
			.getByLabel('Entity Type')
			.textContent()
	).toContain('ObjectDefinition (v1.0 - Liferay Object Admin REST)');
});

test('cannot see relationship nested field', async ({
	apiHelpers,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			companyObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.selectEntityType(OBJECT_ENTRY_ENTITY_TYPE);

	await expect(page.getByText('testRelationship')).not.toBeVisible();
});

test.describe('can rely on anyOf form validation', () => {
	const studentObjectDefinition: ObjectDefinition = {
		active: true,
		externalReferenceCode: 'student-definition',
		label: {
			en_US: 'Student',
		},
		name: 'Student',
		objectFields: [
			{
				DBType: ObjectField.DBTypeEnum.String,
				businessType: ObjectField.BusinessTypeEnum.Text,
				externalReferenceCode: 'student-name-field',
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: 'en_US',
				label: {
					en_US: 'Student name',
				},
				listTypeDefinitionId: 0,
				name: 'studentName',
				required: true,
				state: false,
				system: false,
				type: ObjectField.TypeEnum.String,
			},
		],
		objectRelationships: [
			{
				deletionType: ObjectRelationship.DeletionTypeEnum.Cascade,
				externalReferenceCode: 'student-subjects-relationship-1',
				label: {
					en_US: 'Student subjects 1',
				},
				name: 'studentSubjects1',
				objectDefinitionExternalReferenceCode1: 'student-definition',
				objectDefinitionExternalReferenceCode2: 'subject-definition',
				objectDefinitionModifiable2: true,
				objectDefinitionName2: 'Subject',
				objectDefinitionSystem2: false,
				objectField: {
					DBType: ObjectField.DBTypeEnum.Long,
					businessType: ObjectField.BusinessTypeEnum.Relationship,
					externalReferenceCode:
						'student-subjects-relationship-field-1',
					indexed: true,
					indexedAsKeyword: false,
					indexedLanguageId: '',
					label: {
						en_US: 'Student subjects 1',
					},
					name: 'r_studentSubjects1_c_studentId',
					readOnly: ObjectField.ReadOnlyEnum.False,
					relationshipType:
						ObjectField.RelationshipTypeEnum.OneToMany,
					required: true,
					state: false,
					system: false,
					type: ObjectField.TypeEnum.Long,
					unique: false,
				},
				parameterObjectFieldId: 0,
				parameterObjectFieldName: '',
				reverse: false,
				system: false,
				type: ObjectRelationship.TypeEnum.OneToMany,
			},
			{
				deletionType: ObjectRelationship.DeletionTypeEnum.Cascade,
				externalReferenceCode: 'student-subjects-relationship-2',
				label: {
					en_US: 'Student subjects 2',
				},
				name: 'studentSubjects2',
				objectDefinitionExternalReferenceCode1: 'student-definition',
				objectDefinitionExternalReferenceCode2: 'subject-definition',
				objectDefinitionModifiable2: true,
				objectDefinitionName2: 'Subject',
				objectDefinitionSystem2: false,
				objectField: {
					DBType: ObjectField.DBTypeEnum.Long,
					businessType: ObjectField.BusinessTypeEnum.Relationship,
					externalReferenceCode:
						'student-subjects-relationship-field-2',
					indexed: true,
					indexedAsKeyword: false,
					indexedLanguageId: '',
					label: {
						en_US: 'Student subjects 2',
					},
					name: 'r_studentSubjects2_c_studentId',
					readOnly: ObjectField.ReadOnlyEnum.False,
					relationshipType:
						ObjectField.RelationshipTypeEnum.OneToMany,
					required: true,
					state: false,
					system: false,
					type: ObjectField.TypeEnum.Long,
					unique: false,
				},
				parameterObjectFieldId: 0,
				parameterObjectFieldName: '',
				reverse: false,
				system: false,
				type: ObjectRelationship.TypeEnum.OneToMany,
			},
			{
				deletionType: ObjectRelationship.DeletionTypeEnum.Cascade,
				externalReferenceCode: 'student-subjects-relationship-3',
				label: {
					en_US: 'Student subjects 3',
				},
				name: 'studentSubjects3',
				objectDefinitionExternalReferenceCode1: 'student-definition',
				objectDefinitionExternalReferenceCode2: 'subject-definition',
				objectDefinitionModifiable2: true,
				objectDefinitionName2: 'Subject',
				objectDefinitionSystem2: false,
				objectField: {
					DBType: ObjectField.DBTypeEnum.Long,
					businessType: ObjectField.BusinessTypeEnum.Relationship,
					externalReferenceCode:
						'student-subjects-relationship-field-3',
					indexed: true,
					indexedAsKeyword: false,
					indexedLanguageId: '',
					label: {
						en_US: 'Student subjects 3',
					},
					name: 'r_studentSubjects3_c_studentId',
					readOnly: ObjectField.ReadOnlyEnum.False,
					relationshipType:
						ObjectField.RelationshipTypeEnum.OneToMany,
					required: false,
					state: false,
					system: false,
					type: ObjectField.TypeEnum.Long,
					unique: false,
				},
				parameterObjectFieldId: 0,
				parameterObjectFieldName: '',
				reverse: false,
				system: false,
				type: ObjectRelationship.TypeEnum.OneToMany,
			},
		],
		panelCategoryKey: 'control_panel.object',
		pluralLabel: {
			en_US: 'Students',
		},
		portlet: true,
		restContextPath: '/o/c/students',
		scope: 'company',
		status: {
			code: 0,
		},
	};

	const subjectObjectDefinition: ObjectDefinition = {
		active: true,
		externalReferenceCode: 'subject-definition',
		label: {
			en_US: 'Subject',
		},
		name: 'Subject',
		objectFields: [
			{
				DBType: ObjectField.DBTypeEnum.String,
				businessType: ObjectField.BusinessTypeEnum.Text,
				externalReferenceCode: 'subject-name-field',
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: 'en_US',
				label: {
					en_US: 'Subject name',
				},
				listTypeDefinitionId: 0,
				name: 'subjectName',
				required: false,
				state: false,
				system: false,
				type: ObjectField.TypeEnum.String,
			},
		],
		panelCategoryKey: 'control_panel.object',
		pluralLabel: {
			en_US: 'Subjects',
		},
		portlet: true,
		restContextPath: '/o/c/subjects',
		scope: 'company',
		status: {
			code: 0,
		},
	};

	test('cannot preview fields with no required anyOf fields selected', async ({
		apiHelpers,
		dataMigrationCenterPage,
		page,
	}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {body: subjectResponse} =
			await objectDefinitionAPIClient.postObjectDefinition(
				subjectObjectDefinition
			);

		apiHelpers.data.push({
			id: subjectResponse.id,
			type: 'objectDefinition',
		});

		const {body: studentResponse} =
			await objectDefinitionAPIClient.postObjectDefinition(
				studentObjectDefinition
			);

		apiHelpers.data.push({
			id: studentResponse.id,
			type: 'objectDefinition',
		});

		await dataMigrationCenterPage.goto();
		await dataMigrationCenterPage.goToImportFile();

		await dataMigrationCenterPage.selectEntityType(
			'com.liferay.object.rest.dto.v1_0.ObjectEntry#C_Subject'
		);

		await expect(
			page.getByLabel('r_studentSubjects1_c_studentERC', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects1_c_studentId', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects2_c_studentERC', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects2_c_studentId', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects3_c_studentERC', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects3_c_studentId', {exact: true})
		).toBeEmpty();

		await dataMigrationCenterPage.selectFile(
			path.join(__dirname, '/dependencies/any_of_object_entries.csv')
		);
		await page.getByRole('button', {name: 'Next'}).click();
		await expect(
			page.getByText(
				'Error:You must map at least one field and all required fields before continuing.'
			)
		).toBeVisible();
	});

	test('cannot preview fields with one required anyOf field missing', async ({
		apiHelpers,
		dataMigrationCenterPage,
		page,
	}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {body: subjectResponse} =
			await objectDefinitionAPIClient.postObjectDefinition(
				subjectObjectDefinition
			);

		apiHelpers.data.push({
			id: subjectResponse.id,
			type: 'objectDefinition',
		});

		const {body: studentResponse} =
			await objectDefinitionAPIClient.postObjectDefinition(
				studentObjectDefinition
			);

		apiHelpers.data.push({
			id: studentResponse.id,
			type: 'objectDefinition',
		});

		await dataMigrationCenterPage.goto();
		await dataMigrationCenterPage.goToImportFile();

		await dataMigrationCenterPage.selectEntityType(
			'com.liferay.object.rest.dto.v1_0.ObjectEntry#C_Subject'
		);

		await expect(
			page.getByLabel('r_studentSubjects1_c_studentERC', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects1_c_studentId', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects2_c_studentERC', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects2_c_studentId', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects3_c_studentERC', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects3_c_studentId', {exact: true})
		).toBeEmpty();

		await dataMigrationCenterPage.selectFile(
			path.join(__dirname, '/dependencies/any_of_object_entries.csv')
		);

		await page
			.getByLabel('r_studentSubjects1_c_studentERC')
			.selectOption('studentSubjects1_ERC');

		await page.getByRole('button', {name: 'Next'}).click();

		await expect(
			page
				.getByText(
					'Error:You must map at least one field and all required fields before continuing.'
				)
				.first()
		).toBeVisible();
	});

	test('can preview import with all required anyOf fields selected', async ({
		apiHelpers,
		dataMigrationCenterPage,
		page,
	}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {body: subjectResponse} =
			await objectDefinitionAPIClient.postObjectDefinition(
				subjectObjectDefinition
			);

		apiHelpers.data.push({
			id: subjectResponse.id,
			type: 'objectDefinition',
		});

		const {body: studentResponse} =
			await objectDefinitionAPIClient.postObjectDefinition(
				studentObjectDefinition
			);

		apiHelpers.data.push({
			id: studentResponse.id,
			type: 'objectDefinition',
		});

		await dataMigrationCenterPage.goto();
		await dataMigrationCenterPage.goToImportFile();

		await dataMigrationCenterPage.selectEntityType(
			'com.liferay.object.rest.dto.v1_0.ObjectEntry#C_Subject'
		);

		await expect(
			page.getByLabel('r_studentSubjects1_c_studentERC', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects1_c_studentId', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects2_c_studentERC', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects2_c_studentId', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects3_c_studentERC', {exact: true})
		).toBeEmpty();
		await expect(
			page.getByLabel('r_studentSubjects3_c_studentId', {exact: true})
		).toBeEmpty();

		await dataMigrationCenterPage.selectFile(
			path.join(__dirname, '/dependencies/any_of_object_entries.csv')
		);

		await page
			.getByLabel('r_studentSubjects1_c_studentERC')
			.selectOption('studentSubjects1_ERC');
		await page
			.getByLabel('r_studentSubjects2_c_studentERC')
			.selectOption('studentSubjects2_ERC');

		await page.getByRole('button', {name: 'Next'}).click();

		await expect(
			page
				.getByLabel('Preview')
				.getByRole('cell', {name: 'r_studentSubjects1_c_studentERC'})
		).toBeVisible();
		await expect(
			page
				.getByLabel('Preview')
				.getByRole('cell', {name: 'r_studentSubjects2_c_studentERC'})
		).toBeVisible();
	});
});
