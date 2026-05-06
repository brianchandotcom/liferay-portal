/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminDiscountsPage extends CommerceDNDTablePage {
	readonly addDiscountButton: Locator;
	readonly addDiscountModal: FrameLocator;
	readonly addDiscountModalApplyToSelect: Locator;
	readonly addDiscountModalHeading: Locator;
	readonly addDiscountModalNameInput: Locator;
	readonly addDiscountModalSubmitButton: Locator;
	readonly addDiscountModalTypeSelect: Locator;
	readonly discountLink: (name: string) => Locator;
	readonly discountsHeading: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;
	readonly tableRowAt: (rowIndex: number) => Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_pricing_web_internal_portlet_CommerceDiscountPortlet_fm .fds table'
		);
		this.addDiscountButton = page
			.getByTestId('managementToolbar')
			.locator('[data-testid="fdsCreationActionButton"]')
			.or(
				page
					.getByRole('button', {exact: true, name: 'Add Discount'})
					.first()
			)
			.or(page.getByRole('button', {exact: true, name: 'Add'}).first());
		this.addDiscountModal = page.frameLocator(
			'iframe[src*="add_commerce_discount"]'
		);
		this.addDiscountModalHeading = page
			.locator('.modal-title')
			.filter({hasText: 'Add Discount'});
		this.addDiscountModalNameInput =
			this.addDiscountModal.getByLabel('Name');
		this.addDiscountModalTypeSelect =
			this.addDiscountModal.getByLabel('Type');
		this.addDiscountModalApplyToSelect =
			this.addDiscountModal.getByLabel('Apply To');
		this.addDiscountModalSubmitButton = this.addDiscountModal.getByRole(
			'button',
			{exact: true, name: 'Submit'}
		);
		this.discountLink = (name: string) =>
			page.getByRole('link', {name}).first();
		this.discountsHeading = page.getByRole('heading', {
			exact: true,
			name: 'Discounts',
		});
		this.globalMenuPage = new GlobalMenuPage(page);
		this.page = page;
		this.tableRowAt = (rowIndex: number) =>
			this.table.locator('tbody tr').nth(rowIndex);
	}

	async enterPromoCodeToWidget(promoCode: string) {
		await this.page.getByPlaceholder('Enter Promo Code').fill(promoCode);
		await this.page.getByPlaceholder('Enter Promo Code').waitFor();
		await this.page
			.getByRole('button', {exact: true, name: 'Apply'})
			.click();
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Discounts');
	}
}
