/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {partnerPagesTest} from './fixtures/partnerPages';
import {partnerSiteFixture} from './fixtures/partnerSite';
import {
	MDFRequestActivityBudgetExpense,
	MDFRequestActivityTactics,
	MDFRequestLiferayBusinessSalesGoals,
	MDFRequestTargetAudienceRoles,
	MDFRequestTargetMarkets,
	MDFRequestTypeOfActivity,
} from './utils/enums';
import moment from 'moment';

const test = mergeTests(apiHelpersTest, partnerSiteFixture, partnerPagesTest);

test('Create Basic MDF Request', async ({
	page,
	partnerHomePage,
	partnerMDFRequestForm,
}) => {
	await partnerHomePage.cardsNavigator('MDFs');

	await partnerMDFRequestForm.createNewRequest({
		activities: [
			{
				activityName: 'Test Activity',
				claimPercent: 0.5,
				endDate: moment().add(2, 'days').format('YYYY-MM-DD'),
				expenses: [
					{
						type: MDFRequestActivityBudgetExpense.BROADCAST_ADVERTISING,
						value: 1000,
					},
				],
				leadGenerated: false,
				marketingActivity: 'Marketing Description',
				startDate: moment().add(1, 'days').format('YYYY-MM-DD'),
				tactic: MDFRequestActivityTactics.OTHER,
				typeOfActivity:
					MDFRequestTypeOfActivity.MISCELLANEOUS_MARKETING,
			},
		],
		goals: {
			companyName: 'Deathray, Inc.*',
			liferayBusinessSalesGoals: [
				MDFRequestLiferayBusinessSalesGoals.LEAD_GENERATION,
			],
			overallCampaignDescription: 'Campaign Description',
			overallCampaignName: 'Campaign Name',
			targetAudienceRoles: [
				MDFRequestTargetAudienceRoles.C_LEVEL_EXECUTIVE_VP,
				MDFRequestTargetAudienceRoles.ADMINISTRATOR,
			],
			targetMarkets: [
				MDFRequestTargetMarkets.AEROSPACE_DEFENSE,
				MDFRequestTargetMarkets.AGRICULTURE,
			],
		},
	});

	await expect(
		page.getByRole('cell', {name: 'Deathray, Inc.*'})
	).toBeVisible();

	await expect(page.getByRole('cell', {name: 'Campaign Name'})).toBeVisible();

	await expect(
		page.getByRole('cell', {name: 'Campaign Description'})
	).toBeVisible();

	await expect(
		page.getByRole('cell', {name: 'Lead generation'})
	).toBeVisible();

	await expect(
		page.getByRole('cell', {name: 'Aerospace & Defense; Agriculture'})
	).toBeVisible();

	await expect(
		page.getByRole('cell', {name: 'C-Level/Executive/VP; Administrator'})
	).toBeVisible();

	await expect(page.getByText('Test Activity').first()).toBeVisible();

	await expect(page.getByText('$500.00').first()).toBeVisible();

	await page.getByRole('tab', {name: 'MDF Requested'}).click();

	await expect(page.getByRole('cell', {name: 'Test Activity'})).toBeVisible();

	await expect(
		page.getByRole('cell', {name: 'Miscellaneous Marketing'})
	).toBeVisible();

	await expect(page.getByRole('cell', {name: 'Other'})).toBeVisible();

	await expect(
		page.getByRole('cell', {name: 'Marketing Description'})
	).toBeVisible();

	await expect(
		page.getByRole('cell', {name: moment().add(1, 'days').format('l')})
	).toBeVisible();

	await expect(
		page.getByRole('cell', {name: moment().add(2, 'days').format('l')})
	).toBeVisible();

	await expect(page.getByRole('cell', {name: '$1,000.00'})).toBeVisible();

	await expect(page.getByRole('cell', {name: 'No'})).toBeVisible();

	await page.getByRole('tab', {name: 'MDF Requested'}).click();

	await expect(page.getByText('$500.00').nth(1)).toBeVisible();

	await partnerMDFRequestForm.submitButton.click();

	await expect(partnerMDFRequestForm.successMessage).toBeVisible();
});
