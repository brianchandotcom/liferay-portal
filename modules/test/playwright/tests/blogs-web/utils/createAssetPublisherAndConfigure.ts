/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {AssetPublisherPage} from '../../../pages/asset-publisher-web/AssetPublisherPage';
import getRandomString from '../../../utils/getRandomString';
import {openFieldset} from '../../../utils/openFieldset';
import {waitForAlert} from '../../../utils/waitForAlert';
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
	const assetPublisherPage = new AssetPublisherPage(page);

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

	const configurationIframe = await assetPublisherPage.configurationIframe;

	await pageEditorPage.goToWidgetConfiguration(widgetId);
	await configurationIframe.locator('.portlet-body').waitFor();

	await assetPublisherPage.changeAssetSelection('Dynamic');

	await openFieldset(configurationIframe, 'Source');
	await configurationIframe.getByLabel('Asset Type').selectOption({
		label: 'Blogs Entry',
	});

	await configurationIframe.getByRole('button', {name: 'Save'}).click();
	await waitForAlert(
		configurationIframe,
		'Success:You have successfully updated the setup.'
	);
	await page.getByLabel('close', {exact: true}).click();

	await pageEditorPage.publishPage();
}
