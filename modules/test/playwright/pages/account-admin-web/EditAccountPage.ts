/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditAccountPage {
	readonly contactLink: Locator;
	readonly page: Page;
	readonly rolesLink: Locator;
	readonly usersLink: Locator;

	constructor(page: Page) {
		this.contactLink = page.getByRole('link', {name: 'Contact'});
		this.page = page;
		this.rolesLink = page.getByRole('link', {name: 'Roles'});
		this.usersLink = page.getByRole('link', {name: 'Users'});
	}
}
