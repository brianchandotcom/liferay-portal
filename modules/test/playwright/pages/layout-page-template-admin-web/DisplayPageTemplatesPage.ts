/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class DisplayPageTemplatesPage {
	readonly page: Page;

	readonly newButton: Locator;
	readonly publishButton: Locator;
	readonly successfulMessage: Locator;

	constructor(page: Page) {
		this.page = page;

		this.newButton = page.getByText('New', {exact: true});
		this.publishButton = page.getByLabel('Publish', {exact: true});
		this.successfulMessage = page.getByText(
			'Success:Your request completed successfully.'
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.displayPageTemplates}`
		);
	}

	async publishNewDisplayPageTemplate(name: string) {
		await this.newButton.click();
		await this.page.getByRole('button', {name: 'Blank'}).click();
		await this.page.getByLabel('Name').fill(name);
		await this.page
			.getByLabel('Content Type')
			.selectOption({label: 'Web Content Article'});
		await this.page
			.getByLabel('Subtype')
			.selectOption({label: 'Basic Web Content'});
		await this.page.getByRole('button', {name: 'Save'}).click();
		await this.publishButton.waitFor();
		await this.publishButton.click();
	}

	async clickPageTemplateMoreActions(name: string) {
		await this.page
			.locator(
				'#_com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet_displayPagesSearchContainer .card-page-item'
			)
			.filter({hasText: name})
			.getByLabel('More actions')
			.click();
	}

	async markPageTemplateAsDefault(name: string) {
		await this.clickPageTemplateMoreActions(name);
		await this.page.once('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});
		await this.page
			.getByRole('menuitem', {
				exact: true,
				name: 'Mark as Default',
			})
			.click();
		await this.successfulMessage.waitFor();
	}
}
