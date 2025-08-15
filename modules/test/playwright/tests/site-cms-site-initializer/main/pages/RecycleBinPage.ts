/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../../../utils/portletUrls';
import {DataSetPage} from './DataSetPage';

export class RecycleBinPage {
	readonly page: Page;
	readonly dataSetFragmentPage: DataSetPage;
	readonly deleteButton: Locator;
	readonly deleteItemConfirmationText: Locator;

	constructor(page: Page) {
		this.page = page;
		this.dataSetFragmentPage = new DataSetPage(page);
		this.deleteButton = page.getByText('Delete', {exact: true});
		this.deleteItemConfirmationText = page.getByText(
			'You are about to permanently'
		);
	}

	async execItemAction({action, filter}: {action: 'Delete'; filter: string}) {
		await this.dataSetFragmentPage.execItemAction({
			action,
			filter,
		});
	}

	async goto() {
		await expect(async () => {
			await this.page.goto(PORTLET_URLS.cmsRecycleBin);

			await this.page.locator('.fds').waitFor({timeout: 3000});
		}).toPass();
	}

	getItem(filter: string) {
		return this.dataSetFragmentPage.getRow(filter);
	}
}
