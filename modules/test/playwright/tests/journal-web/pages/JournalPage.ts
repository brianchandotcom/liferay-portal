/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {waitForSuccessAlert} from '../../../utils/waitForSuccessAlert';

export class JournalPage {
	readonly page: Page;

	readonly createBasicWebContentLink: Locator;
	readonly newButton: Locator;
	readonly permissionsFrameLocator: FrameLocator;
	readonly publishButton: Locator;
	readonly templatesLink: Locator;
	readonly articleTitleInput: Locator;
	readonly articleContentTextBox: Locator;

	constructor(page: Page) {
		this.page = page;

		this.createBasicWebContentLink = this.page.getByRole('menuitem', {
			name: 'Basic Web Content',
		});
		this.newButton = page.getByText('New', {exact: true});
		this.permissionsFrameLocator = page.frameLocator(
			'iframe[title="Permissions"]'
		);
		this.templatesLink = page.getByRole('link', {name: 'Templates'});
		this.publishButton = page.getByRole('button', {name: 'Publish'});
		this.articleTitleInput = page.locator(
			'.article-content-title .input-group-item input'
		);
		this.articleContentTextBox = this.page
			.getByLabel('Content')
			.getByRole('textbox')
			.frameLocator('iframe')
			.locator('.html-editor');
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.journal}`
		);
	}

	async fillArticleData(title: string, content: string) {
		await this.articleTitleInput.fill(title);

		await this.articleContentTextBox.click();

		await this.page.keyboard.press('Control+KeyA');
		await this.page.keyboard.press('Backspace');
		await this.page.keyboard.type(content);
	}

	async goToCreateArticle(structureName?: string) {
		const target = structureName
			? this.page.getByRole('menuitem', {
					name: structureName,
				})
			: this.createBasicWebContentLink;

		await clickAndExpectToBeVisible({
			autoClick: true,
			target,
			trigger: this.newButton,
		});

		await this.page.locator('.article-content-content').waitFor();
	}

	async goToJournalArticleAction(action: string, title: string) {
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

	async assertJournalArticlePermissions(
		title: string,
		permissions: {enabled: boolean; locator: string}[]
	) {
		await this.goToJournalArticleAction('Permissions', title);

		await this.assertPermissions(permissions);
	}

	async assertPermissions(
		permissions: {enabled: boolean; locator: string}[]
	) {
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

	async assertPrivateContentIcon() {
		await expect(
			this.page.getByLabel('Not Visible to Guest Users').locator('use')
		).toBeVisible({timeout: 1000});
	}

	async changeView(viewName: string) {
		await this.page
			.getByLabel('Select View, Currently Selected: ')
			.waitFor();
		await this.page.getByLabel('Select View, Currently Selected: ').click();
		await this.page.getByRole('menuitem', {name: viewName}).click();
	}

	async publishArticle() {
		await this.publishButton.click();

		await waitForSuccessAlert(this.page, `was created successfully.`);
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

		await this.setPermissions(permissionLocators);
	}

	async setPermissions(permissionLocators: string[]) {
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
			.getByRole('button', {name: 'Cancel'})
			.click();
	}

	async setArticleViewableBy(value: 'Anyone' | 'Site Members' | 'Owner') {
		const permissionsGroup = this.page.getByRole('link', {
			name: 'Permissions',
		});

		await permissionsGroup.waitFor();

		const isOpen = await permissionsGroup.evaluate(
			(element) => element.getAttribute('aria-expanded') === 'true'
		);

		if (!isOpen) {
			await permissionsGroup.click();
		}

		await this.page.getByLabel('Viewable by').waitFor();

		await this.page.getByLabel('Viewable by').selectOption(value);
	}
}
