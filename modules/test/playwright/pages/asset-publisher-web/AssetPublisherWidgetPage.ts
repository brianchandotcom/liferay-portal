/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../helpers/ApiHelpers';
import getRandomString from '../../utils/getRandomString';
import {WidgetPagePage} from '../layout-admin-web/WidgetPagePage';
export class AssetPublisherWidgetPage {
	readonly apiHelpers: ApiHelpers;
	readonly page: Page;
	readonly widgetPagePage: WidgetPagePage;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.page = page;
		this.widgetPagePage = new WidgetPagePage(page);
	}

	async addAssetPublisherPortlet(site: Site) {
		const layout = await this.apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await this.page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyURL}`
		);

		await this.widgetPagePage.addPortlet('Asset Publisher');

		return layout;
	}
}
