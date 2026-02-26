/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../utils/waitForAlert';
import {waitForPageToBeLoaded} from '../../utils/waitForPageToBeLoaded';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';
import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';
import {ProductMenuPage} from '../product-navigation-control-menu-web/ProductMenuPage';

export class InstanceSettingsPage {
	readonly actionsButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.actionsButton = page.getByRole('button', {name: 'Actions'});
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.globalMenuPage = new GlobalMenuPage(page);
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
		this.saveButton = page
			.getByRole('button', {name: 'Save'})
			.or(page.getByRole('button', {name: 'Update'}));
	}

	async goto({
		forceReload = true,
		useProductMenu = false,
	}: {forceReload?: boolean; useProductMenu?: boolean} = {}) {
		if (useProductMenu) {
			await this.productMenuPage.goToPortlet({
				category: 'Configuration',
				panel: 'Control Panel',
				portlet: 'Instance Settings',
			});
		}
		else {
			if (forceReload) {
				await this.globalMenuPage.goToHome();
			}

			await this.globalMenuPage.goToControlPanel('Instance Settings');
		}
	}

	async checkOption(label: string, checked: boolean) {
		const checkbox = this.page.getByLabel(label).first();
		await expect(checkbox).toBeVisible();
		checked ? await checkbox.check() : await checkbox.uncheck();
	}

	async assertOptionVisible(options: {
		customLocator?: Locator;
		description?: string;
		label?: string;
	}) {
		const {customLocator, description, label} = options;

		if (label) {
			await expect(this.page.getByLabel(label).first()).toBeVisible();
		}

		if (description) {
			await expect(
				this.page.getByText(description).first()
			).toBeVisible();
		}

		if (customLocator) {
			await expect(customLocator).toBeVisible();
		}
	}

	async clickOnAction(actionName: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: actionName}),
			trigger: this.actionsButton,
		});
	}

	async resetInstanceSetting() {
		await this.clickOnAction('Reset Default Values');

		await waitForAlert(this.page);
	}

	async exportInstanceSetting() {
		await this.clickOnAction('Export');
	}

	async goToInstanceSetting(
		categoryKey: string,
		configurationName: string,
		forceReload = true,
		sectionName?: string,
		useProductMenu?: boolean
	) {
		await this.goto({forceReload, useProductMenu});

		await this.page
			.getByRole('link', {
				exact: true,
				name: categoryKey,
			})
			.click();

		let parent: Locator | Page = this.page;

		if (sectionName) {
			parent = this.page
				.locator('div')
				.filter({hasText: sectionName})
				.locator('+ div')
				.getByRole('menubar');
		}

		await parent
			.getByRole('menuitem', {
				exact: true,
				name: configurationName,
			})
			.click();

		await waitForPageToBeLoaded(this.page);
	}

	async exportFactoryEntries() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Export Entries'}),
			trigger: this.page.getByRole('button', {name: 'Actions'}).first(),
		});
	}

	async exportFactoryEntry(entryName: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Export'}),
			trigger: this.page
				.locator(`tbody tr:has-text("${entryName}")`)
				.getByRole('button', {name: 'Actions'}),
		});
	}

	async editFactoryEntry(entryName: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('link', {name: 'Edit'}),
			trigger: this.page
				.locator(`tbody tr:has-text("${entryName}")`)
				.getByRole('button', {name: 'Actions'}),
		});
	}

	async deleteFactoryEntry(entryName: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('link', {name: 'Delete'}),
			trigger: this.page
				.locator(`tbody tr:has-text("${entryName}")`)
				.getByRole('button', {name: 'Actions'}),
		});
	}

	async goToSSO() {
		await this.goto();
		await this.page.getByRole('link', {name: 'SSO'}).click();
	}

	async saveAndWaitForAlert({
		autoClose,
		text = 'Success:Your request completed successfully.',
		type,
	}: {
		autoClose?: boolean;
		text?: string;
		type?: 'success' | 'info' | 'warning' | 'danger';
	} = {}) {
		await this.saveButton.click();

		await waitForAlert(this.page, text, {autoClose, type});
	}
}
