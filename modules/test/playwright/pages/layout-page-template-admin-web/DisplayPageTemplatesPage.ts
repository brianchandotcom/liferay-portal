/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
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

	async clickMoreActions(name: string) {
		await this.page
			.locator(
				'#_com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet_displayPagesSearchContainer .card-page-item'
			)
			.filter({hasText: name})
			.getByLabel('More actions')
			.click();
	}

	async deleteAllDisplayPageTemplates() {
		await this.page
			.getByLabel('Select All Items on the Page')
			.setChecked(true);

		await this.page.getByRole('button', {name: 'Delete'}).click();

		await this.page
			.getByLabel('Delete Entries- Loading')
			.getByRole('button', {name: 'Delete'})
			.click();
	}

	async editTemplate(name: string) {
		await this.clickMoreActions(name);

		await this.page
			.getByRole('menuitem', {
				exact: true,
				name: 'Edit',
			})
			.click();

		await this.page
			.getByText('Select a Page Element', {exact: true})
			.waitFor();
	}

	async goToDisplayPageTemplateAction(action: string, cardNum: string) {
		const card = await this.page.locator(
			`[id="_com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet_displayPages_${cardNum}"]`
		);
		await card.getByLabel('More actions').waitFor();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: action,
			}),
			trigger: card.getByLabel('More actions', {
				exact: true,
			}),
		});
	}

	async goToAssetDisplayEntryAction(action: string) {
		await this.page.getByRole('button', {name: 'Actions'}).waitFor();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: action,
			}),
			trigger: this.page.getByLabel('Actions', {
				exact: true,
			}),
		});
	}

	async markAsDefault(name: string) {
		await this.clickMoreActions(name);

		this.page.once('dialog', (dialog) => {
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

	async publishNewTemplate({
		contentSubtype,
		contentType,
		name,
	}: {
		contentSubtype?: string;
		contentType: string;
		name: string;
	}) {
		await this.newButton.click();
		await this.page.getByRole('button', {name: 'Blank'}).click();
		await this.page.getByLabel('Name', {exact: true}).fill(name);
		await this.page
			.getByLabel('Content Type')
			.selectOption({label: contentType});

		if (contentSubtype) {
			await this.page
				.getByLabel('Subtype')
				.selectOption({label: contentSubtype});
		}

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForSuccessAlert(
			this.page,
			'Success:The display page template was created successfully.'
		);

		await this.publishButton.waitFor();
		await this.publishButton.click();

		await waitForSuccessAlert(
			this.page,
			'Success:The display page template was published successfully.'
		);
	}
}
