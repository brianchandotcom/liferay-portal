/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import fillAndClickOutside from '../../utils/fillAndClickOutside';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

export class FragmentsPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.fragments}`
		);
	}

	async createFragmentSet(name: string) {
		await this.page.getByTitle('Add Fragment Set').click();

		const nameInput = this.page.getByPlaceholder('Name');

		await nameInput.waitFor();

		await fillAndClickOutside(this.page, nameInput, name);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForSuccessAlert(this.page);
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

		await waitForSuccessAlert(this.page);
	}
}
