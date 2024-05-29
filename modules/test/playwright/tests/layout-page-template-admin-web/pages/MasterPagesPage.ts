/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PageEditorPage} from '../../../pages/layout-content-page-editor-web/PageEditorPage';
import {PORTLET_URLS} from '../../../utils/portletUrls';

export class MasterPagesPage {
	readonly page: Page;

	readonly pageEditorPage: PageEditorPage;

	readonly newButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.pageEditorPage = new PageEditorPage(this.page);

		this.newButton = page.getByText('New', {exact: true});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.masterPages}`
		);
	}

	async createNewMaster(name: string) {
		await this.newButton.click();
		await this.page.getByLabel('Name').fill(name);
		await this.page.getByRole('button', {name: 'Save'}).click();

		await this.pageEditorPage.publishPage();
	}

	async editMaster(name: string) {
		await this.getMasterCard(name).getByRole('link', {name}).click();

		await this.page.getByText('Configure Allowed Fragments').waitFor();
	}

	async openMasterActionsMenu(name: string) {
		await this.getMasterCard(name).getByLabel('More actions').click();
	}

	getMasterCard(name: string) {
		return this.page.locator('.card-page-item').filter({hasText: name});
	}
}
