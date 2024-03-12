/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {ProductMenuPage} from '../../../pages/product-navigation-product-menu/ProductMenuPage';
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

	async goToCreateNewBasicArticle() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.createBasicWebContentLink,
			trigger: this.newButton,
		});
	}

	async goToCreateStructureArticle(structureName) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				name: structureName,
			}),
			trigger: this.newButton,
		});
	}

	async goToCreateNewTemplate() {
		await this.goToTemplates();
		await this.newButton.click();
	}

	async goToJournalArticleAction(action: string, title: string) {
		await this.goto();

		await this.page.getByLabel(`Actions for ${title}`).waitFor();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: action,
			}),
			trigger: this.page.getByLabel(`Actions for ${title}`, {
				exact: true,
			}),
		});
	}

	async goToTemplates() {
		await this.templatesLink.click();
	}

	async assertJournalArticlePermissions(
		title: string,
		permissions: {enabled: boolean; locator: string}[]
	) {
		await this.goToJournalArticleAction('Permissions', title);

		await this.permissionsFrameLocator
			.locator(permissions[0].locator)
			.waitFor();

		for (const permission of permissions) {
			const permissionCheckbox = this.permissionsFrameLocator.locator(
				permission.locator
			);

			if (permission.enabled) {
				await expect(permissionCheckbox).toBeChecked();
			}
			else {
				await expect(permissionCheckbox).not.toBeChecked();
			}
		}

		await this.permissionsFrameLocator
			.getByRole('button', {name: 'Cancel'})
			.click();
	}

	async deleteJournalArticle(title: string) {
		await this.goToJournalArticleAction('Delete', title);
	}

	async setJournalArticlePermissions(
		articles: Locator[],
		permissionLocators: string[]
	) {
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

		await this.permissionsFrameLocator
			.locator(permissionLocators[0])
			.check({trial: true});

		for (const permissionsLocator of permissionLocators) {
			await this.permissionsFrameLocator
				.locator(permissionsLocator)
				.check({timeout: 2000});
		}

		await this.permissionsFrameLocator
			.getByRole('button', {name: 'Save'})
			.click();

		await this.permissionsFrameLocator
			.getByText('Success:Your request completed successfully.')
			.waitFor();

		await this.permissionsFrameLocator
			.getByRole('button', {name: 'Cancel'})
			.click();
	}
}
