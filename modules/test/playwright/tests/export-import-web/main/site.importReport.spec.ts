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
import getRandomString from '../../../utils/getRandomString';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {objectDefitionRequestData} from './utils/objectDefitionRequestData';
import {getTempFile} from '../../../utils/temp';
import {readFileFromZip} from '../../../utils/zip';

export const test = mergeTests(
	dataApiHelpersTest,
	exportImportPagesTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true},
	}),
	loginTest()
);

test('Can see error report and details', async ({
	apiHelpers,
	exportImportPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			objectDefitionRequestData({
				scope: 'site',
			})
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
		{externalReferenceCode: '', name: 'test'},
		'c/tests/scopes/Guest'
	);

	await exportImportPage.goToExport();

	const exportName = `MyExport-${getRandomString()}`;

	await exportImportPage.export(exportName, 'Tests 1 Items');

	await expect(exportImportPage.taskSuccessLabel(exportName)).toBeVisible();

	const exportFilePath =
		await exportImportPage.downloadExportProcess(exportName);

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

	await exportImportPage.goToImport();

	await exportImportPage.import(exportFilePath);

	await exportImportPage.goToImportDetails(exportName);

	await expect(
		page.getByRole('cell', {
			name: objectEntry.externalReferenceCode,
		})
	).toBeVisible();

	await exportImportPage.goToImportErrorDetails(
		objectEntry.externalReferenceCode
	);

	await expect(
		page.getByText('No value was provided for required object field')
	).toBeVisible();

	await expect(exportImportPage.page.getByText('ScopeSite')).toBeVisible();

	await expect(
		exportImportPage.page.getByText('SiteLiferay DXP')
	).toBeVisible();

	await expect(
		page.getByText(objectEntry.externalReferenceCode)
	).toBeVisible();
});

test(
	'Can donwload export report entries CSV',
	{tag: '@LPD-65208'},
	async ({apiHelpers, exportImportPage, page}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition(
				objectDefitionRequestData({
					scope: 'site',
				})
			);

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{externalReferenceCode: '', name: 'test'},
			'c/tests/scopes/Guest'
		);

		await exportImportPage.goToExport();

		const exportName = `MyExport-${getRandomString()}`;

		await exportImportPage.export(exportName, 'Tests 1 Items');

		await expect(
			exportImportPage.taskSuccessLabel(exportName)
		).toBeVisible();

		const exportFilePath =
			await exportImportPage.downloadExportProcess(exportName);

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

		await exportImportPage.goToImport();

		await exportImportPage.import(exportFilePath);

		await exportImportPage.openExportReportEntriesModal(exportName);

		await expect(
			exportImportPage.exportReportEntriesModal.getByRole('progressbar')
		).toHaveAttribute('aria-valuenow', '100');

		const downloadPromise = page.waitForEvent('download');
		await exportImportPage.exportReportEntriesModal
			.getByRole('button', {name: 'Download'})
			.click();

		const download = await downloadPromise;
		const suggestedFilename = download.suggestedFilename();

		expect(suggestedFilename).toMatch(
			new RegExp(`^${exportName}-(\\d+)_report_entries\\.zip$`)
		);

		const filePath = getTempFile(suggestedFilename);
		await download.saveAs(filePath);
		const content = await readFileFromZip('export.csv', filePath);

		await expect(content).toContain(
			'classExternalReferenceCode,errorMessage,modelName,status.code,status.extendedProperties,status.label,status.xClassName,type.code,type.extendedProperties,type.label,type.xClassName'
		);

		await expect(content).toContain(
			'No value was provided for required object field ""mandatoryField"""'
		);
	}
);
