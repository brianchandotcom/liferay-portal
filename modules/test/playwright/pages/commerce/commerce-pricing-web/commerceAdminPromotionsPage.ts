/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminPromotionsPage extends CommerceDNDTablePage {
	readonly addPromotionButton: Locator;
	readonly addPromotionModal: FrameLocator;
	readonly addPromotionModalCatalogSelect: Locator;
	readonly addPromotionModalCurrencySelect: Locator;
	readonly addPromotionModalNameInput: Locator;
	readonly addPromotionModalSubmitButton: Locator;
	readonly deleteMenuItem: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;
	readonly promotionLink: (name: string) => Locator;
	readonly promotionRowActions: (name: string) => Promise<Locator>;
	readonly promotionRowActionsButton: (name: string) => Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_pricing_web_internal_portlet_CommercePromotionPortlet_fm .fds table'
		);

		this.addPromotionButton = page
			.getByTestId('managementToolbar')
			.locator('[data-testid="fdsCreationActionButton"]')
			.or(
				page
					.getByRole('button', {exact: true, name: 'Add Promotion'})
					.first()
			)
			.or(page.getByRole('button', {exact: true, name: 'Add'}).first());
		this.addPromotionModal = page.frameLocator(
			'iframe[src*="add_commerce_price_list"]'
		);
		this.addPromotionModalCatalogSelect =
			this.addPromotionModal.getByLabel('Catalog');
		this.addPromotionModalCurrencySelect =
			this.addPromotionModal.getByLabel('Currency');
		this.addPromotionModalNameInput =
			this.addPromotionModal.getByLabel('Name');
		this.addPromotionModalSubmitButton = this.addPromotionModal.getByRole(
			'button',
			{exact: true, name: 'Submit'}
		);
		this.deleteMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.globalMenuPage = new GlobalMenuPage(page);
		this.page = page;
		this.promotionLink = (name: string) =>
			page.getByRole('link', {name}).first();
		this.promotionRowActions = async (name: string) => {
			const result = await this.tableRow(0, name, true);

			return result.row.getByRole('button', {name: 'Actions'});
		};
		this.promotionRowActionsButton = (name: string) =>
			page.getByRole('row').filter({hasText: name}).getByRole('button');
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Promotions');
	}
}
