/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AxeBuilder from '@axe-core/playwright';
import {Page, expect} from '@playwright/test';

interface Props {
	page: Page;
}

/**
 * Check accessibility on a page.
 *
 * It uses the axe API to analyze a page and return a JSON object that lists
 * any accessibility issues found.
 *
 * @param page current page
 */

export async function checkAccessibility({page}: Props) {
	const results = await new AxeBuilder({page}).analyze();

	await expect(results.violations, 'Accessibility issues').toEqual([]);
}
