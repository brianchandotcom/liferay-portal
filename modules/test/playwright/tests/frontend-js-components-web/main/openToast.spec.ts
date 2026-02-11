/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {performLoginViaApi, performLogout} from '../../../utils/performLogin';

export const test = mergeTests(loginTest());

test('Check timeout toast has focus', {tag: '@LPD-79043'}, async ({page}) => {
	await performLogout(page);
	await performLoginViaApi({page, rememberMe: false, screenName: 'test'});

	await expect(page.getByText('Due to inactivity')).toBeVisible();

	await expect(
		page.locator('.lfr-tooltip-scope:has(#sessionToast)')
	).toBeFocused();
});
