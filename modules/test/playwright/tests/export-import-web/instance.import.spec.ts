/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionApi,
	ObjectField,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {depotAdminPageTest} from '../../fixtures/depotAdminPageTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageTemplatesPagesTest} from '../../fixtures/pageTemplatesPagesTest';
import {wikiPagesTest} from '../../fixtures/wikiPagesTest';
import {readFileFromZip} from '../../utils/zip';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {stagingPageTest} from './fixtures/stagingPageTest';

export const test = mergeTests(
	companyExportImportPageTest,
	dataApiHelpersTest,
	depotAdminPageTest,
	documentLibraryPagesTest,
	featureFlagsTest({
		'LPD-35013': {enabled: true},
		'LPD-35914': {enabled: true, system: true},
	}),
	exportImportPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pageTemplatesPagesTest,
	stagingPageTest,
	wikiPagesTest
);

test('can export and import custom object entries at instance level', async ({
	apiHelpers,
	companyExportImportPage,
}) => {
	const objectActionApiClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectActionApiClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'test',
			label: {
				en_US: 'Test',
			},
			name: 'Test',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'Name',
					},
					name: 'name',
					required: true,
				},
			],
			pluralLabel: {
				en_US: 'Tests',
			},
			portlet: true,
			scope: 'company',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests'
	);

	const exportFilePath =
		await companyExportImportPage.export('Tests 1 Items');

	const content = await readFileFromZip('C_Test.json', exportFilePath);

	const json = JSON.parse(content);

	expect(json.length).toBe(1);
	expect(json[0]).not.toHaveProperty('permissions');

	await apiHelpers.delete(`${apiHelpers.baseUrl}c/tests/${objectEntry.id}`);

	expect(
		await apiHelpers.get(
			`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}`
		)
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import(exportFilePath);

	expect(
		await apiHelpers.get(
			`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}`
		)
	).toEqual(
		expect.objectContaining({
			externalReferenceCode: objectEntry.externalReferenceCode,
			name: objectEntry.name,
		})
	);
});

test('can import custom object entries at instance level with or without permissions based on selection', async ({
	apiHelpers,
	companyExportImportPage,
}) => {
	const objectActionApiClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: objectDefinition} =
		await objectActionApiClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'test',
			label: {
				en_US: 'Test',
			},
			name: 'Test',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					indexed: true,
					indexedAsKeyword: true,
					label: {
						en_US: 'Name',
					},
					name: 'name',
					required: true,
				},
			],
			pluralLabel: {
				en_US: 'Tests',
			},
			portlet: true,
			scope: 'company',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	let objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{
			externalReferenceCode: '',
			name: 'test',
			permissions: [
				{
					actionIds: ['VIEW'],
					roleName: 'Guest',
				},
			],
		},
		'c/tests'
	);

	// Export with permissions

	const exportFilePath = await companyExportImportPage.export(
		'Tests 1 Items',
		false,
		true
	);

	// Import with permissions

	await apiHelpers.delete(`${apiHelpers.baseUrl}c/tests/${objectEntry.id}`);

	expect(
		await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode(
			'c/tests',
			objectEntry.externalReferenceCode
		)
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import(exportFilePath, true);

	objectEntry = await apiHelpers.get(
		`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}/?nestedFields=permissions`
	);

	expect(objectEntry).toEqual(
		expect.objectContaining({
			permissions: [
				{
					actionIds: ['VIEW'],
					roleName: 'Guest',
				},
			],
		})
	);

	// Import without permissions

	await apiHelpers.delete(`${apiHelpers.baseUrl}c/tests/${objectEntry.id}`);

	expect(
		await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode(
			'c/tests',
			objectEntry.externalReferenceCode
		)
	).toEqual({status: 'NOT_FOUND'});

	await companyExportImportPage.import(exportFilePath);

	objectEntry = await apiHelpers.get(
		`${apiHelpers.baseUrl}c/tests/by-external-reference-code/${objectEntry.externalReferenceCode}/?nestedFields=permissions`
	);

	expect(objectEntry).not.toEqual(
		expect.objectContaining({
			permissions: [
				{
					actionIds: ['VIEW'],
					roleName: 'Guest',
				},
			],
		})
	);
});
