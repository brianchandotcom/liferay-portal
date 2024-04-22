/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';

export class KnowledgeBasePage {
	readonly basicArticleMenuItem: Locator;
	readonly foldersAndArticlesButton: Locator;
	readonly newButton: Locator;
	readonly page: Page;
	readonly selectAllCheckBox: Locator;

	constructor(page: Page) {
		this.basicArticleMenuItem = page.getByRole('menuitem', {
			name: 'Basic Article',
		});
		this.foldersAndArticlesButton = page.getByLabel('Folders and Articles');
		this.newButton = page
			.locator('.management-bar')
			.getByText('New', {exact: true});
		this.page = page;
		this.selectAllCheckBox = page.getByLabel(
			'Select All Items on the Page'
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.knowledgeBase}`
		);
	}

	async goToCreateNewArticle() {
		await this.goToFoldersAndArticles();
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.basicArticleMenuItem,
			trigger: this.newButton,
		});
	}

	private async goToFoldersAndArticles() {
		await this.foldersAndArticlesButton.click();
	}

	async deleteKnowledgeBaseArticle(title: string) {
		const kbArticle = await this.page
			.locator(
				'#_com_liferay_knowledge_base_web_portlet_AdminPortlet_kbObjectsSearchContainer .list-group-item'
			)
			.filter({hasText: title});

		this.page.once('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Delete'}),
			trigger: kbArticle.getByLabel('Show Actions'),
		});
	}

	async deleteAll(recycleBin: boolean) {
		if (await this.selectAllCheckBox.isDisabled()) {
			return;
		}

		if (!recycleBin) {
			this.page.once('dialog', (dialog) => {
				dialog.accept().catch(() => {});
			});
		}

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('button', {
				name: 'Delete',
			}),
			trigger: this.selectAllCheckBox,
		});
	}
}
