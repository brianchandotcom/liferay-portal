/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class EditObjectViewPage {
	readonly filterBy: Locator;
	readonly filterType: Locator;
	readonly filterValue: Locator;
	readonly filtersTab: Locator;
	readonly newFilterButton: Locator;
	readonly saveFilter: Locator;
	readonly sidePanel: FrameLocator;
	readonly page: Page;

	constructor(page: Page) {
		this.sidePanel = page.frameLocator('iframe');

		this.filterBy = this.sidePanel.getByLabel('Filter By' + 'Mandatory');
		this.filterType = this.sidePanel.getByText('Select an Option');
		this.filtersTab = this.sidePanel.getByRole('tab', {name: 'Filters'});
		this.filterValue = this.sidePanel
			.getByLabel('New Filter')
			.locator('input[type="text"]');
		this.newFilterButton = this.sidePanel.getByRole('button', {
			name: 'New Filter',
		});
		this.saveFilter = this.sidePanel
			.getByLabel('New Filter')
			.getByText('Save');
	}

	async createFilter(
		filterType: 'Includes' | 'Excludes',
		objectRelationshipLabel: string,
		objectEntryId: string
	) {
		await this.filtersTab.click();

		await this.newFilterButton.click();

		await this.filterBy.click();

		await this.sidePanel
			.getByRole('option', {name: objectRelationshipLabel})
			.click();

		await this.filterType.click();

		await this.sidePanel.getByRole('option', {name: filterType}).click();

		await this.filterValue.click();

		await this.sidePanel.getByLabel(objectEntryId).check();

		await this.saveFilter.click();
	}
}
