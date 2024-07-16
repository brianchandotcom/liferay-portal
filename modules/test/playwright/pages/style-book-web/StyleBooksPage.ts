/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class StyleBooksPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.styleBooks}`
		);
	}

	async createStyleBook(styleBookName: string) {
		await this.page.getByRole('button', {exact: true, name: 'Add'}).click();

		await this.page.getByPlaceholder('Name').fill(styleBookName);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await this.page
			.getByText('Success:Your request completed successfully.')
			.waitFor();

		const loadingAnimation = await this.page.locator(
			'.style-book-editor__page-preview .loading-animation'
		);

		await loadingAnimation.waitFor();
		await loadingAnimation.waitFor({state: 'hidden'});
	}

	async deleteStyleBook(styleBookName: string) {
		await this.goto();

		await this.page
			.locator(
				'input[name=_com_liferay_style_book_web_internal_portlet_StyleBookPortlet_keywords][type=search]'
			)
			.fill(styleBookName);

		await this.page.getByTitle('Search for', {exact: true}).click();

		await expect(
			this.page.getByText(`1 Result Found for "${styleBookName}"`)
		).toBeVisible();

		await this.page.getByLabel('More actions').click();

		await this.page.getByRole('menuitem', {name: 'Delete'}).click();

		await this.page.getByRole('button', {name: 'Delete'}).click();
	}
}
