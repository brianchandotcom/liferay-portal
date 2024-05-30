/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class PersonalDataErasurePage {
	readonly allSelectedButton: Locator;
	readonly anonymizeButton: Locator;
	readonly anonymizeMenuItem: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly page: Page;
	readonly pageTitle: Locator;
	readonly selectAllItemsOnPageCheckbox: Locator;

	constructor(page: Page) {
		this.allSelectedButton = page
			.locator('nav')
			.filter({hasText: 'All Selected'})
			.getByRole('button');
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.anonymizeButton = page.getByRole('button', {name: 'Anonymize'});
		this.anonymizeMenuItem = page.getByRole('menuitem', {
			name: 'Anonymize',
		});
		this.page = page;
		this.selectAllItemsOnPageCheckbox = page.getByLabel(
			'Select All Items on the Page'
		);
	}
}
