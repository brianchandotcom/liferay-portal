/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {assetPublisherPagesTest} from '../../../fixtures/assetPublisherPagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {samplePageTest} from '../../frontend-taglib/main/fixtures/samplePageTest';
import {TabName} from '../../frontend-taglib/main/pages/SamplePage';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

const test = mergeTests(
	assetPublisherPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	pageEditorPagesTest,
	samplePageTest
);

test(
	'Check various accessibility in pagination',
	{tag: ['@LPD-38101', '@LPD-38653']},
	async ({page, samplePage}) => {
		await test.step('Select Search Paginator tab', async () => {
			await samplePage.selectTab(TabName.SEARCH_PAGINATOR);
		});

		await test.step('Check pagination button is selected and contains option role', async () => {
			await page.getByLabel('Items per Page').click();

			const paginationFourSelection = page.getByRole('option', {
				name: '20  Entries per Page',
			});

			await paginationFourSelection.click();

			const pagination = page.getByLabel('Items per Page');

			await pagination.waitFor({state: 'visible'});

			const paginationLinkSelected = page.locator(
				'a[aria-selected="true"][role="option"][id="20"]'
			);

			await expect(paginationLinkSelected).toBeHidden();
		});

		await test.step('Check pagination list has aria-labelledby', async () => {
			const element = page.locator('ul.dropdown-menu.dropdown-menu-top');

			await expect(element).toHaveAttribute('aria-labelledby');
		});

		await test.step('Check aria-label is being translated', async () => {
			const url = page.url();

			const esURL = url.replace('/web/', '/es/web/');

			await page.goto(esURL);

			await expect(page.getByLabel('Paginación')).toBeVisible();
		});
	}
);

test(
	'Intermediate pages button and dropdown accessibility issues',
	{tag: '@LPD-42610'},
	async ({page, samplePage}) => {
		await test.step('Select Search Paginator tab', async () => {
			await samplePage.selectTab(TabName.SEARCH_PAGINATOR);
		});

		await test.step('Check intermediate pages button has a tooltip', async () => {
			const intermediatePagesButton = page.getByRole('button', {
				name: 'Intermediate Pages Use TAB to',
			});

			await expect(intermediatePagesButton).toHaveAttribute('title');
		});

		await test.step('Check intermediate pages dropdown items has a role', async () => {
			const intermediatePagesDropdown = page.locator(
				'ul.pagination div.dropdown-menu'
			);

			const pageLink = intermediatePagesDropdown
				.locator('a.dropdown-item')
				.first();

			await expect(pageLink).toHaveRole('menuitem');
		});
	}
);

test(
	'Dropdown menu adjusts to screen size',
	{tag: '@LPD-50471'},
	async ({apiHelpers, assetPublisherPage, page, pageEditorPage, site}) => {
		const widgetId = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: widgetId,
					widgetName:
						'com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.goToWidgetConfiguration(widgetId);

		await assetPublisherPage.openAssetSelectionTab();

		await assetPublisherPage.selectCollectionProvider('Recent Content');

		await assetPublisherPage.openDisplaySettingsTab();

		const itemDisplayInput =
			assetPublisherPage.configurationIframe.getByLabel(
				'Number of Items to Display'
			);

		await itemDisplayInput.waitFor({state: 'visible'});

		await itemDisplayInput.click();

		await itemDisplayInput.fill('1');

		await assetPublisherPage.configurationIframe
			.getByLabel('Pagination Type')
			.selectOption('Regular');

		await assetPublisherPage.saveConfiguration();

		await assetPublisherPage.closeConfiguration();

		await page.getByLabel('Publish', {exact: true}).click();

		await test.step('Create web content articles and test dropdown', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}/${layout.friendlyUrlPath}`
			);

			for (let i = 1; i <= 10; i++) {
				const contentStructureId =
					await getBasicWebContentStructureId(apiHelpers);
				const randomTitle = getRandomString();

				const webContent =
					await apiHelpers.jsonWebServicesJournal.addWebContent({
						ddmStructureId: contentStructureId,
						groupId: site.id,
						titleMap: {en_US: randomTitle},
					});

				apiHelpers.data.push({
					id: `${site.id}_${webContent.articleId}`,
					type: 'webContent',
				});
			}

			await page.reload();

			await page.setViewportSize({height: 600, width: 200});

			const dropdownButton = page.locator(
				'[title="Show Intermediate Pages"]'
			);

			await dropdownButton.waitFor({state: 'visible'});

			await dropdownButton.click();

			const dropdownMenu = page.getByLabel('Page 4');

			await dropdownMenu.waitFor({state: 'visible'});

			await expect(dropdownMenu).toBeInViewport();
		});
	}
);
