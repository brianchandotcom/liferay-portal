/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';

export class TagsAdminPage {
	readonly newButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.newButton = page.getByRole('link', {
			name: 'Add Tag',
		});
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.tagsAdmin}`
		);
	}

	async deleteTags(titles: string[]) {
		for (const i in titles) {
			await this.selectTag(titles[i]);
		}

		await this.page
			.getByRole('button', {exact: true, name: 'Actions'})
			.click();

		await this.page.getByRole('menuitem', {name: 'Delete'}).click();
	}

	async mergeTags(titles: string[]) {
		for (const i in titles) {
			await this.selectTag(titles[i]);
		}

		await this.page
			.getByRole('button', {exact: true, name: 'Actions'})
			.click();

		await this.page.getByRole('menuitem', {name: 'Merge'}).click();
	}

	async gotoAdd() {
		await this.newButton.click();
	}

	async gotoEdit(title: string) {
		await this.page
			.getByRole('row', {name: 'Select ' + title + ' 0 Show Actions'})
			.getByLabel('Show Actions')
			.click();

		await this.page.getByRole('menuitem', {name: 'Edit'}).click();
	}

	async selectTag(title: string) {
		await this.page.getByLabel(title).check();
	}
}
