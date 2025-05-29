/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class UserAssociatedDataMessageBoardPage {
	readonly actionButton: Locator;
	readonly editMenuItem: Locator;
	readonly newButton: Locator;
	readonly page: Page;
	readonly threadMenuItem: Locator;
	readonly threadSubjectLink: (subject: string) => Locator;

	constructor(page: Page) {
		this.actionButton = page.getByRole('button', {name: 'Actions'});
		this.editMenuItem = page.getByRole('menuitem', {name: 'Edit'});
		this.newButton = page.getByRole('button', {name: 'New'});
		this.page = page;
		this.threadMenuItem = page.getByRole('menuitem', {name: 'Thread'});
		this.threadSubjectLink = (subject: string) =>
			page.getByText(`${subject}`);
	}
}
