/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

export class DisplayPageTemplatesPage {
	readonly page: Page;

	readonly newButton: Locator;
	readonly publishButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.newButton = page.getByText('New', {exact: true});
		this.publishButton = page.getByLabel('Publish', {exact: true});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.displayPageTemplates}`
		);
	}

	async publishNewTemplate({
		contentSubtype,
		contentType,
		name,
	}: {
		contentSubtype?: 'Basic Web Content';
		contentType: 'Web Content Article' | 'Blogs Entry';
		name: string;
	}) {
		await this.newButton.click();
		await this.page.getByRole('button', {name: 'Blank'}).click();
		await this.page.getByLabel('Name').fill(name);
		await this.page
			.getByLabel('Content Type')
			.selectOption({label: contentType});

		if (contentSubtype) {
			await this.page
				.getByLabel('Subtype')
				.selectOption({label: contentSubtype});
		}

		await this.page.getByRole('button', {name: 'Save'}).click();
		await this.publishButton.waitFor();
		await this.publishButton.click();
		await waitForSuccessAlert(
			this.page,
			'Success:The display page template was published successfully.'
		);
	}

	private async clickMoreActions(name: string) {
		await this.page
			.locator(
				'#_com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet_displayPagesSearchContainer .card-page-item'
			)
			.filter({hasText: name})
			.getByLabel('More actions')
			.click();
	}

	async markAsDefault(name: string) {
		await this.clickMoreActions(name);

		await this.page.once('dialog', (dialog) => {
			dialog.accept().catch(() => {});
		});

		await this.page
			.getByRole('menuitem', {
				exact: true,
				name: 'Mark as Default',
			})
			.click();

		await waitForSuccessAlert(this.page);
	}
}
