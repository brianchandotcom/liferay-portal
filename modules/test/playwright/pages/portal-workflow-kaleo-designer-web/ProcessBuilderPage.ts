/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ProcessBuilderPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly page: Page;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.page = page;
	}

	async clickWorkflowDefinitionName(name: string) {
		await this.page
			.getByRole('link', {
				exact: true,
				name,
			})
			.click();
	}

	async goto() {
		await this.applicationsMenuPage.goToProcessBuilder();
	}
}
