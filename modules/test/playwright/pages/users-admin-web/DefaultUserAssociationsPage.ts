/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class DefaultUserAssociationsPage {
	readonly page: Page;
	readonly saveButton: Locator;
	readonly userGroupsInput: Locator;

	constructor(page: Page) {
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.userGroupsInput = page.locator('textarea').nth(3);
	}
}
