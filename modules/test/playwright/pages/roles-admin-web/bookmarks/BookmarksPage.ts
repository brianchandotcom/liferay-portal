/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';
import {DataTablePage} from '../../account-admin-web/DataTablePage';

export class BookmarksPage {
	readonly bookmarkItem: (bookmarkName: string) => Locator;
	readonly bookmarksMenuItem: Locator;
	readonly bookmarksTable: DataTablePage;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly permissionsButton: Locator;
	readonly saveButton: Locator;
	readonly urlInput: Locator;
	readonly viewableBySelect: Locator;

	constructor(page: Page) {
		this.bookmarkItem = (bookmarkName) =>
			page
				.getByTestId('row')
				.locator('div')
				.filter({hasText: bookmarkName})
				.nth(1);
		this.bookmarksMenuItem = page.getByRole('menuitem', {name: 'Bookmark'});
		this.bookmarksTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_bookmarks_web_portlet_BookmarksAdminPortlet_fm'
			)
		);
		this.nameInput = page.getByLabel('Name');
		this.page = page;
		this.permissionsButton = page.getByRole('button', {
			name: 'Permissions',
		});
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.urlInput = page.getByLabel('URL');
		this.viewableBySelect = page.getByLabel('Viewable By');
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.bookmarks}`
		);
	}
}
