/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';
import {accountPlatinumMock} from '../../../mocks/accountMock';
import {userAdminMock} from '../../../mocks/userMock';
import {TAccount} from '../../../types/account';
import {EAccountRoles} from '../../../utils/constants';
import {generateMDFRequestFormData} from '../../../utils/mdf';

export const test = mergeTests(partnerPagesTest);

test.describe('MDF Request Form', () => {
	const {emailAddress} = userAdminMock;
	let accountPlatinum: TAccount;

	test.beforeEach(async ({apiHelpers, mdfRequestFormPage, partnerHelper}) => {
		accountPlatinum =
			await apiHelpers.headlessAdminUser.postAccount(accountPlatinumMock);

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			accountPlatinum.id,
			[emailAddress]
		);

		await partnerHelper.assignUserToAccountRole(
			accountPlatinum.id,
			EAccountRoles.PARTNER_MANAGER,
			emailAddress
		);

		await mdfRequestFormPage.goto();
	});

	test.afterEach(async ({apiHelpers}) => {
		if (accountPlatinum) {
			await apiHelpers.headlessAdminUser.deleteAccount(
				Number(accountPlatinum.id)
			);
		}
	});

	test('Open MDF Request Form', async ({mdfRequestFormPage}) => {
		await expect(mdfRequestFormPage.heading).toBeTruthy();
	});

	test('Create a New MDF Request', async ({mdfRequestFormPage}) => {
		const mdfRequestFormData = generateMDFRequestFormData(accountPlatinum);

		await mdfRequestFormPage.createNewRequest(mdfRequestFormData);

		await mdfRequestFormPage.reviewMDFRequest(mdfRequestFormData);

		await mdfRequestFormPage.submitButton.click();

		await expect(mdfRequestFormPage.successMessage).toBeVisible();
	});
});
