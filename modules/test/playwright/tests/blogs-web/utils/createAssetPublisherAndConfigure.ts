/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {waitForSuccessAlert} from '../../../utils/waitForSuccessAlert';
import getPageDefinition from '../../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/utils/getWidgetDefinition';

import type {ApiHelpers} from '../../../helpers/ApiHelpers';
import type {PageEditorPage} from '../../../pages/layout-content-page-editor-web/PageEditorPage';

export async function createAssetPublisherAndConfigure({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}: {
	apiHelpers: ApiHelpers;
	page: Page;
	pageEditorPage: PageEditorPage;
	site: Site;
}) {
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

	const topper = pageEditorPage.getTopper(widgetId);

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
}
