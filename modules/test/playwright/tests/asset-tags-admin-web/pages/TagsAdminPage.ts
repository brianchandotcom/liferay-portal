/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
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

		this.page.getByRole('button', {name: 'Delete'}).click();
	}

	async mergeTags(titles: string[]) {
		for (const i in titles) {
			await this.selectTag(titles[i]);
		}

		this.page.getByRole('button', {name: 'Merge'}).click();
	}

	async gotoAdd() {
		await this.newButton.click();
	}

	async gotoEdit(title: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Edit'}),
			trigger: this.page
				.getByRole('row', {name: title})
				.getByLabel('Show Actions'),
		});
	}

	async selectTag(title: string) {
		await this.page.getByLabel(title).check();
	}
}
