/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {BlogsPage} from './BlogsPage';

export class BlogsEditBlogEntryPage {
	readonly page: Page;

	readonly blogsPage: BlogsPage;
	readonly propertiesTab: Locator;
	readonly publishButton: Locator;
	readonly titlePlaceholder: Locator;

	constructor(page: Page) {
		this.page = page;

		this.blogsPage = new BlogsPage(page);
		this.propertiesTab = page.getByRole('tab', {name: 'Properties'});
		this.publishButton = page.getByRole('button', {name: 'Publish'});
		this.titlePlaceholder = page.getByPlaceholder(
			'Untitled Basic Web Content'
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.blogsPage.goto(siteUrl);
		await this.blogsPage.goToCreateBlogEntry();
	}

	async editBlogEntry(content: string, title: string) {
		await this.page.getByPlaceholder('Title *').waitFor();

		await this.page.getByPlaceholder('Title *').fill(title);

		await this.page.getByRole('paragraph').click();

		await this.page
			.locator(
				'[id="_com_liferay_blogs_web_portlet_BlogsAdminPortlet_contentEditor"]'
			)
			.fill(content);
	}
}
