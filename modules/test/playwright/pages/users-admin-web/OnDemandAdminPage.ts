/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';
import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class OnDemandAdminPage {
	readonly globalMenuPage: GlobalMenuPage;
	readonly instancesTable: DataTablePage;
	readonly page: Page;
	readonly reasonFrame: FrameLocator;
	readonly reasonFrameInput: Locator;
	readonly reasonFrameSaveButton: Locator;
	readonly requestAdministratorAccessMenuItem: Locator;

	constructor(page: Page) {
		this.globalMenuPage = new GlobalMenuPage(page);
		this.instancesTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_on_demand_admin_web_internal_portlet_OnDemandAdminPortlet_companiesSearchContainerSearchContainer'
			)
		);
		this.page = page;
		this.reasonFrame = page.frameLocator(
			'iframe[title="Request Administrator Access"]'
		);
		this.reasonFrameInput = this.reasonFrame.getByLabel(
			'Please provide the reason for'
		);
		this.reasonFrameSaveButton = this.reasonFrame.getByRole('button', {
			name: 'Submit',
		});
		this.requestAdministratorAccessMenuItem = page.getByRole('menuitem', {
			name: 'Request Administrator Access',
		});
	}

	async goto() {
		await this.globalMenuPage.goToHome();
		await this.globalMenuPage.goToControlPanel('On-Demand Admin');
	}
}
