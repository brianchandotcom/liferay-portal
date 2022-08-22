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
		activityPromotion: '',
		ad: '',
		assetsLiferayRequired: '',
		budgets: [],
		description: '',
		detailsLeadFollowUp: '',
		endDate: undefined,
		gatedLandingPage: '',
		goalOfContent: '',
		hiringOutsideWriterOrAgency: '',
		howLiferayBrandUsed: '',
		keywordsForPPCCampaigns: '',
		leadFollowUpStrategies: [],
		leadGenerated: '',
		liferayBranding: '',
		liferayParticipationRequirements: '',
		location: '',
		marketingActivity: '',
		mdfRequestAmount: 0,
		name: '',
		overallMessageContentCTA: '',
		primaryThemeOrMessage: '',
		sourceAndSizeOfInviteeList: '',
		specificSites: '',
		startDate: undefined,
		tactic: {},
		targetOfLeads: '',
		totalCostOfExpense: 0,
		typeActivity: {},
		venueName: '',
	};
}
