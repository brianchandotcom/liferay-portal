/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {assetPublisherPagesTest} from '../../fixtures/assetPublisherPagesTest';
import {collectionsPagesTest} from '../../fixtures/collectionsPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';

const test = mergeTests(
	assetPublisherPagesTest,
	apiHelpersTest,
	collectionsPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test(
	'Create manual collection from asset publisher configuration',
	{
		tag: '@LPD-32724',
	},
	async ({
		apiHelpers,
		assetPublisherPage,
		collectionsPage,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create a page with an Asset Publisher Widget

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

		// Access to the configuration of the widget from the page editor

		await pageEditorPage.goToWidgetConfiguration(layout, site, widgetId);

		// Set asset selection and create a manual collection

		const collectionName = getRandomString();

		await assetPublisherPage.changeAssetSelection('Manual');

		await assetPublisherPage.createCollectionFromAssetPublisher(
			collectionName
		);

		// Check that the collection has been created correctly

		await collectionsPage.goto(site.friendlyUrlPath);

		await expect(page.getByText(collectionName)).toBeVisible();
	}
);

test(
	'Create dynamic collection from asset publisher configuration',
	{
		tag: '@LPD-32724',
	},
	async ({
		apiHelpers,
		assetPublisherPage,
		collectionsPage,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create a page with an Asset Publisher Widget

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

		// Access to the configuration of the widget from the page editor

		await pageEditorPage.goToWidgetConfiguration(layout, site, widgetId);

		// Set asset selection and create a manual collection

		const collectionName = getRandomString();

		await assetPublisherPage.changeAssetSelection('Dynamic');

		await assetPublisherPage.createCollectionFromAssetPublisher(
			collectionName
		);

		// Check that the collection has been created correctly

		await collectionsPage.goto(site.friendlyUrlPath);

		await expect(page.getByText(collectionName)).toBeVisible();
	}
);
