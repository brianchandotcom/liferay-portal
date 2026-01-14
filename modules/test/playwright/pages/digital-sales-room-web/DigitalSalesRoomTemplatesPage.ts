/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class DigitalSalesRoomTemplatesPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly deleteMenuItem: Locator;
	readonly duplicateMenuItem: Locator;
	readonly digitalSalesRoomTemplatesTable: DataTablePage;
	readonly editMenuItem: Locator;
	readonly newDigitalSalesRoomTemplateButton: Locator;
	readonly noResultsFoundMessage: Locator;
	readonly page: Page;
	readonly roomsLink: Locator;
	readonly settingsMenuItem: Locator;
	readonly templatesLink: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.deleteMenuItem = page.getByRole('menuitem', {name: 'Delete'});
		this.duplicateMenuItem = page.getByRole('menuitem', {
			name: 'Duplicate',
		});
		this.digitalSalesRoomTemplatesTable = new DataTablePage(
			page,
			page.locator(
				'#portlet_com_liferay_digital_sales_room_web_internal_portlet_DigitalSalesRoomManagementPortlet'
			)
		);
		this.editMenuItem = page.getByRole('menuitem', {name: 'Edit'});
		this.newDigitalSalesRoomTemplateButton = page.getByText(
			'New Digital Sales Room Template'
		);
		this.noResultsFoundMessage = page.getByText('No Results Found');
		this.page = page;
		this.roomsLink = page.getByRole('link', {exact: true, name: 'Rooms'});
		this.settingsMenuItem = page.getByRole('menuitem', {name: 'Settings'});
		this.templatesLink = page.getByRole('link', {
			exact: true,
			name: 'Templates',
		});
	}

	async goto() {
		await this.applicationsMenuPage.goToDigitalSalesRooms();

		await this.templatesLink.click();
	}
}
