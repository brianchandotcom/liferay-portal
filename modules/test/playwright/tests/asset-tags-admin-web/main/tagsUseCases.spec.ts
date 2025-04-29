/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {searchPageTest} from '../../../fixtures/searchPageTest';
import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';
import {tagsPagesTest} from './fixtures/tagsAdminPagesTest';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	pageEditorPagesTest,
	pageViewModePagesTest,
	isolatedSiteTest,
	searchPageTest,
	tagsPagesTest,
	loginTest()
);

test('View tags with number display template in Tag Filter widget', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
	tagsFilterPage,
}) => {
	const widgetId = getRandomString();
	const widgetDefinition = await getWidgetDefinition({
		id: widgetId,
		widgetName:
			'com_liferay_asset_tags_navigation_web_portlet_AssetTagsNavigationPortlet',
	});
	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([widgetDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	const contentStructureId = await getBasicWebContentStructureId(apiHelpers);

	await apiHelpers.headlessDelivery.postStructuredContent({
		contentStructureId,
		datePublished: null,
		siteId: site.id,
		tags: ['first', 'second'],
		title: getRandomString(),
		viewableBy: 'Anyone',
	});

	await apiHelpers.headlessDelivery.postStructuredContent({
		contentStructureId,
		datePublished: null,
		siteId: site.id,
		tags: ['first'],
		title: getRandomString(),
		viewableBy: 'Anyone',
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);
	await pageEditorPage.goToWidgetConfiguration(widgetId);

	await tagsFilterPage.changeDisplayTemplate('Number');
	await tagsFilterPage.configurationIframe
		.getByLabel('Show Asset Count')
		.check();
	await tagsFilterPage.saveAndClose();

	await pageEditorPage.publishPage();

	await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

	await expect(
		await page.getByRole('link', {name: 'first (2)'})
	).toBeVisible();
	await expect(
		await page.getByRole('link', {name: 'second (1)'})
	).toBeVisible();

	test.step('Tags can be increased', async () => {
		await apiHelpers.headlessDelivery.postStructuredContent({
			contentStructureId,
			datePublished: null,
			siteId: site.id,
			tags: ['first'],
			title: getRandomString(),
			viewableBy: 'Anyone',
		});

		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await expect(
			await page.getByRole('link', {name: 'first (3)'})
		).toBeVisible();
		await expect(
			await page.getByRole('link', {name: 'second (1)'})
		).toBeVisible();
	});
});

test('Add an auto assertion tag via blogs widget', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const widgetId = getRandomString();
	const widgetDefinition = await getWidgetDefinition({
		id: widgetId,
		widgetName: 'com_liferay_blogs_web_portlet_BlogsPortlet',
	});
	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([widgetDefinition]),
		siteId: site.id,
		title: widgetId,
	});

	const blogsEntryName = getRandomString();

	await apiHelpers.headlessDelivery.postBlog(site.id, {
		headline: blogsEntryName,
		keywords: ['first', 'second', 'third'],
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await pageEditorPage.publishPage();

	await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

	await page.getByRole('link', {name: blogsEntryName}).click();

	await expect(page.getByText('first second third')).toBeVisible();

	await test.step('Tag can be removed', async () => {

		// We need to hover in portlet and click in the dropdown to navigate to edit

		await page.locator('.portlet[id$="_BlogsPortlet"]').hover();
		await page
			.locator('.visible-interaction > .dropdown-action', {
				has: page.getByRole('link', {name: 'Edit Entry'}),
			})
			.click();
		await page.getByRole('button', {name: 'Categorization'}).waitFor();

		await expect(async () => {
			await page.getByRole('button', {name: 'Categorization'}).click();

			await expect(page.getByLabel('Remove second')).toBeVisible();
		}).toPass();

		await page.getByLabel('Remove second').click();

		await clickAndExpectToBeHidden({
			target: page.getByLabel('second'),
			trigger: page.getByRole('button', {name: 'Publish'}),
		});

		await page
			.locator('.alert-success', {
				hasText: 'Success:Your request completed successfully.',
			})
			.first()
			.waitFor();

		await expect(page.getByText('first third')).toBeVisible({
			timeout: 5000,
		});
	});
});

test('The Tag Filter widget could display unused tags in Cloud Display', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
	tagsFilterPage,
}) => {
	await apiHelpers.headlessAdminTaxonomy.postSiteKeyword({
		name: 'second',
		siteId: site.id,
	});

	const widgetId = getRandomString();
	const widgetDefinition = await getWidgetDefinition({
		id: widgetId,
		widgetName:
			'com_liferay_asset_tags_navigation_web_portlet_AssetTagsNavigationPortlet',
	});
	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([widgetDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	const contentStructureId = await getBasicWebContentStructureId(apiHelpers);

	await apiHelpers.headlessDelivery.postStructuredContent({
		contentStructureId,
		datePublished: null,
		siteId: site.id,
		tags: ['first'],
		title: getRandomString(),
		viewableBy: 'Anyone',
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);
	await pageEditorPage.goToWidgetConfiguration(widgetId);

	await tagsFilterPage.configurationIframe
		.getByLabel('Show Unused Tags ')
		.check();
	await tagsFilterPage.saveAndClose();

	await pageEditorPage.publishPage();

	await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

	await expect(await page.getByRole('link', {name: 'first'})).toBeVisible();
	await expect(await page.getByRole('link', {name: 'second'})).toBeVisible();
});
