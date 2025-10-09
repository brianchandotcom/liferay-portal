/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {masterPagesPagesTest} from '../../../fixtures/masterPagesPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageTemplatesPagesTest} from '../../../fixtures/pageTemplatesPagesTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {uiElementsPageTest} from '../../../fixtures/uiElementsTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {PageEditorPage} from '../../../pages/layout-content-page-editor-web/PageEditorPage';
import {MasterPagesPage} from '../../../pages/layout-page-template-admin-web/MasterPagesPage';
import getRandomString from '../../../utils/getRandomString';
import {getTempDir} from '../../../utils/temp';
import {exportImportPagesTest} from './fixtures/exportImportPagesTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	dataApiHelpersTest,
	exportImportPagesTest,
	featureFlagsTest({
		'LPD-35914': {enabled: false},
	}),
	isolatedSiteTest,
	loginTest(),
	masterPagesPagesTest,
	pageEditorPagesTest,
	pageTemplatesPagesTest,
	productMenuPageTest,
	uiElementsPageTest
);

export const testWithExportImportAtInstanceLevelFF = mergeTests(
	applicationsMenuPageTest,
	exportImportPagesTest,
	dataApiHelpersTest,
    isolatedSiteTest,
    masterPagesPagesTest,
    pageEditorPagesTest,
    pageTemplatesPagesTest,
    productMenuPageTest,
    uiElementsPageTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true},
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

async function addMasterPage(
	apiHelpers: ApiHelpers,
	masterPageName: string,
	masterPagesPage: MasterPagesPage,
	pageEditorPage: PageEditorPage,
	site: Site
) {

	// Add master page

	const masterPage =
		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addLayoutPageTemplateEntry(
			{
				groupId: site.id,
				name: masterPageName,
				type: 'master-layout',
			}
		);

	// Edit master page

	await masterPagesPage.goto(site.friendlyUrlPath);

	await masterPagesPage.editMaster(masterPageName);

	await pageEditorPage.addFragment('Basic Components', 'Heading');

	const headingId = await pageEditorPage.getFragmentId('Heading');

	await pageEditorPage.editTextEditable(
		headingId,
		'element-text',
		`Master Page: ${masterPageName}`
	);

	await pageEditorPage.publishPage();

	return masterPage;
}

test('can export at site level with custom export task name', async ({
	exportImportPage,
}) => {
	await exportImportPage.goToExport();

	const taskName = 'MyExport-' + getRandomString();

	await exportImportPage.export(taskName);

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

testWithExportImportAtInstanceLevelFF(
	'can see the correct counts of master page templates at site level',
	{tag: ['@LPD-67433']},
	async ({
		apiHelpers,
		exportImportPage,
		masterPagesPage,
		pageEditorPage,
		pageTemplatesPage,
		productMenuPage,
		site,
        uiElementsPage
	}) => {
		const masterPageName1 = getRandomString();

		await addMasterPage(
			apiHelpers,
			masterPageName1,
			masterPagesPage,
			pageEditorPage,
			site
		);

		const masterPageName2 = getRandomString();

		await addMasterPage(
			apiHelpers,
			masterPageName2,
			masterPagesPage,
			pageEditorPage,
			site
		);

		await pageTemplatesPage.goto(site.friendlyUrlPath);

		const pageTemplateCollectionName = getRandomString();

		await pageTemplatesPage.addPageTemplateCollection(
			pageTemplateCollectionName
		);

		const pageTemplateName1 = getRandomString();

		await pageTemplatesPage.addWidgetPageTemplate(pageTemplateName1);

		await pageTemplatesPage.goto(site.friendlyUrlPath);

		const pageTemplateName2 = getRandomString();

		await pageTemplatesPage.addWidgetPageTemplate(pageTemplateName2);

		await pageTemplatesPage.goto(site.friendlyUrlPath);

		await productMenuPage.openProductMenuIfClosed();
		await productMenuPage.goToPublishingExport();

        uiElementsPage.clickNewButton();

		await exportImportPage.page.getByLabel(`Pages ${7} Items`).check();
		await exportImportPage.page
			.locator(
				'[id="_com_liferay_exportimport_web_portlet_ExportPortlet_contentLink_com_liferay_layout_admin_web_portlet_GroupPagesPortlet"]'
			)
			.click();

		expect(
			exportImportPage.page.getByText('Master Pages (2)', {exact: true})
		).toBeVisible();
	}
);
