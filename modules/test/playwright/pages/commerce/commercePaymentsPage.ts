/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommercePaymentsPage {
	readonly addCommentButton: Locator;
	readonly amountInput: Locator;
	readonly backLink: Locator;
	readonly commentInput: Locator;
	readonly commentSubmitButton: Locator;
	readonly ercInput: Locator;
	readonly ercModalOpenerButton: Locator;
	readonly ercSubmitButton: Locator;
	readonly headerDetailsTitle: Locator;
	readonly makeRefundButton: Locator;
	readonly page: Page;
	readonly reasonInput: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.addCommentButton = page.getByRole('link', {
			exact: true,
			name: 'Add',
		});
		this.amountInput = page.getByLabel('Amount');
		this.backLink = page.getByRole('link', {exact: true, name: 'Back'});
		this.commentInput = page
			.frameLocator('iframe[title="Comment"]')
			.getByLabel('Note', {exact: true});
		this.commentSubmitButton = page
			.frameLocator('iframe[title="Comment"]')
			.getByRole('button', {exact: true, name: 'Submit'});
		this.ercInput = page
			.frameLocator('iframe[title="Edit External Reference Code"]')
			.getByLabel('External Reference Code');
		this.ercModalOpenerButton = page.locator('#erc-edit-modal-opener');
		this.ercSubmitButton = page
			.frameLocator('iframe[title="Edit External Reference Code"]')
			.getByRole('button', {exact: true, name: 'Submit'});
		this.headerDetailsTitle = page.getByTestId('headerDetailsTitle');
		this.makeRefundButton = page.getByLabel('Make a Refund', {exact: true});
		this.reasonInput = page.getByLabel('Reason');
		this.saveButton = page.getByRole('link', {exact: true, name: 'Save'});
	}
}
