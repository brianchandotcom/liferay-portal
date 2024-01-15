/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';
import * as path from 'path';

import {HeadlessDeliveryClient} from '../../../../apps/headless/headless-delivery/headless-delivery-client-js/src/main/resources/META-INF/resources/node';
import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {exportImportPagesTest} from '../../fixtures/exportImportPages.fixtures';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	documentLibraryPagesTest,
	exportImportPagesTest,
	loginTest
);

test('can import a folder with document type restrictions and workflow', async ({
	authenticate,
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

	await authenticate(
		HeadlessDeliveryClient
	).documentFolder.deleteSiteDocumentsFolderByExternalReferenceCode({

		// TODO: This can also be a string but the type only supports number

		externalReferenceCode: 'LPS-205933',
		siteId: 'Guest' as any,
	});
});
