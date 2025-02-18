/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionApi,
	ObjectField,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {readFileFromZip} from '../../utils/zip';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {toDateRangeDate, toDateRangeTime} from './utils/dateRangeUtil';

export const test = mergeTests(
	applicationsMenuPageTest,
	companyExportImportPageTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true, system: true},
	}),
	loginTest()
);

test('cannot export site scoped custom object entries at instance level', async ({
	apiHelpers,
	applicationsMenuPage,
	page,
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
			scope: 'site',
			status: {
				code: 0,
			},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests/scopes/Guest'
	);

	await applicationsMenuPage.goToExport();

	await page.getByTestId('creationMenuNewButton').nth(1).click();

	await expect(page.getByLabel('Tests 1 Items')).toBeHidden();
});

test('can export custom object entries at instance level with date filter', async ({
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

	await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests'
	);

	const exportFilePath1 = await companyExportImportPage.export(
		'Tests 1 Items',
		false
	);

	const content1 = await readFileFromZip('C_Test.json', exportFilePath1);

	const json1 = JSON.parse(content1);

	expect(json1.length).toBe(1);

	const endDate = new Date();

	endDate.setDate(endDate.getDate() - 1);

	const startDate = new Date();

	startDate.setDate(startDate.getDate() - 2);

	const exportFilePath2 = await companyExportImportPage.export(
		'Tests 1 Items',
		false,
		{
			endDate: toDateRangeDate(endDate),
			endTime: toDateRangeTime(endDate),
			startDate: toDateRangeDate(startDate),
			startTime: toDateRangeTime(startDate),
		}
	);

	const content2 = await readFileFromZip('C_Test.json', exportFilePath2);

	const json2 = JSON.parse(content2);

	expect(json2.length).toBe(0);

	const exportFilePath3 = await companyExportImportPage.export(
		'Tests 1 Items',
		false,
		{
			rangeLast: '12 Hours',
		}
	);

	const content3 = await readFileFromZip('C_Test.json', exportFilePath3);

	const json3 = JSON.parse(content3);

	expect(json3.length).toBe(1);
});

test('can export custom object entries at instance level with permissions', async ({
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

	await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests'
	);

	const exportFilePath = await companyExportImportPage.export(
		'Tests 1 Items',
		true
	);

	const content = await readFileFromZip('C_Test.json', exportFilePath);

	const json = JSON.parse(content);

	expect(json.length).toBe(1);
	expect(json[0]).toHaveProperty('permissions');
});
