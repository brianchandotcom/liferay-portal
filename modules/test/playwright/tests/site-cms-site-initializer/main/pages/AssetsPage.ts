/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../../utils/portletUrls';
import {DataSetPage} from './DataSetPage';

// Page for All, Content and Files page

export class AssetsPage {
	readonly page: Page;

	readonly dataSetFragmentPage: DataSetPage;
	readonly newButton: Locator;
	readonly table: {
		bodyRows: Locator;
		container: Locator;
		headRow: Locator;
	};

	constructor(page: Page) {
		this.page = page;

		this.dataSetFragmentPage = new DataSetPage(page);
		this.newButton = page.getByLabel('New');

		this.table = this.dataSetFragmentPage.table;
	}

	async gotoAll() {
		await this.page.goto(PORTLET_URLS.cmsAll);
		await this.page
			.getByRole('heading', {name: 'All Restricted Page'})
			.waitFor();
	}

	async gotoFiles() {
		await this.page.goto(PORTLET_URLS.cmsFiles);
		await this.page
			.getByRole('heading', {name: 'Files Restricted Page'})
			.waitFor();
	}

	async createContent(type: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: type}),
			trigger: this.newButton,
		});
	}

	getItem(filter: string) {
		return this.dataSetFragmentPage.getRow(filter);
	}

	async execBulkItemAction(action: string) {
		await this.dataSetFragmentPage.execBulkItemAction({action});
	}

	async execItemAction({
		action,
		filter,
	}: {
		action: 'Download' | 'Share' | 'View';
		filter: string;
	}) {
		await this.dataSetFragmentPage.execItemAction({
			action,
			filter,
		});
	}

	async changeVisualizationMode(
		...args: Parameters<DataSetPage['changeVisualizationMode']>
	) {
		await this.dataSetFragmentPage.changeVisualizationMode(...args);
	}
}
