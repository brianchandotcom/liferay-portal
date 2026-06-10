/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {JournalPage} from '../../../journal-web/main/pages/JournalPage';

type TranslationFields = {
	content?: string;
	description?: string;
	title?: string;
};

export class WebContentTranslationPage {
	readonly page: Page;

	readonly contentEditor: Locator;
	readonly descriptionEditor: Locator;
	readonly journalPage: JournalPage;
	readonly publishButton: Locator;
	readonly targetLocaleToggle: Locator;
	readonly titleInput: Locator;

	constructor(page: Page) {
		this.page = page;

		this.contentEditor = page.locator('.lfr-ck').nth(1).locator('.ck-content');
		this.descriptionEditor = page
			.locator('.lfr-ck')
			.nth(0)
			.locator('.ck-content');
		this.journalPage = new JournalPage(page);
		this.publishButton = page.getByRole('button', {name: 'Publish'});
		this.targetLocaleToggle = page
			.locator('button.dropdown-toggle')
			.filter({hasText: /^[a-z]{2}-[A-Z]{2}$/})
			.last();
		this.titleInput = page.locator(
			'#_com_liferay_translation_web_internal_portlet_TranslationPortlet_infoField--JournalArticle_title--0'
		);
	}

	async assertTargetFields({content, description, title}: TranslationFields) {
		if (title !== undefined) {
			await expect(this.titleInput).toHaveValue(title);
		}

		if (description !== undefined) {
			await expect(this.descriptionEditor).toContainText(description);
		}

		if (content !== undefined) {
			await expect(this.contentEditor).toContainText(content);
		}
	}

	async changeTargetLocale(locale: string) {
		await this.targetLocaleToggle.click();

		await this.page
			.locator('.dropdown-menu.show button.dropdown-item')
			.filter({hasText: new RegExp(`^${locale}$`)})
			.click();

		await expect(this.targetLocaleToggle).toHaveText(locale);
	}

	async open(site: Site, title: string) {
		await this.journalPage.goto(site.friendlyUrlPath);

		await this.journalPage.goToJournalArticleAction('Translate', title);
	}

	async publish() {
		await this.publishButton.click();
	}

	async translateFields({content, description, title}: TranslationFields) {
		if (title !== undefined) {
			await this.titleInput.fill(title);
		}

		if (description !== undefined) {
			await this.fillEditor(this.descriptionEditor, description);
		}

		if (content !== undefined) {
			await this.fillEditor(this.contentEditor, content);
		}
	}

	private async fillEditor(editor: Locator, value: string) {
		await editor.click();

		await this.page.keyboard.press('Control+KeyA');
		await this.page.keyboard.press('Backspace');
		await this.page.keyboard.type(value);
	}
}
