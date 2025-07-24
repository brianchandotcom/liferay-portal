/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AxeBuilder from '@axe-core/playwright';
import {Page, expect} from '@playwright/test';

interface Params {
	bestPractices?: boolean;
	page: Page;
	selectors?: string[];
	selectorsToExclude?: string[];
	soft?: boolean;
}

function indentMultiline(text: string, spaces = 4): string {
	return text
		.split('\n')
		.map((line) => ' '.repeat(spaces) + line)
		.join('\n');
}

function formatAccessibility(violations: any[]): string {
	const USE_COLORS = process.stdout.isTTY;

	const RED = USE_COLORS ? '\x1b[31m' : '';
	const YELLOW = USE_COLORS ? '\x1b[33m' : '';
	const CYAN = USE_COLORS ? '\x1b[36m' : '';
	const WHITE = USE_COLORS ? '\x1b[37m' : '';
	const BOLD = USE_COLORS ? '\x1b[1m' : '';
	const RESET = USE_COLORS ? '\x1b[0m' : '';

	const output = [];

	output.push('\n');
	output.push(
		`${RED}${BOLD}Accessibility issues found: ${RESET}${WHITE}${
			violations.length
		} rules affecting ${violations.reduce(
			(acc, {nodes}) => acc + nodes.length,
			0
		)} node(s)${RESET}`
	);
	output.push(`${CYAN}${'='.repeat(80)}${RESET}`);

	for (const violation of violations) {
		output.push('');
		output.push(
			`${YELLOW}${BOLD}• RULE: ${RESET}${YELLOW}${violation.description}${RESET}`
		);
		output.push(`  Help   : ${violation.help}`);
		output.push(`  Docs   : ${violation.helpUrl}`);
		output.push(`  Impact : ${violation.impact}`);
		output.push('');

		for (let i = 0; i < violation.nodes.length; i++) {
			const node = violation.nodes[i];

			i > 0 && output.push(`${CYAN}    ${'-'.repeat(40)}${RESET}`);
			output.push(`${CYAN}  - Affected element:${RESET}`);
			output.push(`    ${node.html}`);
			output.push(`${CYAN}  - Target selector(s):${RESET}`);
			output.push(`    ${node.target.join(', ')}`);
			output.push(`${CYAN}  - Summary:${RESET}`);
			output.push(indentMultiline(node.failureSummary.trim()));
			output.push('');
		}

		output.push(`${CYAN}${'-'.repeat(80)}${RESET}`);
	}

	return output.join('\n');
}

/**
 * Check accessibility on a page.
 *
 * It uses the axe API to analyze a page and return a JSON object that lists
 * any accessibility issues found.
 *
 * @param bestPractices Enables best practices
 * @param selectors An array of selectors to analyze
 * @param selectorsToExclude An array of selectors toexclude from the analysis
 * @param page Current page
 * @param soft A flag to enable soft assertions
 */

const TAGS = ['wcag2a', 'wcag2aa', 'wcag21a', 'wcag21aa', 'wcag22aa'];

export async function checkAccessibility({
	bestPractices = false,
	page,
	selectors,
	selectorsToExclude,
	soft = true,
}: Params) {
	const tags = bestPractices ? [...TAGS, 'best-practice'] : TAGS;

	const axeBuilder = new AxeBuilder({page});

	if (selectors) {
		for (const selector of selectors) {
			await page.locator(selector).waitFor();

			axeBuilder.include(selector);
		}
	}

	if (selectorsToExclude) {
		for (const selector of selectorsToExclude) {
			axeBuilder.exclude(selector);
		}
	}

	const {violations} = await axeBuilder.withTags(tags).analyze();

	if (!violations.length) {
		(soft ? expect.soft : expect)(
			false,
			formatAccessibility(violations)
		).toBe(true);
	}
	else {
		expect(true, 'Accessibility issues').toBe(true);
	}
}
