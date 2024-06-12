/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceLayoutsPage} from '../commerceLayoutsPage';

export class ProductDetailsPage {
	readonly downloadSampleField: (
		downloadSampleText: string
	) => Promise<Locator>;
	readonly fullDescriptionField: (
		fullDescription: string
	) => Promise<Locator>;
	readonly gtinField: (gtin: string) => Promise<Locator>;
	readonly layoutsPage: CommerceLayoutsPage;
	readonly mpnField: (mpn: string) => Promise<Locator>;
	readonly optionSelector: (optionName: string) => Promise<Locator>;
	readonly page: Page;
	readonly pageTitle: Locator;
	readonly priceField: (price: string) => Promise<Locator>;
	readonly promoPriceField: (promoPrice: string) => Promise<Locator>;
	readonly selectOption: (
		optionLabel: string,
		optionName: string
	) => Promise<string[]>;
	readonly shortDescriptionField: (
		shortDescription: string
	) => Promise<Locator>;
	readonly skuField: (sku: string) => Promise<Locator>;
	readonly uomTable: (uomTableCell: string) => Promise<Locator>;
	readonly viewButton: Locator;

	constructor(page: Page) {
		this.downloadSampleField = async (downloadSampleText: string) => {
			return page.getByRole('link', {name: downloadSampleText});
		};
		this.fullDescriptionField = async (fullDescription: string) => {
			return page.getByText(fullDescription, {exact: true});
		};
		this.gtinField = async (gtin: string) => {
			return page.getByText(gtin);
		};
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.mpnField = async (mpn: string) => {
			return page.getByText(mpn, {exact: true});
		};
		this.optionSelector = async (optionName: string) => {
			return page.getByLabel(optionName);
		};
		this.page = page;
		this.priceField = async (price: string) => {
			return page.getByText(price);
		};
		this.promoPriceField = async (promoPrice: string) => {
			return page.getByText(promoPrice);
		};
		this.selectOption = (optionLabel: string, optionName: string) =>
			page.getByLabel(optionName).selectOption({label: optionLabel});
		this.shortDescriptionField = async (shortDescription: string) => {
			return page.getByText(shortDescription);
		};
		this.skuField = async (sku: string) => {
			return page.getByText(sku);
		};
		this.uomTable = async (cellValue: string) => {
			return page.getByRole('cell', {name: cellValue});
		};
		this.viewButton = page.getByLabel('View');
	}

	async addProductDetailsWidget() {
		await this.layoutsPage.addWidgetToPage('Product Details');
	}

	async goto() {
		await this.layoutsPage.goto();
	}
}
