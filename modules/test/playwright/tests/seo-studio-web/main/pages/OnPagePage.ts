/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class OnPagePage {
	readonly page: Page;

	readonly filterButton: Locator;
	readonly filteredEmptyStateTitle: Locator;
	readonly insightsHeading: Locator;
	readonly lastScanLabel: Locator;
	readonly noInsightsEmptyStateDescription: Locator;
	readonly noInsightsEmptyStateTitle: Locator;
	readonly noScansEmptyStateDescription: Locator;
	readonly noScansEmptyStateTitle: Locator;
	readonly onPageHeading: Locator;
	readonly runScanNowButton: Locator;
	readonly tableRows: Locator;

	constructor(page: Page) {
		this.page = page;

		this.filterButton = this.page.getByRole('button', {name: 'Filter'});
		this.filteredEmptyStateTitle = this.page.getByText(
			'No Insights Match These Filters'
		);
		this.insightsHeading = this.page.getByRole('heading', {
			level: 3,
			name: 'Insights',
		});
		this.lastScanLabel = this.page.locator(
			'.seo-studio-section-header-last-scan'
		);
		this.noInsightsEmptyStateDescription = this.page.getByText(
			'There are no insights for the last scan.'
		);
		this.noInsightsEmptyStateTitle =
			this.page.getByText('No Insights Found');
		this.noScansEmptyStateDescription = this.page.getByText(
			'Run a scan to see insights for your pages.'
		);
		this.noScansEmptyStateTitle = this.page.getByText('No Scans Yet');
		this.onPageHeading = this.page.getByRole('heading', {
			level: 2,
			name: 'On Page',
		});
		this.runScanNowButton = this.page.getByRole('button', {
			name: 'Run scan now',
		});
		this.tableRows = this.page.locator('.table tbody tr');
	}

	async applyFilter(filterLabel: string, itemLabels: string[]) {
		await this.filterButton.click();

		const dropdownMenu = this.page.locator('.dropdown-menu');

		await dropdownMenu.getByRole('menuitem', {name: filterLabel}).click();

		for (const itemLabel of itemLabels) {
			await dropdownMenu.getByRole('checkbox', {name: itemLabel}).check();
		}

		await dropdownMenu.getByRole('button', {name: 'Add Filter'}).click();
	}

	async emptyStateIsVisible(variant: 'no-insights' | 'no-scans') {
		if (variant === 'no-scans') {
			await expect(this.noScansEmptyStateTitle).toBeVisible();
			await expect(this.noScansEmptyStateDescription).toBeVisible();
		}
		else {
			await expect(this.noInsightsEmptyStateTitle).toBeVisible();
			await expect(this.noInsightsEmptyStateDescription).toBeVisible();
		}
	}

	activeFilterChip(text: string): Locator {
		return this.page.getByRole('button', {name: text});
	}

	getInsightRow(name: string): Locator {
		return this.page.getByRole('row', {
			name: new RegExp(`(^|\\s)${name}(\\s|$)`),
		});
	}

	async getInsightNamesInOrder(): Promise<string[]> {
		const rows = await this.tableRows.all();

		const names = await Promise.all(
			rows.map((row) => row.locator('td').first().innerText())
		);

		return names.map((name) => name.trim());
	}

	async sortByColumn(name: string) {
		await this.page
			.getByRole('columnheader', {name})
			.getByRole('button')
			.click();
	}

	async selectInsight(name: string) {
		await this.getInsightRow(name).getByText(name, {exact: true}).click();
	}

	async goto(siteFriendlyUrlPath: string) {
		await this.page.goto(`/web${siteFriendlyUrlPath}/content-seo`);

		await this.onPageHeading.waitFor();
	}
}
