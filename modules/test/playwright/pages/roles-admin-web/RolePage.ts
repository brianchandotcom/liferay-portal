/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class RolePage {
	readonly definePermissionsLink: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.definePermissionsLink = page.getByRole('link', {
			name: 'Define Permissions',
		});
		this.page = page;
	}
}
