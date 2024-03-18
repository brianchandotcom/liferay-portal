/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';

export class FragmentsPage {
	readonly page: Page;

	readonly successMessage: Locator;

	constructor(page: Page) {
		this.page = page;

		this.successMessage = this.page.getByText(
			'Success:Your request completed successfully.'
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.fragments}`
		);
	}

	async createFragmentSet(name: string) {
		await this.page.getByTitle('Add Fragment Set').click();

		await this.page.getByPlaceholder('Name').fill(name);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await this.successMessage.waitFor();
	}

	async createFragment(setName: string, name: string) {
		await this.page
			.getByRole('menuitem', {exact: true, name: setName})
			.click();

		await this.page.locator('.sheet-title').getByText(setName).waitFor();

		await this.page.getByRole('button', {name: 'Add'}).click();

		await this.page.getByRole('heading', {name: 'Add Fragment'}).waitFor();

		await this.page.getByRole('button', {name: 'Next'}).click();

		await this.page.getByLabel('Name').fill(name);

		await this.page.getByText('Add', {exact: true}).click();

		await this.successMessage.waitFor();
	}
}
