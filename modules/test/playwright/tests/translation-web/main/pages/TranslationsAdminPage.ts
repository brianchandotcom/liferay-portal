/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../../utils/portletUrls';
import {waitForAlert} from '../../../../utils/waitForAlert';

type TranslationEntry = {
	language: string;
	status: string;
	title: string;
};

export class TranslationsAdminPage {
	readonly page: Page;

	readonly emptyState: Locator;

	readonly heading: Locator;

	constructor(page: Page) {
		this.page = page;

		this.emptyState = this.page.getByText('No entries were found.');
		this.heading = this.page.getByRole('heading', {
			name: 'Translation Processes',
		});
	}

	async assertEmptyState() {
		await expect(this.emptyState).toBeVisible();
	}

	async assertNoEntry(title: string) {
		await expect(this.entryRow(title)).toBeHidden();
	}

	async assertEntry({language, status, title}: TranslationEntry) {
		const row = this.entryRow(title);

		await expect(row).toBeVisible();

		await expect(
			row.getByRole('cell', {exact: true, name: language})
		).toBeVisible();

		await expect(
			row.getByRole('cell', {exact: true, name: status})
		).toBeVisible();
	}

	async deleteEntry(title: string) {
		this.page.once('dialog', (dialog) => dialog.accept());

		await this.openEntryAction('Delete', title);

		await waitForAlert(this.page);
	}

	async editEntry(title: string) {
		await this.openEntryAction('Edit', title);
	}

	async goto(site: Site) {

		// After a publish the editor fires a deferred navigation that can
		// briefly bounce the admin list to the home page; retry until it renders

		const url = `/group${site.friendlyUrlPath}${PORTLET_URLS.translation}&p_p_lifecycle=0&p_p_state=maximized`;

		await expect(async () => {
			await this.page.goto(url);

			await expect(this.heading).toBeVisible({timeout: 5000});
		}).toPass({timeout: 20000});
	}

	private entryRow(title: string): Locator {
		return this.page.getByRole('row').filter({hasText: title});
	}

	private async openEntryAction(action: string, title: string) {
		const row = this.entryRow(title);

		await row.waitFor();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: action,
			}),
			trigger: row.getByRole('button', {name: 'Show Actions'}),
		});
	}
}
