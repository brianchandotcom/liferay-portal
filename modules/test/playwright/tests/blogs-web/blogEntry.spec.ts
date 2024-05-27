/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';
import {blogsPagesTest} from './fixtures/blogsPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	blogsPagesTest,
	pageEditorPagesTest,
	loginTest(),
	featureFlagsTest({
		'LPD-11147': true,
		'LPS-178052': true,
	})
);

test('LPD-22497: Permission sets are differing depending on autosaving of a blog entry', async ({
	blogsEditBlogEntryPage,
	blogsPage,
	page,
	site,
}) => {
	await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

	const title = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content: getRandomString(),
		publish: false,
		title,
	});

	await expect(
		page.locator(
			'#_com_liferay_blogs_web_portlet_BlogsAdminPortlet_saveStatus'
		)
	).toContainText('Draft Saved at', {
		timeout: 40000,
	});

	await blogsPage.goto(site.friendlyUrlPath);

	await blogsPage.assertBlogEntryPermissions(
		[
			{enabled: true, locator: '#guest_ACTION_ADD_DISCUSSION'},
			{enabled: true, locator: '#guest_ACTION_VIEW'},
			{enabled: true, locator: '#site-member_ACTION_ADD_DISCUSSION'},
			{enabled: true, locator: '#site-member_ACTION_VIEW'},
		],
		title
	);
});

test('LPD-26752 Select categories for the custom friendly URL', async ({
	apiHelpers,
	blogsEditBlogEntryPage,
	displayPageTemplatesPage,
	page,
	pageEditorPage,
	site,
}) => {
	const vocabularyName = getRandomString();
	const friendlyUrlCategories = ['category-1', 'category-2', 'category-3'];

	const {id: vocabularyId} =
		await apiHelpers.headlessAdminTaxonomy.createVocabulary({
			name: vocabularyName,
			siteId: site.id,
		});

	for (const categoryName of friendlyUrlCategories) {
		await apiHelpers.headlessAdminTaxonomy.createCategory({
			name: categoryName,
			vocabularyId,
		});
	}

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.publishNewTemplate({
		contentType: 'Blogs Entry',
		name: displayPageTemplateName,
	});

	await displayPageTemplatesPage.markAsDefault(displayPageTemplateName);

	const widgetId = getRandomString();

	const widgetDefinition = getWidgetDefinition({
		id: widgetId,
		widgetName:
			'com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([widgetDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	const topper = await pageEditorPage.getTopper(widgetId);

	await topper.hover();
	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		}),
		trigger: topper.locator('.portlet-options'),
	});

	const assetPublisherConfigurationIframe = await page.frameLocator(
		'iframe[title="Asset Publisher\\a      - Configuration"]'
	);

	const assetPublisherConfigurationDynamicRadio =
		assetPublisherConfigurationIframe.getByText('Dynamic', {exact: true});
	await assetPublisherConfigurationDynamicRadio.waitFor();
	if (await assetPublisherConfigurationDynamicRadio.isHidden()) {
		await assetPublisherConfigurationIframe
			.getByRole('link', {name: 'Asset Selection'})
			.click();
	}
	await assetPublisherConfigurationDynamicRadio.click();

	const assetPublisherConfigurationSourceAssetTypeSelect =
		await assetPublisherConfigurationIframe.getByLabel('Asset Type');
	if (await assetPublisherConfigurationSourceAssetTypeSelect.isHidden()) {
		await assetPublisherConfigurationIframe
			.getByRole('link', {name: 'Source'})
			.click();
	}
	await assetPublisherConfigurationSourceAssetTypeSelect.selectOption({
		label: 'Blogs Entry',
	});

	await assetPublisherConfigurationIframe
		.getByRole('button', {name: 'Save'})
		.click();

	await page.getByLabel('close', {exact: true}).click();
	await page.getByLabel('Publish', {exact: true}).click();

	await waitForSuccessAlert(
		page,
		'Success:The page was published successfully.'
	);

	await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

	const title = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content: getRandomString(),
		friendlyUrl: {categories: friendlyUrlCategories, vocabularyName},
		publish: false,
		title,
	});

	await expect(
		page.getByText(`/-/blogs/${friendlyUrlCategories.join('/')}/`)
	).toBeVisible();
});
