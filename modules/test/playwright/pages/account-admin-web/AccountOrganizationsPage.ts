/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class AccountOrganizationsPage {
	readonly organizationName: (organizationName: string) => Locator;
	readonly page: Page;
	readonly removeButton: Locator;
	readonly selectAllItemsCheckbox: Locator;

	constructor(page: Page) {
		this.organizationName = (organizationName: string) => {
			return this.page.getByText(organizationName, {exact: true});
		};
		this.page = page;
		this.removeButton = page.getByRole('button', {name: 'Remove'});
		this.selectAllItemsCheckbox = page.getByLabel(
			'Select All Items on the Page'
		);
	}
}
