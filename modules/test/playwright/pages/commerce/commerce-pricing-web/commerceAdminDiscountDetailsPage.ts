/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminDiscountDetailsPage extends CommerceDNDTablePage {
	readonly addDiscountRuleButton: Locator;
	readonly amountFieldReuiredErrorMessage: Locator;
	readonly cartTotalMiniumAmountInput: Locator;
	readonly editDiscountRuleFrame: FrameLocator;
	readonly mustBeDecimalErrorMessage: Locator;
	readonly mustBeValidNumberErrorMessage: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_pricing_web_internal_portlet_CommerceDiscountPortlet_fm .dnd-table'
		);
		this.editDiscountRuleFrame = page.frameLocator('iframe').nth(1);

		this.addDiscountRuleButton = page.getByRole('button', {
			exact: true,
			name: 'Add',
		});
		this.amountFieldReuiredErrorMessage =
			this.editDiscountRuleFrame.getByText(
				'The Cart Total Minimum Amount field is required.'
			);
		this.cartTotalMiniumAmountInput = this.editDiscountRuleFrame.getByLabel(
			'Cart Total Minimum Amount'
		);
		this.mustBeDecimalErrorMessage = this.editDiscountRuleFrame.getByText(
			'Error:Cart total minimum amount cannot be empty and must be a decimal number.'
		);
		this.mustBeValidNumberErrorMessage =
			this.editDiscountRuleFrame.getByText(
				'Please enter a valid number.'
			);
		this.page = page;
		this.saveButton = this.editDiscountRuleFrame.getByRole('button', {
			name: 'Save',
		});
	}
}
