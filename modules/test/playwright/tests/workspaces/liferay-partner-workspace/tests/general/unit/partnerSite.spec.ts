/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';

export const test = mergeTests(partnerPagesTest);

test.describe('Partner Site', () => {
	test('Open Partner Homepage', async ({homePage, page}) => {
		await homePage.goto();

		const heading = await page.getByRole('heading', {
			name: 'Welcome to Partner Portal',
		});

		expect(heading).toBeTruthy();
	});
});
