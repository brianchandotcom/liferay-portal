/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';

import {
	EMDFRequestActivityExpenseTypes,
	EMDFRequestActivityTactics,
	EMDFRequestActivityTypes,
	EMDFRequestLiferayBusinessSalesGoals,
	EMDFRequestTargetAudienceRoles,
	EMDFRequestTargetMarkets,
} from './constants';

export function createMDFRequest(companyName: string) {
	return {
		activities: [
			{
				activityName: 'Test Activity',
				claimPercent: 0.5,
				endDate: moment().add(2, 'days').format('YYYY-MM-DD'),
				expenses: [
					{
						type: EMDFRequestActivityExpenseTypes.BROADCAST_ADVERTISING,
						value: 500,
					},
				],
				leadGenerated: false,
				marketingActivity: 'Marketing Description',
				startDate: moment().add(1, 'days').format('YYYY-MM-DD'),
				tactic: EMDFRequestActivityTactics.OTHER,
				typeOfActivity: EMDFRequestActivityTypes.MISCELLANEOUS_MARKETING,
			},
		],
		goals: {
			companyName,
			liferayBusinessSalesGoals: [
				EMDFRequestLiferayBusinessSalesGoals.LEAD_GENERATION,
			],
			overallCampaignDescription: 'Campaign Description',
			overallCampaignName: 'Campaign Name',
			targetAudienceRoles: [
				EMDFRequestTargetAudienceRoles.C_LEVEL_EXECUTIVE_VP,
				EMDFRequestTargetAudienceRoles.ADMINISTRATOR,
			],
			targetMarkets: [
				EMDFRequestTargetMarkets.AEROSPACE_DEFENSE,
				EMDFRequestTargetMarkets.AGRICULTURE,
			],
		},
	};
}
