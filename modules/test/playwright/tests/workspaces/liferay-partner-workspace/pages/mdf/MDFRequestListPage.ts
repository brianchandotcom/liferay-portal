/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PARTNER_SITE_FRIENLY_URL_PATH} from '../../utils/constants';

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
	readonly heading: Locator;
	readonly mdfRequestHeading: Locator;
	readonly newRequestButton: Locator;
	readonly noEntriesFoundMessage: Locator;
	readonly openTab: Locator;
	readonly page: Page;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.page = page;

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
		this.heading = page.getByRole('heading', {
			name: 'MDF Requests',
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

	async filterByActivityPeriod(
		activityAfterDate: string,
		activityBeforeDate: string
	) {
		await this.filterButton.click();

		await this.activityPeriodButton.click();

		await this.activityAfterDateInput.fill(activityAfterDate);
		await this.activityBeforeDateInput.fill(activityBeforeDate);

		await this.applyFilterButton.click();
	}

	async filterByPartner(partner: string) {
		await this.filterButton.click();

		await this.activityPartnerButton.click();

		await this.page.getByLabel(partner).last().check();

		await this.applyFilterButton.click();
	}

	async filterBySearchInput(text: string) {
		await this.searchInput.click();

		await this.searchInput.fill(text);

		await this.searchInput.press('Enter');
	}

	async filterByStatus(status: string) {
		await this.filterButton.click();

		await this.activityStatusButton.click();

		await this.page.getByLabel(status).check();

		await this.applyFilterButton.click();
	}

	async getRenderedCampaignName(campaignName: string) {
		return this.page.getByRole('cell', {name: campaignName}).first();
	}

	async getRenderedClaimed(claimed: string) {
		return this.page.getByRole('cell', {name: claimed}).first();
	}

	async getRenderedDateSubmitted(dateSubmitted: string) {
		return this.page.getByRole('cell', {name: dateSubmitted}).first();
	}

	async getRenderedEndActPeriod(endActPeriod: string) {
		return this.page.getByRole('cell', {name: endActPeriod}).first();
	}

	async getRenderedPartnerName(partnerName: string) {
		return this.page.getByRole('cell', {name: partnerName}).first();
	}

	async getRenderedRequested(valueRequested: string) {
		return this.page
			.getByRole('cell', {exact: true, name: valueRequested})
			.first();
	}

	async getRenderedRequestId(requestId: string) {
		return this.page.getByRole('cell', {name: requestId}).first();
	}

	async getRenderedRow(campaignName: string) {
		const row = this.page.locator('tr').filter({hasText: campaignName});

		const claimed = await row.locator('td').nth(1).innerText();
		const requestId = await row.locator('td').nth(0).innerText();
		const status = await row.locator('td').nth(2).innerText();
		const submitDate = await row.locator('td').nth(3).innerText();

		return {claimed, requestId, status, submitDate};
	}

	async getRenderedStartActPeriod(startActPeriod: string) {
		return this.page.getByRole('cell', {name: startActPeriod}).first();
	}

	async getRenderedStatus(status: string) {
		return this.page.getByRole('cell', {name: status}).first();
	}

	async goto() {
		await this.page.goto(
			`${PARTNER_SITE_FRIENLY_URL_PATH}/marketing/mdf-requests`,
			{
				waitUntil: 'commit',
			}
		);
	}
}
