/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class CommerceThemeMiniumCatalogPage {
	readonly configurationIFrame: FrameLocator;
	readonly configurationIFrameCloseButton: Locator;
	readonly configurationIFrameDefaultSortingDropdownMenu: Locator;
	readonly configurationIFrameSaveButton: Locator;
	readonly configurationMenuItem: Locator;
	readonly firstCardItem: Locator;
	readonly optionsButton: Locator;
	readonly orderByButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.configurationIFrame = page.frameLocator(
			'iframe[id="modalIframe"]'
		);
		this.configurationIFrameCloseButton =
			this.configurationIFrame.getByRole('button', {name: 'Close'});
		this.configurationIFrameDefaultSortingDropdownMenu =
			this.configurationIFrame.getByLabel('Default Sort');
		this.configurationIFrameSaveButton = this.configurationIFrame.getByRole(
			'button',
			{name: 'Save'}
		);
		this.configurationMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.firstCardItem = page.locator('.product-card').first();
		this.optionsButton = page
			.locator(
				'[id^="portlet_com_liferay_commerce_product_content_search_web_internal_portlet_CPSortPortlet"]'
			)
			.getByTitle('Options');
		this.orderByButton = page.locator('#commerce-order-by');
		this.page = page;
	}

	async selectSorting(orderByText: string) {
		await this.orderByButton.click();
		const orderByLink = this.page.getByText(orderByText);
		await orderByLink.click();
		await this.page.waitForTimeout(1000);
	}
}
