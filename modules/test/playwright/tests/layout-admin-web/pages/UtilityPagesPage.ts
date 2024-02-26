/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {StaticPagesPage} from '../../layout-admin-web/pages/StaticPagesPage';

export class UtilityPagesPage {
	readonly page: Page;

	readonly staticPagesPage: StaticPagesPage;

	constructor(page: Page) {
		this.page = page;

		this.staticPagesPage = new StaticPagesPage(page);
	}

	async clickOnAction(action: string, title: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: action,
			}),
			trigger: this.page
				.locator('div.card-row', {has: this.page.getByTitle(title)})
				.getByRole('button'),
		});
	}

	async goto() {
		await this.staticPagesPage.goToUtilityPages();
	}

	async goToEdit(title: string) {
		await this.goto();

		await expect(this.page.getByTitle(title)).toBeVisible();

		const linkElement = this.page
			.locator('div.card-row', {has: this.page.getByTitle(title)})
			.getByRole('link');

		const href = await linkElement.getAttribute('href');

		await this.page.goto(href);

		await expect(
			this.page.getByRole('button', {exact: true, name: 'Publish'})
		).toBeVisible();
	}
}
