/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditAccountChannelDefaultsPage {
	readonly page: Page;
	readonly addDefaultPaymentTermButton: Locator;
	readonly addDefaultPaymentTermSelector: Locator;
	readonly addDefaultTermSaveButton: Locator;

	constructor(page: Page) {
		this.page = page;
		this.addDefaultPaymentTermButton = page
			.locator(
				"[id='_com_liferay_account_admin_web_internal_portlet_AccountEntriesAdminPortlet_defaultPaymentCommerceTermEntries']"
			)
			.getByRole('button', {name: 'Add Default Term'})
			.first();
		this.addDefaultPaymentTermSelector = page
			.frameLocator('.fds-modal-body > iframe')
			.getByLabel('Term');
		this.addDefaultTermSaveButton = page
			.frameLocator('.fds-modal-body > iframe')
			.getByRole('button', {name: 'Save'});
	}

	async addDefaultPaymentTerm(paymentTermId: number) {
		await this.addDefaultPaymentTermButton.click();
		await this.addDefaultPaymentTermSelector.selectOption(
			paymentTermId.toString()
		);
		await this.addDefaultTermSaveButton.click();
		await this.page.waitForTimeout(200);
	}
}
