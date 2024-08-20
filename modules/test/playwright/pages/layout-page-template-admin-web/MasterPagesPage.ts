/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';
import {PageEditorPage} from '../layout-content-page-editor-web/PageEditorPage';

export class MasterPagesPage {
	readonly page: Page;

	readonly newButton: Locator;
	readonly pageEditorPage: PageEditorPage;

	constructor(page: Page) {
		this.page = page;

		this.newButton = page.getByText('New', {exact: true});
		this.pageEditorPage = new PageEditorPage(this.page);
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

		const templateCard = this.getMasterCard(name);

		await templateCard.getByLabel('More actions').waitFor();

		await templateCard.locator('.custom-control.custom-checkbox').waitFor();
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
