/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import getRandomString from '../../utils/getRandomString';
import {getTempDir} from '../../utils/temp';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	exportImportPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35914': {enabled: false, system: true},
	}),
	loginTest(),
    productMenuPageTest
);

export const testWithStagingInInstanceFF = mergeTests(
	applicationsMenuPageTest,
	exportImportPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true, system: true},
	}),
	loginTest()
);

async function expectExportName(exportImportPage, taskName: string) {
	await exportImportPage.goToExport();

	await exportImportPage.newExportButton.click();

	await exportImportPage.exportButton.click();

	await expect(
		exportImportPage.page
			.locator('//h2[span[normalize-space()="' + taskName + '"]]')
			.first()
			.locator('../..')
			.getByText('Successful')
	).toBeVisible();

	const exportFilePath =
		await exportImportPage.downloadExportProcess(taskName);

	expect(exportFilePath).toMatch(new RegExp(`^${getTempDir()}${taskName}-`));
}

test('can export at site level with custom export task name', async ({
	exportImportPage,
}) => {
	await exportImportPage.goToExport();

	const taskName = 'MyExport-' + getRandomString();

	await exportImportPage.createNewExportProcess(taskName);

	await expect(
		exportImportPage.page
			.locator('//h2[span[normalize-space()="' + taskName + '"]]')
			.first()
			.locator('../..')
			.getByText('Successful')
	).toBeVisible();

	const exportFilePath =
		await exportImportPage.downloadExportProcess(taskName);

	expect(exportFilePath).toMatch(new RegExp(`^${getTempDir()}MyExport-`));
});

test('can export at site level with old file name', async ({
	exportImportPage,
}) => {
	await expectExportName(exportImportPage, 'Pages');
});

testWithStagingInInstanceFF(
	'can export at site level with new file name',
	async ({exportImportPage}) => {
		await expectExportName(exportImportPage, 'Export');
	}
);

test('can see corresponding elements at site level', async ({
	productMenuPage,
}) => {
	await productMenuPage.openProductMenuIfClosed();
	await productMenuPage.goToPublishingExport();
	await productMenuPage.page
		.getByRole('link', {name: 'Custom Export'})
		.click();

	await expect(
		productMenuPage.page.getByText('Comments, Ratings')
	).toBeVisible();
});