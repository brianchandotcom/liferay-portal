/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {CommerceDNDTablePage} from '../commerceDNDTablePage';
import {CommerceLayoutsPage} from '../commerceLayoutsPage';

export class ReturnDetailsPage extends CommerceDNDTablePage {
	readonly layoutsPage: CommerceLayoutsPage;
	readonly returnActionsButton: Locator;
	readonly returnActionsButtonViewRefunds: Locator;
	readonly page: Page;
	readonly submitReturnRequestButton: Locator;
	readonly viewRefunds: FrameLocator;
	readonly refundsTitle: Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_order_content_web_internal_portlet_CommerceReturnContentPortlet_return-items-container .dnd-table'
		);

		this.layoutsPage = new CommerceLayoutsPage(page);
		this.returnActionsButton = page.getByRole('button', {
			name: 'Actions',
		});
		this.returnActionsButtonViewRefunds = page.getByRole('menuitem', {
			name: 'View Refunds',
		});
		this.page = page;
		this.submitReturnRequestButton = page.getByRole('link', {
			name: 'Submit Return Request',
		});
		this.viewRefunds = page.frameLocator('iframe').nth(1);
		this.refundsTitle = this.viewRefunds.getByRole('heading', {
			name: 'Refunds',
		});
	}

	async goto() {
		await this.layoutsPage.goto();
	}
}
