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

	async gotoFragmentSet(name: string) {
		await this.page
			.getByRole('menuitem', {
				exact: true,
				name,
			})
			.click();

		await this.page
			.locator('.sheet-title')
			.getByText(name, {exact: true})
			.waitFor();
	}

	async clickAction(action: string, title: string) {
		const actionsPath = '//p[@title="' + title + '"]/../..';

		await this.page.locator(actionsPath).getByLabel('More actions').click();
		await this.page.getByRole('menuitem', {name: action}).click();

		await waitForSuccessAlert(this.page);
	}

	async copyFragment(title: string) {
		await this.clickAction('Make a Copy', title);

		await waitForSuccessAlert(this.page);
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
		await this.gotoFragmentSet(setName);

		await this.page.getByRole('button', {name: 'Add'}).click();

		await this.page.getByRole('heading', {name: 'Add Fragment'}).waitFor();

		await this.page.getByRole('button', {name: 'Next'}).click();

		await this.page.getByLabel('Name').fill(name);

		await this.page.getByText('Add', {exact: true}).click();

		await waitForSuccessAlert(this.page);
	}

	async deleteFragment(title: string) {
		await this.clickAction('Delete', title);

		await this.page.getByRole('button', {name: 'Delete'}).click();

		await waitForSuccessAlert(this.page);
	}

	async markAsDefault(title: string) {
		this.page.on('dialog', (dialog) => dialog.accept());

		await this.clickAction('Mark as Default', title);

		await waitForSuccessAlert(this.page);
	}

	async renameFragment(newName: string, oldName: string) {
		await this.clickAction('Rename', oldName);

		await this.page.getByLabel('Name', {exact: true}).fill(newName);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForSuccessAlert(this.page);
	}
}
