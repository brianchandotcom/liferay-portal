/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {BlogsPage} from './BlogsPage';

export class BlogsEditBlogEntryPage {
	readonly page: Page;

	readonly blogsPage: BlogsPage;
	readonly publishButton: Locator;
	readonly contentEditor: Locator;

	constructor(page: Page) {
		this.page = page;

		this.blogsPage = new BlogsPage(page);
		this.contentEditor = page.locator(
			'#_com_liferay_blogs_web_portlet_BlogsAdminPortlet_contentEditor.cke_editable'
		);
		this.publishButton = page.getByRole('button', {name: 'Publish'});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.blogsPage.goto(siteUrl);
		await this.blogsPage.goToCreateBlogEntry();
	}

	private async editBlogEntryAddfriendlyUrl({
		categories,
	}: {
		categories: string[];
	}) {
		const fieldset = await this.page.locator(
			'#_com_liferay_blogs_web_portlet_BlogsAdminPortlet_friendly-url'
		);

		if (await fieldset.locator('.panel-body').isHidden()) {
			await fieldset.getByRole('button', {name: 'Friendly URL'}).click();
		}

		await fieldset.getByText('Use a Customized URL').click();
		await fieldset.getByRole('button', {name: 'Select'}).click();

		const categoriesSelectorIframe = await this.page.frameLocator(
			'iframe[title="Filter by Categories"]'
		);

		await categoriesSelectorIframe.getByText('Test').click();

		for (const categoryName of categories) {
			await categoriesSelectorIframe.getByText(categoryName).click();
		}

		await this.page
			.getByLabel('Filter by Categories')
			.getByRole('button', {name: 'Select'})
			.click();
	}

	async editBlogEntry({
		content,
		friendlyUrl,
		publish = true,
		title,
	}: {
		content: string;
		friendlyUrl?: {categories: string[]};
		publish?: boolean;
		title: string;
	}) {
		await this.page.getByPlaceholder('Title *').fill(title);

		await this.contentEditor.fill(content);

		const {categories} = friendlyUrl;

		if (categories?.length) {
			await this.editBlogEntryAddfriendlyUrl({
				categories,
			});
		}

		if (publish) {
			await this.publishButton.click();
		}
	}
}
