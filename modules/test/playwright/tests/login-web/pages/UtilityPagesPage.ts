/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../utils/portletUrls';

export class UtilityPagesPage {
	readonly newButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.newButton = page.getByRole('button', {name: 'New'});
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.utilityPages}`
		);
	}

	async add(title: string, pageType: string) {
		await this.newButton.click();

		await this.page.getByRole('menuitem', {name: pageType}).click();
		await this.page.getByRole('button', {name: 'Blank'}).click();
		await this.page.getByPlaceholder('Name').fill(title);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('button', {name: 'Publish'}),
			timeout: 2000,
			trigger: this.page.getByRole('button', {name: 'Save'}),
		});
	}

	async deletePage(title: string) {
		const actionsPath = '//p[@title="' + title + '"]/../..';

		await this.page.locator(actionsPath).getByLabel('More actions').click();
		await this.page.getByRole('menuitem', {name: 'Delete'}).click();
		await this.page.getByRole('button', {name: 'Delete'}).click();
	}

	async markAsDefault(title: string) {
		const actionsPath = '//p[@title="' + title + '"]/../..';

		await this.page.locator(actionsPath).getByLabel('More actions').click();
		await this.page.getByRole('menuitem', {name: 'Edit'}).waitFor();
		await this.page
			.getByRole('menuitem', {name: 'Mark as Default'})
			.click();
	}
}
