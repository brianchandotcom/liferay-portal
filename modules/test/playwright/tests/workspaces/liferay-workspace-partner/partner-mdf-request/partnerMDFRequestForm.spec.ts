/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import moment from 'moment';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {partnerPagesTest} from '../fixtures/partnerPages';
import {partnerSiteFixture} from '../fixtures/partnerSite';
import {generateMDFRequestData} from '../utils/mdfRequestData';

const test = mergeTests(apiHelpersTest, partnerSiteFixture, partnerPagesTest);

test.describe('MDF Request Form', () => {
	test.beforeEach(async ({partnerMDFListPage}) => {
		await partnerMDFListPage.goto();
	});

	test('Should Create a New MDF Request', async ({
		page,
		partnerMDFRequestForm,
	}) => {
		const activityName = page.getByText('Test Activity').first();
		const campaignDescription = await page.getByRole('cell', {
			name: 'Campaign Description',
		});
		const campaignName = await page.getByRole('cell', {
			name: 'Campaign Name',
		});
		const companyName = await page.getByRole('cell', {
			name: 'Deathray, Inc.*',
		});
		const endDate = await page.getByRole('cell', {
			name: moment().add(2, 'days').format('l'),
		});
		const expenses = await page.getByRole('cell', {name: '$500.00'});
		const expensePercentage = page.getByText('$250.00').first();
		const leadGenerated = page.getByRole('cell', {name: 'No'});
		const liferayBusinessSalesGoals = await page.getByRole('cell', {
			name: 'Lead generation',
		});
		const marketingActivity = await page.getByRole('cell', {
			name: 'Marketing Description',
		});
		const mdfRequestData = generateMDFRequestData();
		const startDate = await page.getByRole('cell', {
			name: moment().add(1, 'days').format('l'),
		});
		const tactic = await page.getByRole('cell', {name: 'Other'});
		const targetAudienceRoles = await page.getByRole('cell', {
			name: 'C-Level/Executive/VP; Administrator',
		});
		const targetMarkets = await page.getByRole('cell', {
			name: 'Aerospace & Defense; Agriculture',
		});
		const typeOfActivity = await page.getByRole('cell', {
			name: 'Miscellaneous Marketing',
		});

		await partnerMDFRequestForm.createNewRequest(mdfRequestData);

		await expect(companyName).toBeVisible();
		await expect(campaignName).toBeVisible();
		await expect(campaignDescription).toBeVisible();
		await expect(liferayBusinessSalesGoals).toBeVisible();
		await expect(targetMarkets).toBeVisible();
		await expect(targetAudienceRoles).toBeVisible();
		await expect(activityName).toBeVisible();
		await expect(expensePercentage).toBeVisible();

		await page.getByRole('tab', {name: 'MDF Requested'}).click();

		await expect(activityName).toBeVisible();
		await expect(typeOfActivity).toBeVisible();
		await expect(tactic).toBeVisible();
		await expect(marketingActivity).toBeVisible();
		await expect(startDate).toBeVisible();
		await expect(endDate).toBeVisible();
		await expect(expenses).toBeVisible();
		await expect(leadGenerated).toBeVisible();

		await page.getByRole('tab', {name: 'MDF Requested'}).click();

		await expect(expensePercentage).toBeVisible();

		await partnerMDFRequestForm.submitButton.click();

		await expect(partnerMDFRequestForm.successMessage).toBeVisible();
	});
});
