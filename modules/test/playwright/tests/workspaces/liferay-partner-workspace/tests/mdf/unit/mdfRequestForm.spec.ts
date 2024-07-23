/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {getRandomInt} from '../../../../../../utils/getRandomInt';
import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';
import {createMDFRequest} from '../../../utils/mdf';

export const test = mergeTests(partnerPagesTest);

test.describe('MDF Request Form', () => {
	let partnerAccount;
	let userAccount;

	test.beforeEach(
		async ({mdfRequestFormPage, partnerHelper, partnerSite}) => {
			partnerAccount =
				await partnerHelper.apiHelpers.headlessAdminUser.postAccount({
					name: 'Partner Account' + getRandomInt(),
					type: 'business',
				});
			userAccount =
				await partnerHelper.apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
					'test@liferay.com'
				);

			const rolesResponse =
				await partnerHelper.apiHelpers.headlessAdminUser.getAccountRoles(
					partnerAccount.id
				);

			const role = rolesResponse?.items?.filter(
				(role) => role.name === '[Account] Partner Manager (PM)'
			);

			await partnerHelper.apiHelpers.headlessAdminUser.assignUserToAccountRole(
				partnerAccount.id,
				role[0].id,
				userAccount.id
			);

			await mdfRequestFormPage.goto(partnerSite.friendlyUrlPath);
		}
	);

	test.afterEach(async ({partnerHelper}) => {
		await partnerHelper.apiHelpers.headlessAdminUser.deleteAccount(
			partnerAccount.id
		);
		await partnerHelper.apiHelpers.headlessAdminUser.deleteAccount(
			userAccount.id
		);
	});

	test('Open MDF Request Form', async ({page}) => {
		const heading = await page.getByRole('heading', {
			name: 'MDF Request',
		});

		expect(heading).toBeTruthy();
	});

	test('Create a New MDF Request', async ({mdfRequestFormPage}) => {
		const mdfRequestData = createMDFRequest();

		await mdfRequestFormPage.createNewRequest(mdfRequestData);
		await mdfRequestFormPage.reviewMDFRequest(mdfRequestData);

		await mdfRequestFormPage.submitButton.click();

		await expect(mdfRequestFormPage.successMessage).toBeVisible();
	});
});
