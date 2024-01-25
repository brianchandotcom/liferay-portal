/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

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

		await kbArticle
			.getByLabel('Show Actions')
			.and(this.page.locator('[aria-haspopup]'))
			.click();

		this.page.once('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});
		await this.page.getByRole('menuitem', {name: 'Delete'}).click();
	}

	async deleteAll(page: Page, recycleBin: boolean) {
		await this.goto();
		const disabled = await this.selectAllCheckBox.isDisabled();

		if (!disabled) {
			await this.selectAllCheckBox.click();

			await page.getByRole('button', {name: 'Delete'}).waitFor();

			if (!recycleBin) {
				page.once('dialog', (dialog) => {
					dialog.accept().catch(() => {});
				});
				await page.getByRole('button', {name: 'Delete'}).click();
			}
			else {
				await page.getByRole('button', {name: 'Delete'}).click();
			}
		}
	}
}
