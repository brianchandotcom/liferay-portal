/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {JournalTemplatesPage} from './JournalTemplatesPage';

export class JournalEditTemplatePage {
	readonly page: Page;

	readonly basicInformation: Locator;
	readonly elementsButton: Locator;
	readonly selectorWindow: FrameLocator;
	readonly journalTemplatesPage: JournalTemplatesPage;

	constructor(page: Page) {
		this.page = page;

		this.basicInformation = page.getByRole('button', {
			name: 'Basic Information',
		});
		this.elementsButton = page.getByTitle('Elements', {exact: true});
		this.selectorWindow = page.frameLocator(
			'iframe[title="Select Structure"]'
		);
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

	async selectTemplateToEdit(title: string) {
		await this.page.getByRole('link', {name: title}).click();
	}

	async editTemplate(title?: string, script?: string) {
		if (script) {
			await this.page.locator('.CodeMirror-lines').dblclick();
			await this.page.keyboard.press('ControlOrMeta+A');
			await this.page.keyboard.press('Backspace');

			await this.page.keyboard.type(script);
		}

		if (title) {
			await this.page.getByPlaceholder('Untitled Template').fill(title);
			await this.page.getByPlaceholder('Untitled Template').click();
		}
	}

	async getDDMTemplateKey(title?: string, script?: string): Promise<string> {
		await this.page.getByLabel('Properties').click();

		return this.page.getByLabel('DDM Template Key').inputValue();
	}

	async selectStructure(title?: string) {
		await this.page
			.locator(
				'[id="_com_liferay_journal_web_portlet_JournalPortlet_selectDDMStructure"]'
			)
			.click();

		await this.selectorWindow
			.getByRole('cell', {name: title})
			.waitFor({state: 'visible'});
		await this.selectorWindow.getByRole('cell', {name: title}).hover();
		await this.selectorWindow.getByRole('cell', {name: title}).click();
	}

	async saveTemplate() {
		await this.page
			.getByRole('button', {exact: true, name: 'Save'})
			.click();
	}
}
