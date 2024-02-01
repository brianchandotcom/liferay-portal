/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectAdminRestClient,
	ObjectDefinition,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';
import * as path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataMigrationCenterPagesTest} from '../../fixtures/dataMigrationCenterPages';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {OBJECT_ENTRY_ENTITY_TYPE} from './utils/constants';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'COMMERCE-8087': true,
	}),
	loginTest,
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
			DBType: 'Date',
			businessType: 'Date',
			externalReferenceCode: 'Test-date',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testDateField'},
			listTypeDefinitionId: 0,
			name: 'testDateField',

			required: false,
			system: false,
			type: 'Date',
		},
		{
			DBType: 'DateTime',
			businessType: 'DateTime',
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
			type: 'DateTime',
		},
		{
			DBType: 'Double',
			businessType: 'Decimal',
			externalReferenceCode: 'Test-DecimalField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testDecimalField'},
			listTypeDefinitionId: 0,
			name: 'testDecimalField',
			required: false,
			system: false,
			type: 'Double',
		},
		{
			DBType: 'Integer',
			businessType: 'Integer',
			externalReferenceCode: 'Test-IntegerField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testIntegerField'},
			listTypeDefinitionId: 0,
			name: 'testIntegerField',
			required: false,
			system: false,
			type: 'Integer',
		},
		{
			DBType: 'Long',
			businessType: 'LongInteger',
			externalReferenceCode: 'Test-LongInteger',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testLongInteger'},
			listTypeDefinitionId: 0,
			name: 'testLongInteger',
			required: false,
			system: false,
			type: 'Long',
		},
		{
			DBType: 'Clob',
			businessType: 'LongText',
			externalReferenceCode: 'Test-LongTextField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testLongTextField'},
			listTypeDefinitionId: 0,
			name: 'testLongTextField',
			required: false,
			system: false,
			type: 'Clob',
		},
		{
			DBType: 'BigDecimal',
			businessType: 'PrecisionDecimal',
			externalReferenceCode: 'Test-PrecisionDecimalField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testPrecisionDecimalField'},
			listTypeDefinitionId: 0,
			name: 'testPrecisionDecimalField',
			required: false,
			system: false,
			type: 'BigDecimal',
		},
		{
			DBType: 'Clob',
			businessType: 'RichText',
			externalReferenceCode: 'Test-RichTextField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testRichTextField'},
			listTypeDefinitionId: 0,
			name: 'testRichTextField',
			required: false,
			system: false,
			type: 'Clob',
		},
		{
			DBType: 'String',
			businessType: 'Text',
			externalReferenceCode: 'Test-name',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'name'},
			listTypeDefinitionId: 0,
			name: 'name',
			required: false,
			system: false,
			type: 'String',
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
			DBType: 'Date',
			businessType: 'Date',
			externalReferenceCode: 'Test-date',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testDateField'},
			listTypeDefinitionId: 0,
			name: 'testDateField',
			required: false,
			system: false,
			type: 'Date',
		},
		{
			DBType: 'DateTime',
			businessType: 'DateTime',
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
			type: 'DateTime',
		},
		{
			DBType: 'Double',
			businessType: 'Decimal',
			externalReferenceCode: 'Test-DecimalField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testDecimalField'},
			listTypeDefinitionId: 0,
			name: 'testDecimalField',
			required: false,
			system: false,
			type: 'Double',
		},
		{
			DBType: 'Integer',
			businessType: 'Integer',
			externalReferenceCode: 'Test-IntegerField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testIntegerField'},
			listTypeDefinitionId: 0,
			name: 'testIntegerField',
			required: false,
			system: false,
			type: 'Integer',
		},
		{
			DBType: 'Long',
			businessType: 'LongInteger',
			externalReferenceCode: 'Test-LongInteger',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testLongInteger'},
			listTypeDefinitionId: 0,
			name: 'testLongInteger',
			required: false,
			system: false,
			type: 'Long',
		},
		{
			DBType: 'Clob',
			businessType: 'LongText',
			externalReferenceCode: 'Test-LongTextField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testLongTextField'},
			listTypeDefinitionId: 0,
			name: 'testLongTextField',
			required: false,
			system: false,
			type: 'Clob',
		},
		{
			DBType: 'BigDecimal',
			businessType: 'PrecisionDecimal',
			externalReferenceCode: 'Test-PrecisionDecimalField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testPrecisionDecimalField'},
			listTypeDefinitionId: 0,
			name: 'testPrecisionDecimalField',
			required: false,
			system: false,
			type: 'BigDecimal',
		},
		{
			DBType: 'Clob',
			businessType: 'RichText',
			externalReferenceCode: 'Test-RichTextField',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'testRichTextField'},
			listTypeDefinitionId: 0,
			name: 'testRichTextField',
			required: false,
			system: false,
			type: 'Clob',
		},
		{
			DBType: 'String',
			businessType: 'Text',
			externalReferenceCode: 'Test-name',
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: '',
			label: {en_US: 'name'},
			listTypeDefinitionId: 0,
			name: 'name',
			required: false,
			system: false,
			type: 'String',
		},
	],
	panelCategoryKey: 'site_administration.design',
	pluralLabel: {'en-US': 'Tests'},
	portlet: true,
	scope: 'site',
	status: {code: 0},
};

test('can import CSV file with an unexisting field', async ({
	apiHelpers,
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: companyObjectDefinition,
	});

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
		(await apiHelpers.object.getObjectDefinitionObjectEntries('c/tests'))
			.items
	).toEqual([
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: expect.any(Number),
			keywords: [],
			name: 'TestName',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testDateField: '2024-01-05T00:00:00Z',
			testDateTimeField: '2024-01-05T15:00:00.000Z',
			testDecimalField: 10.2,
			testIntegerField: 100,
			testLongInteger: 123456789,
			testLongTextField: 'This is a long text to test testLongTextField',
			testPrecisionDecimalField: 321.123,
			testRichTextField: 'null',
			testRichTextFieldRawText: 'null',
		},
	]);

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
});

test('can import CSV file with custom columns order', async ({
	apiHelpers,
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: siteObjectDefinition,
	});

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
			await apiHelpers.object.getObjectDefinitionObjectEntriesByScope(
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
			name: 'TestName',
			scopeKey: 'Guest',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testDateField: '2024-01-05T00:00:00Z',
			testDateTimeField: '2024-01-05T15:00:00.000Z',
			testDecimalField: 10.2,
			testIntegerField: 100,
			testLongInteger: 123456789,
			testLongTextField: 'This is a long text to test testLongTextField',
			testPrecisionDecimalField: 321.123,
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField',
		},
	]);

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
});

test('can import CSV file with multiple site scoped object entries', async ({
	apiHelpers,
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: siteObjectDefinition,
	});

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
			await apiHelpers.object.getObjectDefinitionObjectEntriesByScope(
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
			name: 'TestName_FirstEntry',
			scopeKey: 'Guest',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testDateField: '2024-01-05T00:00:00Z',
			testDateTimeField: '2024-01-05T15:00:00.000Z',
			testDecimalField: 10.2,
			testIntegerField: 100,
			testLongInteger: 123456789,
			testLongTextField:
				'This is a long text to test testLongTextField. The first entry',
			testPrecisionDecimalField: 321.123,
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField. The first entry.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField. The first entry.',
		},
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08273',
			id: expect.any(Number),
			keywords: [],
			name: 'TestName_SecondEntry',
			scopeKey: 'Guest',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testDateField: '2024-01-06T00:00:00Z',
			testDateTimeField: '2024-01-06T15:00:00.000Z',
			testDecimalField: 11.2,
			testIntegerField: 101,
			testLongInteger: 123456790,
			testLongTextField:
				'This is a long text to test testLongTextField. The second entry',
			testPrecisionDecimalField: 123.321,
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField. The second entry.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField. The second entry.',
		},
	]);

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
});

test('can import CSV file with new and existing site scoped object entries', async ({
	apiHelpers,
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: siteObjectDefinition,
	});

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
			await apiHelpers.object.getObjectDefinitionObjectEntriesByScope(
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
			name: 'TestName',
			scopeKey: 'Guest',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testDateField: '2024-01-05T00:00:00Z',
			testDateTimeField: '2024-01-05T15:00:00.000Z',
			testDecimalField: 10.2,
			testIntegerField: 100,
			testLongInteger: 123456789,
			testLongTextField:
				'This is a long text to test testLongTextField. The first entry',
			testPrecisionDecimalField: 321.123,
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField.',
		},
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08273',
			id: expect.any(Number),
			keywords: [],
			name: 'TestName_SecondEntry',
			scopeKey: 'Guest',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testDateField: '2024-01-06T00:00:00Z',
			testDateTimeField: '2024-01-06T15:00:00.000Z',
			testDecimalField: 11.2,
			testIntegerField: 101,
			testLongInteger: 123456790,
			testLongTextField:
				'This is a long text to test testLongTextField. The second entry',
			testPrecisionDecimalField: 123.321,
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField. New entry.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField. New entry.',
		},
	]);

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
});

test('can import CSV file with new and modified existing company scoped object entries', async ({
	apiHelpers,
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: companyObjectDefinition,
	});

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
		(await apiHelpers.object.getObjectDefinitionObjectEntries('c/tests'))
			.items
	).toEqual([
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: expect.any(Number),
			keywords: [],
			name: 'TestName_Modified',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testDateField: '2024-01-05T00:00:00Z',
			testDateTimeField: '2024-01-05T15:00:00.000Z',
			testDecimalField: 10.2,
			testIntegerField: 100,
			testLongInteger: 123456789,
			testLongTextField:
				'This is a long text to test testLongTextField. The first entry',
			testPrecisionDecimalField: 321.123,
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField. The modified entry.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField. The modified entry.',
		},
		{
			actions: expect.any(Object),
			creator: expect.any(Object),
			dateCreated: expect.any(String),
			dateModified: expect.any(String),
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08273',
			id: expect.any(Number),
			keywords: [],
			name: 'TestName_NewEntry',
			status: expect.any(Object),
			taxonomyCategoryBriefs: [],
			testDateField: '2024-01-06T00:00:00Z',
			testDateTimeField: '2024-01-06T15:00:00.000Z',
			testDecimalField: 11.2,
			testIntegerField: 101,
			testLongInteger: 123456790,
			testLongTextField:
				'This is a long text to test testLongTextField. The second entry',
			testPrecisionDecimalField: 123.321,
			testRichTextField:
				'<p>This is a long text <strong>with some fomatting</strong> to text\n  testRichTextField. The new entry.  </p>',
			testRichTextFieldRawText:
				'This is a long text with some fomatting to text testRichTextField. The new entry.',
		},
	]);

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
});

test('can map all imported fields', async ({
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: siteObjectDefinition,
	});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.selectImportEntityType(
		OBJECT_ENTRY_ENTITY_TYPE
	);

	await expect(page.getByText('externalReferenceCode')).toBeVisible();
	await expect(page.getByText('keywords', {exact: true})).toBeVisible();
	await expect(page.getByText('taxonomyCategoryIds')).toBeVisible();
	await expect(page.getByText('testDateField')).toBeVisible();
	await expect(page.getByText('testDecimalField')).toBeVisible();
	await expect(page.getByText('testIntegerField')).toBeVisible();
	await expect(page.getByText('testLongInteger')).toBeVisible();
	await expect(page.getByText('testLongTextField')).toBeVisible();
	await expect(page.getByText('testPrecisionDecimalField')).toBeVisible();
	await expect(page.getByText('testRichTextField')).toBeVisible();
	await expect(page.getByText('name', {exact: true})).toBeVisible();

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
});

test('can preview CSV file', async ({
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: siteObjectDefinition,
	});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.selectFile(
		path.join(__dirname, '/dependencies/object_entries.csv')
	);

	await dataMigrationCenterPage.selectImportEntityType(
		OBJECT_ENTRY_ENTITY_TYPE
	);

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
			.getByRole('cell', {exact: true, name: 'name'})
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

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
});

test('can show duplicate error message with CSV import existing entry and only add new record fields', async ({
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: companyObjectDefinition,
	});

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
			'com.liferay.object.exception.DuplicateObjectEntryExternalReferenceCodeException'
		)
	).toBeVisible();

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
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
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: siteObjectDefinition,
	});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.selectFile(
		path.join(
			__dirname,
			'/dependencies/empty_header_values_object_entries.csv'
		)
	);

	await dataMigrationCenterPage.selectImportEntityType(
		OBJECT_ENTRY_ENTITY_TYPE
	);

	await page.waitForTimeout(2000);

	await page.getByRole('button', {name: 'Next'}).click();

	await expect(
		page.getByText(
			'Error:You must map at least one field and all required fields before continuing.'
		)
	).toBeVisible();

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
});

test('cannot import CSV file with object entry with UPSERT strategy', async ({
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: companyObjectDefinition,
	});

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

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
});

test('cannot import empty CSV file', async ({
	authenticate,
	dataMigrationCenterPage,
	page,
}) => {
	const objectDefinition = await authenticate(
		ObjectAdminRestClient
	).objectDefinition.postObjectDefinition({
		requestBody: companyObjectDefinition,
	});

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToImportFile();

	await dataMigrationCenterPage.selectFile(
		path.join(__dirname, '/dependencies/empty_object_entries.csv')
	);

	await dataMigrationCenterPage.selectImportEntityType(
		OBJECT_ENTRY_ENTITY_TYPE
	);

	await page.waitForTimeout(2000);

	await page.getByRole('button', {name: 'Next'}).click();

	await expect(page.getByText('Error:Please upload a file.')).toBeVisible();

	await authenticate(
		ObjectAdminRestClient
	).objectDefinition.deleteObjectDefinition({
		objectDefinitionId: objectDefinition.id,
	});
});
