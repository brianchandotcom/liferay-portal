/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';

export class LockedItemsPage {
	readonly page: Page;
	readonly lockedPagesMenuItem: Locator;
	readonly lockedPagesTitle: Locator;
	readonly pageTitle: Locator;

	constructor(page: Page) {
		this.page = page;
		this.lockedPagesMenuItem = page.getByRole('menuitem', {
			name: 'Pages',
		});
		this.lockedPagesTitle = page
			.locator('p.sheet-title')
			.filter({hasText: /^Pages$/});
		this.pageTitle = page.getByRole('heading', {name: 'Locked Items'});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.lockedItems}`
		);
	}

	async goToLockedPages() {
		await this.lockedPagesMenuItem.click();
	}
}
