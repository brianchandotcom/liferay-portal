/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {PagesAdminPage} from '../layout-admin-web/PagesAdminPage';
import {PageEditorPage} from '../layout-content-page-editor-web/PageEditorPage';

export class MasterPagesPage {
	readonly page: Page;

	readonly newButton: Locator;
	readonly pageEditorPage: PageEditorPage;
	readonly pageAdminPage: PagesAdminPage;

	constructor(page: Page) {
		this.page = page;

		this.newButton = page.getByText('New', {exact: true});
		this.pageAdminPage = new PagesAdminPage(this.page);
		this.pageEditorPage = new PageEditorPage(this.page);
	}

	async selectClientExtension({
		clientExtensionName,
		layoutTitle,
		siteUrl,
		type,
	}: {
		clientExtensionName: string;
		layoutTitle: string;
		openConfiguration?: boolean;
		siteUrl?: Site['friendlyUrlPath'];
		type?: 'globalCSS' | 'globalJS' | 'themeFavicon';
	}) {
		await this.goto(siteUrl);

		await this.gotoConfiguration(layoutTitle);

		await this.pageAdminPage.selectClientExtension({
			clientExtensionName,
			layoutTitle,
			openConfiguration: false,
			siteUrl,
			type,
		});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.masterPages}`
		);
	}

	async gotoConfiguration(name: string) {
		await this.page.getByRole('link', {exact: true, name}).click();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				name: 'Configure',
			}),
			trigger: this.page
				.locator('.control-menu-nav-item')
				.getByLabel('Options', {exact: true}),
		});
	}

	async createNewMaster(name: string) {
		await clickAndExpectToBeVisible({
			target: this.page.getByLabel('Name'),
			trigger: this.newButton,
		});

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

	async publishMaster(name: string) {
		await this.page.getByLabel(name, {exact: true}).click();
		await this.page.getByLabel('Publish').click();

		await this.page
			.getByRole('heading', {name: 'Page Templates'})
			.waitFor();
	}

	getMasterCard(name: string) {
		return this.page.locator('.card-page-item').filter({hasText: name});
	}
}
