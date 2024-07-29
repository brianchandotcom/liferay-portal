/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PartnerHelper} from '../../helpers/PartnerHelper';

export class MDFRequestListPage {
	readonly actionButton: Locator;
	readonly activityAfterDateInput: Locator;
	readonly activityBeforeDateInput: Locator;
	readonly activityPartnerButton: Locator;
	readonly activityPeriodButton: Locator;
	readonly activityStatusButton: Locator;
	readonly applyFilterButton: Locator;
	readonly cleanSearch: Locator;
	readonly completedTab: Locator;
	readonly completeMenuItem: Locator;
	readonly exportRequestButton: Locator;
	readonly filterButton: Locator;
	readonly mdfRequestHeading: Locator;
	readonly newRequestButton: Locator;
	readonly noEntriesFoundMessage: Locator;
	readonly openTab: Locator;
	readonly page: Page;
	readonly partnerHelper: PartnerHelper;
	readonly searchInput: Locator;
	readonly site: Site;

	constructor(partnerHelper: PartnerHelper) {
		this.page = partnerHelper.page;
		this.partnerHelper = partnerHelper;
		this.site = partnerHelper.site;

		this.actionButton = this.page
			.getByRole('cell', {name: 'Action Button'})
			.first();
		this.activityAfterDateInput = this.page
			.locator('div')
			.filter({hasText: /^Activity Date On Or After$/})
			.locator('#basicInputText');
		this.activityBeforeDateInput = this.page
			.locator('div')
			.filter({hasText: /^Activity Date On Or Before$/})
			.locator('#basicInputText');
		this.activityPartnerButton = this.page.getByRole('button', {
			name: 'Partner',
		});
		this.activityPeriodButton = this.page.getByRole('button', {
			name: 'Activity Period',
		});
		this.activityStatusButton = this.page.getByRole('button', {
			name: 'Status',
		});
		this.applyFilterButton = this.page.getByRole('button', {name: 'Apply'});
		this.cleanSearch = this.page.getByLabel('Clean Search');
		this.completedTab = this.page.getByRole('tab', {
			exact: true,
			name: 'Completed',
		});
		this.completeMenuItem = this.page.getByRole('menuitem', {
			name: 'Complete',
		});
		this.exportRequestButton = this.page.getByRole('link', {
			name: 'Export MDF Report',
		});
		this.filterButton = this.page.getByRole('button', {
			exact: true,
			name: 'Filter',
		});
		this.mdfRequestHeading = this.page.getByText('MDF Requests');
		this.newRequestButton = this.page.getByRole('button', {
			name: 'New Request',
		});
		this.noEntriesFoundMessage = this.page.getByText(
			'Info:No entries were found'
		);
		this.openTab = this.page.getByRole('tab', {exact: true, name: 'Open'});
		this.searchInput = this.page.getByPlaceholder('Search');
	}

	async clearAllFilters() {
		await this.page
			.getByRole('button', {
				name: 'Clear All Filters',
			})
			.click();
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
		await this.page.goto(
			`/web${this.site.friendlyUrlPath}/marketing/mdf-requests`,
			{
				waitUntil: 'commit',
			}
		);
	}
}
