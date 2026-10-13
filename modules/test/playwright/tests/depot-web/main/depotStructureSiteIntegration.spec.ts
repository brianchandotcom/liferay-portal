/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {DepotSitesPage} from '../../../pages/depot-web/DepotSitesPage';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import {JournalEditTemplatePage} from '../../journal-web/main/pages/JournalEditTemplatePage';
import getDataStructureDefinition from '../../journal-web/main/utils/getDataStructureDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';
import addConnectedDepot from './utils/addConnectedDepot';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest()
);

test(
	'A depot structure made available to a connected site can be used there and cannot be deleted',
	{tag: '@LPD-109574'},
	async ({
		apiHelpers,
		journalEditArticlePage,
		journalPage,
		journalStructuresPage,
		page,
		site,
	}) => {
		const assetLibrary = await addConnectedDepot(apiHelpers, site);

		const structureName = getRandomString();

		await apiHelpers.dataEngine.createStructure(
			String(assetLibrary.siteId),
			getDataStructureDefinition({
				defaultLanguageId: 'en_US',
				fields: [{name: 'Text'}],
				name: structureName,
			})
		);

		const depotSitesPage = new DepotSitesPage(page);

		const siteRow = depotSitesPage.getSiteRow(site.name);

		await test.step('Make the depot structures available to the site', async () => {
			await depotSitesPage.goto(assetLibrary.id);

			await siteRow.getByRole('button').click();

			let confirmMessage = '';

			page.once('dialog', async (dialog) => {
				confirmMessage = dialog.message();

				await dialog.accept();
			});

			await page
				.getByRole('menuitem', {name: 'Make Structures Available'})
				.click();

			await waitForAlert(page);

			expect(confirmMessage).toBe(
				'You will not be able to disconnect this site when structure and document type sharing is enabled. In order to disconnect this site from this asset library, you must disable structure and document type sharing first.'
			);

			// The columns are Name, Searchable Content and Structures and Types

			await expect(siteRow.getByRole('cell').nth(2)).toHaveText('Yes');

			await siteRow.getByRole('button').click();

			await expect(
				page.getByRole('menuitem', {name: 'Disconnect'})
			).toBeDisabled();

			await page.keyboard.press('Escape');
		});

		await test.step('The site does not manage the depot structure', async () => {
			await journalStructuresPage.goto(site.friendlyUrlPath);

			await expect(
				page.getByRole('link', {exact: true, name: structureName})
			).toHaveCount(0);
		});

		const templateName = getRandomString();

		await test.step('Add a site template for the depot structure', async () => {
			const journalEditTemplatePage = new JournalEditTemplatePage(page);

			await journalEditTemplatePage.goto(site.friendlyUrlPath);

			await journalEditTemplatePage.selectStructure(structureName);

			await journalEditTemplatePage.editTemplate(
				templateName,
				'${Text.getData()}'
			);

			await journalEditTemplatePage.saveTemplate();
		});

		const text = getRandomString();
		const title = getRandomString();

		await test.step('Add a site web content with the depot structure', async () => {
			await journalEditArticlePage.goto({
				siteUrl: site.friendlyUrlPath,
				structureName,
			});

			await journalEditArticlePage.fillTitle(title);

			await page
				.getByRole('textbox', {exact: true, name: 'Text'})
				.fill(text);

			await journalEditArticlePage.publishArticle();

			await expect(
				page
					.getByTestId('row')
					.getByRole('link', {exact: true, name: title})
			).toBeVisible();
		});

		await test.step('Display it in a Web Content Display and an Asset Publisher', async () => {
			let articleKey;

			// The structured contents are searched through the index

			await expect(async () => {
				const {items} = await apiHelpers.get(
					`${apiHelpers.baseUrl}headless-delivery/v1.0/sites/${site.id}/structured-contents?search=${title}`
				);

				expect(items).toHaveLength(1);

				articleKey = items[0].key;
			}).toPass();

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([
					getWidgetDefinition({
						id: getRandomString(),
						widgetConfig: {
							articleId: articleKey,
							groupId: String(site.id),
						},
						widgetName:
							'com_liferay_journal_content_web_portlet_JournalContentPortlet',
					}),
					getWidgetDefinition({
						id: getRandomString(),
						widgetConfig: {selectionStyle: 'dynamic'},
						widgetName:
							'com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet',
					}),
				]),
				siteId: site.id,
				title: getRandomString(),
			});

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await expect(page.getByText(text).first()).toBeVisible();
			await expect(
				page
					.locator(
						'[id^="portlet_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet"]'
					)
					.getByText(title)
			).toBeVisible();
		});

		await test.step('The site lists the depot as the scope of its structure', async () => {

			// Enough site structures to move the depot one into More

			for (let i = 0; i < 7; i++) {
				await apiHelpers.dataEngine.createStructure(
					site.id,
					getDataStructureDefinition({
						defaultLanguageId: 'en_US',
						fields: [{name: 'Text'}],
						name: getRandomString(),
					})
				);
			}

			await journalPage.goto(site.friendlyUrlPath);

			await page.getByRole('button', {exact: true, name: 'New'}).click();

			await page.getByRole('button', {exact: true, name: 'More'}).click();

			await expect(
				page
					.getByRole('dialog', {name: 'More'})
					.frameLocator('iframe')
					.getByRole('row', {name: structureName})
					.getByRole('cell', {exact: true, name: assetLibrary.name})
			).toBeVisible();
		});

		await test.step('The depot structure cannot be deleted while the site uses it', async () => {
			await journalStructuresPage.goto(
				`/asset-library-${assetLibrary.id}`
			);

			page.once('dialog', (dialog) => dialog.accept());

			await journalStructuresPage.goToJournalStructureAction(
				'Delete',
				structureName
			);

			await expect(
				page.getByText(
					'The structure cannot be deleted because it is required by one or more structure links.'
				)
			).toBeVisible();
		});

		await test.step('Make the depot structures unavailable again', async () => {
			await depotSitesPage.goto(assetLibrary.id);

			await siteRow.getByRole('button').click();

			let confirmMessage = '';

			page.once('dialog', async (dialog) => {
				confirmMessage = dialog.message();

				await dialog.accept();
			});

			await page
				.getByRole('menuitem', {name: 'Make Structures Unavailable'})
				.click();

			await waitForAlert(page);

			expect(confirmMessage).toBe(
				'After disabling structure and document type sharing, any site content that uses the structures or document types will be invalid. Do you want to disable structure and document type sharing?'
			);

			await expect(siteRow.getByRole('cell').nth(2)).toHaveText('No');
		});
	}
);
