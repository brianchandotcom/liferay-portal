/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {waitForAlert} from '../../utils/waitForAlert';

export class PageTemplatesPage {
	readonly page: Page;

	readonly newButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.newButton = page.getByText('New', {exact: true});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.pageTemplates}`
		);
	}

	async addContentPageTemplate(name: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				name: 'Content Page Template',
			}),
			trigger: this.newButton,
		});

		await this.page.getByRole('button', {name: 'Blank'}).click();

		await this.page.getByPlaceholder('Name', {exact: true}).fill(name);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(
			this.page,
			'Success:The page template was created successfully.'
		);
	}

	async addPageTemplateCollection(name: string) {
		await this.page.getByRole('link', {exact: true, name: 'New'}).click();

		await this.page
			.getByRole('heading', {name: 'Add Page Template Set'})
			.waitFor();

		await fillAndClickOutside(
			this.page,
			this.page.getByLabel('Name'),
			name
		);

		await this.page
			.getByRole('button', {exact: true, name: 'Save'})
			.click();

		await waitForAlert(this.page);
	}

	async addWidgetPageTemplate(name: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				name: 'Widget Page Template',
			}),
			trigger: this.newButton,
		});

		await this.page.getByPlaceholder('Name', {exact: true}).fill(name);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await this.page.getByRole('heading', {name}).waitFor();
	}

	async clickAction(action: string, title: string, collectionName?: string) {
		if (collectionName) {
			await this.page
				.getByRole('menuitem', {name: collectionName})
				.click();
		}

		await this.page
			.locator('.card-type-asset')
			.filter({hasText: title})
			.getByLabel('More actions')
			.click();

		await this.page.getByRole('menuitem', {name: action}).click();
	}

	async clickPageTemplateCollectionAction(action: string, title: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: action}),
			trigger: this.page
				.getByRole('heading', {name: title})
				.getByLabel('Show Actions'),
		});
	}

	async deletePageTemplate(name: string) {
		await this.clickAction('Delete', name);

		await this.page.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(this.page);
	}

	async deletePageTemplateCollection(name: string) {
		await this.clickPageTemplateCollectionAction('Delete', name);

		await this.page.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(this.page);
	}
}
