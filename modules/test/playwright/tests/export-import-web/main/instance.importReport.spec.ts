/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionAPI,
	ObjectFieldAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {objectDefitionRequestData} from './utils/objectDefitionRequestData';

export const test = mergeTests(
	dataApiHelpersTest,
	exportImportPagesTest,
	companyExportImportPageTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true, system: true},
	}),
	loginTest()
);

test('Can see error report and details', async ({
	apiHelpers,
	companyExportImportPage,
	exportImportPage,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			objectDefitionRequestData()
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests'
	);

	const exportFilePath = await companyExportImportPage.export(
		'Tests 1 Items',
		false
	);

	const objectFieldAPIClient =
		await apiHelpers.buildRestClient(ObjectFieldAPI);

	await objectFieldAPIClient.postObjectDefinitionObjectField(
		objectDefinition.id,
		{
			DBType: 'String',
			businessType: 'Text',
			label: {en_US: 'mandatoryField'},
			name: 'mandatoryField',
			required: true,
		}
	);

	await companyExportImportPage.import(exportFilePath, true);

	const lastSlashIndex = exportFilePath.lastIndexOf('/');
	const exportName = exportFilePath.slice(lastSlashIndex + 1);

	await exportImportPage.goToImportDetails(exportName);

	//TODO: Uncomment after LPD-64508
	await expect(
		exportImportPage.page.getByRole('cell', {name: objectDefinition.name})
	).toBeVisible();

	await expect(
		exportImportPage.page.getByRole('cell', {
			name: objectEntry.externalReferenceCode,
		})
	).toBeVisible();

	await exportImportPage.viewErrorDetails.click();

	//TODO: Uncomment after LPD-64508
	await expect(
		exportImportPage.page.getByRole('heading', {
			name: objectDefinition.name,
		})
	).toBeVisible();

	await expect(
		exportImportPage.page.getByText(
			'No value was provided for required object field'
		)
	).toBeVisible();

	await expect(
		exportImportPage.page.getByText(objectEntry.externalReferenceCode)
	).toBeVisible();
});
