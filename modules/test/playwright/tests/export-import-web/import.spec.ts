/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import * as path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {loginTest} from '../../fixtures/loginTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import getRandomString from '../../utils/getRandomString';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import {stagingPageTest} from './fixtures/stagingPageTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	dataApiHelpersTest,
	documentLibraryPagesTest,
	productMenuPageTest,
	exportImportPagesTest,
	stagingPageTest,
	loginTest()
);

test('can import a folder with document type restrictions and workflow', async ({
	apiHelpers,
	documentLibraryEditFolderPage,
	documentLibraryPage,
	exportImportFramePage,
}) => {
	await documentLibraryPage.goto();
	await documentLibraryPage.openOptionsMenu();
	await documentLibraryPage.exportImportOptionsMenuItem.click();
	await exportImportFramePage.importLARFile(
		path.join(__dirname, 'dependencies', 'folder.portlet.lar')
	);
	await exportImportFramePage.close();
	await documentLibraryPage.editEntry('LPS-205933');

	expect(
		await documentLibraryEditFolderPage.getSelectedWorkflowDefinition()
	).toBe('Single Approver@1');

	await apiHelpers.headlessDelivery.deleteSiteDocumentsFolderByExternalReferenceCode(
		'LPS-205933'
	);
});

test('can import a lar file selecting some items to import', async ({
	exportImportPage,
}) => {
	await exportImportPage.goToExport();

	const exportName = 'MyExport-' + getRandomString();

	await exportImportPage.createNewExportProcess(exportName);

	await expect(
		exportImportPage.page
			.getByText(exportName)
			.locator('../..')
			.getByText('Successful')
	).toBeVisible();

	const exportFilePath =
		await exportImportPage.downloadExportProcess(exportName);

	await exportImportPage.goToImport();

	await exportImportPage.createNewImportProcess(exportFilePath);

	await expect(
		exportImportPage.page
			.getByText(exportName)
			.locator('../../..')
			.getByText('Successful')
	).toBeVisible();
});

[{name: 'com.liferay.site.initializer.welcome'}].forEach(({name}) => {
	test(`site initializer ${name} can be exported and imported`, async ({
		apiHelpers,
		stagingPage,
	}) => {
		const site = await apiHelpers.headlessSite.createSite({
			name,
			templateKey: name,
			templateType: 'site-initializer',
		});

		expect(site.name).toBeDefined();

		apiHelpers.data.push({id: site.id, type: 'site'});

		await stagingPage.goto(site.name);

		await stagingPage.enableDefaultLocalStaging();

		await stagingPage.compareCurrentPageVersions(site.name);
	});
});
