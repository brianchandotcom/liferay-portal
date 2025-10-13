/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import * as path from 'path';

import {documentLibraryPagesTest} from '../../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {exportImportPagesTest} from './fixtures/revamp/exportImportPagesTest';
import {stagingPageTest} from './fixtures/stagingPageTest';

export const test = mergeTests(
	documentLibraryPagesTest,
	productMenuPageTest,
	exportImportPagesTest,
	featureFlagsTest({
		'LPD-57655': {enabled: true},
	}),
	loginTest(),
	stagingPageTest
);

test('Can import using the new navigation buttons', async ({
	documentLibraryPage,
	exportImportPage,
}) => {
	await documentLibraryPage.goto();
	await documentLibraryPage.openOptionsMenu();
	await exportImportPage.importMenuItem.click();

	await expect(exportImportPage.newImportTab).toBeVisible();

	await expect(exportImportPage.currentAndPreviousTab).toBeVisible();

	await exportImportPage.import(
		path.join(__dirname, 'dependencies', 'folder.portlet.lar')
	);
});
