/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class BlogsPage {
	readonly blogContainer: Locator;
	readonly blogTitle: (title: string) => Locator;
	readonly noEntriesMessage: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.blogContainer = page.locator(
			'#portlet_com_liferay_blogs_web_portlet_BlogsPortlet'
		);
		this.blogTitle = (title) =>
			this.blogContainer.getByRole('link', {name: title});
		this.noEntriesMessage = page.getByRole('heading', {
			name: 'No entries were found.',
		});
		this.page = page;
	}
}
