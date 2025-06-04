/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class UserAssociatedDataMessageBoardWidgetPage {
	readonly actionsButton: Locator;
	readonly permissionsMenuItem: Locator;
	readonly threadSubjectLink: (subject: string) => Locator;

	constructor(page: Page) {
		this.actionsButton = page.getByRole('button', {name: 'Actions'});
		this.permissionsMenuItem = page.locator('a[data-title="Permissions"]');
		this.threadSubjectLink = (subject: string) =>
			page.locator('ol').getByRole('link', {name: subject});
	}
}
