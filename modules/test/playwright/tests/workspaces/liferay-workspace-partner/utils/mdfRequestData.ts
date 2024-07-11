/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';

import {
	MDFRequestActivityBudgetExpense,
	MDFRequestActivityTactics,
	MDFRequestLiferayBusinessSalesGoals,
	MDFRequestTargetAudienceRoles,
	MDFRequestTargetMarkets,
	MDFRequestTypeOfActivity,
} from './enums';

export function generateMDFRequestData() {
	return {
		activities: [
			{
				activityName: 'Test Activity',
				claimPercent: 0.5,
				endDate: moment().add(2, 'days').format('YYYY-MM-DD'),
				expenses: [
					{
						type: MDFRequestActivityBudgetExpense.BROADCAST_ADVERTISING,
						value: 500,
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
	};
}
