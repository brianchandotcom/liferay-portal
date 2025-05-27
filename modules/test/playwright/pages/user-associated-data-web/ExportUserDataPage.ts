/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';

export class ExportUserDataPage extends DataTablePage {
	readonly actionsButton: Locator;
	readonly addExportProcessesButton: Locator;
	readonly announcementsCheckbox: Locator;
	readonly announcementsStatus: Locator;
	readonly blogsCheckbox: Locator;
	readonly blogsStatus: Locator;
	readonly contactsCenterCheckbox: Locator;
	readonly contactsCenterStatus: Locator;
	readonly creationMenuNewButton: Locator;
	readonly deleteLink: Locator;
	readonly documentsAndMediaCheckbox: Locator;
	readonly documentsAndMediaStatus: Locator;
	readonly emptyExportProcessesMessage: Locator;
	readonly exportButton: Locator;
	readonly formsCheckbox: Locator;
	readonly formsStatus: Locator;
	readonly messageBoardsCheckbox: Locator;
	readonly messageBoardsStatus: Locator;
	readonly nameMenuItem: Locator;
	readonly orderByButton: Locator;
	readonly page: Page;
	readonly paginationResults: Locator;
	readonly webContentCheckbox: Locator;
	readonly webContentStatus: Locator;
	readonly wikiCheckbox: Locator;
	readonly wikiStatus: Locator;

	constructor(page: Page) {
		super(
			page,
			page.locator(
				'#_com_liferay_user_associated_data_web_portlet_UserAssociatedData_backgroundTasksSearchContainer'
			)
		);
		this.actionsButton = page.getByRole('button', {name: 'Actions'});
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
		this.deleteLink = page.getByRole('link', {name: 'Delete'});
		this.documentsAndMediaCheckbox = page.getByLabel('Documents and Media');
		this.documentsAndMediaStatus = page.getByText(
			'Documents and Media Successful'
		);
		this.emptyExportProcessesMessage = page.getByText(
			'No personal data export processes were found. Please create a data export process.'
		);
		this.exportButton = page.getByRole('button', {
			exact: true,
			name: 'Export',
		});
		this.formsCheckbox = page.getByLabel('Forms');
		this.formsStatus = page.getByText('Forms Successful');
		this.messageBoardsCheckbox = page.getByLabel('Message Boards');
		this.messageBoardsStatus = page.getByText('Message Boards Successful');
		this.nameMenuItem = page.getByRole('menuitem', {name: 'Name'});
		this.orderByButton = page.getByLabel('Order');
		this.page = page;
		this.paginationResults = page.locator('.pagination-results');
		this.webContentCheckbox = page.getByLabel('Web Content');
		this.webContentStatus = page.getByText('Web Content Successful');
		this.wikiCheckbox = page.getByLabel('Wiki');
		this.wikiStatus = page.getByText('Wiki Successful');
	}

	async verifyPaginationResult(from: number, to: number, total: number) {
		const expected = `Showing ${from} to ${to} of ${total} entries.`;
		const resultsText = (await this.paginationResults.textContent()).trim();

		expect(resultsText).toContain(expected);
	}
}
