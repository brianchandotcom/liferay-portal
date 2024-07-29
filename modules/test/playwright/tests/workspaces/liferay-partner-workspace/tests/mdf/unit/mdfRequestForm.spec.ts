/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';
import {accountPlatinumMock} from '../../../mocks/accountMock';
import {userPMMock} from '../../../mocks/userMock';
import {TAccount} from '../../../types/account';
import {TUserAccount} from '../../../types/user';
import {EAccountRoles} from '../../../utils/constants';
import {generateMDFRequestData} from '../../../utils/mdf';

export const test = mergeTests(partnerPagesTest);

test.describe('MDF Request Form', () => {
	let accountPlatinum: TAccount;
	let userPM: TUserAccount;

	test.beforeEach(async ({partnerHelper}) => {
		accountPlatinum =
			await partnerHelper.apiHelpers.headlessAdminUser.postAccount(
				accountPlatinumMock
			);
		userPM =
			await partnerHelper.apiHelpers.headlessAdminUser.postUserAccount(
				userPMMock
			);

		await partnerHelper.assignUserToAccountRole(
			Number(accountPlatinum.id),
			EAccountRoles.PARTNER_MANAGER,
			Number(userPM.id)
		);
	});

	test.afterEach(async ({partnerHelper}) => {
		if (accountPlatinum) {
			await partnerHelper.apiHelpers.headlessAdminUser.deleteAccount(
				Number(accountPlatinum.id)
			);
		}

		if (userPM) {
			await partnerHelper.apiHelpers.headlessAdminUser.deleteUserAccount(
				Number(userPM.id)
			);
		}
	});

	test('Open MDF Request Form', async ({
		mdfRequestFormPage,
		partnerHelper,
	}) => {
		await mdfRequestFormPage.goto();

		const heading = await partnerHelper.page.getByRole('heading', {
			name: 'MDF Request',
		});

		await expect(heading).toBeTruthy();
	});

	test('Create a New MDF Request', async ({mdfRequestFormPage}) => {
		await mdfRequestFormPage.goto();

		const mdfRequestData = generateMDFRequestData(accountPlatinum, userPM);

		await mdfRequestFormPage.createNewRequest(mdfRequestData);
		await mdfRequestFormPage.reviewMDFRequest(mdfRequestData);

		await mdfRequestFormPage.submitButton.click();

		await expect(mdfRequestFormPage.successMessage).toBeVisible();
	});
});
