/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {GlobalMenuPage} from '../../../../pages/product-navigation-applications-menu/GlobalMenuPage';
import {waitForPageToBeLoaded} from '../../../../utils/waitForPageToBeLoaded';

type FilterLabel = 'All Validations' | 'Failed' | 'Ignored' | 'Passed';
type SummaryLabel = 'Failed' | 'Passed' | 'Total';

const FILTER_LABELS: FilterLabel[] = [
	'All Validations',
	'Failed',
	'Ignored',
	'Passed',
];

const RESULTS_RESOURCE_FRAGMENT = 'get_production_readiness_results';

export class ProductionReadinessPage {
	readonly dashboard: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;
	readonly summary: Locator;
	readonly tabLink: Locator;

	constructor(page: Page) {
		this.page = page;

		this.dashboard = page.locator('.production-readiness-dashboard');
		this.globalMenuPage = new GlobalMenuPage(page);
		this.summary = page.locator(
			'[data-testid="production-readiness-summary"]'
		);
		this.tabLink = page.getByRole('link', {
			exact: true,
			name: 'Production Readiness',
		});
	}

	categoryPanelButton(): Locator {
		return this.dashboard.locator('button.panel-header[aria-expanded]');
	}

	async goto() {
		await this.globalMenuPage.goToHome();
		await this.globalMenuPage.goToControlPanel('Server Administration');

		await Promise.all([
			this.page.waitForResponse(
				(response) =>
					response.url().includes(RESULTS_RESOURCE_FRAGMENT) &&
					response.status() === 200
			),
			this.tabLink.click(),
		]);

		await waitForPageToBeLoaded(this.page);

		await expect(this.summary).toBeVisible();
	}

	async summaryCount(label: SummaryLabel): Promise<number> {
		const text = await this.summary
			.locator(
				`[data-testid="production-readiness-count-${label.toLowerCase()}"]`
			)
			.innerText();

		const value = parseInt(text.trim(), 10);

		if (Number.isNaN(value)) {
			throw new Error(
				`Summary count "${label}" did not render a numeric value (got "${text}")`
			);
		}

		return value;
	}

	async ignoredCount(): Promise<number> {
		const [failed, passed, total] = await Promise.all([
			this.summaryCount('Failed'),
			this.summaryCount('Passed'),
			this.summaryCount('Total'),
		]);

		return total - failed - passed;
	}

	filterButton(label: FilterLabel): Locator {
		return this.page.getByRole('button', {exact: true, name: label});
	}

	async selectFilter(label: FilterLabel) {
		await this.filterButton(label).click();

		for (const filterLabel of FILTER_LABELS) {
			await expect(this.filterButton(filterLabel)).toHaveAttribute(
				'aria-pressed',
				filterLabel === label ? 'true' : 'false'
			);
		}
	}

	ruleRow(ruleKey: string): Locator {
		return this.dashboard.locator(`[data-rule-key="${ruleKey}"]`);
	}

	ruleRows(): Locator {
		return this.dashboard.locator('[data-rule-key]');
	}

	ignoreButton(ruleKey: string): Locator {
		return this.ruleRow(ruleKey).getByRole('button');
	}

	async isIgnored(ruleKey: string): Promise<boolean> {
		const label =
			await this.ignoreButton(ruleKey).getAttribute('aria-label');

		return Boolean(label?.startsWith('Unignore'));
	}

	async expandAllCategoryPanels() {
		const collapsed = this.dashboard.locator(
			'button.panel-header[aria-expanded="false"]'
		);

		for (let i = 0; i < 20; i++) {
			if ((await collapsed.count()) === 0) {
				return;
			}

			await collapsed.first().click();
		}
	}

	async firstRuleKey(): Promise<string> {
		const row = this.ruleRows().first();

		await expect(row).toBeVisible();

		const ruleKey = await row.evaluate(
			(element) => (element as HTMLElement).dataset.ruleKey
		);

		if (!ruleKey) {
			throw new Error('The first rule row has no rule key');
		}

		return ruleKey;
	}

	async toggleIgnore(ruleKey: string) {
		const button = this.ignoreButton(ruleKey);

		await expect(button).toBeEnabled();

		await Promise.all([
			this.page.waitForResponse(
				(response) =>
					response.url().includes(RESULTS_RESOURCE_FRAGMENT) &&
					response.status() === 200
			),
			button.click(),
		]);

		await expect(this.summary).toBeVisible();

		await this.expandAllCategoryPanels();
	}

	async unignoreRule(ruleKey: string) {
		await this.goto();

		await this.selectFilter('Ignored');

		await this.expandAllCategoryPanels();

		if (await this.ruleRow(ruleKey).count()) {
			await this.toggleIgnore(ruleKey);
		}
	}
}
