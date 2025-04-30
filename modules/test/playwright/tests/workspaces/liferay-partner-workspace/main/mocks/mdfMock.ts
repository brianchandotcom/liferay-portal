/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../../../../utils/getRandomInt';
import {
	EMDFRequestActivityExpenseTypes,
	EMDFRequestActivityTactics,
	EMDFRequestActivityTypes,
	EMDFRequestLiferayBusinessSalesGoals,
	EMDFRequestTargetAudienceRoles,
	EMDFRequestTargetMarkets,
} from '../utils/constants';

const namespace = getRandomInt();

export const mdfClaimDataMock = {
	companyName: 'Test Company ' + namespace,
	submitDate: '2024-08-07T13:27:12.347Z',
};

export const mdfRequestMock = {
	activities: [
		{
			activityName: 'Test Activity' + namespace,
			claimPercent: 0.5,
			endDate: '2024-07-12',
			expenses: [
				{
					type: EMDFRequestActivityExpenseTypes.BROADCAST_ADVERTISING,
					value: 500,
				},
			],
			leadGenerated: false,
			marketingActivity: 'Marketing Description',
			startDate: '2024-07-11',
			tactic: EMDFRequestActivityTactics.OTHER,
			typeOfActivity: EMDFRequestActivityTypes.MISCELLANEOUS_MARKETING,
		},
	],
	convertedTotalMDFRequestAmount: 25000,
	goals: {
		companyName: 'Test Company ' + namespace,
		liferayBusinessSalesGoals: [
			EMDFRequestLiferayBusinessSalesGoals.LEAD_GENERATION,
		],
		overallCampaignDescription: 'Campaign Description',
		overallCampaignName: 'Campaign Name' + namespace,
		targetAudienceRoles: [
			EMDFRequestTargetAudienceRoles.C_LEVEL_EXECUTIVE_VP,
			EMDFRequestTargetAudienceRoles.ADMINISTRATOR,
		],
		targetMarkets: [
			EMDFRequestTargetMarkets.AEROSPACE_DEFENSE,
			EMDFRequestTargetMarkets.AGRICULTURE,
		],
	},
	submitDate: '2024-07-10T18:11:39.346Z',
	totalMDFRequestAmount: 25000,
	userId: 0,
};

export const mdfRequestDataMock = {
	claimPercent: 0.5,
	companyName: 'Test Company ' + namespace,
	convertedTotalCostOfExpense: 50000,
	convertedTotalMDFRequestAmount: 25000,
	liferayBusinessSalesGoals: 'Lead generation',
	liferayBusinessSalesGoalsOther: '',
	maxDateActivity: '2024-07-12',
	minDateActivity: '2024-07-11',
	overallCampaignDescription: 'Campaign Description',
	overallCampaignName: 'Campaign Name' + namespace,
	submitDate: '2024-07-10T18:11:39.346Z',
	targetAudienceRoles: 'C-Level/Executive/VP; Independent Contractor',
	targetMarkets: 'Aerospace & Defense; Agriculture',
	totalCostOfExpense: 0,
	totalMDFRequestAmount: 25000,
};
