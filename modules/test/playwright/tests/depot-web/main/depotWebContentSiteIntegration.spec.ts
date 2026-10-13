/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {DataApiHelpers} from '../../../helpers/ApiHelpers';
import {WebContentDisplayPage} from '../../../pages/journal-content-web/WebContentDisplayPage';
import {PageEditorPage} from '../../../pages/layout-content-page-editor-web/PageEditorPage';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import getDataStructureDefinition from '../../journal-web/main/utils/getDataStructureDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';
import addConnectedDepot from './utils/addConnectedDepot';
import gotoDepotInItemSelector from './utils/gotoDepotInItemSelector';

const test = mergeTests(
	dataApiHelpersTest,
	displayPageTemplatesPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	pageEditorPagesTest
);

async function addConnectedDepotWebContent(
	apiHelpers: DataApiHelpers,
	site: Site
) {
	const assetLibrary = await addConnectedDepot(apiHelpers, site);

	const content = getRandomString();
	const title = getRandomString();

	await apiHelpers.headlessDelivery.postStructuredContent({
		contentFields: [{contentFieldValue: {data: content}, name: 'content'}],
		contentStructureId: await getBasicWebContentStructureId(apiHelpers),
		datePublished: null,
		siteId: String(assetLibrary.siteId),
		title,
	});

	return {assetLibrary, content, title};
}

async function selectDepotWebContentInWidget(
	page: Page,
	depotName: string,
	title: string
) {
	const webContentDisplayPage = new WebContentDisplayPage(page);

	const widget = page
		.locator(
			'[id^="portlet_com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE"]'
		)
		.first();

	await widget.hover();
	await widget.click();

	await page
		.locator('#wrapper')
		.getByText('Web Content Display')
		.last()
		.locator('..')
		.getByRole('button', {name: 'Options'})
		.click();

	await webContentDisplayPage.configurationOption.click();

	await webContentDisplayPage.configurationFrame
		.locator('.web-content-selector')
		.click();

	const itemSelector =
		webContentDisplayPage.selectWebContentInConfigurationFrame;

	await gotoDepotInItemSelector(itemSelector, depotName);

	await itemSelector.getByText(title, {exact: true}).click();

	// The selected card adds a suffix to the title

	await expect(
		webContentDisplayPage.configurationFrame.getByText(title)
	).toBeVisible();

	await webContentDisplayPage.saveConfigurationFrameOptions();
}

async function mapDepotWebContentInContentDisplay(
	page: Page,
	pageEditorPage: PageEditorPage,
	depotName: string,
	title: string
) {
	await pageEditorPage.addFragment('Content Display', 'Content Display');

	await pageEditorPage.selectFragment(
		await pageEditorPage.getFragmentId('Content Display')
	);

	await pageEditorPage.selectItemMappingButton.click();

	const itemSelector = page.frameLocator('iframe[title="Select"]');

	await gotoDepotInItemSelector(itemSelector, depotName);

	await itemSelector.getByRole('paragraph').filter({hasText: title}).click();

	await expect(
		page.locator('.page-editor__item-selector__content-input')
	).toHaveValue(title);

	await pageEditorPage.waitForChangesSaved();
}

test(
	'A Web Content Display of a connected site shows web content of its depots',
	{tag: '@LPD-109574'},
	async ({
		apiHelpers,
		displayPageTemplatesPage,
		page,
		pageEditorPage,
		site,
	}) => {
		const depot1 = await addConnectedDepotWebContent(apiHelpers, site);
		const depot2 = await addConnectedDepotWebContent(apiHelpers, site);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_journal_content_web_portlet_JournalContentPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		const layoutURL = `/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`;

		await test.step('Select the web content of a depot', async () => {
			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await selectDepotWebContentInWidget(
				page,
				depot1.assetLibrary.name,
				depot1.title
			);

			await pageEditorPage.publishPage();

			await page.goto(layoutURL);

			await expect(page.getByText(depot1.content)).toBeVisible();
		});

		await test.step('Change it to the web content of another depot', async () => {
			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await selectDepotWebContentInWidget(
				page,
				depot2.assetLibrary.name,
				depot2.title
			);

			await pageEditorPage.publishPage();

			await page.goto(layoutURL);

			await expect(page.getByText(depot2.content)).toBeVisible();
			await expect(page.getByText(depot1.content)).toHaveCount(0);
		});

		await test.step('Select it in a display page template', async () => {
			const name = getRandomString();

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.createTemplate({
				contentSubtype: 'Basic Web Content',
				contentType: 'Web Content Article',
				name,
			});

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.editTemplate(name);

			await pageEditorPage.addWidget(
				'Content Management',
				'Web Content Display'
			);

			await selectDepotWebContentInWidget(
				page,
				depot1.assetLibrary.name,
				depot1.title
			);

			await expect(page.getByText(depot1.content)).toBeVisible();
		});
	}
);

test(
	'A Content Display fragment of a connected site shows web content of a depot',
	{tag: '@LPD-109574'},
	async ({
		apiHelpers,
		displayPageTemplatesPage,
		page,
		pageEditorPage,
		site,
	}) => {
		const {assetLibrary, content, title} =
			await addConnectedDepotWebContent(apiHelpers, site);

		await test.step('Map it in a content page', async () => {
			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([]),
				siteId: site.id,
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await mapDepotWebContentInContentDisplay(
				page,
				pageEditorPage,
				assetLibrary.name,
				title
			);

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await expect(page.getByText(content)).toBeVisible();
		});

		await test.step('Map it in the display page of a site web content', async () => {
			const name = getRandomString();

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.createTemplate({
				contentSubtype: 'Basic Web Content',
				contentType: 'Web Content Article',
				name,
			});

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.editTemplate(name);

			await mapDepotWebContentInContentDisplay(
				page,
				pageEditorPage,
				assetLibrary.name,
				title
			);

			await displayPageTemplatesPage.publishTemplate();

			await displayPageTemplatesPage.markAsDefault(name);

			// The friendly URL of the site web content is its title

			const siteWebContentTitle = getRandomString();

			await apiHelpers.headlessDelivery.postStructuredContent({
				contentFields: [
					{
						contentFieldValue: {data: getRandomString()},
						name: 'content',
					},
				],
				contentStructureId:
					await getBasicWebContentStructureId(apiHelpers),
				datePublished: null,
				siteId: site.id,
				title: siteWebContentTitle,
			});

			await page.goto(
				`/web${site.friendlyUrlPath}/w/${siteWebContentTitle}`
			);

			await expect(page.getByText(content)).toBeVisible();
		});
	}
);

test(
	'A web content field of a connected site article keeps a depot web content',
	{tag: ['@LPD-109574', '@LPS-105119']},
	async ({apiHelpers, journalEditArticlePage, page, site}) => {
		const {assetLibrary, title: depotTitle} =
			await addConnectedDepotWebContent(apiHelpers, site);

		const fieldName = 'WebContentSelector';
		const structureName = getRandomString();

		await apiHelpers.dataEngine.createStructure(
			site.id,
			getDataStructureDefinition({
				defaultLanguageId: 'en_US',
				fields: [{fieldType: 'journal_article', name: fieldName}],
				name: structureName,
			})
		);

		// Select the depot web content in the field of a site article

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		const title = getRandomString();

		await journalEditArticlePage.fillTitle(title);

		await page
			.getByTestId(fieldName)
			.getByRole('button', {name: 'Select'})
			.click();

		const itemSelector = page.frameLocator('iframe[title="Web Content"]');

		await gotoDepotInItemSelector(itemSelector, assetLibrary.name);

		await itemSelector.getByText(depotTitle, {exact: true}).click();

		await journalEditArticlePage.publishArticle();

		// The saved field links to the depot web content

		await expect(async () => {
			const {items} = await apiHelpers.get(
				`${apiHelpers.baseUrl}headless-delivery/v1.0/sites/${site.id}/structured-contents?search=${title}`
			);

			expect(items).toHaveLength(1);

			const [contentField] = items[0].contentFields;

			expect(
				contentField.contentFieldValue.structuredContentLink.title
			).toBe(depotTitle);
		}).toPass();
	}
);
