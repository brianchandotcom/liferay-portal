/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {JournalTemplatesPage} from './JournalTemplatesPage';

export class JournalEditTemplatePage {
	readonly page: Page;

	readonly basicInformation: Locator;
	readonly elementsButton: Locator;
	readonly journalTemplatesPage: JournalTemplatesPage;

	constructor(page: Page) {
		this.page = page;

		this.basicInformation = page.getByRole('button', {
			name: 'Basic Information',
		});
		this.elementsButton = page.getByTitle('Elements', {exact: true});
		this.journalTemplatesPage = new JournalTemplatesPage(page);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.journalTemplatesPage.goto(siteUrl);
		await this.journalTemplatesPage.goToCreateNewTemplate();

		// Do it twice so we decrease flakiness

		await this.journalTemplatesPage.goto(siteUrl);
		await this.journalTemplatesPage.goToCreateNewTemplate();

		await this.basicInformation.waitFor();
	}

	async gotoElements() {
		await this.elementsButton.click();
	}
}
