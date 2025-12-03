/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class DigitalSalesRoomsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly digitalSalesRoomsTable: DataTablePage;
	readonly noResultsFoundMessage: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.digitalSalesRoomsTable = new DataTablePage(
			page,
			page.locator(
				'#portlet_com_liferay_digital_sales_room_web_internal_portlet_DigitalSalesRoomManagementPortlet'
			)
		);
		this.noResultsFoundMessage = page.getByText('No Results Found');
		this.page = page;
	}

	async goto() {
		await this.applicationsMenuPage.goToDigitalSalesRooms();
	}
}
