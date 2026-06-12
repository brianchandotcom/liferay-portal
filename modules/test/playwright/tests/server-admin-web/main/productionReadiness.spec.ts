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
		await test.step('Open the Production Readiness tab', async () => {
			await productionReadinessPage.goto();
		});

		await test.step('Check the inline summary counts', async () => {
			for (const label of ['Failed', 'Passed', 'Total'] as const) {
				expect(
					await productionReadinessPage.summaryCount(label)
				).toBeGreaterThanOrEqual(0);
			}
		});

		await test.step('Check the category sections', async () => {
			expect(
				await productionReadinessPage.categoryPanelButton().count()
			).toBeGreaterThan(0);
		});

		await test.step('Check the single-select filter pills', async () => {
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
		});

		await checkAccessibility({page: productionReadinessPage.page});
	}
);

test(
	'LPD-87225 - Ignoring a rule updates the summary counts and the status filters.',
	{tag: '@LPD-87225'},
	async ({productionReadinessPage}) => {
		await productionReadinessPage.goto();

		await productionReadinessPage.selectFilter('Failed');

		await productionReadinessPage.expandAllCategoryPanels();

		test.skip(
			!(await productionReadinessPage.ruleRows().count()),
			'There are no failed production readiness rules'
		);

		const targetRuleKey = await productionReadinessPage.firstRuleKey();

		const failedBaseline =
			await productionReadinessPage.summaryCount('Failed');
		const ignoredBaseline = await productionReadinessPage.ignoredCount();
		const passedBaseline =
			await productionReadinessPage.summaryCount('Passed');

		try {
			await test.step('Ignore the rule', async () => {
				await productionReadinessPage.toggleIgnore(targetRuleKey);

				expect(
					await productionReadinessPage.summaryCount('Passed')
				).toBe(passedBaseline);
				expect(
					await productionReadinessPage.summaryCount('Failed')
				).toBe(failedBaseline - 1);
				expect(await productionReadinessPage.ignoredCount()).toBe(
					ignoredBaseline + 1
				);
			});

			await test.step('Failed filter hides ignored rule', async () => {
				await expect(
					productionReadinessPage.ruleRow(targetRuleKey)
				).toHaveCount(0);
			});

			await test.step('Ignored filter shows ignored rule', async () => {
				await productionReadinessPage.selectFilter('Ignored');

				await productionReadinessPage.expandAllCategoryPanels();

				await expect(
					productionReadinessPage.ruleRow(targetRuleKey)
				).toBeVisible();

				expect(
					await productionReadinessPage.isIgnored(targetRuleKey)
				).toBe(true);
			});

			await test.step('Unignore and restore the counts', async () => {
				await productionReadinessPage.toggleIgnore(targetRuleKey);

				await productionReadinessPage.selectFilter('All Validations');

				await productionReadinessPage.expandAllCategoryPanels();

				expect(
					await productionReadinessPage.isIgnored(targetRuleKey)
				).toBe(false);

				expect(
					await productionReadinessPage.summaryCount('Passed')
				).toBe(passedBaseline);
				expect(
					await productionReadinessPage.summaryCount('Failed')
				).toBe(failedBaseline);
				expect(await productionReadinessPage.ignoredCount()).toBe(
					ignoredBaseline
				);
			});
		}
		finally {

			// Unignore the target rule even when an assertion failed, since
			// the environment is shared

			await productionReadinessPage.unignoreRule(targetRuleKey);
		}
	}
);
