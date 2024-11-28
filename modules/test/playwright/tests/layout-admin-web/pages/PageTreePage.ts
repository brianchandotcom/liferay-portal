/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export class PageTreePage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async open() {
		if (
			!(await this.page
				.getByLabel('Product Menu')
				.locator('.treeview')
				.isVisible())
		) {
			await this.page
				.getByRole('button', {exact: true, name: 'Page Tree'})
				.click();

			await this.page
				.getByLabel('Product Menu')
				.locator('.treeview')
				.waitFor();
		}
	}
}
