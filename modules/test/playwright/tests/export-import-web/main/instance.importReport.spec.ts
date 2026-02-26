/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectFieldAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {normalizeRestPath} from '../../../utils/normalizeRestPath';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';

export const test = mergeTests(
	dataApiHelpersTest,
	exportImportPagesTest,
	companyExportImportPageTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	globalMenuPagesTest,
	loginTest()
);

test('Can see error report and details', async ({
	apiHelpers,
	companyExportImportPage,
	exportImportPage,
	globalMenuPage,
}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: objectDefinition.name},
		normalizeRestPath(`${objectDefinition.restContextPath}`)
	);

	await globalMenuPage.goToApplications('Export');

	const exportFilePath = await exportImportPage.export({
		portletLabels: [`${objectDefinition.name} 1 Items`],
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

	await clickAndExpectToBeVisible({
		target: exportImportPage.clearMenuItem,
		trigger: exportImportPage.taskActionsMenu(exportName),
	});

	await expect(exportImportPage.viewReportEntriesMenuItem).toBeVisible();

	await expect(exportImportPage.exportReportEntriesMenuItem).toBeVisible();

	await exportImportPage.goToImportDetails(exportName);

	await expect(
		exportImportPage.page.getByRole('cell', {
			name: objectEntry.externalReferenceCode,
		})
	).toBeVisible();

	await exportImportPage.goToImportReportEntryDetails(
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
	companyExportImportPage,
	exportImportPage,
	globalMenuPage,
}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			status: {code: 0},
		});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: objectDefinition.name},
		normalizeRestPath(`${objectDefinition.restContextPath}`)
	);

	await globalMenuPage.goToApplications('Export');

	const exportFilePath = await exportImportPage.export({
		portletLabels: [`${objectDefinition.name} 1 Items`],
	});

	await companyExportImportPage.import({
		filePath: exportFilePath,
		includePermissions: true,
	});

	const exportName = exportFilePath.slice(
		exportFilePath.lastIndexOf('/') + 1
	);

	await clickAndExpectToBeVisible({
		target: exportImportPage.clearMenuItem,
		trigger: exportImportPage.taskActionsMenu(exportName),
	});

	await expect(exportImportPage.viewReportEntriesMenuItem).not.toBeVisible();

	await expect(
		exportImportPage.exportReportEntriesMenuItem
	).not.toBeVisible();
});
