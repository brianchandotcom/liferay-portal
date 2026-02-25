/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {masterPagesPagesTest} from '../../../fixtures/masterPagesPagesTest';
import {pageTemplatesPagesTest} from '../../../fixtures/pageTemplatesPagesTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {uiElementsPageTest} from '../../../fixtures/uiElementsTest';
import getRandomString from '../../../utils/getRandomString';
import {getTempDir} from '../../../utils/temp';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';

export const baseTest = mergeTests(
	dataApiHelpersTest,
	exportImportPagesTest,
	isolatedSiteTest,
	loginTest(),
	productMenuPageTest,
	uiElementsPageTest
);

export const test = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-35443': {enabled: false},
	})
);

export const testWithExportImportAtInstanceLevelFF = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-35443': {enabled: true},
	})
);

export const testWithHeadlessContentPagesFF = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-35443': {enabled: true},
	}),
	masterPagesPagesTest,
	pageTemplatesPagesTest
);

async function expectExportName(exportImportPage, taskName: string) {
	await exportImportPage.goToExport();

	await exportImportPage.newExportButton.click();

	await exportImportPage.exportButton.click();

	const exportFilePath =
		await exportImportPage.downloadExportProcess(taskName);

	expect(exportFilePath).toMatch(new RegExp(`^${getTempDir()}${taskName}-`));
}

test('can export at site level with custom export task name', async ({
	exportImportPage,
}) => {
	await exportImportPage.goToExport();

	const taskName = 'MyExport-' + getRandomString();

	const exportFilePath = await exportImportPage.export({taskName});

	expect(exportFilePath).toMatch(new RegExp(`^${getTempDir()}MyExport-`));
});

testWithExportImportAtInstanceLevelFF(
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

	await expect(
		productMenuPage.page.getByRole('link', {name: 'Refresh Counts'})
	).toBeVisible();
});

test(
	'can see the Deletions label at the site level',
	{tag: ['@LPD-37317']},
	async ({exportImportPage, productMenuPage, uiElementsPage}) => {
		await productMenuPage.openProductMenuIfClosed();
		await productMenuPage.goToPublishingExport();

		uiElementsPage.clickNewButton();

		const deletionsLabelText =
			await exportImportPage.deletionsLabel.textContent();

		expect(deletionsLabelText?.replace(/\s+/g, ' ').trim()).toBe(
			'Export Individual Deletions: If this is checked, the delete operations performed will be exported in the LAR file.'
		);
	}
);

testWithHeadlessContentPagesFF(
	'can see the correct counts of master page templates at site level',
	{tag: ['@LPD-67433']},
	async ({
		exportImportPage,
		masterPagesPage,
		pageTemplatesPage,
		productMenuPage,
		site,
		uiElementsPage,
	}) => {
		await masterPagesPage.goto(site.friendlyUrlPath);
		await masterPagesPage.createNewMaster(getRandomString());
		await masterPagesPage.createNewMaster(getRandomString());

		await pageTemplatesPage.goto(site.friendlyUrlPath);
		await pageTemplatesPage.addPageTemplateCollection(getRandomString());
		await pageTemplatesPage.addWidgetPageTemplate(getRandomString());

		await pageTemplatesPage.goto(site.friendlyUrlPath);
		await pageTemplatesPage.addWidgetPageTemplate(getRandomString());

		await pageTemplatesPage.goto(site.friendlyUrlPath);

		await productMenuPage.openProductMenuIfClosed();
		await productMenuPage.goToPublishingExport();

		uiElementsPage.clickNewButton();

		await exportImportPage.page.getByLabel(/Pages\s+\d+\s+Items/i).check();
		await exportImportPage.page
			.locator('button.content-link[data-portlettitle="Pages"]')
			.click();

		expect(
			exportImportPage.page.getByText('Master Pages (2)', {exact: true})
		).toBeVisible();
	}
);

testWithHeadlessContentPagesFF(
	'cannot see Site Pages checkbox',
	async ({exportImportPage, productMenuPage}) => {
		await productMenuPage.openProductMenuIfClosed();
		await productMenuPage.goToPublishingExport();
		await productMenuPage.page
			.getByRole('link', {name: 'Custom Export'})
			.click();

		await expect(
			exportImportPage.page.getByLabel(/Site Pages\s+\d+\s+Items/)
		).not.toBeVisible();
	}
);
