/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';

export const test = mergeTests(loginTest(), systemSettingsPageTest);

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
