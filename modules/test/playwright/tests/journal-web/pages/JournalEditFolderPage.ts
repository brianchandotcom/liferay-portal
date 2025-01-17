/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {JournalPage} from './JournalPage';

export class JournalEditFolderPage {
	readonly journalPage: JournalPage;
	readonly page: Page;
	readonly structureRestricions: Locator;
	readonly title: Locator;

	constructor(page: Page) {
		this.journalPage = new JournalPage(page);
		this.page = page;
		this.structureRestricions = page.getByRole('button', {
			name: 'Structure Restrictions and',
		});
		this.title = page.getByLabel('Name Required');
	}

	async editFolder(title: string) {
		await this.journalPage.goToJournalFolderAction('Edit', title);

		await this.page.waitForURL(/folderId/);
	}

	async gotToPermission(title: string) {
		await this.journalPage.goToJournalFolderAction('Permissions', title);
	}
}
