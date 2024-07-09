/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class WikiPage {
	readonly page: Page;
	readonly publishButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.publishButton = page.getByRole('button', {name: 'Publish'});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.wikiAdmin}`
		);
	}

	async addSourceContentToWikiPage(content: string) {
		await this.page.getByLabel('Source').waitFor();

		await this.page.getByLabel('Source').click();

		await this.page
			.getByLabel(
				'Editor, _com_liferay_wiki_web_portlet_WikiAdminPortlet_contentEditor',
				{exact: true}
			)
			.fill(content);

		await this.page.getByLabel('Source').click();
	}

	async assertAddedImage(locator: Locator, image: string) {
		await expect(locator).toHaveAttribute('src', image);
	}

	async createNewWikiNode(wikiNodeTitle: string) {
		await this.page.getByRole('link', {name: 'Add Wiki'}).click();

		await this.page.getByLabel('Name').fill(wikiNodeTitle);

		await this.page.getByRole('button', {name: 'Save'}).click();
	}

	async goToEditFirstWikiPage() {
		await this.page
			.locator(
				'[id="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiPages_1"]'
			)
			.getByLabel('Show Actions')
			.click();
		await this.page.getByRole('menuitem', {name: 'Edit'}).click();
	}

	async goToFirstWikiPage() {
		await this.page
			.locator(
				'[id="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiPages_1"]'
			)
			.getByRole('link')
			.click();
	}

	async goToWikiNode(title: string) {
		await this.page.getByRole('link', {exact: true, name: title}).click();
	}

	async publishPage() {
		await this.publishButton.click();
	}
}
