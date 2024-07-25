/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	MDFRequestActivityExpenseTypes,
	MDFRequestActivityTactics,
	MDFRequestActivityTypes,
	MDFRequestAdditionalOptions,
	MDFRequestLiferayBusinessSalesGoals,
	MDFRequestTargetAudienceRoles,
	MDFRequestTargetMarkets,
} from '../utils/constants';

export type MDFRequestActivityExpense = {
	type: MDFRequestActivityExpenseTypes;
	value: number;
};

export type MDFRequestActivity = {
	activityName: string;
	claimPercent: number;
	endDate: string;
	expenses: MDFRequestActivityExpense[];
	leadGenerated: boolean;
	marketingActivity: string;
	startDate: string;
	tactic: MDFRequestActivityTactics;
	typeOfActivity: MDFRequestActivityTypes;
};

export type MDFRequestGoal = {
	additionalOptions?: MDFRequestAdditionalOptions[];
	companyName: string;
	liferayBusinessSalesGoals: MDFRequestLiferayBusinessSalesGoals[];
	liferayBusinessSalesGoalsOther?: string;
	overallCampaignDescription: string;
	overallCampaignName: string;
	targetAudienceRoles: MDFRequestTargetAudienceRoles[];
	targetMarkets: MDFRequestTargetMarkets[];
};

export type MDFRequest = {
	activities: MDFRequestActivity[];
	goals: MDFRequestGoal;
	review?: any;
};

export type TPartnerAccount = {
	currency?: string;
	externalReferenceCode?: string;
	id?: number;
	name: string;
	partnerCountry?: string;
	type?: string;
};
