/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionAPI,
	ObjectFieldAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {objectDefitionRequestData} from './utils/objectDefitionRequestData';

export const test = mergeTests(
	applicationsMenuPageTest,
	dataApiHelpersTest,
	exportImportPagesTest,
	companyExportImportPageTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true},
	}),
	loginTest()
);

test('Can see error report and details', async ({
	apiHelpers,
	applicationsMenuPage,
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

	await applicationsMenuPage.goToExport();

	const exportFilePath = await exportImportPage.export({
		portletLabels: ['Tests 1 Items'],
	});

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

	await companyExportImportPage.import({
		filePath: exportFilePath,
		includePermissions: true,
		taskStatus: 'completedWithErrors',
	});

	const exportName = exportFilePath.slice(
		exportFilePath.lastIndexOf('/') + 1
	);

	await exportImportPage.taskActionsMenu(exportName).click();

	await expect(
		exportImportPage.page.getByRole('menuitem', {
			name: 'View Report Entries',
		})
	).toBeVisible();

	await expect(
		exportImportPage.page.getByRole('menuitem', {
			name: 'Export Report Entries',
		})
	).toBeVisible();

	await exportImportPage.goToImportDetails(exportName);

	await expect(
		exportImportPage.page.getByRole('cell', {
			name: objectEntry.externalReferenceCode,
		})
	).toBeVisible();

	await exportImportPage.goToImportErrorDetails(
		objectEntry.externalReferenceCode
	);

	await expect(
		exportImportPage.page.getByText(
			'No value was provided for required object field'
		)
	).toBeVisible();

	await expect(exportImportPage.page.getByText('ScopeCompany')).toBeVisible();

	await expect(
		exportImportPage.page.getByText('SiteLiferay DXP')
	).not.toBeVisible();

	await expect(
		exportImportPage.page.getByText(objectEntry.externalReferenceCode)
	).toBeVisible();
});

test('Report entries actions are not visible for a successful import', async ({
	apiHelpers,
	applicationsMenuPage,
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

	await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests'
	);

	await applicationsMenuPage.goToExport();

	const exportFilePath = await exportImportPage.export({
		portletLabels: ['Tests 1 Items'],
	});

	await companyExportImportPage.import({
		filePath: exportFilePath,
		includePermissions: true,
	});

	const exportName = exportFilePath.slice(
		exportFilePath.lastIndexOf('/') + 1
	);

	await exportImportPage.taskActionsMenu(exportName).click();

	await expect(
		exportImportPage.page.getByRole('menuitem', {
			name: 'View Report Entries',
		})
	).not.toBeVisible();

	await expect(
		exportImportPage.page.getByRole('menuitem', {
			name: 'Export Report Entries',
		})
	).not.toBeVisible();
});
