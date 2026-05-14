/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';

export class CommerceAdminCatalogsPage {
	readonly addCatalogsButton: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly catalogActionsButton: (catalogName: string) => Locator;
	readonly catalogId: Locator;
	readonly catalogLink: (name: string) => Locator;
	readonly catalogSaveButton: Locator;
	readonly deleteMenuItem: Locator;
	readonly modalFieldName: Locator;
	readonly modalFrameLocator: FrameLocator;
	readonly modalLinkSupplierAutocomplete: Locator;
	readonly modalLinkSupplierDropdownItem: (name: string) => Locator;
	readonly modalSubmitButton: Locator;
	readonly page: Page;
	readonly permissionsFrame: FrameLocator;
	readonly permissionsMenuItem: Locator;

	constructor(page: Page) {
		this.addCatalogsButton = page
			.getByTestId('managementToolbar')
			.locator('[data-testid="fdsCreationActionButton"]');
		this.globalMenuPage = new GlobalMenuPage(page);
		this.catalogActionsButton = (catalogName: string) =>
			page.getByRole('button', {
				exact: true,
				name: `${catalogName} Actions`,
			});
		this.catalogId = page.locator('span:has-text("ID")+strong');
		this.catalogLink = (name: string) =>
			page.getByRole('link', {exact: true, name});
		this.catalogSaveButton = page.getByRole('link', {
			exact: true,
			name: 'Save',
		});
		this.deleteMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.modalFrameLocator = page.frameLocator('.fds-modal-body iframe');
		this.modalFieldName =
			this.modalFrameLocator.getByLabel('Name Required');
		this.modalLinkSupplierAutocomplete = this.modalFrameLocator
			.locator('#link-account-entry-autocomplete-root input[type="text"]')
			.first();
		this.modalLinkSupplierDropdownItem = (name: string) =>
			this.modalFrameLocator
				.locator('.autocomplete-dropdown-menu')
				.getByText(name, {exact: true});
		this.modalSubmitButton = this.modalFrameLocator.getByRole('button', {
			exact: true,
			name: 'Submit',
		});
		this.page = page;
		this.permissionsFrame = page.frameLocator(
			'iframe[title="Permissions"]'
		);
		this.permissionsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Permissions',
		});
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Catalogs');
	}
}
