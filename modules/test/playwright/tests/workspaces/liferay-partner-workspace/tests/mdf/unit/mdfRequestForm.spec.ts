/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';
import {accountPlatinumMock} from '../../../mocks/accountMock';
import {userAdminMock, userPMMock} from '../../../mocks/userMock';
import {TAccount} from '../../../types/account';
import {TUserAccount} from '../../../types/user';
import {EAccountRoles} from '../../../utils/constants';
import {generateMDFRequestData} from '../../../utils/mdf';

export const test = mergeTests(partnerPagesTest);

test.describe('MDF Request Form', () => {
	let accountPlatinum: TAccount;
	let userPM: TUserAccount;

	test.beforeEach(async ({apiHelpers, page, partnerHelper}) => {
		await partnerHelper.performLogin(page, userAdminMock);

		accountPlatinum =
			await apiHelpers.headlessAdminUser.postAccount(accountPlatinumMock);
		userPM = await apiHelpers.headlessAdminUser.postUserAccount(userPMMock);

		await partnerHelper.assignUserToAccountRole(
			accountPlatinum.id,
			EAccountRoles.PARTNER_MANAGER,
			Number(userPM.id)
		);

		await partnerHelper.performLogout(page);
	});

	test.afterEach(async ({apiHelpers, page, partnerHelper}) => {
		await partnerHelper.performLogin(page, userAdminMock);

		if (accountPlatinum) {
			await apiHelpers.headlessAdminUser.deleteAccount(
				accountPlatinum.id
			);
		}

		if (userPM) {
			await apiHelpers.headlessAdminUser.deleteUserAccount(
				Number(userPM.id)
			);
		}

		await partnerHelper.performLogout(page);
	});

	test('Open MDF Request Form', async ({
		mdfRequestFormPage,
		page,
		partnerHelper,
	}) => {
		await partnerHelper.performLogin(page, userPM);

		await mdfRequestFormPage.goto();

		const heading = await page.getByRole('heading', {
			name: 'MDF Request',
		});

		expect(heading).toBeTruthy();

		await partnerHelper.performLogout(page);
	});

	test('Create a New MDF Request', async ({
		mdfRequestFormPage,
		page,
		partnerHelper,
	}) => {
		await partnerHelper.performLogin(page, userPM);

		await mdfRequestFormPage.goto();

		const mdfRequestData = generateMDFRequestData(accountPlatinum, userPM);

		await mdfRequestFormPage.createNewRequest(mdfRequestData);
		await mdfRequestFormPage.reviewMDFRequest(mdfRequestData);

		await mdfRequestFormPage.submitButton.click();

		await expect(mdfRequestFormPage.successMessage).toBeVisible();

		await partnerHelper.performLogout(page);
	});
});
