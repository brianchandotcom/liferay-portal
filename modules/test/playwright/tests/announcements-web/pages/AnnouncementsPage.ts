/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../../pages/product-navigation-applications-menu/ApplicationsMenuPage';

export class AnnouncementsPage {
	readonly page: Page;

	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly newButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.newButton = page.getByRole('link', {name: 'Add Announcement'});
	}

	async goto() {
		await this.applicationsMenuPage.goToAnnouncements();
	}

	async goToCreateNewAnnouncement() {
		await this.goto();
		await this.newButton.click();
	}
}
