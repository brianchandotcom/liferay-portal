/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {GlobalMenuPage} from '../../../../../../pages/product-navigation-applications-menu/GlobalMenuPage';

export class SystemDataSetsPage {
	readonly activeToggle: Locator;
	readonly createButton: Locator;
	readonly creationModal: {
		readonly body: Locator;
		readonly cancelButton: Locator;
		readonly createButton: Locator;
		readonly header: Locator;
		readonly listItems: Locator;
		readonly searchInput: Locator;
	};
	private readonly globalMenuPage: GlobalMenuPage;
	readonly inactiveToggle: Locator;
	readonly page: Page;
	readonly pageContainer: Locator;
	private readonly systemDataSetsTab: Locator;

	constructor(page: Page) {
		this.activeToggle = page.getByLabel('Active', {exact: true});
		this.globalMenuPage = new GlobalMenuPage(page);

		const systemDataSetsPageContainer = page.locator('.system-data-sets');

		this.createButton = systemDataSetsPageContainer
			.locator('.management-bar')
			.getByRole('button', {
				name: 'Create System Data Set Customization',
			});

		const creationModalBody = page.locator(
			'.select-system-data-set-modal-body'
		);

		const creationModalFooter = page.locator(
			'.select-system-data-set-modal-footer'
		);

		const creationModalHeader = page.locator(
			'.select-system-data-set-modal-header'
		);

		this.creationModal = {
			body: creationModalBody,
			cancelButton: creationModalFooter.getByRole('button', {
				name: 'Cancel',
			}),
			createButton: creationModalFooter.getByRole('button', {
				name: 'Create',
			}),
			header: creationModalHeader,
			listItems: creationModalBody.getByRole('listitem'),
			searchInput: creationModalBody.getByPlaceholder('Search'),
		};
		this.inactiveToggle = page.getByLabel('Inactive', {exact: true});
		this.page = page;
		this.pageContainer = systemDataSetsPageContainer;
		this.systemDataSetsTab = page
			.locator('.nav-item')
			.filter({hasText: 'System Data Sets'});
	}

	async goto() {
		await this.globalMenuPage.goToControlPanel('Data Sets');

		await this.systemDataSetsTab.click();

		await expect(this.pageContainer).toBeAttached();
	}
}
