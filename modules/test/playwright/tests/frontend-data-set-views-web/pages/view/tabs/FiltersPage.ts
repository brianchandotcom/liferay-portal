/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ViewPage} from '../ViewPage';

export class FiltersPage {
	readonly newDateRangeFilterModal: {
		filterBySelect: Locator;
	};
	private readonly newFilterButton: Locator;
	private readonly newFilterModal: {
		cancelButton: Locator;
		saveButton: Locator;
	};
	readonly page: Page;
	private readonly viewPage: ViewPage;

	constructor(page: Page) {
		this.newFilterButton = page
			.getByRole('button', {name: 'Add'})
			.and(page.getByTitle('Add'));
		this.newDateRangeFilterModal = {
			filterBySelect: page.getByLabel('Filter By'),
		};
		this.newFilterModal = {
			cancelButton: page.getByRole('button', {name: 'Cancel'}),
			saveButton: page.getByRole('button', {name: 'Save'}),
		};
		this.page = page;
		this.viewPage = new ViewPage(page);
	}

	async goto({
		dataSetLabel,
		viewLabel,
	}: {
		dataSetLabel: string;
		viewLabel: string;
	}) {
		await this.viewPage.goto({
			dataSetLabel,
			viewLabel,
		});

		await Promise.all([
			this.viewPage.selectTab('Filters'),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp.url().includes('/openapi.json')
			),
		]);
	}

	async openNewFilterModal({dropdownItemLabel}: {dropdownItemLabel: string}) {
		await expect(this.newFilterButton).toBeVisible();

		await this.newFilterButton.click();

		const menuItem = this.page.getByRole('menuitem', {
			name: dropdownItemLabel,
		});

		await expect(menuItem).toBeVisible();

		await menuItem.click();

		await expect(this.newFilterModal.saveButton).toBeVisible();
	}
}
