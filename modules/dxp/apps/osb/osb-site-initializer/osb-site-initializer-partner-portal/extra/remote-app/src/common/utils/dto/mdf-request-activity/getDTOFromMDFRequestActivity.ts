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

import MDFRequestActivityDTO from '../../../interfaces/dto/mdfRequestActivityDTO';
import MDFRequestActivity from '../../../interfaces/mdfRequestActivity';

export default function getDTOFromMDFRequestActivity(
	mdfRequestActivity: MDFRequestActivity,
	mdfRequestId?: number
): MDFRequestActivityDTO {
	return {
		activityPromotion: mdfRequestActivity.activityPromotion,
		ad: mdfRequestActivity.ad,
		assetsLiferayRequired: mdfRequestActivity.assetsLiferayRequired,
		description: mdfRequestActivity.description,
		detailsLeadFollowUp: mdfRequestActivity.detailsLeadFollowUp,
		endDate: mdfRequestActivity.endDate,
		gatedLandingPage: mdfRequestActivity.gatedLandingPage,
		goalOfContent: mdfRequestActivity.goalOfContent,
		hiringOutsideWriterOrAgency:
			mdfRequestActivity.hiringOutsideWriterOrAgency,
		howLiferayBrandUsed: mdfRequestActivity.howLiferayBrandUsed,
		keywordsForPPCCampaigns: mdfRequestActivity.keywordsForPPCCampaigns,
		leadFollowUpStrategies: mdfRequestActivity.leadFollowUpStrategies.join(
			', '
		),
		leadGenerated: mdfRequestActivity.leadGenerated,
		liferayBranding: mdfRequestActivity.liferayBranding,
		liferayParticipationRequirements:
			mdfRequestActivity.liferayParticipationRequirements,
		location: mdfRequestActivity.location,
		marketingActivity: mdfRequestActivity.marketingActivity,
		mdfRequestAmount: mdfRequestActivity.mdfRequestAmount,
		name: mdfRequestActivity.name,
		overallMessageContentCTA: mdfRequestActivity.overallMessageContentCTA,
		primaryThemeOrMessage: mdfRequestActivity.primaryThemeOrMessage,
		r_mdfRequestToActivities_c_mdfRequestId: mdfRequestId,
		r_tacticToActivities_c_tacticId: mdfRequestActivity.tactic.id,
		r_typeActivityToActivities_c_typeActivityId:
			mdfRequestActivity.typeActivity.id,
		sourceAndSizeOfInviteeList:
			mdfRequestActivity.sourceAndSizeOfInviteeList,
		specificSites: mdfRequestActivity.specificSites,
		startDate: mdfRequestActivity.startDate,
		targetOfLeads: mdfRequestActivity.targetOfLeads,
		totalCostOfExpense: mdfRequestActivity.totalCostOfExpense,
		venueName: mdfRequestActivity.venueName,
	};
}
