/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import * as fs from 'fs';
import * as path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataMigrationCenterPagesTest} from '../../fixtures/dataMigrationCenterPages';
import {exportImportPagesTest} from '../../fixtures/exportImportPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {streamToJson, unzipFile} from '../../utils/zipFolder';

export const test = mergeTests(
	apiHelpersTest,
	exportImportPagesTest,
	loginTest(),
	dataMigrationCenterPagesTest,
	featureFlagsTest({
		'COMMERCE-8087': true,
		'LPS-174455': true,
	})
);

const stockObjectDefinition = {
	active: true,
	externalReferenceCode: 'stockERC',
	label: {
		en_US: 'stock',
	},
	name: 'Stock',
	objectFields: [
		{
			DBType: 'String',
			businessType: 'Text',
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
};

const stockObjectEntry = {
	externalReferenceCode: 'nameERC',
	name: 'Stock Entry',
};

test('can download jsont format file as json', async ({
	apiHelpers,
	dataMigrationCenterPage,
}) => {
	const objectDefinition = await apiHelpers.objectAdmin.postObjectDefinition(
		stockObjectDefinition
	);

	await apiHelpers.object.postObjectEntry(stockObjectEntry, 'c/stocks');

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToExportFile();

	await dataMigrationCenterPage.exportFile(
		'JSONT',
		'C_Stock (v1_0 - Liferay Object REST)',
		__dirname + '/../../tmp/',
		['name']
	);

	const exportPath = path.resolve(__dirname, '../../tmp/Export.zip');

	await unzipFile(
		handleUnzipFile,
		exportPath,
		'jsont_objectEntry_import',
		require('./dependencies/jsont_objectEntry_import.json')
	);

	fs.unlinkSync(exportPath);
	await apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition.id);
});

test('verify users can exclude fields from being exported in JSON format', async ({
	apiHelpers,
	dataMigrationCenterPage,
}) => {
	const objectDefinition = await apiHelpers.objectAdmin.postObjectDefinition(
		stockObjectDefinition
	);

	await apiHelpers.object.postObjectEntry(stockObjectEntry, 'c/stocks');

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToExportFile();

	await dataMigrationCenterPage.exportFile(
		'JSON',
		'C_Stock (v1_0 - Liferay Object REST)',
		__dirname + '/../../tmp/',
		['name']
	);

	const exportPath = path.resolve(__dirname, '../../tmp/Export.zip');

	await unzipFile(
		handleUnzipFile,
		exportPath,
		'json_objectEntry_export_excluded',
		require('./dependencies/json_objectEntry_export_excluded.json')
	);

	fs.unlinkSync(exportPath);
	await apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition.id);
});

test('export custom object with all field types mapped', async ({
	apiHelpers,
	dataMigrationCenterPage,
}) => {
	const picklist = await apiHelpers.post(
		'/o/headless-admin-list-type/v1.0/list-type-definitions',
		{
			externalReferenceCode: 'customPicklistERC',
			name: 'customPicklist',
			name_i18n: {
				en_US: 'customPicklist',
			},
		}
	);

	await apiHelpers.post(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${picklist.id}/list-type-entries`,
		{
			key: 'distance1',
			name: 'distance1',
			name_i18n: {
				en_US: 'distance1',
			},
		}
	);

	const objectDefinition = await apiHelpers.objectAdmin.postObjectDefinition({
		active: true,
		externalReferenceCode: 'stockERC',
		label: {
			en_US: 'stock',
		},
		name: 'Stock',
		objectFields: [
			{
				DBType: 'String',
				businessType: 'Text',
				externalReferenceCode: 'nameERC',
				indexed: true,
				indexedAsKeyword: true,
				label: {
					en_US: 'name',
				},
				name: 'name',
				required: true,
			},
			{
				DBType: 'Boolean',
				businessType: 'Boolean',
				externalReferenceCode: 'customBoolean',
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: 'customBoolean'},
				listTypeDefinitionId: 0,
				name: 'customBoolean',
				required: false,
				system: false,
				type: 'Boolean',
			},
			{
				DBType: 'Clob',
				businessType: 'LongText',
				externalReferenceCode: 'customLongText',
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: 'customLongText'},
				listTypeDefinitionId: 0,
				name: 'customLongText',
				required: false,
				system: false,
				type: 'Clob',
			},
			{
				DBType: 'BigDecimal',
				businessType: 'PrecisionDecimal',
				externalReferenceCode: 'customPrecisionDecimal',
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: '',
				label: {en_US: 'customPrecisionDecimal'},
				listTypeDefinitionId: 0,
				name: 'customPrecisionDecimal',
				required: false,
				system: false,
				type: 'BigDecimal',
			},
			{
				DBType: 'String',
				businessType: 'Picklist',
				externalReferenceCode: 'customPicklist',
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: 'en_US',
				label: {
					en_US: 'customPicklist',
				},
				listTypeDefinitionExternalReferenceCode: 'customPicklistERC',
				name: 'customPicklist',
				required: false,
				state: false,
			},
			{
				DBType: 'Long',
				businessType: 'Attachment',
				indexed: true,
				indexedAsKeyword: false,
				label: {
					en_US: 'customAttachment',
				},
				name: 'customAttachment',
				objectFieldSettings: [
					{
						name: 'acceptedFileExtensions',
						value: 'jpeg, jpg, pdf, png',
					},
					{
						name: 'fileSource',
						value: 'documentsAndMedia',
					},
					{
						name: 'maximumFileSize',
						value: '100',
					},
				],
				required: false,
				type: 'Long',
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

	const objectEntry = await apiHelpers.object.postObjectEntry(
		{
			customAttachment: {
				fileBase64:
					'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',
				name: 'test.png',
			},
			customBoolean: true,
			customLongText: 'This is a custom LongText field',
			customPicklist: {key: 'distance1'},
			customPrecisionDecimal: 12.55,
			name: 'NameValue',
		},
		'c/stocks'
	);

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToExportFile();

	await dataMigrationCenterPage.exportFile(
		'JSON',
		'C_Stock (v1_0 - Liferay Object REST)',
		__dirname + '/../../tmp/',
		[
			'creator',
			'customAttachment',
			'customPicklist',
			'customBoolean',
			'customPrecisionDecimal',
			'customLongText',
			'name',
		]
	);

	const exportPath = path.resolve(__dirname, '../../tmp/Export.zip');

	await unzipFile(
		handleUnzipFile,
		exportPath,
		'json_objectEntry_export',
		require('./dependencies/json_objectEntry_export.json')
	);

	fs.unlinkSync(exportPath);
	await apiHelpers.delete(
		`o/headless-delivery/v1.0/documents/${objectEntry.customAttachment.id}`
	);
	await apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition.id);
});

test('verify users can exclude fields from being exported in JSONL format', async ({
	apiHelpers,
	dataMigrationCenterPage,
}) => {
	const objectDefinition = await apiHelpers.objectAdmin.postObjectDefinition(
		stockObjectDefinition
	);

	await apiHelpers.object.postObjectEntry(stockObjectEntry, 'c/stocks');

	await dataMigrationCenterPage.goto();
	await dataMigrationCenterPage.goToExportFile();

	await dataMigrationCenterPage.exportFile(
		'JSONL',
		'C_Stock (v1_0 - Liferay Object REST)',
		__dirname + '/../../tmp/',
		['name']
	);

	const exportPath = path.resolve(__dirname, '../../tmp/Export.zip');

	expect(JSON.parse(await unzipFile(exportPath))).toEqual(
		_getExpectedValue(
			'jsonl_objectEntry_import',
			require('./dependencies/jsonl_objectEntry_import.json')
		)
	);

	fs.unlinkSync(exportPath);
	await apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition.id);
});

function _getExpectedValue(id: string, unzipFile: any) {
	let expectedValue: {};

	if (id === 'json_objectEntry_export') {
		expectedValue = [
			{
				...unzipFile[0],
				creator: {
					...unzipFile[0].creator,
					id: expect.any(Number),
				},
				customAttachment: {
					id: expect.any(Number),
					link: {
						href: expect.any(String),
						label: unzipFile[0].customAttachment.link.label,
					},
					name: expect.any(String),
				},
			},
		];
	} else if (id === 'json_objectEntry_export_excluded') {
		expectedValue = unzipFile;
	} else if (id === 'jsonl_objectEntry_import') {
		expectedValue = unzipFile;
	} else if (id === 'jsont_objectEntry_import') {
		expectedValue = {
			actions: unzipFile.actions,
			configuration: {
				...unzipFile.configuration,
				companyId: expect.any(Number),
				userId: expect.any(Number),
			},
			items: unzipFile.items,
		};
	}

	return expectedValue;
}
