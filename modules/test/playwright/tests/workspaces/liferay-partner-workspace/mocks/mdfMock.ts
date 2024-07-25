/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../../../../utils/getRandomInt';

export const companyName = 'Partner Account' + getRandomInt();

export const mdfRequestMock = {
	claimPercent: 0.5,
	companyName,
	convertedTotalCostOfExpense: 50000,
	convertedTotalMDFRequestAmount: 25000,
	liferayBusinessSalesGoals: 'Lead generation',
	liferayBusinessSalesGoalsOther: '',
	maxDateActivity: '2024-07-12',
	minDateActivity: '2024-07-11',
	overallCampaignDescription: 'Campaign Description',
	overallCampaignName: 'Test Campaign',
	submitDate: '2024-07-10T18:11:39.346Z',
	targetAudienceRoles: 'C-Level/Executive/VP; Independent Contractor',
	targetMarkets: 'Aerospace & Defense; Agriculture',
	totalCostOfExpense: 0,
	totalMDFRequestAmount: 25000,
};
