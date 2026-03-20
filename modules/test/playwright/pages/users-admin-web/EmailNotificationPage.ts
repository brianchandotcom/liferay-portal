/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {liferayConfig} from '../../liferay.config';

export class EmailNotificationPage {
	readonly deleteAllLink: Locator;
	readonly noEmailsInQueueMessage: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.deleteAllLink = page.getByRole('link', {name: 'Delete all'});
		this.noEmailsInQueueMessage = page.getByText('No emails in queue');
		this.page = page;
	}

	emailBodyLink(text: string) {
		return this.page.getByRole('link', {name: text});
	}

	emailBodyText(text: string) {
		return this.page.getByText(text).first();
	}

	emailSubjectLink(subject: string) {
		return this.page.getByRole('link', {name: subject});
	}

	async getEmailBodyLinkHref(hrefContains: string): Promise<string> {
		const link = this.page.locator(`a[href*="${hrefContains}"]`).first();

		if (await link.isVisible()) {
			return (await link.getAttribute('href')) || '';
		}

		const bodyText = await this.page
			.locator(
				'[name="bodyPlainText"] .well, [name="bodyHTML_Unformatted"] .well'
			)
			.first()
			.innerText();

		const urlMatch = bodyText.match(
			new RegExp(
				`https?://[^\\s<>"]*${hrefContains}[^\\s<>"]*[^\\s<>".)]`
			)
		);

		return urlMatch ? urlMatch[0] : '';
	}

	async goto() {
		await this.page.goto(
			liferayConfig.environment.baseUrl.replace(/:([0-9]*)$/, ':8282')
		);
	}
}
