/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {CommerceDNDTablePage} from '../commerceDNDTablePage';
import {CommerceLayoutsPage} from './commerceLayoutsPage';

export class PlacedOrdersPage extends CommerceDNDTablePage {
	readonly commerceBillingAddress: Locator;
	readonly configurationIFrame: FrameLocator;
	readonly configurationIFrameSaveButton: Locator;
	readonly configurationIFrameShowFullAddressToggle: Locator;
	readonly configurationIFrameShowPhoneNumberToggle: Locator;
	readonly configurationMenuItem: Locator;
	readonly layoutsPage: CommerceLayoutsPage;
	readonly optionsButton: Locator;
	readonly orderAccountName: (accountName: string) => Locator;
	readonly orderItemActionsButton: Locator;
	readonly orderItemActionsButtonEdit: Locator;
	readonly page: Page;
	readonly pageLabel: Locator;
	readonly pageTitle: Locator;
	readonly panelList: Locator;
	readonly placedOrdersTable: Locator;
	readonly placedOrderTableOrderDate: (orderDate: string) => Locator;
	readonly placedOrderTableViewButton: Locator;
	readonly searchButton: Locator;
	readonly searchInput: Locator;
	readonly commerceShippingAddress: Locator;
	readonly viewButton: Locator;

	constructor(page: Page) {
		super(
			page,
			'#portlet_com_liferay_commerce_order_content_web_internal_portlet_CommerceOrderContentPortlet .fds table'
		);

		this.commerceBillingAddress = page.getByTestId(
			'commerceBillingAddress'
		);
		this.configurationIFrame = page.frameLocator(
			'iframe[id="modalIframe"]'
		);
		this.configurationIFrameSaveButton = this.configurationIFrame.getByRole(
			'button',
			{name: 'Save'}
		);
		this.configurationIFrameShowFullAddressToggle =
			this.configurationIFrame.getByLabel('Show Order Full Address');
		this.configurationIFrameShowPhoneNumberToggle =
			this.configurationIFrame.getByLabel('Show Order Phone Number');

		this.configurationMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.optionsButton = page
			.locator(
				'#portlet_com_liferay_commerce_order_content_web_internal_portlet_CommerceOrderContentPortlet'
			)
			.getByLabel('Options');
		this.orderAccountName = (accountName: string) =>
			page.getByText(accountName);
		this.orderItemActionsButton = page.getByRole('button', {
			name: 'Actions',
		});
		this.orderItemActionsButtonEdit = page.getByRole('menuitem', {
			name: 'Edit',
		});
		this.page = page;
		this.pageLabel = page
			.getByTestId('layoutHref')
			.getByLabel('Placed Orders Page');
		this.pageTitle = page
			.getByTestId('headerTitle')
			.filter({hasText: 'Placed Orders Page'});
		this.panelList = page
			.getByTestId('specificationFacetPanel')
			.getByRole('button');
		this.placedOrdersTable = page.locator(
			'#portlet_com_liferay_commerce_order_content_web_internal_portlet_CommerceOrderContentPortlet .fds table'
		);
		this.placedOrderTableOrderDate = (orderDate) =>
			this.placedOrdersTable.getByText(orderDate);
		this.placedOrderTableViewButton =
			this.placedOrdersTable.getByLabel('View');
		this.searchButton = page.getByRole('button', {name: 'Search'});
		this.searchInput = page.getByPlaceholder('Search');
		this.commerceShippingAddress = page.getByTestId(
			'commerceShippingAddress'
		);
		this.viewButton = page.getByLabel('View');
	}

	async addPlacedOrdersWidget() {
		await this.layoutsPage.addWidgetToPage('Placed Orders');
	}

	async goto() {
		await this.layoutsPage.goto();
	}
}
