/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

export class CommerceThemeMiniumCatalogPage {
	readonly catalogSearch: Locator;
	readonly clearSearchButton: Locator;
	readonly configurationIFrame: FrameLocator;
	readonly configurationIFrameCloseButton: Locator;
	readonly configurationIFrameDefaultSortingDropdownMenu: Locator;
	readonly configurationIFrameSaveButton: Locator;
	readonly configurationMenuItem: Locator;
	readonly firstCardItem: Locator;
	readonly globalSearchBarButton: Locator;
	readonly globalSearchBarInput: Locator;
	readonly globalSearchBarCommerceItemLink: (text: string) => Locator;
	readonly globalSearchBarCommerceOrderLink: (
		orderId: string,
		accountName: string
	) => Locator;
	readonly quantitySelector: (targetLocator: Locator) => Locator;
	readonly optionsButton: Locator;
	readonly orderByButton: Locator;
	readonly page: Page;
	readonly popOverMessage: (popOverMessage: string) => Locator;
	readonly productCard: (productName: string) => Locator;
	readonly productCardAddToCartButton: (productName: string) => Locator;
	readonly productLink: (productName: string) => Locator;

	constructor(page: Page) {
		this.catalogSearch = page.getByTestId('searchInput');
		this.clearSearchButton = page.getByRole('button', {
			name: 'Clear Search',
		});
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
		this.globalSearchBarButton = page
			.locator('.commerce-topbar-button__icon')
			.first();
		this.globalSearchBarInput = page
			.locator('#search-bar')
			.getByPlaceholder('Search');
		this.globalSearchBarCommerceItemLink = (text) =>
			page.getByRole('link', {name: text});
		this.globalSearchBarCommerceOrderLink = (
			orderId: string,
			accountName: string
		) =>
			page
				.getByRole('link', {name: orderId})
				.filter({hasText: accountName});
		this.quantitySelector = (targetLocator: Locator) =>
			targetLocator.getByRole('spinbutton');
		this.optionsButton = page
			.locator(
				'[id^="portlet_com_liferay_commerce_product_content_search_web_internal_portlet_CPSortPortlet"]'
			)
			.getByTitle('Options');
		this.orderByButton = page.locator('#commerce-order-by');
		this.page = page;
		this.popOverMessage = (popOverMessage: string) =>
			this.page
				.locator('.popover-body')
				.getByText(popOverMessage, {exact: true});
		this.productCard = (productName: string) =>
			this.page.locator('.product-card').filter({hasText: productName});
		this.productCardAddToCartButton = (productName: string) =>
			this.productCard(productName).getByRole('button', {
				exact: true,
				name: 'Add to Cart',
			});
		this.productLink = (productName: string) =>
			this.page.getByRole('link', {
				exact: true,
				name: productName,
			});
	}

	async checkQuantitiesInPopOverMessages(
		minQuantity: string,
		maxQuantity: string,
		multipleQuantity: string
	) {
		await expect(
			this.popOverMessage('Min quantity per order is ' + minQuantity)
		).toBeVisible();
		await expect(
			this.popOverMessage(
				'Maximum quantity per order is ' + maxQuantity + '.'
			)
		).toBeVisible();
		await expect(
			this.popOverMessage(
				'Quantity must be a multiple of ' + multipleQuantity
			)
		).toBeVisible();
	}

	async selectSorting(orderByText: string) {
		await this.orderByButton.click();
		const orderByLink = this.page.getByText(orderByText);
		await orderByLink.click();
		await this.page.waitForTimeout(1000);
	}

	async search(query: string) {
		await this.globalSearchBarInput.waitFor({state: 'visible'});
		await this.globalSearchBarInput.fill(query);
	}

	async focusGlobalSearchBarInput() {
		await expect(this.globalSearchBarButton).toBeAttached();
		await this.globalSearchBarButton.click();
	}
}
