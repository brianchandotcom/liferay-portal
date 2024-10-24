/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {customerPagesTest} from '../../../fixtures/customerPagesTest';
import {
	customerPerformLogin,
	customerPerformLogout,
} from '../../../utils/customerLogin';
import {mockOktaApiSession} from '../../../utils/oktaUtil';

export const test = mergeTests(customerPagesTest);

test.afterEach(async ({page}) => {
	await customerPerformLogout(page);
});

test.beforeEach(async ({page}) => {
	await customerPerformLogin(page, 'test@liferay.com');

	await mockOktaApiSession(page);
});

test.describe('Customer Site', () => {
	test('Open Customer Homepage', async ({homePage}) => {
		await homePage.goto();

		await expect(homePage.heading).toBeVisible();
		await expect(homePage.projectCard.first()).toBeVisible();
		await expect(homePage.searchBar).toBeVisible();
	});
});
