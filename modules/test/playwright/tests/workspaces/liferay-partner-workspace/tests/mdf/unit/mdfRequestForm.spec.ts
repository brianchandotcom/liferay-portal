/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import { apiHelpersTest } from '../../../../../../fixtures/apiHelpersTest';
import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';
import { generateMDFRequestData } from '../../../pages/mdf/utils/mdfRequestData';

export const test = mergeTests(apiHelpersTest, partnerPagesTest);

test.describe('MDF Request Form', () => {
	const accountName = 'Deathray, Inc.*';
	const accountRole = '[Account] Partner Manager (PM)';
	let accountUser;

	test.beforeEach(async ({mdfRequestFormPage, partnerHelper, partnerSite}) => {
		const { account } = await partnerHelper.createAccountUser({accountName});
		await partnerHelper.assignUserToAccountRole({accountId: account.id, accountRole});

		accountUser = account;

		await mdfRequestFormPage.goto(partnerSite.friendlyUrlPath); 
	});

	test.afterEach(async ({apiHelpers}) => {
		await apiHelpers.headlessAdminUser.deleteAccount(accountUser.id);
	})

	test('Open MDF Request Form', async ({page}) => {
		const heading = await page.getByRole('heading', {
			name: 'MDF Request',
		});

		expect(heading).toBeTruthy();
	});

	test('Create a New MDF Request', async ({
		mdfRequestFormPage
	}) => {
		const mdfRequestData = generateMDFRequestData();

		await mdfRequestFormPage.createNewRequest(mdfRequestData);
		await mdfRequestFormPage.reviewMDFRequest(mdfRequestData);

		await mdfRequestFormPage.submitButton.click();

		await expect(mdfRequestFormPage.successMessage).toBeVisible();
	});
});
