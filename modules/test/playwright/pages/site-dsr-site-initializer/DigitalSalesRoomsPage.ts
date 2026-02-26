/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';
import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class DigitalSalesRoomsPage {
	readonly deleteButton: Locator;
	readonly deleteConfirmationModal: Locator;
	readonly deleteMenuItem: Locator;
	readonly digitalSalesRoomsTable: DataTablePage;
	readonly editMenuItem: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly newDigitalSalesRoomButton: Locator;
	readonly noResultsFoundMessage: Locator;
	readonly page: Page;
	readonly roomsLink: Locator;
	readonly saveAsTemplateMenuItem: Locator;
	readonly shareMenuItem: Locator;
	readonly settingsMenuItem: Locator;
	readonly startFromScratchButton: Locator;
	readonly startFromTemplateButton: Locator;
	readonly templatesLink: Locator;
	readonly viewMenuItem: Locator;

	constructor(page: Page) {
		this.deleteButton = page.getByRole('button', {name: 'Delete'});
		this.deleteConfirmationModal = page.getByRole('heading', {
			name: 'Delete Digital Sales Room',
		});
		this.deleteMenuItem = page.getByRole('menuitem', {name: 'Delete'});
		this.digitalSalesRoomsTable = new DataTablePage(
			page,
			page.locator(
				'[class*="site-dsr-site-initializer-internal-fragment-renderer-viewrooms"]'
			)
		);
		this.editMenuItem = page.getByRole('menuitem', {name: 'Edit'});
		this.globalMenuPage = new GlobalMenuPage(page);
		this.newDigitalSalesRoomButton = page.getByText(
			'New Digital Sales Room'
		);
		this.noResultsFoundMessage = page.getByText('No Results Found');
		this.page = page;
		this.roomsLink = page.getByRole('menuitem', {
			exact: true,
			name: 'Rooms',
		});
		this.saveAsTemplateMenuItem = page.getByRole('menuitem', {
			name: 'Save as Template',
		});
		this.shareMenuItem = page.getByRole('menuitem', {name: 'Share'});
		this.settingsMenuItem = page.getByRole('menuitem', {name: 'Settings'});
		this.startFromScratchButton = page.getByRole('menuitem', {
			name: 'Start from Scratch',
		});
		this.startFromTemplateButton = page.getByRole('menuitem', {
			name: 'Start from Template',
		});
		this.templatesLink = page.getByRole('link', {
			exact: true,
			name: 'Templates',
		});
		this.viewMenuItem = page.getByRole('menuitem', {name: 'View'});
	}

	roomLink(roomName: string): Locator {
		return this.digitalSalesRoomsTable
			.cell(roomName, false)
			.getByRole('link', {name: roomName});
	}

	async goto() {
		await this.globalMenuPage.goToHome();
		await this.globalMenuPage.goToCommerce('Digital Sales Room Management');
		await this.roomsLink.click();
	}
}
