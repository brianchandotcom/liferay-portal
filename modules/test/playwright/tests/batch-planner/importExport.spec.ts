/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import * as path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpers.fixture';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPages.fixture';
import {dataMigrationCenterPageTest} from '../../fixtures/dataMigrationCenterPages.fixture';
import {objectPagesTest} from '../../fixtures/objectPages.fixture';
import {
	C_TEST,
	C_TESTCOMPANY,
	INSERT,
	PARTIAL_UPDATE,
	UPDATE,
	UPSERT,
} from './utils/constants';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	dataMigrationCenterPageTest,
	objectPagesTest
);

const siteTestObjectDefinitionData = {
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
			objectFieldSettings: [{name: 'timeStorage', value: 'convertToUTC'}],
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

const companyTestObjectDefinitionData = {
	active: true,
	externalReferenceCode: 'TestCompany',
	label: {'en-US': 'TestCompany'},
	name: 'TestCompany',
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
			objectFieldSettings: [{name: 'timeStorage', value: 'convertToUTC'}],
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
	pluralLabel: {'en-US': 'TestsCompanies'},
	portlet: true,
	scope: 'company',
	status: {code: 0},
};

test('Can map all imported fields', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);

	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		siteTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	await _dataMigrationCenterPage.selectImportEntityType(C_TEST);

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

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Cannot import CSV file without headers and an unexisting field header', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);

	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		siteTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPath = path.join(
		__dirname,
		'/dependencies/c_test-NoHeaders.csv'
	);
	await _dataMigrationCenterPage.selectFile(fileWithPath);

	await _dataMigrationCenterPage.selectImportEntityType(C_TEST);

	await page.waitForTimeout(2000);

	await page.getByRole('button', {name: 'Next'}).click();

	await expect(
		page.getByText(
			'Error:You must map at least one field and all required fields before continuing.'
		)
	).toBeVisible();

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Can see object entry info on preview modal', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);

	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		siteTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPath = path.join(__dirname, '/dependencies/c_test.csv');
	await _dataMigrationCenterPage.selectFile(fileWithPath);

	await _dataMigrationCenterPage.selectImportEntityType(C_TEST);

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

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Can import CSV file with custom columns order', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);

	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		siteTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPath = path.join(
		__dirname,
		'/dependencies/c_test-CustomColumnsOrder.csv'
	);
	await _dataMigrationCenterPage.importFile(
		C_TEST,
		fileWithPath,
		UPSERT,
		UPDATE
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	const testObjectEntries = await _apiHelpers.customObject.getObjectDefinitionObjectEntriesByScope(
		'c/tests',
		'Guest'
	);

	expect([
		{
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/tests/35234',
					method: 'DELETE',
				},
				get: {
					href: 'http://localhost:8080/o/c/tests/35234',
					method: 'GET',
				},
				permissions: {
					href: 'http://localhost:8080/o/c/tests/35234/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/tests/35234',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/tests/35234',
					method: 'PATCH',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				name: 'Test Test',
			},
			dateCreated: '2024-01-10T10:41:27Z',
			dateModified: '2024-01-10T10:41:27Z',
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: 35234,
			keywords: [],
			name: 'TestName',
			scopeKey: 'Guest',
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Aprobado',
			},
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
	]).toEqual(
		testObjectEntries.items.map((item) =>
			expect.objectContaining({
				...item,
				actions: expect.any(Object),
				creator: expect.any(Object),
				dateCreated: expect.any(String),
				dateModified: expect.any(String),
				id: expect.any(Number),
				status: expect.any(Object),
			})
		)
	);

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Can Import Multiple Object Entries With Site Scope', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);

	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		siteTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPath = path.join(
		__dirname,
		'/dependencies/c_test-TwoEntries.csv'
	);
	await _dataMigrationCenterPage.importFile(
		C_TEST,
		fileWithPath,
		UPSERT,
		UPDATE
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	const testObjectEntries = await _apiHelpers.customObject.getObjectDefinitionObjectEntriesByScope(
		'c/tests',
		'Guest'
	);

	expect([
		{
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/tests/35330',
					method: 'DELETE',
				},
				get: {
					href: 'http://localhost:8080/o/c/tests/35330',
					method: 'GET',
				},
				permissions: {
					href: 'http://localhost:8080/o/c/tests/35330/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/tests/35330',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/tests/35330',
					method: 'PATCH',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				name: 'Test Test',
			},
			dateCreated: '2024-01-10T10:48:33Z',
			dateModified: '2024-01-10T10:48:33Z',
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: 35330,
			keywords: [],
			name: 'TestName_FirstEntry',
			scopeKey: 'Guest',
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Aprobado',
			},
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
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/tests/35332',
					method: 'DELETE',
				},
				get: {
					href: 'http://localhost:8080/o/c/tests/35332',
					method: 'GET',
				},
				permissions: {
					href: 'http://localhost:8080/o/c/tests/35332/permissions',
					method: 'GET',
				},

				replace: {
					href: 'http://localhost:8080/o/c/tests/35332',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/tests/35332',
					method: 'PATCH',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				name: 'Test Test',
			},
			dateCreated: '2024-01-10T10:48:33Z',
			dateModified: '2024-01-10T10:48:33Z',
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08273',
			id: 35332,
			keywords: [],
			name: 'TestName_SecondEntry',
			scopeKey: 'Guest',
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Aprobado',
			},
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
	]).toEqual(
		testObjectEntries.items.map((item) =>
			expect.objectContaining({
				...item,
				actions: expect.any(Object),
				creator: expect.any(Object),
				dateCreated: expect.any(String),
				dateModified: expect.any(String),
				id: expect.any(Number),
				status: expect.any(Object),
			})
		)
	);

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Can Create ObjectEntry From CSV File Containing Existing And New With Unchecked Stop Import On Error', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);

	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		siteTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPath = path.join(__dirname, '/dependencies/c_test.csv');
	await _dataMigrationCenterPage.importFile(
		C_TEST,
		fileWithPath,
		UPSERT,
		UPDATE
	);

	await page.getByRole('button', {exact: true, name: 'Close'}).click();

	const fileWithPathTwoEntries = path.join(
		__dirname,
		'/dependencies/c_test-TwoEntries.csv'
	);
	await _dataMigrationCenterPage.importFile(
		C_TEST,
		fileWithPathTwoEntries,
		UPSERT,
		UPDATE
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	const testObjectEntries = await _apiHelpers.customObject.getObjectDefinitionObjectEntriesByScope(
		'c/tests',
		'Guest'
	);

	expect([
		{
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/tests/35430',
					method: 'DELETE',
				},
				get: {
					href: 'http://localhost:8080/o/c/tests/35430',
					method: 'GET',
				},
				permissions: {
					href: 'http://localhost:8080/o/c/tests/35430/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/tests/35430',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/tests/35430',
					method: 'PATCH',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				name: 'Test Test',
			},
			dateCreated: '2024-01-10T10:51:11Z',
			dateModified: '2024-01-10T10:51:14Z',
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: 35430,
			keywords: [],
			name: 'TestName_FirstEntry',
			scopeKey: 'Guest',
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Aprobado',
			},
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
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/tests/35440',
					method: 'DELETE',
				},
				get: {
					href: 'http://localhost:8080/o/c/tests/35440',
					method: 'GET',
				},
				permissions: {
					href: 'http://localhost:8080/o/c/tests/35440/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/tests/35440',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/tests/35440',
					method: 'PATCH',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				name: 'Test Test',
			},
			dateCreated: '2024-01-10T10:51:15Z',
			dateModified: '2024-01-10T10:51:15Z',
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08273',
			id: 35440,
			keywords: [],
			name: 'TestName_SecondEntry',
			scopeKey: 'Guest',
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Aprobado',
			},
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
	]).toEqual(
		testObjectEntries.items.map((item) =>
			expect.objectContaining({
				...item,
				actions: expect.any(Object),
				creator: expect.any(Object),
				dateCreated: expect.any(String),
				dateModified: expect.any(String),
				id: expect.any(Number),
				status: expect.any(Object),
			})
		)
	);

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Cannot Import With Empty File', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);

	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		companyTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPath = path.join(__dirname, '/dependencies/c_test-Empty.csv');
	await _dataMigrationCenterPage.selectFile(fileWithPath);

	await _dataMigrationCenterPage.selectImportEntityType(C_TESTCOMPANY);

	await page.waitForTimeout(2000);

	await page.getByRole('button', {name: 'Next'}).click();

	await expect(page.getByText('Error:Please upload a file.')).toBeVisible();

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Cannot Import ObjectEntry With Update Changed Records Fiels', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);
	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		companyTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPathTwoEntries = path.join(
		__dirname,
		'/dependencies/c_test.csv'
	);
	await _dataMigrationCenterPage.importFile(
		C_TESTCOMPANY,
		fileWithPathTwoEntries,
		UPSERT,
		PARTIAL_UPDATE
	);

	await expect(
		page.getByText(
			'javax.ws.rs.NotSupportedException: Create strategy "UPSERT" is not supported for'
		)
	).toBeVisible();

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Can See Duplicate Error Message With Import Existing Entry And Only Add New Record siels', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);
	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		companyTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPathFirst = path.join(__dirname, '/dependencies/c_test.csv');
	await _dataMigrationCenterPage.importFile(
		C_TESTCOMPANY,
		fileWithPathFirst,
		UPSERT,
		UPDATE
	);

	await page.getByRole('button', {exact: true, name: 'Close'}).click();

	const fileWithPathSecond = path.join(__dirname, '/dependencies/c_test.csv');
	await _dataMigrationCenterPage.importFile(
		C_TESTCOMPANY,
		fileWithPathSecond,
		INSERT,
		UPDATE
	);

	await expect(
		page.getByText(
			'com.liferay.object.exception.DuplicateObjectEntryExternalReferenceCodeException'
		)
	).toBeVisible();

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Can Import CSV File With An Unexisting Field', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);

	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		companyTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPathTwoEntries = path.join(
		__dirname,
		'/dependencies/c_test-NonExistingField.csv'
	);
	await _dataMigrationCenterPage.importFile(
		C_TESTCOMPANY,
		fileWithPathTwoEntries,
		UPSERT,
		UPDATE
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	const testObjectEntries = await _apiHelpers.customObject.getObjectDefinitionObjectEntries(
		'c/testcompanies'
	);

	expect([
		{
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/testcompanies/35537',
					method: 'DELETE',
				},
				get: {
					href: 'http://localhost:8080/o/c/testcompanies/35537',
					method: 'GET',
				},
				permissions: {
					href:
						'http://localhost:8080/o/c/testcompanies/35537/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/testcompanies/35537',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/testcompanies/35537',
					method: 'PATCH',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				name: 'Test Test',
			},
			dateCreated: '2024-01-10T10:54:16Z',
			dateModified: '2024-01-10T10:54:16Z',
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: 35537,
			keywords: [],
			name: 'TestName',
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Aprobado',
			},
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
	]).toEqual(
		testObjectEntries.items.map((item) =>
			expect.objectContaining({
				...item,
				actions: expect.any(Object),
				creator: expect.any(Object),
				dateCreated: expect.any(String),
				dateModified: expect.any(String),
				id: expect.any(Number),
				status: expect.any(Object),
			})
		)
	);

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Cannot Import Without Headers', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPath = path.join(
		__dirname,
		'/dependencies/c_test-NoHeaders2.csv'
	);
	await _dataMigrationCenterPage.selectFile(fileWithPath);

	await page.getByRole('button', {name: 'Next'}).click();

	await expect(page.getByText('Unexpected Error')).toBeVisible();
	await expect(
		page.getByText(
			'Error:Please upload a file and select the required columns before continuing.'
		)
	).toBeVisible();

	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});

test('Can Update And Create ObjectEntries Existing ObjectEntries With Same CSV File', async ({
	_apiHelpers,
	_dataMigrationCenterPage,
	page,
}) => {
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', true);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', true);

	const response = await _apiHelpers.objectAdmin.postObjectDefinition(
		companyTestObjectDefinitionData
	);
	const objectDefinitionId = await response.id;

	await _dataMigrationCenterPage.goto();
	await _dataMigrationCenterPage.goToImportFile();

	const fileWithPath = path.join(__dirname, '/dependencies/c_test.csv');
	await _dataMigrationCenterPage.importFile(
		C_TESTCOMPANY,
		fileWithPath,
		UPSERT,
		UPDATE
	);

	await page.getByRole('button', {exact: true, name: 'Close'}).click();

	const fileWithPathTwoEntries = path.join(
		__dirname,
		'/dependencies/c_test-TwoEntriesExistingModified.csv'
	);
	await _dataMigrationCenterPage.importFile(
		C_TESTCOMPANY,
		fileWithPathTwoEntries,
		UPSERT,
		UPDATE
	);

	await expect(
		page.getByText('The import process completed successfully.')
	).toBeVisible();

	const testObjectEntries = await _apiHelpers.customObject.getObjectDefinitionObjectEntries(
		'c/testcompanies'
	);

	expect([
		{
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/testcompanies/35728',
					method: 'DELETE',
				},
				get: {
					href: 'http://localhost:8080/o/c/testcompanies/35728',
					method: 'GET',
				},
				permissions: {
					href:
						'http://localhost:8080/o/c/testcompanies/35728/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/testcompanies/35728',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/testcompanies/35728',
					method: 'PATCH',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				name: 'Test Test',
			},
			dateCreated: '2024-01-10T11:09:14Z',
			dateModified: '2024-01-10T11:09:17Z',
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08271',
			id: 35728,
			keywords: [],
			name: 'TestName_Modified',
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Aprobado',
			},
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
			actions: {
				delete: {
					href: 'http://localhost:8080/o/c/testcompanies/35737',
					method: 'DELETE',
				},
				get: {
					href: 'http://localhost:8080/o/c/testcompanies/35737',
					method: 'GET',
				},
				permissions: {
					href:
						'http://localhost:8080/o/c/testcompanies/35737/permissions',
					method: 'GET',
				},
				replace: {
					href: 'http://localhost:8080/o/c/testcompanies/35737',
					method: 'PUT',
				},
				update: {
					href: 'http://localhost:8080/o/c/testcompanies/35737',
					method: 'PATCH',
				},
			},
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				name: 'Test Test',
			},
			dateCreated: '2024-01-10T11:09:17Z',
			dateModified: '2024-01-10T11:09:17Z',
			externalReferenceCode: '83b46736-f89b-9b90-188c-497d06c08273',
			id: 35737,
			keywords: [],
			name: 'TestName_NewEntry',
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Aprobado',
			},
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
	]).toEqual(
		testObjectEntries.items.map((item) =>
			expect.objectContaining({
				...item,
				actions: expect.any(Object),
				creator: expect.any(Object),
				dateCreated: expect.any(String),
				dateModified: expect.any(String),
				id: expect.any(Number),
				status: expect.any(Object),
			})
		)
	);

	await _apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinitionId);
	await _apiHelpers.featureFlag.updateFeatureFlag('COMMERCE-8087', false);
	await _apiHelpers.featureFlag.updateFeatureFlag('LPS-173135', false);
});
