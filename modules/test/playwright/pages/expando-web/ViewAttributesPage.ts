/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ViewAttributesPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly enabledField: Locator;
	readonly entityIdField: Locator;
	readonly page: Page;
	readonly samlRoleField: Locator;
	readonly saveButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.enabledField = page.getByText('Enabled');
		this.entityIdField = page.getByLabel('Entity ID');
		this.page = page;
		this.samlRoleField = page.getByLabel('SAML Role');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async goto(resource: string) {
		await this.applicationsMenuPage.goToCustomFields();

		await this.page
			.getByRole('link', {exact: true, name: resource})
			.click();
	}
}
