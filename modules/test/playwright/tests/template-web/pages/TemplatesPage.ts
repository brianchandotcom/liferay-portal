/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';
import path from 'path';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../../utils/fillAndClickOutside';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {waitForAlert} from '../../../utils/waitForAlert';

export class TemplatesPage {
	readonly page: Page;

	readonly newButton: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.newButton = page.getByRole('button', {name: 'Add'});
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.templates}`
		);
	}

	async gotoWidgetTemplates(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.widgetTemplates}`
		);
	}

	async clickAction(action: string, title: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: action}),
			trigger: this.page
				.locator('tr')
				.filter({hasText: title})
				.locator('button.dropdown-toggle'),
		});
	}

	async copyInformationTemplate(title: string) {
		await this.clickAction('Make a Copy', title);

		await fillAndClickOutside(
			this.page,
			this.page.getByLabel('name'),
			`${title} (Copy)`
		);

		await this.page.getByRole('button', {name: 'Copy'}).click();

		await waitForAlert(this.page);
	}

	async createInformationTemplate({
		itemSubtype,
		itemType,
		name,
	}: {
		itemSubtype?: string;
		itemType: string;
		name: string;
	}) {
		await this.newButton.click();

		await this.page.getByLabel('Name', {exact: true}).fill(name);

		await this.page.getByLabel('Item Type').selectOption({label: itemType});

		if (itemSubtype) {
			await this.page
				.getByLabel('Item Subtype')
				.selectOption({label: itemSubtype});
		}

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(this.page);
	}

	async createWidgetTemplate(name: string, type: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				name: type,
			}),
			trigger: this.page.getByRole('button', {name: 'New'}),
		});

		// Wait until the editor is loaded

		await this.page.locator('.ddm_template_editor__App').waitFor();

		await fillAndClickOutside(
			this.page,
			this.page.getByPlaceholder('Untitled Template'),
			name
		);

		await this.saveTemplate();
	}

	async deleteInformationTemplate(title: string) {
		await this.clickAction('Delete', title);

		await this.page.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(this.page);
	}

	async editTemplate(name: string) {
		await this.page.getByRole('link', {exact: true, name}).click();
	}

	async importInformationTemplate(dirname: string, fileName: string) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page
				.locator('.dropdown-menu')
				.getByRole('menuitem', {name: 'Import Script'}),
			trigger: this.page
				.locator('.control-menu-nav-item')
				.getByLabel('Options', {exact: true}),
		});

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(
			path.join(dirname, '/dependencies/' + fileName)
		);

		await waitForAlert(this.page, `Success:${fileName} Imported`);
	}

	async saveTemplate() {
		await this.saveButton.click();

		// Wait for the redirection to the templates admin when the template is saved

		await this.page.waitForURL(
			(url) => !url.href.includes('ddmTemplateId=')
		);
	}
}
