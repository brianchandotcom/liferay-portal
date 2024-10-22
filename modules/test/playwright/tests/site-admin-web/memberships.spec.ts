/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';

export const test = mergeTests(loginTest(), productMenuPageTest);

test(
	'Confirm search bar does not display for membership requests',
	{
		tag: '@LPD-36275',
	},
	async ({page, productMenuPage}) => {
		await productMenuPage.openProductMenuIfClosed();
		await productMenuPage.goToMemberships();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'View Membership Requests',
			}),
			trigger: page.getByLabel('Options', {exact: true}),
		});

		await expect(page.getByPlaceholder('Search for')).not.toBeVisible();
	}
);
