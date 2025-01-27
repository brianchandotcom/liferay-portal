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
import getRandomString from '../../utils/getRandomString';
import {readFileFromZip} from '../../utils/zip';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	dataApiHelpersTest,
	exportImportPagesTest,
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

test('can export custom object entries at instance level with permissions', async ({
	apiHelpers,
	applicationsMenuPage,
	exportImportPage,
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

	await applicationsMenuPage.goToExport();

	await page.getByTestId('creationMenuNewButton').nth(1).click();

	await page.getByLabel('Tests 1 Items').click();

	const exportName = 'CustomObject-WithPermissions-' + getRandomString();

	await exportImportPage.title.fill(exportName);

	await exportImportPage.exportPermissionsButton.click();

	await exportImportPage.exportButton.click();

	const exportFilePath =
		await exportImportPage.downloadExportProcess(exportName);

	const content = await readFileFromZip('C_Test.json', exportFilePath);

	const json = JSON.parse(content);

	expect(json.length).toBe(1);
	expect(json[0]).toHaveProperty('permissions');
});
