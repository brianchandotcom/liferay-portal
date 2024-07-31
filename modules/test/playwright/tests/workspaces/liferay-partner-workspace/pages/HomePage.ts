/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PARTNER_SITE_FRIENLY_URL_PATH} from '../utils/constants';

export class HomePage {
	readonly heading: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.heading = this.heading = page.getByRole('heading', {
			name: 'Welcome to Partner Portal',
		});
		this.page = page;
	}

	async goto() {
		await this.page.goto(`${PARTNER_SITE_FRIENLY_URL_PATH}/home`);
	}
}
