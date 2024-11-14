/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';

export class TagsEditPage {
	readonly nameInput: Locator;
	readonly saveButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.nameInput = page.getByPlaceholder('Name');
		this.saveButton = page.getByRole('button', {
			name: 'Save',
		});
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.tagsAdmin}/new`
		);
	}

	async add(title: string, siteUrl?: Site['friendlyUrlPath']) {
		await this.goto(siteUrl);

		await this.nameInput.fill(title);
		await this.saveButton.click();
	}
}
