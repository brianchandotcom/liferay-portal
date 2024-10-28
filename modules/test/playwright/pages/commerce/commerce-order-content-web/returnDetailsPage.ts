/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {CommerceDNDTablePage} from '../commerceDNDTablePage';
import {CommerceLayoutsPage} from './commerceLayoutsPage';

export class ReturnDetailsPage extends CommerceDNDTablePage {
	readonly layoutsPage: CommerceLayoutsPage;
	readonly page: Page;
	readonly returnActionsButton: Locator;
	readonly returnActionsViewDetailsButton: Locator;
	readonly returnActionsViewRefundsButton: Locator;
	readonly submitReturnRequestButton: Locator;
	readonly submitReturnRequestLink: Locator;
	readonly viewDetailsFrame: FrameLocator;
	readonly viewDetailsCommentInput: Locator;
	readonly viewDetailsSubmitButton: Locator;
	readonly viewDetailsTitle: Locator;
	readonly viewRefundsFrame: FrameLocator;
	readonly viewRefundsTitle: Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_order_content_web_internal_portlet_CommerceReturnContentPortlet_return-items-container .dnd-table'
		);

		this.layoutsPage = new CommerceLayoutsPage(page);
		this.page = page;
		this.returnActionsButton = page.getByRole('button', {
			name: 'Actions',
		});
		this.returnActionsViewDetailsButton = page.getByRole('menuitem', {
			name: 'View Details',
		});
		this.returnActionsViewRefundsButton = page.getByRole('menuitem', {
			name: 'View Refunds',
		});
		this.submitReturnRequestButton = page.getByRole('link', {
			name: 'Submit Return Request',
		});
		this.viewDetailsFrame = page.frameLocator('iframe');
		this.viewDetailsCommentInput = this.viewDetailsFrame.getByPlaceholder(
			'Type your comment here.'
		);
		this.viewDetailsSubmitButton = this.viewDetailsFrame.getByRole(
			'button',
			{name: 'Save'}
		);
		this.viewDetailsTitle = this.viewDetailsFrame.getByText('Details');
		this.viewRefundsFrame = page.frameLocator('iframe').nth(1);
		this.viewRefundsTitle = this.viewRefundsFrame.getByRole('heading', {
			name: 'Refunds',
		});
		this.submitReturnRequestLink = page.getByRole('link', {
			exact: true,
			name: 'Submit Return Request',
		});
	}

	async goto() {
		await this.layoutsPage.goto();
	}
}
