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

import MDFRequestActivity from '../../../../../common/interfaces/mdfRequestActivity';

export default function getNewActivity(): MDFRequestActivity {
	return {
		activityDescription: {
			activityPromotion: '',
			ad: '',
			assetsLiferayDescription: '',
			assetsLiferayRequired: '',
			audienceTarget: '',
			broadcastChannel: '',
			cta: '',
			description: '',
			detailsLeadFollowUp: '',
			expectedImpressions: '',
			gatedLandingPage: '',
			goalOfContent: '',
			guaranteedImpressions: '',
			hiringOutsideWriterOrAgency: '',
			howLiferayBrandUsed: '',
			keywordsForPPCCampaigns: '',
			landingPageCopy: '',
			leadFollowUpStrategies: [],
			leadGenerated: '',
			liferayBranding: '',
			liferayParticipationRequirements: '',
			location: '',
			manySeries: '',
			marketingActivity: '',
			nurtureDripCampaign: '',
			overallMessageContentCTA: '',
			primaryThemeOrMessage: '',
			publication: '',
			quantity: '',
			resourcesNecessaryFromLiferay: '',
			sourceAndSizeOfCallList: '',
			sourceAndSizeOfInviteeList: '',
			specificSites: '',
			targetOfLeads: '',
			targetOfSends: '',
			typeMerchandise: '',
			usingCIABAssets: '',
			venueName: '',
			webinarHostPlatform: '',
			webinarTopic: '',
			weeksAiring: '',
		},
		budgets: [],
		endDate: undefined,
		mdfRequestAmount: 0,
		name: '',
		startDate: undefined,
		tactic: {},
		totalCostOfExpense: 0,
		typeActivity: {},
	};
}
