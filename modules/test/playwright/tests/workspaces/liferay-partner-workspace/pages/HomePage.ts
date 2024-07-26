/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export class HomePage {
	readonly page: Page;
	readonly site: Site;

	constructor(page: Page, site: Site) {
		this.page = page;
		this.site = site;
	}

	async goto() {
		await this.page.goto(`/web${this.site.friendlyUrlPath}/home`, {
			waitUntil: 'commit',
		});
	}
}
