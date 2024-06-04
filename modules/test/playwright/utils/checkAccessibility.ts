/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AxeBuilder from '@axe-core/playwright';
import {Page, expect} from '@playwright/test';

interface Props {
	bestPractices?: boolean;
	page: Page;
	selectors?: string[];
	selectorsToExclude?: string[];
	soft?: boolean;
}

/**
 * Check accessibility on a page.
 *
 * It uses the axe API to analyze a page and return a JSON object that lists
 * any accessibility issues found.
 *
 * @param bestPractices enables best practices
 * @param selectors an array of selectors to analyze
 * @param selectorsToExclude an array of selectors toexclude from the analysis
 * @param page current page
 * @param soft A flag to enable soft assertions
 */

const TAGS = ['wcag2a', 'wcag2aa', 'wcag21a', 'wcag21aa', 'wcag22aa'];

export async function checkAccessibility({
	bestPractices = false,
	page,
	selectors,
	selectorsToExclude,
	soft = true,
}: Props) {
	const tags = bestPractices ? [...TAGS, 'best-practice'] : TAGS;

	if (selectors) {
		for (const selector of selectors) {
			page.locator(selector).waitFor();
		}
	}

	const results = await new AxeBuilder({page})
		.withTags(tags)
		.include(selectors)
		.exclude(selectorsToExclude)
		.analyze();

	await (soft ? expect.soft : expect)(results.violations, 'Accessibility issues').toEqual([]);
}
