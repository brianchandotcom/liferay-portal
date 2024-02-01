/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HeadlessDeliveryClient} from '@liferay/headless-delivery-client-js';

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';
import * as path from 'path';

import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {exportImportPagesTest} from '../../fixtures/exportImportPages.fixtures';
import {headlessClientsTest} from '../../fixtures/headlessClientsTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	documentLibraryPagesTest,
	exportImportPagesTest,
	headlessClientsTest({headlessDeliveryClient: HeadlessDeliveryClient}),
	loginTest
);

test('can import a folder with document type restrictions and workflow', async ({
	documentLibraryEditFolderPage,
	documentLibraryPage,
	exportImportFramePage,
	headlessClients: {headlessDeliveryClient},
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

	await headlessDeliveryClient.documentFolder.deleteSiteDocumentsFolderByExternalReferenceCode(
		{

			// TODO: This can also be a string but the type only supports number

			externalReferenceCode: 'LPS-205933',
			siteId: 'Guest' as any,
		}
	);
});
