/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class DigitalSalesRoomsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly deleteMenuItem: Locator;
	readonly digitalSalesRoomsTable: DataTablePage;
	readonly newDigitalSalesRoomButton: Locator;
	readonly noResultsFoundMessage: Locator;
	readonly page: Page;
	readonly roomLink: Locator;
	readonly saveAsTemplateMenuItem: Locator;
	readonly startFromScratchButton: Locator;
	readonly startFromTemplateButton: Locator;
	readonly templateLink: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.deleteMenuItem = page.getByRole('menuitem', {name: 'Delete'});
		this.digitalSalesRoomsTable = new DataTablePage(
			page,
			page.locator(
				'#portlet_com_liferay_digital_sales_room_web_internal_portlet_DigitalSalesRoomManagementPortlet'
			)
		);
		this.newDigitalSalesRoomButton = page.getByText(
			'New Digital Sales Room'
		);
		this.noResultsFoundMessage = page.getByText('No Results Found');
		this.page = page;
		this.roomLink = page.getByRole('link', {exact: true, name: 'Room'});
		this.saveAsTemplateMenuItem = page.getByRole('menuitem', {
			name: 'Save as Template',
		});
		this.startFromScratchButton = page.getByRole('menuitem', {
			name: 'Start from Scratch',
		});
		this.startFromTemplateButton = page.getByRole('menuitem', {
			name: 'Start from Template',
		});
		this.templateLink = page.getByRole('link', {
			exact: true,
			name: 'Template',
		});
	}

	async goto() {
		await this.applicationsMenuPage.goToDigitalSalesRooms();
	}
}
