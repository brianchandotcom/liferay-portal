/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {DisplayPageTemplatesPage} from '../../../../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../../utils/portletUrls';
import {waitForAlert} from '../../../../utils/waitForAlert';

export class NavigationMenusPage {
	readonly page: Page;

	readonly getMenuItem: (menuItemName: string) => Promise<Locator>;
	readonly getModalListItem: (itemName: string) => Promise<Locator>;
	readonly getModalMenuItem: (menuItemName: string) => Promise<Locator>;

	readonly addButton: Locator;
	readonly addMenuItemButton: Locator;
	readonly blogsModal: FrameLocator;
	readonly categoriesModal: FrameLocator;
	readonly displayTemplate: Locator;
	readonly journalArticleModal: FrameLocator;
	readonly pagesModal: FrameLocator;
	readonly previewButton: Locator;
	readonly saveButton: Locator;
	readonly selectButton: Locator;
	readonly submenuModal: FrameLocator;
	readonly urlModal: FrameLocator;
	readonly vocabulariesModal: FrameLocator;

	constructor(page: Page) {
		this.page = page;

		this.getMenuItem = async (menuItemName: string) => {
			return page.getByRole('menuitem', {name: menuItemName});
		};
		this.getModalListItem = async (itemName: string) => {
			return page
				.frameLocator('iframe')
				.getByRole('listitem')
				.getByText(itemName);
		};
		this.getModalMenuItem = async (menuItemName: string) => {
			return page
				.frameLocator('iframe')
				.getByRole('menuitem')
				.getByText(menuItemName);
		};

		this.addButton = page.getByRole('button', {name: 'Add'});
		this.addMenuItemButton = page.getByLabel('Add Menu Item');
		this.blogsModal = page.frameLocator(
			'iframe[title="Select Blogs Entry"]'
		);
		this.categoriesModal = page.frameLocator(
			'iframe[title="Select Categories"]'
		);
		this.displayTemplate = page.getByLabel('Display Template');
		this.journalArticleModal = page.frameLocator(
			'iframe[title="Select Web Content Article"]'
		);
		this.pagesModal = page.frameLocator('iframe[title="Select Pages"]');
		this.previewButton = page.getByRole('button', {name: 'Preview'});
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.selectButton = page.getByRole('button', {name: 'Select'});
		this.submenuModal = page.frameLocator('iframe[title="Add Submenu"]');
		this.urlModal = page.frameLocator('iframe[title="Add URL"]');
		this.vocabulariesModal = page.frameLocator(
			'iframe[title="Select Vocabularies"]'
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.navigationMenus}`
		);
	}

	async addBlogItem(name: string) {
		await this.addMenuItemButton.click();

		await (await this.getMenuItem('Blogs Entry')).click();

		await this.page.waitForSelector('iframe', {state: 'attached'});

		const blogItemButton = this.blogsModal.getByRole('button', {
			name,
		});

		while (await blogItemButton.isVisible()) {
			await blogItemButton.click();
		}

		await this.page.waitForSelector('iframe', {state: 'detached'});
	}

	async addOrChangeIcon(iconName: string) {
		await this.page.getByLabel('Select an Icon').click();

		await this.page.getByLabel('Select ' + iconName + ' icon').click();

		await this.saveButton.click();
	}

	async addSubmenuItem(submenuName: string) {
		await this.addMenuItemButton.click();

		await (await this.getMenuItem('Submenu')).click();

		// Wait until the modal is fully loaded

		await this.page.waitForTimeout(500);

		const textBox = this.submenuModal.getByPlaceholder('Name');

		await textBox.fill(submenuName);

		const addButton = this.submenuModal.getByRole('button', {name: 'Add'});

		await this.page.waitForTimeout(300);

		await addButton.click();

		await this.page.waitForSelector('iframe', {state: 'detached'});
	}

	async addURLItem(urlName: string, submenuItemName: string) {
		await this.page
			.locator('p.card-title')
			.filter({hasText: submenuItemName})
			.hover();

		await this.page
			.getByLabel('View ' + submenuItemName + ' Options')
			.click();

		await (await this.getMenuItem('Add Child')).hover();

		await this.page.getByText('URL').nth(3).click();

		// Wait until the modal is fully loaded

		await this.page.waitForTimeout(500);

		const urlTextBox = this.urlModal.getByPlaceholder('http://');

		await urlTextBox.fill('https://www.liferay.com');

		const urlNameTextbox = this.urlModal.getByPlaceholder('Name');

		await urlNameTextbox.fill(urlName);

		const addButton = this.urlModal.getByRole('button', {name: 'Add'});

		await this.page.waitForTimeout(300);

		await addButton.click();

		await this.page.waitForSelector('iframe', {state: 'detached'});
	}

	async addWebContentArticleItem(name: string) {
		await this.addMenuItemButton.click();

		await (await this.getMenuItem('Web Content Article')).click();

		await this.page.waitForSelector('iframe', {state: 'attached'});

		const journalArticleItemButton =
			this.journalArticleModal.getByText(name);

		await journalArticleItemButton.click();

		while (await journalArticleItemButton.isVisible()) {
			await journalArticleItemButton.click();
		}

		await this.page.waitForSelector('iframe', {state: 'detached'});
	}

	async addWidgetToPageTemplate(templateName: string) {
		const displayPageTemplatesPage = new DisplayPageTemplatesPage(
			this.page
		);

		await displayPageTemplatesPage.editTemplate(templateName);

		await this.page.getByLabel('Search Fragments and Widgets').click();

		await this.page
			.getByLabel('Search Fragments and Widgets')
			.fill('display page content');

		await this.page.waitForTimeout(300);

		await (await this.getMenuItem('Display Page Content Add'))
			.locator('div')
			.first()
			.dragTo(this.page.locator('#page-editor div').nth(1));

		await this.page.getByLabel('Publish').click();

		await displayPageTemplatesPage.markAsDefault(templateName);
	}

	async createNavigationMenu(menuName: string) {
		await this.addButton.click();

		const input = this.page.getByPlaceholder('Name');

		await input.waitFor();

		await input.fill(menuName);

		await this.saveButton.click();

		await waitForAlert(this.page);
	}

	async openAddCategoryModal() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: await this.getMenuItem('Category'),
			trigger: this.addMenuItemButton,
		});

		await this.categoriesModal.getByPlaceholder('Search').waitFor();
	}

	async openAddPageModal() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: await this.getMenuItem('Page'),
			trigger: this.addMenuItemButton,
		});

		await this.pagesModal.getByPlaceholder('Search').waitFor();
	}

	async openAddVocabularyModal() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: await this.getMenuItem('Vocabulary'),
			trigger: this.addMenuItemButton,
		});

		await this.vocabulariesModal.getByPlaceholder('Search').waitFor();
	}
}
