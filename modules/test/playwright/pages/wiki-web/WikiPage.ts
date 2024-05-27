/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class WikiPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.wikiAdmin}`
		);
	}

	async createNewWikiNode(wikiNodeTitle: string) {
		await this.page.getByRole('link', {name: 'Add Wiki'}).click();

		await this.page.getByLabel('Name').fill(wikiNodeTitle);

		await this.page.getByRole('button', {name: 'Save'}).click();
	}
}
