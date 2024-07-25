/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';
import {accountPlatinumMock} from '../../../mocks/accountMock';
import {userCOMMock} from '../../../mocks/userMock';
import {generateMDFRequestData} from '../../../utils/mdf';
import {EAccountRoles} from '../../../utils/constants';
import { TAccount } from '../../../types/account';
import { TUserAccount } from '../../../types/user';

export const test = mergeTests(partnerPagesTest);

test.describe('MDF Request Form', () => {
	let accountPlatinum: TAccount;
	let userCOM: TUserAccount;

	test.beforeEach(
		async ({mdfRequestFormPage, partnerHelper, partnerSite}) => {
			accountPlatinum = await partnerHelper.apiHelpers.headlessAdminUser.postAccount(
				accountPlatinumMock
			);
			userCOM = await partnerHelper.apiHelpers.headlessAdminUser.postUserAccount(
				userCOMMock
			);

			await partnerHelper.assignUserToAccountRole(accountPlatinum.id, EAccountRoles.PARTNER_MANAGER, Number(userCOM.id));

			await mdfRequestFormPage.goto(partnerSite.friendlyUrlPath);
		}
	);

	test.afterEach(async ({partnerHelper}) => {
		if (accountPlatinum) {
			await partnerHelper.apiHelpers.headlessAdminUser.deleteAccount(
				accountPlatinum.id
			);
		}

		if (userCOM) {
			await partnerHelper.apiHelpers.headlessAdminUser.deleteUserAccount(
				Number(userCOM.id)
			);
		}
	});

	test('Open MDF Request Form', async ({page}) => {
		const heading = await page.getByRole('heading', {
			name: 'MDF Request',
		});

		expect(heading).toBeTruthy();
	});

	test('Create a New MDF Request', async ({mdfRequestFormPage}) => {
		const mdfRequestData = generateMDFRequestData(accountPlatinum, userCOM);

		await mdfRequestFormPage.createNewRequest(mdfRequestData);
		await mdfRequestFormPage.reviewMDFRequest(mdfRequestData);

		await mdfRequestFormPage.submitButton.click();

		await expect(mdfRequestFormPage.successMessage).toBeVisible();
	});
});
