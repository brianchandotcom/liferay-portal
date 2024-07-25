/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {partnerPagesTest} from '../../../fixtures/partnerPagesTest';
import {companyName} from '../../../mocks/mdfMock';
import {createMDFRequest} from '../../../utils/mdf';

export const test = mergeTests(partnerPagesTest);

test.describe('MDF Request Form', () => {
	let partnerAccount;

	test.beforeEach(
		async ({mdfRequestFormPage, partnerHelper, partnerSite}) => {
			partnerAccount = await partnerHelper.createAccountUser({
				currency: 'USD',
				externalReferenceCode: '0017000000b3ScRAAU',
				name: companyName,
				partnerCountry: 'US',
				type: 'business',
			});

			await partnerHelper.assignUserToAccountRole({
				accountId: partnerAccount.id,
				accountRole: '[Account] Partner Manager (PM)',
			});

			await mdfRequestFormPage.goto(partnerSite.friendlyUrlPath);
		}
	);

	test.afterEach(async ({partnerHelper}) => {
		await partnerHelper.apiHelpers.headlessAdminUser.deleteAccount(
			partnerAccount.id
		);
	});

	test('Open MDF Request Form', async ({page}) => {
		const heading = await page.getByRole('heading', {
			name: 'MDF Request',
		});

		expect(heading).toBeTruthy();
	});

	test('Create a New MDF Request', async ({mdfRequestFormPage}) => {
		const mdfRequestData = createMDFRequest(companyName);

		await mdfRequestFormPage.createNewRequest(mdfRequestData);
		await mdfRequestFormPage.reviewMDFRequest(mdfRequestData);

		await mdfRequestFormPage.submitButton.click();

		await expect(mdfRequestFormPage.successMessage).toBeVisible();
	});
});
