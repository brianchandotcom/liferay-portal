/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PARTNER_SITE_FRIENLY_URL_PATH} from '../../utils/constants';

export class MDFClaimListPage {
	readonly actionButton: Locator;
	readonly dateSubmittedAfterDateInput: Locator;
	readonly dateSubmittedBeforeDateInput: Locator;
	readonly activityPartnerButton: Locator;
	readonly dateSubmittedButton: Locator;
	readonly claimStatusButton: Locator;
	readonly applyFilterButton: Locator;
	readonly cleanSearch: Locator;
	readonly completedTab: Locator;
	readonly completeMenuItem: Locator;
	readonly exportClaimButton: Locator;
	readonly filterButton: Locator;
	readonly heading: Locator;
	readonly mdfClaimHeading: Locator;
	readonly noEntriesFoundMessage: Locator;
	readonly openTab: Locator;
	readonly page: Page;
	readonly searchInput: Locator;
	readonly showMoreButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.actionButton = this.page
			.getByRole('cell', {name: 'Action Button'})
			.first();
		this.dateSubmittedAfterDateInput = this.page
			.locator('div')
			.filter({hasText: /^Claim Submitted On Or After$/})
			.locator('#basicInputText');
		this.dateSubmittedBeforeDateInput = this.page
			.locator('div')
			.filter({hasText: /^Claim Submitted On Or Before$/})
			.locator('#basicInputText');
		this.activityPartnerButton = this.page.getByRole('button', {
			name: 'Partner',
		});
		this.dateSubmittedButton = this.page.getByRole('button', {
			name: 'Date Submitted',
		});
		this.claimStatusButton = this.page.getByRole('button', {
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
		this.exportClaimButton = this.page.getByRole('link', {
			name: 'Export MDF Claim',
		});
		this.filterButton = this.page.getByRole('button', {
			exact: true,
			name: 'Filter',
		});
		this.heading = this.page.getByText('MDF Claim', {exact: true});
		this.mdfClaimHeading = this.page.getByText('MDF Claim', {exact: true});
		this.noEntriesFoundMessage = this.page.getByText(
			'Info:No entries were found'
		);
		this.openTab = this.page.getByRole('tab', {exact: true, name: 'Open'});
		this.searchInput = this.page.getByPlaceholder('Search');
		this.showMoreButton = this.page
			.getByRole('list')
			.getByText('Show more');
	}

	async clearAllFilters() {
		await this.page
			.getByRole('button', {
				name: 'Clear All Filters',
			})
			.click();
	}

	async filterByDateSubmitted(
		dateSubmittedAfterDate: string,
		dateSubmittedBeforeDate: string
	) {
		await this.filterButton.click();

		await this.dateSubmittedButton.click();

		await this.dateSubmittedAfterDateInput.fill(dateSubmittedAfterDate);
		await this.dateSubmittedBeforeDateInput.fill(dateSubmittedBeforeDate);

		await this.applyFilterButton.click();
	}

	async filterByPartner(partner: string) {
		await this.filterButton.click();

		await this.activityPartnerButton.click();

		await this.showMoreButton.click();

		await this.page.getByLabel(partner).check();

		await this.applyFilterButton.click();
	}

	async filterBySearchInput(text: string) {
		await this.searchInput.click();

		await this.searchInput.fill(text);

		await this.searchInput.press('Enter');
	}

	async filterByStatus(status: string) {
		await this.filterButton.click();

		await this.claimStatusButton.click();

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

	async getRenderedStartActPeriod(startActPeriod: string) {
		return this.page.getByRole('cell', {name: startActPeriod}).first();
	}

	async getRenderedStatus(status: string) {
		return this.page.getByRole('cell', {name: status}).first();
	}

	async goto() {
		await this.page.goto(
			`${PARTNER_SITE_FRIENLY_URL_PATH}/marketing/mdf-claims`,
			{
				waitUntil: 'commit',
			}
		);
	}
}
