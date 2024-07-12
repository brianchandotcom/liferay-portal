/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class MDFRequestListPage {
	readonly actionButton: Locator;
	readonly activityAfterDateInput: Locator;
	readonly activityBeforeDateInput: Locator;
	readonly activityPartnerButton: Locator;
	readonly activityPeriodButton: Locator;
	readonly activityStatusButton: Locator;
	readonly applyFilterButton: Locator;
	readonly cleanSearch: Locator;
	readonly clearAllFilters: Locator;
	readonly completedTab: Locator;
	readonly completeMenuItem: Locator;
	readonly exportRequestButton: Locator;
	readonly filterButton: Locator;
	readonly mdfRequestHeading: Locator;
	readonly newRequestButton: Locator;
	readonly noEntriesFoundMessage: Locator;
	readonly openTab: Locator;
	readonly page: Page;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.actionButton = page.getByRole('cell', { name: 'Action Button' }).first();
		this.activityAfterDateInput = page
			.locator('div')
			.filter({hasText: /^Activity Date On Or After$/})
			.locator('#basicInputText');
		this.activityBeforeDateInput = page
			.locator('div')
			.filter({hasText: /^Activity Date On Or Before$/})
			.locator('#basicInputText');
		this.activityPartnerButton = page.getByRole('button', {
			name: 'Partner',
		});
		this.activityPeriodButton = page.getByRole('button', {
			name: 'Activity Period',
		});
		this.activityStatusButton = page.getByRole('button', {name: 'Status'});
		this.applyFilterButton = page.getByRole('button', {name: 'Apply'});
		this.cleanSearch = page.getByLabel('Clean Search');
		this.clearAllFilters = page.getByText('Clear All Filters');
		this.completedTab = page.getByRole('tab', {
			exact: true,
			name: 'Completed',
		});
		this.completeMenuItem = page.getByRole('menuitem', { name: 'Complete' });
		this.exportRequestButton = page.getByRole('link', {
			name: 'Export MDF Report',
		});
		this.filterButton = page.getByRole('button', {name: 'Filter'});
		this.mdfRequestHeading = page.getByText('MDF Requests');
		this.newRequestButton = page.getByRole('button', {
			name: 'New Request',
		});
		this.noEntriesFoundMessage = page.getByText(
			'Info:No entries were found'
		);
		this.openTab = page.getByRole('tab', {exact: true, name: 'Open'});
		this.page = page;
		this.searchInput = page.getByPlaceholder('Search');
	}
	
	async createNewMDFRequestButton() {
		await this.newRequestButton.click();
	}

	async filterMDFRequestByPartner(partner: string) {
		await this.filterButton.click();
		await this.activityPartnerButton.click();

		await this.page.getByLabel(partner).check();
		await this.applyFilterButton.click();
	}
	
	async filterMDFRequestByPeriod(
		activityAfterDate: string,
		activityBeforeDate: string
	) {
		await this.filterButton.click();
		await this.activityPeriodButton.click();

		await this.activityAfterDateInput.fill(activityAfterDate);
		await this.activityBeforeDateInput.fill(activityBeforeDate);

		await this.applyFilterButton.click();
	}

	async filterMDFRequestByStatus(status: string) {
		await this.filterButton.click();
		await this.activityStatusButton.click();

		await this.page.getByLabel(status).check();
		await this.applyFilterButton.click();
	}

	async filterUsingSearchInput(text: string) {
		await this.searchInput.click();
		await this.searchInput.fill(text);
		await this.searchInput.press('Enter');
	} 

	async getCampaignName() {
		return this.page.locator('td:nth-child(4)').first();
	}

	async getClaimed(claimed: string) {
		return this.page.getByRole('cell', {name: claimed});
	}

	async getEndActPeriod(endActPeriod: string) {
		return this.page.getByRole('cell', {name: endActPeriod}).first();
	}

	async getGeneratedDataFromRequest(campaignName: string) {
		const row = this.page.locator('tr').filter({hasText: campaignName});
		const claimed = await row.locator('td').nth(1).innerText();
		const requestId = await row.locator('td').nth(0).innerText();
		const status = await row.locator('td').nth(2).innerText();
		const submitDate = await row.locator('td').nth(3).innerText();

		return {claimed, requestId, status, submitDate};
	}

	async getPartnerName(partnerName: string) {
		return this.page.getByRole('cell', {name: partnerName}).first();
	}

	async getRequested(valueRequested: string) {
		return this.page.getByRole('cell', {exact: true, name: valueRequested});
	}

	async getRequestId(requestId: string) {
		return this.page.getByRole('cell', {name: requestId});
	}

	async getStartActPeriod(startActPeriod: string) {
		return this.page.getByRole('cell', {name: startActPeriod}).first();
	}

	async getStatus(status: string) {
		return this.page.getByRole('cell', {name: status}).first();
	}

	async goto() {
		await this.page.goto('/web/liferay-partner/marketing/mdf-requests', {
			waitUntil: 'networkidle',
		});
	}
}
