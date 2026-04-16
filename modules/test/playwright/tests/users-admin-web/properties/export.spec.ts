/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';

const test = mergeTests(loginTest(), usersAndOrganizationsPagesTest);

test(
	'Can export users with direct servlet context disabled',
	{tag: ['@LPD-81993', '@LPS-79937']},
	async ({page, usersAndOrganizationsPage}) => {
		page.on('dialog', (dialog) => dialog.accept());

		await usersAndOrganizationsPage.goToUsers();

		await expect(async () => {
			await page.getByTestId('headerOptions').click();

			await expect(
				page.getByRole('menuitem', {name: 'Export Users'})
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await page.getByRole('menuitem', {name: 'Export Users'}).click();

		await page.waitForTimeout(2000);
	}
);
