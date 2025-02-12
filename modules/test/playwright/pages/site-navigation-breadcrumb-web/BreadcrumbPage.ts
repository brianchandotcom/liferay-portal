/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Page, expect} from '@playwright/test';

import {WidgetPagePage} from '../layout-admin-web/WidgetPagePage';

export class BreadcrumbPage {
	readonly page: Page;
	readonly widgetPagePage: WidgetPagePage;

	constructor(page: Page) {
		this.page = page;
		this.widgetPagePage = new WidgetPagePage(page);
	}

	async assertBreadcrumbEntries(
		expectedLength: number,
		expectedValues: string[],
		parent: Page | FrameLocator = this.page
	) {
		const breadcrumbEntries = await parent
			.locator(
				'[id^="_com_liferay_site_navigation_breadcrumb_web_portlet_SiteNavigationBreadcrumbPortlet_INSTANCE_"] .breadcrumb-text-truncate'
			)
			.allInnerTexts();

		await expect(breadcrumbEntries.length).toBe(expectedLength);

		await expect(breadcrumbEntries).toEqual(
			expect.arrayContaining(expectedValues)
		);
	}

	async toggleBreadcrumbConfiguration(configuration: string) {
		await this.widgetPagePage.clickOnAction('Breadcrumb', 'Configuration');

		const configurationIFrame = this.page.frameLocator(
			'iframe[title*="Breadcrumb"]'
		);

		await configurationIFrame.getByLabel(configuration).click();

		await this.widgetPagePage.saveAndClose('Breadcrumb');
	}
}
