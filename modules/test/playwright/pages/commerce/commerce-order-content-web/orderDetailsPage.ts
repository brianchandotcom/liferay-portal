/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class OrderDetailsPage {
	readonly accountNotQualifiedError: Locator;
	readonly applyCouponCodeButton: Locator;
	readonly checkoutButton: Locator;
	readonly couponCodeHeading: (code: string) => Locator;
	readonly couponCodeUsageLimitError: Locator;
	readonly discountNotApplicableError: Locator;
	readonly enterPromoCodeInput: Locator;
	readonly orderTotal: (amount: string) => Locator;
	readonly page: Page;
	readonly totalDiscountLabel: Locator;

	constructor(page: Page) {
		this.accountNotQualifiedError = page.getByText(
			'The account is not qualified to use the discount.'
		);
		this.applyCouponCodeButton = page.getByRole('button', {
			exact: true,
			name: 'Apply',
		});
		this.checkoutButton = page.getByRole('button', {
			exact: true,
			name: 'Checkout',
		});
		this.couponCodeHeading = (code: string) =>
			page.getByRole('heading', {name: code});
		this.couponCodeUsageLimitError = page.getByText(
			'The inserted coupon code has reached its usage limit.'
		);
		this.discountNotApplicableError = page.getByText(
			'The discount is not applicable to the current order.'
		);
		this.enterPromoCodeInput = page.getByPlaceholder('Enter Promo Code');
		this.orderTotal = (amount: string) => page.getByText(amount).first();
		this.page = page;
		this.totalDiscountLabel = page.getByText('Total Discount').first();
	}

	async applyCouponCode(code: string) {
		await expect(this.enterPromoCodeInput).toBeVisible();

		await this.enterPromoCodeInput.fill(code);

		await this.applyCouponCodeButton.click();

		await this.page.waitForLoadState('networkidle');
	}
}
