/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/utils/getWidgetDefinition';

export class TaglibSamplePage {
	readonly apiHelpers: ApiHelpers;
	readonly page: Page;
	readonly linkList: Locator;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.page = page;
		this.linkList = page.getByRole('link');
	}

	async selectLink(tabName: string) {
		const linkHeading = this.linkList.getByText(tabName);

		await expect(linkHeading).toBeInViewport();

		await linkHeading.click();
	}

	async setupTaglibSampleWidget({site}) {
		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName: 'com_liferay_sample_web_portlet_SamplePortlet',
		});

		const layout = await this.apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await this.page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);
	}
}
