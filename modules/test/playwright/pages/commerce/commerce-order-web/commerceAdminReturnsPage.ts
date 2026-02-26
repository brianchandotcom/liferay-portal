/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminReturnsPage extends CommerceDNDTablePage {
	readonly editReturnItemFrame: FrameLocator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly returnActionsButton: Locator;
	readonly returnActionsEditButton: Locator;
	readonly returnItemsCommentInput: Locator;
	readonly returnItemsCommentTitle: Locator;
	readonly returnItemsSubmitButton: Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_order_web_internal_portlet_CommerceReturnPortlet_fm .fds table'
		);
		this.globalMenuPage = new GlobalMenuPage(page);
		this.editReturnItemFrame = page.frameLocator('iframe');
		this.returnActionsButton = page.getByRole('button', {
			name: 'Actions',
		});
		this.returnActionsEditButton = page.getByRole('menuitem', {
			name: 'Edit',
		});
		this.returnItemsCommentInput =
			this.editReturnItemFrame.getByPlaceholder(
				'Type your comment here.'
			);
		this.returnItemsCommentTitle =
			this.editReturnItemFrame.getByText('Comments');
		this.returnItemsSubmitButton = this.editReturnItemFrame.getByRole(
			'button',
			{name: 'Save'}
		);
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Returns');
	}
}
