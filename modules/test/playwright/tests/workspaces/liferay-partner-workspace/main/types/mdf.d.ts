/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	EMDFRequestActivityExpenseTypes,
	EMDFRequestActivityTactics,
	EMDFRequestActivityTypes,
	EMDFRequestAdditionalOptions,
	EMDFRequestLiferayBusinessSalesGoals,
	EMDFRequestTargetAudienceRoles,
	EMDFRequestTargetMarkets,
} from '../utils/constants';

export type TMDFClaim = {
	companyName: string;
	id?: number;
	submitDate: string;
};

export type TMDFRequestActivityExpense = {
	type: EMDFRequestActivityExpenseTypes;
	value: number;
};

export type TMDFRequestActivity = {
	activityName: string;
	claimPercent: number;
	endDate: string;
	expenses: TMDFRequestActivityExpense[];
	leadGenerated: boolean;
	marketingActivity: string;
	startDate: string;
	tactic: EMDFRequestActivityTactics;
	typeOfActivity: EMDFRequestActivityTypes;
};

export type TMDFRequestGoal = {
	additionalOptions?: EMDFRequestAdditionalOptions[];
	companyName: string;
	liferayBusinessSalesGoals: EMDFRequestLiferayBusinessSalesGoals[];
	liferayBusinessSalesGoalsOther?: string;
	overallCampaignDescription: string;
	overallCampaignName: string;
	targetAudienceRoles: EMDFRequestTargetAudienceRoles[];
	targetMarkets: EMDFRequestTargetMarkets[];
};

export type TMDFRequest = {
	activities?: TMDFRequestActivity[];
	convertedTotalMDFRequestAmount: number;
	goals?: TMDFRequestGoal;
	id?: number;
	review?: any;
	submitDate?: string;
	totalMDFRequestAmount: number;
	userId?: number;
};

export type TMDFRequestDataFromRequest = TMDFRequest & {
	companyName: string;
	maxDateActivity: string;
	minDateActivity: string;
	overallCampaignName: string;
};
