/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import * as path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {loginTest} from '../../fixtures/loginTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	documentLibraryPagesTest,
	productMenuPageTest,
	exportImportPagesTest,
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

	expect(
		await exportImportPage.page.getByText(exportName).locator('../..').getByText('Successful').textContent()
	).toBe('Successful');

	const exportFilePath = await exportImportPage.downloadExportProcess(exportName);

	await exportImportPage.goToImport();

	await exportImportPage.createNewImportProcess(exportFilePath);

	expect(
		await exportImportPage.page.getByText('Successful').textContent()
	).toBe('Successful');
});
