/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class SearchAdminPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly page: Page;
	readonly connectionsTab: Locator;
	readonly fieldMappingsTab: Locator;
	readonly indexActionsTab: Locator;
	readonly indexActionsConfigurationSection: Locator;
	readonly indexActionsModal: Locator;
	readonly indexActionsReindexActionSection: Locator;
	readonly indexActionsReindexActionItems: Locator;

	constructor(page) {
		this.page = page;

		this.applicationsMenuPage = new ApplicationsMenuPage(page);

		// Navigation

		this.connectionsTab = this.page.getByText('Connections');
		this.fieldMappingsTab = this.page.getByText('Field Mappings');
		this.indexActionsTab = this.page.getByText('Index Actions');

		// Index Actions Tab

		this.indexActionsConfigurationSection = this.page.locator(
			'.execution-scope-sheet'
		);
		this.indexActionsReindexActionSection = this.page.locator(
			'.index-actions-sheet'
		);
		this.indexActionsReindexActionItems = this.page.locator(
			'.index-actions-sheet .list-group-item'
		);
		this.indexActionsModal = this.page.getByRole('dialog');
	}

	async getIndexActionsItem(title: string) {
		return this.indexActionsReindexActionSection
			.locator(
				`xpath=//*[contains(.,'${title}') and contains(@class, 'list-group-item')]`
			)
			.nth(0);
	}

	async goto() {
		await this.applicationsMenuPage.goToSearch();
	}

	async goToConnectionsTab() {
		await this.connectionsTab.click();
	}

	async goToFieldMappingsTab() {
		await this.fieldMappingsTab.click();
	}

	async goToIndexActionsTab() {
		await this.indexActionsTab.click();
	}

	async reindexAllSearchIndexes() {
		const reindexAllSearchIndexes =
			await this.getIndexActionsItem('All Search Indexes');

		await reindexAllSearchIndexes.getByRole('button').click();

		await expect(this.indexActionsModal).toHaveText(
			/Reindex Search Indexes/
		);

		await this.indexActionsModal
			.getByRole('button', {name: 'Execute'})
			.click();

		await expect(
			reindexAllSearchIndexes.locator('.progress')
		).toBeVisible();
	}

	async reindexAllSpellCheckDictionaries() {
		const reindexAllSpellCheckDictionaries = await this.getIndexActionsItem(
			'All Spell Check Dictionaries'
		);

		await reindexAllSpellCheckDictionaries.getByRole('button').click();

		await expect(this.indexActionsModal).toHaveText(
			/Reindex Spell-Check Dictionaries/
		);

		await this.indexActionsModal
			.getByRole('button', {name: 'Execute'})
			.click();

		await expect(
			reindexAllSpellCheckDictionaries.getByRole('button')
		).toBeDisabled();
	}

	async reindexIndexActionsItem(title: string) {
		const reindexIndexAction = await this.getIndexActionsItem(title);

		await reindexIndexAction.getByRole('button').click();

		await expect(this.indexActionsModal.locator('.modal-title')).toHaveText(
			`Reindex Type <${title}>`
		);

		await this.indexActionsModal
			.getByRole('button', {name: 'Execute'})
			.click();

		await expect(reindexIndexAction.locator('.progress')).toBeVisible();
	}
}
