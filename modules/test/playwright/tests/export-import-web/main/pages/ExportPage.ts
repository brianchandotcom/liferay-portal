/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import getRandomString from '../../../../utils/getRandomString';
import {PORTLET_URLS} from '../../../../utils/portletUrls';

export class ExportPage {
	readonly page: Page;
	readonly newButton: Locator;
	constructor(page: Page) {
		this.page = page;
		this.newButton = page.getByRole('link', {
			name: 'Add FreeMarker (.ftl)',
		});
	}

	async exportPages() {
		await this.page.getByRole('link', {name: 'Custom Export'}).click();

		await this.page.getByLabel('Title').fill(getRandomString());

		await this.page.getByRole('button', {name: 'Export'}).click();

		await this.page
			.getByTestId('processResult')
			.first()
			.getByText('Successful')
			.waitFor();
	}

	async goto(siteKey: string) {
		await this.page.goto(`/group${siteKey}${PORTLET_URLS.export}`, {
			waitUntil: 'domcontentloaded',
		});
	}
}
