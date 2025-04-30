/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class BecomePublisherPage {
	readonly becomePublisherTitle: Locator;
	readonly completePublisherTitle: Locator;
	readonly continueButton: Locator;
	readonly description: Locator;
	readonly firstName: Locator;
	readonly lastName: Locator;
	readonly page: Page;
	readonly phone: Locator;
	readonly requestAccountButton: Locator;
	readonly requestAccountTitle: Locator;
	readonly thankYou: Locator;

	constructor(page: Page) {
		this.becomePublisherTitle = page.getByText('Becoming a Liferay');
		this.completePublisherTitle = page.getByText('Complete Publisher');
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.description = page.getByPlaceholder('Enter the name and a brief');
		this.firstName = page.getByPlaceholder('Enter first name');
		this.lastName = page.getByPlaceholder('Enter last name');
		this.page = page;
		this.phone = page.getByPlaceholder('___–___–____');
		this.requestAccountTitle = page.getByText('Request a Marketplace');
		this.requestAccountButton = page.getByRole('button', {
			name: 'Request Account',
		});
		this.thankYou = page.getByText('Thank you for your');
	}

	async goto(siteUrl?: string) {
		await this.page.goto(`/web${siteUrl}/publisher-gate`, {
			waitUntil: 'networkidle',
		});
	}
}
