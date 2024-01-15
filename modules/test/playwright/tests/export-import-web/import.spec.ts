/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';
import * as path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpers.fixture';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {exportImportPagesTest} from '../../fixtures/exportImportPages.fixtures';

export const test = mergeTests(
	apiHelpersTest,
	documentLibraryPagesTest,
	exportImportPagesTest
);

test('can import a folder with document type restrictions and workflow', async ({
	_apiHelpers,
	_documentLibraryEditFolderPage,
	_documentLibraryPage,
	_exportImportFramePage,
}) => {
	await _documentLibraryPage.goto();
	await _documentLibraryPage.openOptionsMenu();
	await _documentLibraryPage.exportImportOptionsMenuItem.click();
	await _exportImportFramePage.importLARFile(
		path.join(__dirname, 'dependencies', 'folder.portlet.lar')
	);
	await _exportImportFramePage.close();
	await _documentLibraryPage.editEntry('LPS-205933');

	expect(
		await _documentLibraryEditFolderPage.getSelectedWorkflowDefinition()
	).toBe('Single Approver@1');

	await _apiHelpers.headlessDelivery.deleteSiteDocumentsFolderByExternalReferenceCode(
		'LPS-205933'
	);
});
