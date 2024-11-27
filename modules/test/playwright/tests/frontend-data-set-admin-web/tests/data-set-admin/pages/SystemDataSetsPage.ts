/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ApplicationsMenuPage} from '../../../../../pages/product-navigation-applications-menu/ApplicationsMenuPage';

export class SystemDataSetsPage {
	private readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly createButton: Locator;
	readonly creationModal: {
		readonly cancel: Locator;
		readonly container: Locator;
		readonly createButton: Locator;
		readonly header: Locator;
		readonly listItems: Locator;
		readonly search: Locator;
	};
	readonly page: Page;
	private readonly pageContainer: Locator;
	private readonly systemDataSetsTab: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);

		const systemDataSetsPageContainer = page.locator('.system-data-sets');

		this.createButton = systemDataSetsPageContainer
			.locator('.management-bar')
			.getByRole('button', {
				name: 'Create System Data Set Customization',
			});

		const creationModalContainer = page.locator(
			'.select-system-data-set-modal-content'
		);

		this.creationModal = {
			cancel: creationModalContainer.getByRole('button', {
				name: 'Cancel',
			}),
			container: creationModalContainer,
			createButton: creationModalContainer.getByRole('button', {
				name: 'Create',
			}),
			header: creationModalContainer.locator('.modal-header'),
			listItems: creationModalContainer.getByRole('listitem'),
			search: creationModalContainer.getByPlaceholder('Search'),
		};
		this.page = page;
		this.pageContainer = systemDataSetsPageContainer;
		this.systemDataSetsTab = page
			.locator('.nav-item')
			.filter({hasText: 'System Data Sets'});
	}

	async goto() {
		await this.applicationsMenuPage.goToDataSetManager();

		await this.systemDataSetsTab.click();

		await expect(this.pageContainer).toBeAttached();
	}
}
