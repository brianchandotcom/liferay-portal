/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ExportUserDataPage {
	readonly addExportProcessesButton: Locator;
	readonly announcementsCheckbox: Locator;
	readonly announcementsStatus: Locator;
	readonly blogsCheckbox: Locator;
	readonly blogsStatus: Locator;
	readonly contactsCenterCheckbox: Locator;
	readonly contactsCenterStatus: Locator;
	readonly creationMenuNewButton: Locator;
	readonly documentsAndMediaCheckbox: Locator;
	readonly documentsAndMediaStatus: Locator;
	readonly exportButton: Locator;
	readonly messageBoardsCheckbox: Locator;
	readonly messageBoardsStatus: Locator;
	readonly page: Page;
	readonly webContentCheckbox: Locator;
	readonly webContentStatus: Locator;
	readonly wikiCheckbox: Locator;
	readonly wikiStatus: Locator;

	constructor(page: Page) {
		this.addExportProcessesButton = page.getByRole('link', {
			name: 'Add Export Processes',
		});
		this.announcementsCheckbox = page.getByLabel('Announcements');
		this.announcementsStatus = page.getByText('Announcements Successful');
		this.blogsCheckbox = page.getByLabel('Blogs');
		this.blogsStatus = page.getByText('Blogs Successful');
		this.contactsCenterCheckbox = page.getByLabel('Contacts Center');
		this.contactsCenterStatus = page.getByText(
			'Contacts Center Successful'
		);
		this.creationMenuNewButton = page
			.getByTestId('creationMenuNewButton')
			.locator('visible=true');
		this.documentsAndMediaCheckbox = page.getByLabel('Documents and Media');
		this.documentsAndMediaStatus = page.getByText(
			'Documents and Media Successful'
		);
		this.exportButton = page.getByRole('button', {
			exact: true,
			name: 'Export',
		});
		this.messageBoardsCheckbox = page.getByLabel('Message Boards');
		this.messageBoardsStatus = page.getByText('Message Boards Successful');
		this.page = page;
		this.webContentCheckbox = page.getByLabel('Web Content');
		this.webContentStatus = page.getByText('Web Content Successful');
		this.wikiCheckbox = page.getByLabel('Wiki');
		this.wikiStatus = page.getByText('Wiki Successful');
	}
}
