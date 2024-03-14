/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

export class EditAccountPage {
	readonly addressesLink: Locator;
	readonly contactInformationLink: Locator;
	readonly contactLink: Locator;
	readonly facebookInput: Locator;
	readonly jabberInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly skypeInput: Locator;
	readonly smsInput: Locator;
	readonly twitterInput: Locator;

	constructor(page: Page) {
		this.addressesLink = page.getByRole('link', {
			exact: true,
			name: 'Addresses',
		});
		this.contactInformationLink = page.getByRole('link', {
			exact: true,
			name: 'Contact Information',
		});
		this.contactLink = page.getByRole('link', {name: 'Contact'});
		this.facebookInput = page.getByLabel('Facebook');
		this.jabberInput = page.getByLabel('Jabber');
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.skypeInput = page.getByLabel('Skype');
		this.smsInput = page.getByLabel('SMS');
		this.twitterInput = page.getByLabel('Twitter');
	}

	async updateContactInformation(
		facebookInput: string,
		jabberInput: string,
		skypeInput: string,
		smsInput: string,
		twitterInput: string
	) {
		await this.facebookInput.fill(facebookInput);
		await this.jabberInput.fill(jabberInput);
		await this.skypeInput.fill(skypeInput);
		await this.smsInput.fill(smsInput);
		await this.twitterInput.fill(twitterInput);
		await this.saveButton.click();
	}

	async goToContact() {
		await Promise.all([
			this.contactLink.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp.url().includes('screenNavigationCategoryKey=contact')
			),
		]);
	}
}
