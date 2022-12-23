/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFRequestBudget from './mdfRequestBudget';

export default interface MDFRequestActivity extends Partial<LiferayObject> {
	activityPromotion: string;
	ad: string;
	assetsLiferayRequired: string;
	budgets: MDFRequestBudget[];
	description: string;
	detailsLeadFollowUp: string;
	endDate?: string;
	gatedLandingPage: string;
	goalOfContent: string;
	hiringOutsideWriterOrAgency: string;
	howLiferayBrandUsed: string;
	keywordsForPPCCampaigns: string;
	leadFollowUpStrategies?: string[];
	leadGenerated: string;
	liferayBranding: string;
	liferayParticipationRequirements: string;
	location: string;
	marketingActivity: string;
	mdfRequestAmount: number;
	name: string;
	overallMessageContentCTA: string;
	primaryThemeOrMessage: string;
	sourceAndSizeOfInviteeList: string;
	specificSites: string;
	startDate?: string;
	tactic: LiferayPicklist;
	targetOfLeads: string;
	totalCostOfExpense: number;
	typeActivity: LiferayPicklist;
	venueName: string;
}
