/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPagesTest} from '../../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import getRandomString from '../../../utils/getRandomString';
import {stagingPageTest} from '../main/fixtures/stagingPageTest';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';

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

test('Can export using the new navigation buttons', async ({
	documentLibraryPage,
	exportImportPage,
}) => {
	await documentLibraryPage.goto();
	await documentLibraryPage.openOptionsMenu();
	await exportImportPage.exportMenuItem.click();

	await expect(exportImportPage.newExportTab).toBeVisible();
	await expect(exportImportPage.currentAndPreviousTab).toBeVisible();

	const exportName = `Test export-${getRandomString()}`;

	await exportImportPage.export(exportName);
});
