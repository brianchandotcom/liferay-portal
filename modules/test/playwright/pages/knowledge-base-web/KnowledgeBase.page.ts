/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {showUI} from '../../utils/showUI';
import {ProductMenuPage} from '../product-navigation-product-menu/ProductMenu.page';

export class KnowledgeBasePage {
	readonly basicArticleMenuItem: Locator;
	readonly foldersAndArticlesButton: Locator;
	readonly newButton: Locator;
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;
	readonly selectAllCheckBox: Locator;

	constructor(page: Page) {
		this.basicArticleMenuItem = page.getByRole('menuitem', {
			name: 'Basic Article',
		});
		this.foldersAndArticlesButton = page.getByLabel('Folders and Articles');
		this.newButton = page.getByLabel('New', {exact: true});
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
		this.selectAllCheckBox = page.getByLabel(
			'Select All Items on the Page'
		);
	}

	async goto() {
		await this.productMenuPage.goToKnowledgeBaseMenuItem();
	}

	async goToCreateNewArticle() {
		await this.goToFoldersAndArticles();
		await this.newButton.waitFor();
		await this.newButton.click();
		await this.basicArticleMenuItem.waitFor();
		await this.basicArticleMenuItem.click();
	}

	private async goToFoldersAndArticles() {
		await this.goto();

		const foldersAndArticlesButtonVisible =
			await this.foldersAndArticlesButton.isVisible();

		if (foldersAndArticlesButtonVisible) {
			await this.foldersAndArticlesButton.click();
		}
	}

	async deleteKnowledgeBaseArticle(title: string) {
		await this.goto();

		const kbArticle = await this.page
			.locator(
				'#_com_liferay_knowledge_base_web_portlet_AdminPortlet_kbObjectsSearchContainer .list-group-item'
			)
			.filter({hasText: title});

		this.page.once('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});
		await showUI({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Delete'}),
			trigger: kbArticle.getByLabel('Show Actions'),
		});
	}

	async deleteAll(recycleBin: boolean) {
		await this.goto();

		if (await this.selectAllCheckBox.isDisabled()) {
			return;
		}

		if (!recycleBin) {
			this.page.once('dialog', (dialog) => {
				dialog.accept().catch(() => {});
			});
		}

		await showUI({
			autoClick: true,
			target: this.page.getByRole('button', {
				name: 'Delete',
			}),
			trigger: this.selectAllCheckBox,
		});
	}
}
