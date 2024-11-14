/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../../utils/waitForAlert';
import {CommerceLayoutsPage} from '../commerce-order-content-web/commerceLayoutsPage';

export class ProductDetailsPage {
	readonly addToCartButton: Locator;
	readonly addSpecification: Locator;
	readonly addSpecificationFrame: FrameLocator;
	readonly attachments: Locator;
	readonly attachmentItem: (title: string) => Promise<Locator>;
	readonly attachmentItems: Locator;
	readonly checkSpecificationProduct: (text: string) => Promise<Locator>;
	readonly closeEditFrame: Locator;
	readonly createNewSpecificationProduct: Locator;
	readonly createNewValueSpecificationProduct: Locator;
	readonly diagramPin: (pinSequence: string) => Promise<Locator>;
	readonly downloadAttachmentLink: Locator;
	readonly downloadSampleField: (
		downloadSampleText: string
	) => Promise<Locator>;
	readonly dropdownProductSpecification: (
		chooseAddOrCreate: string
	) => Promise<Locator>;
	readonly editFrameSpecificationProduct: (
		specificationValue: string
	) => Promise<string[]>;
	readonly ellipsisProductSpecification: Locator;
	readonly ellipsisFrameProductSpecification: FrameLocator;
	readonly frameChooseSpecification: (
		specificationName: string
	) => Promise<Locator>;
	readonly frameChooseSpecificationValue: (
		specificationValue: string
	) => Promise<string[]>;
	readonly frameSubmitSpecification: Locator;
	readonly frameDropdownSpecification: Locator;
	readonly fullDescriptionField: (
		fullDescription: string
	) => Promise<Locator>;
	readonly gtinField: (gtin: string) => Promise<Locator>;
	readonly layoutsPage: CommerceLayoutsPage;
	readonly mappedProductAddToCartButton: Locator;
	readonly mappedProductCheckbox: Locator;
	readonly mpnField: (mpn: string) => Promise<Locator>;
	readonly menuItemSpecification: (
		chooseAddOrCreate: string
	) => Promise<Locator>;
	readonly nameField: (name: string) => Promise<Locator>;
	readonly optionSelector: (optionName: string) => Promise<Locator>;
	readonly page: Page;
	readonly pageTitle: Locator;
	readonly pinAddToCartButton: Locator;
	readonly priceContainer: Locator;
	readonly priceField: (
		price: string,
		container?: Locator | Page
	) => Promise<Locator>;
	readonly productNameHeading: (productName: string) => Promise<Locator>;
	readonly promoPriceField: (
		promoPrice: string,
		container?: Locator | Page
	) => Promise<Locator>;
	readonly saveButtonEditFrame: Locator;
	readonly selectOption: (
		optionLabel: string,
		optionName: string
	) => Promise<string[]>;
	readonly shortDescriptionField: (
		shortDescription: string
	) => Promise<Locator>;
	readonly skuField: (sku: string) => Promise<Locator>;
	readonly uomCombobox: Locator;
	readonly uomTable: (uomTableCell: string) => Promise<Locator>;
	readonly viewButton: Locator;
	readonly waitForEditScuccessMessage: Locator;

	constructor(page: Page) {
		this.addToCartButton = page
			.getByRole('button', {exact: true, name: 'Add to Cart'})
			.first();
		this.addSpecification = page
			.getByTestId('management-toolbar')
			.locator('[data-testid="fdsCreationActionButton"]');
		this.addSpecificationFrame = page.frameLocator('iframe >> nth=2');
		this.attachments = page.locator(
			'#_com_liferay_commerce_product_content_web_internal_portlet_CPContentPortlet_navCPMedia'
		);
		this.attachmentItem = async (title: string) => {
			return page.getByText(title);
		};
		this.attachmentItems = this.attachments.locator('li.list-group-item');
		this.checkSpecificationProduct = async (text: string) => {
			return page.getByText(text);
		};
		this.closeEditFrame = page
			.frameLocator('iframe >> nth=1')
			.getByRole('button')
			.first();
		this.createNewSpecificationProduct =
			this.addSpecificationFrame.getByPlaceholder('Specification');
		this.createNewValueSpecificationProduct = this.addSpecificationFrame
			.getByRole('textbox')
			.nth(1);
		this.diagramPin = async (pinSequence: string) => {
			return page
				.locator("[class='pin-node-text']")
				.filter({hasText: pinSequence});
		};
		this.downloadAttachmentLink = page.getByRole('link', {
			exact: true,
			name: 'Download',
		});
		this.ellipsisProductSpecification = page.getByRole('button', {
			name: 'Actions',
		});
		this.ellipsisFrameProductSpecification =
			page.frameLocator('iframe >> nth=1');
		this.editFrameSpecificationProduct = async (
			specificationValue: string
		) => {
			return this.ellipsisFrameProductSpecification
				.locator(
					'select[name="_com_liferay_commerce_product_definitions_web_internal_portlet_CPDefinitionsPortlet_listTypeEntriesSelect"]'
				)
				.selectOption(specificationValue);
		};
		this.frameChooseSpecification = async (specificationName: string) => {
			return this.addSpecificationFrame.getByRole('option', {
				name: specificationName,
			});
		};
		this.frameChooseSpecificationValue = async (
			specificationValue: string
		) => {
			return this.addSpecificationFrame
				.locator('select[name="listTypeEntriesSelect"]')
				.selectOption(specificationValue);
		};
		this.frameSubmitSpecification = this.addSpecificationFrame.getByRole(
			'button',
			{name: 'Submit'}
		);
		this.downloadSampleField = async (downloadSampleText: string) => {
			return page.getByRole('link', {name: downloadSampleText});
		};
		this.dropdownProductSpecification = async (
			chooseEditOrDelete: string
		) => {
			return page.getByRole('menuitem', {name: chooseEditOrDelete});
		};
		this.fullDescriptionField = async (fullDescription: string) => {
			return page.getByText(fullDescription, {exact: true});
		};
		this.gtinField = async (gtin: string) => {
			return page.getByText(gtin);
		};
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.mappedProductAddToCartButton = page.getByRole('button', {
			name: 'Add Selected Product(s) to',
		});
		this.mappedProductCheckbox = page.getByLabel('Select SKU');
		this.mpnField = async (mpn: string) => {
			return page.getByText(mpn, {exact: true});
		};
		this.menuItemSpecification = async (chooseAddOrCreate: string) => {
			return page.getByRole('menuitem', {name: chooseAddOrCreate});
		};
		this.nameField = async (name: string) => {
			return page.getByRole('heading', {name});
		};
		this.optionSelector = async (optionName: string) => {
			return page.getByLabel(optionName);
		};
		this.page = page;
		this.pinAddToCartButton = page
			.locator("[class='diagram-tooltip']")
			.getByRole('button');
		this.priceContainer = page.locator('div.price-container');
		this.priceField = async (price: string, container = this.page) => {
			return container.getByText(price);
		};
		this.productNameHeading = async (productName) => {
			return page.getByRole('heading', {name: productName});
		};
		this.promoPriceField = async (
			promoPrice: string,
			container = this.page
		) => {
			return container.getByText(promoPrice);
		};
		this.saveButtonEditFrame =
			this.ellipsisFrameProductSpecification.getByRole('button', {
				name: 'Save',
			});
		this.selectOption = (optionLabel: string, optionName: string) =>
			page.getByLabel(optionName).selectOption({label: optionLabel});
		this.frameDropdownSpecification = this.addSpecificationFrame.getByLabel(
			'SpecificationRequired'
		);
		this.shortDescriptionField = async (shortDescription: string) => {
			return page.getByText(shortDescription);
		};
		this.skuField = async (sku: string) => {
			return page.getByText(sku);
		};
		this.uomCombobox = page.getByRole('combobox', {exact: true});
		this.uomTable = async (cellValue: string) => {
			return page.getByRole('cell', {name: cellValue});
		};
		this.viewButton = page.getByLabel('View');
		this.waitForEditScuccessMessage =
			this.ellipsisFrameProductSpecification.getByText(
				'Success:Your request completed successfully.'
			);
	}

	async addProductDetailsWidget() {
		await this.layoutsPage.addWidgetToPage('Product Details');
		await waitForAlert(
			this.page,
			'Success:The application was added to the page.'
		);
	}

	async addSpecificationToProduct(
		chooseAddOrEdit: string,
		specificationName: string,
		specificationValue: string
	) {
		await this.addSpecification.click();
		(await this.menuItemSpecification(chooseAddOrEdit)).click();
		await this.frameDropdownSpecification.click();
		(await this.frameChooseSpecification(specificationName)).click();
		await this.frameChooseSpecificationValue(specificationValue);
		await this.frameSubmitSpecification.click();
	}

	async changeValueInProductSpecification(
		chooseAddOrCreate: string,
		specificationValue: string
	) {
		this.ellipsisProductSpecification.click();
		(await this.dropdownProductSpecification(chooseAddOrCreate)).click();
		await this.editFrameSpecificationProduct(specificationValue);
		this.saveButtonEditFrame.click();
	}

	async createSpecificationProduct(
		chooseAddOrCreate: string,
		specificationName: string,
		specificationValue: string
	) {
		await this.addSpecification.click();
		(await this.menuItemSpecification(chooseAddOrCreate)).click();
		await this.createNewSpecificationProduct.fill(specificationName);
		await this.createNewValueSpecificationProduct.fill(specificationValue);
		await this.frameSubmitSpecification.click();
	}

	async goto() {
		await this.layoutsPage.goto();
	}
}
