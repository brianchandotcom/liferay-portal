/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {oauth2ApplicationPagesTest} from '../../../fixtures/oauth2ApplicationPagesTest';

const test = mergeTests(oauth2ApplicationPagesTest, loginTest());

test.describe('OAuth2 Application ERC field', () => {
	test(
		'ERC field is readonly on the credentials tab',
		{tag: '@LPD-89572'},
		async ({oauth2ApplicationPage}) => {
			await oauth2ApplicationPage.goTo();

			await oauth2ApplicationPage.goToApplication('Analytics Cloud');

			await expect(oauth2ApplicationPage.ercInput).toBeVisible();

			await expect(oauth2ApplicationPage.ercInput).not.toBeEditable();

			await expect(
				oauth2ApplicationPage.page.getByRole('button', {name: 'Edit'})
			).toHaveCount(2);
		}
	);
});
