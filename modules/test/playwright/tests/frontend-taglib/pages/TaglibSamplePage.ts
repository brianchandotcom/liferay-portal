/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/utils/getWidgetDefinition';

export class TaglibSamplePage {
	readonly page: Page;
	readonly tablist: Locator;

	constructor(page: Page) {
		this.page = page;
		this.tablist = page.getByRole('tablist');
	}

	async selectTab(tabName: string) {
		const tabHeading = this.tablist.getByText(tabName);

		await expect(tabHeading).toBeInViewport();

		await tabHeading.click();
	}

	async setupTaglibSampleWidget({apiHelpers, site}) {
		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName: 'com_liferay_sample_web_portlet_TaglibSamplePortlet',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await this.page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);
	}
}