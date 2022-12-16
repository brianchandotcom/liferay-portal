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
	activityDescription?: {
		activityPromotion: string;
		ad: string;
		assetsLiferayDescription?: string;
		assetsLiferayRequired: string;
		audienceTarget?: string;
		broadcastChannel?: string;
		cta?: string;
		description: string;
		detailsLeadFollowUp: string;
		expectedImpressions?: string;
		gatedLandingPage: string;
		goalOfContent: string;
		guaranteedImpressions?: string;
		hiringOutsideWriterOrAgency: string;
		howLiferayBrandUsed: string;
		keywordsForPPCCampaigns: string;
		landingPageCopy?: string;
		leadFollowUpStrategies: string[];
		leadGenerated: string;
		liferayBranding: string;
		liferayParticipationRequirements: string;
		location: string;
		manySeries?: string;
		marketingActivity: string;
		nurtureDripCampaign?: string;
		overallMessageContentCTA: string;
		primaryThemeOrMessage: string;
		publication?: string;
		quantity?: string;
		resourcesNecessaryFromLiferay?: string;
		sourceAndSizeOfCallList?: string;
		sourceAndSizeOfInviteeList: string;
		specificSites: string;
		targetOfLeads: string;
		targetOfSends?: string;
		typeMerchandise?: string;
		usingCIABAssets?: string;
		venueName: string;
		webinarHostPlatform?: string;
		webinarTopic?: string;
		weeksAiring?: string;
	};
	budgets: MDFRequestBudget[];
	endDate?: Date;
	mdfRequestAmount: number;
	name: string;
	startDate?: Date;
	tactic: LiferayPicklist;
	totalCostOfExpense: number;
	typeActivity: LiferayPicklist;
}
