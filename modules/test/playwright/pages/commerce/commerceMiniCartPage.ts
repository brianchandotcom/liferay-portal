/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceMiniCartPage {
	readonly cartItemActionsButton: Locator;
	readonly editMenuItem: Locator;
	readonly editOptionsLabel: Locator;
	readonly editQuantityLabel: Locator;
	readonly editUnitOfMeasureLabel: Locator;
	readonly miniCartButton: Locator;
	readonly miniCartButtonClose: Locator;
	readonly miniCartItemsContainer: Locator;
	readonly miniCartSaveButton: Locator;
	readonly miniCartUnitOfMeasureSelector: Locator;
	readonly page: Page;
	readonly editQuantitySelector: Locator;
	readonly priceField: (
		price: string,
		container?: Locator | Page
	) => Promise<Locator>;
	readonly quickAddToCartButton: Locator;
	readonly quickAddToCartSku: (sku: string) => Locator;
	readonly searchProductsInput: Locator;
	readonly showOptionsButton: Locator;
	readonly submitButton: Locator;
	readonly unitOfMeasureTableLabel: Locator;
	readonly viewDetailsButton: Locator;

	constructor(page: Page) {
		this.cartItemActionsButton = page.getByTestId('cartItemActions');
		this.editOptionsLabel = page.getByText('Edit Options', {exact: true});
		this.editQuantityLabel = page.getByText('Edit Quantity', {exact: true});
		this.editQuantitySelector = page.getByRole('spinbutton');
		this.editUnitOfMeasureLabel = page.getByText('Edit Unit of Measure', {
			exact: true,
		});
		this.editMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Edit',
		});
		this.miniCartButton = page.getByTestId('miniCartButton');
		this.miniCartButtonClose = page.locator('.mini-cart-close');
		this.miniCartItemsContainer = page.locator('div.mini-cart-cart-items');
		this.miniCartSaveButton = page.getByRole('button', {
			exact: true,
			name: 'Save',
		});
		this.miniCartUnitOfMeasureSelector = page.locator(
			'select[name="minicart-uom-selector"]'
		);
		this.priceField = async (price: string, container = this.page) => {
			return container.getByText(price);
		};
		this.quickAddToCartButton = page.getByTestId('quickAddToCartButton');
		this.quickAddToCartSku = (sku) =>
			page.getByRole('menuitem', {name: sku});
		this.searchProductsInput = page.getByPlaceholder('Search Products');
		this.showOptionsButton = page.getByRole('button', {
			exact: true,
			name: 'Show Options',
		});
		this.submitButton = page.getByRole('button', {name: 'Submit'});
		this.unitOfMeasureTableLabel = page.getByText('Unit of Measure Table', {
			exact: true,
		});
		this.viewDetailsButton = page.getByRole('button', {
			exact: true,
			name: 'View Details',
		});
	}

	async quickAddToCart(sku: string) {
		await this.miniCartButton.click();
		await this.searchProductsInput.fill(sku);
		await this.quickAddToCartSku(sku).waitFor({state: 'visible'});
		await this.quickAddToCartSku(sku).click();
		await this.quickAddToCartButton.click();
	}
}
