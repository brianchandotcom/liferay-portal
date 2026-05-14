/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminPriceListsPage extends CommerceDNDTablePage {
	readonly addPriceListButton: Locator;
	readonly addPriceListModal: FrameLocator;
	readonly addPriceListModalCatalogSelect: Locator;
	readonly addPriceListModalCurrencySelect: Locator;
	readonly addPriceListModalNameInput: Locator;
	readonly addPriceListModalSubmitButton: Locator;
	readonly deleteMenuItem: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;
	readonly priceListLink: (name: string) => Locator;
	readonly priceListRowActions: (name: string) => Promise<Locator>;
	readonly priceListRowActionsButton: (name: string) => Locator;
	readonly requiredFieldError: (fieldLabel: string) => Locator;
	readonly sidebarMenuItem: (name: string) => Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_pricing_web_internal_portlet_CommercePriceListPortlet_fm .fds table'
		);

		this.addPriceListButton = page
			.getByTestId('managementToolbar')
			.locator('[data-testid="fdsCreationActionButton"]')
			.or(
				page
					.getByRole('button', {exact: true, name: 'Add Price List'})
					.first()
			)
			.or(page.getByRole('button', {exact: true, name: 'Add'}).first());
		this.addPriceListModal = page.frameLocator(
			'iframe[src*="add_commerce_price_list"]'
		);
		this.addPriceListModalCatalogSelect =
			this.addPriceListModal.getByLabel('Catalog');
		this.addPriceListModalCurrencySelect =
			this.addPriceListModal.getByLabel('Currency');
		this.addPriceListModalNameInput =
			this.addPriceListModal.getByLabel('Name');
		this.addPriceListModalSubmitButton = this.addPriceListModal.getByRole(
			'button',
			{
				exact: true,
				name: 'Submit',
			}
		);
		this.deleteMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.globalMenuPage = new GlobalMenuPage(page);
		this.page = page;
		this.priceListLink = (name: string) =>
			page.getByRole('link', {name}).first();
		this.priceListRowActions = async (name: string) => {
			const result = await this.tableRow(0, name, true);

			return result.row.getByRole('button', {name: 'Actions'});
		};
		this.priceListRowActionsButton = (name: string) =>
			page.getByRole('row').filter({hasText: name}).getByRole('button');
		this.requiredFieldError = (fieldLabel: string) =>
			this.addPriceListModal.getByText(
				`The ${fieldLabel} field is required.`,
				{exact: false}
			);
		this.sidebarMenuItem = (name: string) =>
			page.getByRole('menuitem', {exact: true, name});
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Price Lists');
	}
}
