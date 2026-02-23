/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';

export const test = mergeTests(loginTest(), systemSettingsPageTest);

test(
	'Language keys are properly rendered in the UI',
	{tag: '@LPD-80034'},
	async ({page, systemSettingsPage}) => {
		await test.step('Navigate to Accessibility Menu', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Accessibility',
				'Accessibility Menu'
			);
		});

		await test.step('Assert that the title and description are properly rendered', async () => {
			await expect(
				page.getByRole('heading', {name: 'Accessibility Menu'})
			).toBeVisible();

			await expect(
				page.getByRole('checkbox', {name: 'Enable Accessibility Menu'})
			).toBeVisible();

			await expect(page.locator('.form-help-text')).toHaveText(
				'Enable the accessibility menu which can be accessed by tabbing focus to the quick access menu. When enabled, users are able to save their accessibility settings in the browser local storage when not signed in and in the database when signed in.'
			);
		});
	}
);

test(
	'Configuration admin UI can autogenerate a doc link',
	{tag: ['@LPS-194725']},
	async ({page, systemSettingsPage}) => {
		await test.step('Navigate to Bundle Blacklist configuration page', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Module Container',
				'Bundle Blacklist'
			);
		});

		await test.step('Assert hyperlink is available within an anchor tag, and it says “How does bundle blacklisting work?"', async () => {
			const hyperlinkLocator = page.getByRole('link', {
				name: 'How does bundle blacklisting work?',
			});

			await expect(hyperlinkLocator).toBeVisible();

			await expect(hyperlinkLocator).toHaveAttribute(
				'href',
				'https://learn.liferay.com/w/dxp/system-administration/installing-and-managing-apps/managing-apps/blacklisting-apps'
			);
		});
	}
);
