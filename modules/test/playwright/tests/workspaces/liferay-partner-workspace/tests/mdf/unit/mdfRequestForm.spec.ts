/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';

export const test = mergeTests(partnerPagesTest);

test.describe('MDF Request Form', () => {
	test('Open MDF Request Form', async ({mdfRequestFormPage, page, partnerSite}) => {
		await mdfRequestFormPage.goto(partnerSite.friendlyUrlPath);

		const heading = await page.getByRole('heading', {
			name: 'MDF Request',
		});

		expect(heading).toBeTruthy();
	});
});
