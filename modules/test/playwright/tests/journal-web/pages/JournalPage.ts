/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {ProductMenuPage} from '../../../pages/product-navigation-product-menu/ProductMenu.page';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';

export class JournalPage {
	readonly page: Page;

	readonly createBasicWebContentLink: Locator;
	readonly newButton: Locator;
	readonly permissionsFrameLocator: FrameLocator;
	readonly productMenuPage: ProductMenuPage;
	readonly templatesLink: Locator;

	constructor(page: Page) {
		this.page = page;

		this.createBasicWebContentLink = this.page.getByRole('menuitem', {
			name: 'Basic Web Content',
		});
		this.newButton = page.getByText('New', {exact: true});
		this.permissionsFrameLocator = page.frameLocator(
			'iframe[title="Permissions"]'
		);
		this.productMenuPage = new ProductMenuPage(page);
		this.templatesLink = page.getByRole('link', {name: 'Templates'});
	}

	async goto() {
		await this.productMenuPage.goToJournalMenuItem();
	}

	async goToCreateNewTemplate() {
		await this.goToTemplates();
		await this.newButton.click();
	}

	async goToTemplates() {
		await this.goto();
		await this.templatesLink.click();
	}

	async assertJournalArticlePermissions(
		title: string,
		permissionLocators: string[]
	) {
		await this.goto();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				name: 'Permissions',
			}),
			trigger: this.page.getByLabel(`Actions for ${title}`, {
				exact: true,
			}),
		});

		for (const permissionsLocator of permissionLocators) {
			await expect(
				this.permissionsFrameLocator.locator(permissionsLocator)
			).toBeChecked();
		}

		await this.permissionsFrameLocator
			.getByRole('button', {name: 'Cancel'})
			.click();
	}

	async deleteJournalArticle(title: string) {
		await this.goto();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				name: 'Delete',
			}),
			trigger: this.page.getByLabel(`Actions for ${title}`, {
				exact: true,
			}),
		});
	}

	async setJournalArticlePermissions(
		articles: Locator[],
		permissionLocators: string[]
	) {
		await this.goto();

		for (const article of articles) {
			await article.getByTitle('Select').check();
		}

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				name: 'Permissions',
			}),
			trigger: this.page.getByTitle('Actions', {exact: true}),
		});

		for (const permissionsLocator of permissionLocators) {
			await this.permissionsFrameLocator
				.locator(permissionsLocator)
				.check();
		}

		await this.permissionsFrameLocator
			.getByRole('button', {name: 'Save'})
			.click();
		await this.permissionsFrameLocator
			.getByRole('button', {name: 'Cancel'})
			.click();
	}
}
