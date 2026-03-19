/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {claySamplePageTest} from './fixtures/claySamplePageTest';
import {TabName} from './pages/ClaySamplePage';

const test = mergeTests(claySamplePageTest);

test(
	'It can render the sample UI properly',
	{tag: '@LPD-72010'},
	async ({claySamplePage, page}) => {
		test.slow();

		const tabEntries = Object.entries(TabName);

		expect(tabEntries.length).toBeGreaterThan(0);

		for (const [key, name] of tabEntries) {
			await test.step(`The ${name} is rendered properly`, async () => {
				await claySamplePage.selectTab(name);

				const tab = page.getByRole('tab', {exact: true, name});

				await expect(tab).toBeVisible();
				await expect(tab).toHaveAttribute('aria-selected', 'true');

				const tabPanel = page.getByRole('tabpanel', {
					exact: true,
					name,
				});

				await expect(tabPanel).toBeVisible();
				await expect(tabPanel).toMatchAriaSnapshot({
					name: `${key}.yaml`,
					timeout: 5000,
				});
			});
		}
	}
);
