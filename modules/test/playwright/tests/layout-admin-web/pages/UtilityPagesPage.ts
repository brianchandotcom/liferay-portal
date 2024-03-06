/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {getSiteUrl} from '../../../utils/siteUrl';

export class UtilityPagesPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goto() {
		const siteUrl = await getSiteUrl(this.page);

		await this.page.goto(
			`/group${siteUrl}/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_tabs1=utility-pages`
		);
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

	async goToEdit(title: string) {
		await this.goto();

		await this.page.getByLabel(title).waitFor();

		const href = await this.page
			.locator('div.card-row', {has: this.page.getByLabel(title)})
			.getByRole('link')
			.getAttribute('href');

		await this.page.goto(href);

		await this.page
			.getByRole('button', {exact: true, name: 'Publish'})
			.waitFor();
	}
}
