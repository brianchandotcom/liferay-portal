/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../../../pages/product-navigation-applications-menu/GlobalMenuPage';

export class JobSchedulerPage {
	readonly exportAnalyticsDxpEntitiesItem: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly newJobSchedulerTriggerButton: Locator;
	readonly newJobSchedulerTriggerTitleBox: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.exportAnalyticsDxpEntitiesItem = page.getByRole('menuitem', {
			name: 'Export Analytics DXP Entities',
		});
		this.globalMenuPage = new GlobalMenuPage(page);
		this.newJobSchedulerTriggerButton = page.getByRole('button', {
			name: 'New',
		});
		this.newJobSchedulerTriggerTitleBox = page.locator(
			'//*[@id="_com_liferay_dispatch_web_internal_portlet_DispatchPortlet_name"]'
		);
		this.page = page;
	}

	async goTo() {
		await this.globalMenuPage.goToControlPanel('Job Scheduler');
	}

	async createNewJobSchedulerTrigger(jobSchedulerTriggerName: string) {
		await this.newJobSchedulerTriggerButton.click();
		await this.exportAnalyticsDxpEntitiesItem.click();
		await this.newJobSchedulerTriggerTitleBox.fill(jobSchedulerTriggerName);

		await this.page.getByRole('button', {name: 'Save'}).click();
	}
}
