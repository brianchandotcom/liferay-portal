/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {JournalPage} from './JournalPage';

export class JournalEditTemplatePage {
	readonly page: Page;

	readonly basicInformation: Locator;
	readonly elementsButton: Locator;
	readonly journalPage: JournalPage;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.basicInformation = page.getByRole('link', {
			name: 'Basic Information',
		});
		this.elementsButton = page.getByTitle('Elements', {exact: true});
		this.journalPage = new JournalPage(page);
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.journalPage.goto(siteUrl);
		await this.journalPage.goToCreateNewTemplate();

		// Do it twice so we decrease flakiness

		await this.journalPage.goto(siteUrl);
		await this.journalPage.goToCreateNewTemplate();

		// Do it twice so we decrease flakiness

		await this.journalPage.goto();
		await this.journalPage.goToCreateNewTemplate();

		await this.basicInformation.waitFor();
	}

	async gotoElements() {
		await this.elementsButton.click();
	}
}
