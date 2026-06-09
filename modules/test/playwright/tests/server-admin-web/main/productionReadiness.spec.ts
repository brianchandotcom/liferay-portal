/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {checkAccessibility} from '../../../utils/checkAccessibility';
import {productionReadinessPagesTest} from './fixtures/productionReadinessPagesTest';

export const test = mergeTests(productionReadinessPagesTest, loginTest());

test(
	'LPD-87225 - Production Readiness dashboard renders the summary, filters, and category sections and passes a11y review.',
	{tag: '@LPD-87225'},
	async ({productionReadinessPage}) => {

		// Open the Production Readiness tab

		await productionReadinessPage.goto();

		// Check the inline summary counts

		for (const label of ['Failed', 'Passed', 'Total'] as const) {
			expect(
				await productionReadinessPage.summaryCount(label)
			).toBeGreaterThanOrEqual(0);
		}

		// Check the category sections

		expect(
			await productionReadinessPage.categoryPanelButton().count()
		).toBeGreaterThan(0);

		// Check the single-select filter pills

		for (const label of [
			'All Validations',
			'Failed',
			'Ignored',
			'Passed',
		] as const) {
			await expect(
				productionReadinessPage.filterButton(label)
			).toBeVisible();
		}

		// Check the pagination bar

		await expect(
			productionReadinessPage.page.getByLabel('Items Per Page')
		).toBeVisible();

		await checkAccessibility({page: productionReadinessPage.page});
	}
);

test(
	'LPD-87225 - Ignoring a rule updates the summary counts and the status filters.',
	{tag: '@LPD-87225'},
	async ({productionReadinessPage}) => {

		// Open the Production Readiness tab and pick a currently failed
		// rule as the target

		await productionReadinessPage.goto();

		await productionReadinessPage.selectFilter('Failed');

		await productionReadinessPage.expandAllCategoryPanels();

		test.skip(
			!(await productionReadinessPage.ruleRows().count()),
			'There are no failed production readiness rules'
		);

		const targetRuleKey = await productionReadinessPage.firstRuleKey();

		// Record the baseline summary counts

		const failedBaseline =
			await productionReadinessPage.summaryCount('Failed');
		const ignoredBaseline = await productionReadinessPage.ignoredCount();
		const passedBaseline =
			await productionReadinessPage.summaryCount('Passed');

		try {

			// Ignore the rule

			await productionReadinessPage.toggleIgnore(targetRuleKey);

			expect(await productionReadinessPage.summaryCount('Passed')).toBe(
				passedBaseline
			);
			expect(await productionReadinessPage.summaryCount('Failed')).toBe(
				failedBaseline - 1
			);
			expect(await productionReadinessPage.ignoredCount()).toBe(
				ignoredBaseline + 1
			);

			// The Failed filter hides the ignored rule

			await expect(
				productionReadinessPage.ruleRow(targetRuleKey)
			).toHaveCount(0);

			// The Ignored filter shows it

			await productionReadinessPage.selectFilter('Ignored');

			await productionReadinessPage.expandAllCategoryPanels();

			await expect(
				productionReadinessPage.ruleRow(targetRuleKey)
			).toBeVisible();

			expect(await productionReadinessPage.isIgnored(targetRuleKey)).toBe(
				true
			);

			// Unignore the rule and check the counts are restored

			await productionReadinessPage.toggleIgnore(targetRuleKey);

			await productionReadinessPage.selectFilter('All Validations');

			await productionReadinessPage.expandAllCategoryPanels();

			expect(await productionReadinessPage.isIgnored(targetRuleKey)).toBe(
				false
			);

			expect(await productionReadinessPage.summaryCount('Passed')).toBe(
				passedBaseline
			);
			expect(await productionReadinessPage.summaryCount('Failed')).toBe(
				failedBaseline
			);
			expect(await productionReadinessPage.ignoredCount()).toBe(
				ignoredBaseline
			);
		}
		finally {

			// Unignore the target rule even when an assertion failed, since
			// the environment is shared

			await productionReadinessPage.unignoreRule(targetRuleKey);
		}
	}
);
