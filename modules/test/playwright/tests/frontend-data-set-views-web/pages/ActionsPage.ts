/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CreationActionTypes, ItemActionTypes} from '../utils/types';
import {ViewsPage} from './ViewsPage';

export class ActionsPage {
	readonly creationActionsTab: Locator;
	readonly itemActionsTab: Locator;
	readonly newActionButton: Locator;
	readonly newCreationActionForm: {
		addIconButton: Locator;
		nameInput: Locator;
		saveButton: Locator;
		selectIconModal: {
			iconsList: Locator;
			searchInput: Locator;
		};
		typeSelect: Locator;
		urlText: Locator;
	};
	readonly page: Page;
	readonly viewsPage: ViewsPage;

	constructor(page: Page) {
		this.creationActionsTab = page.getByRole('tab', {
			name: 'Creation Actions',
		});
		this.itemActionsTab = page.getByRole('tab', {name: 'Item Actions'});
		this.newActionButton = page.getByRole('button', {name: 'Add Action'});
		this.newCreationActionForm = {
			addIconButton: page.getByLabel('add-icon'),
			nameInput: page.getByPlaceholder('Action Name'),
			saveButton: page.getByRole('button', {name: 'Save'}),
			selectIconModal: {
				iconsList: page.locator('li'),
				searchInput: page.getByPlaceholder('Search'),
			},
			typeSelect: page.getByLabel('TypeRequired', {exact: true}),
			urlText: page.getByPlaceholder('Add a URL here.'),
		};
		this.page = page;
		this.viewsPage = new ViewsPage(page);
	}

	async goto() {
		await this.viewsPage.goto();
		await this.viewsPage.gotoSampleDataSetView();

		this.page.getByRole('button', {name: 'Actions'}).first().click();
	}

	async createCreationAction({
		icon,
		name,
		type,
		url,
	}: {
		icon: string;
		name: string;
		type: CreationActionTypes;
		url: string;
	}) {
		await this.creationActionsTab.click();

		await this.newActionButton.click();

		await this.newCreationActionForm.nameInput.fill(name);
		await this.newCreationActionForm.addIconButton.click();

		await this.newCreationActionForm.selectIconModal.searchInput.fill(icon);
		await this.newCreationActionForm.selectIconModal.iconsList
			.filter({hasText: icon})
			.click();

		await this.newCreationActionForm.typeSelect.selectOption(type);

		if (type === 'modal' || type === 'sidePanel') {
			await this.page.getByPlaceholder('add-here-the-title').click();
			await this.page
				.getByPlaceholder('add-here-the-title')
				.fill(`${name} Title`);
		}

		await this.newCreationActionForm.urlText.fill(url);
		await this.newCreationActionForm.saveButton.click();
	}

	async createItemAction({
		icon,
		name,
		type,
		url,
	}: {
		icon: string;
		name: string;
		type: ItemActionTypes;
		url: string;
	}) {
		await this.itemActionsTab.click();

		await this.newActionButton.click();

		await this.newCreationActionForm.nameInput.fill(name);
		await this.newCreationActionForm.addIconButton.click();

		await this.newCreationActionForm.selectIconModal.searchInput.fill(icon);
		await this.newCreationActionForm.selectIconModal.iconsList
			.filter({hasText: icon})
			.click();

		await this.newCreationActionForm.typeSelect.selectOption(type);

		if (type === 'modal' || type === 'sidePanel') {
			await this.page.getByPlaceholder('add-here-the-title').click();
			await this.page
				.getByPlaceholder('add-here-the-title')
				.fill(`${name} Title`);
		}

		await this.newCreationActionForm.urlText.fill(url);
		await this.newCreationActionForm.saveButton.click();
	}
}
