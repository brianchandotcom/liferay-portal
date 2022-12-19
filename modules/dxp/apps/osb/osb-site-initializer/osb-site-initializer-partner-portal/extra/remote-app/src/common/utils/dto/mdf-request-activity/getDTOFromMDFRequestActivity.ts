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
import LiferayAccountBrief from '../../../interfaces/liferayAccountBrief';
import MDFRequestActivity from '../../../interfaces/mdfRequestActivity';

export default function getDTOFromMDFRequestActivity(
	mdfRequestActivity: MDFRequestActivity,
	company?: LiferayAccountBrief,
	mdfRequestId?: number,
	mdfRequestExternalReferenceCodeSF?: string,
	externalReferenceCodeSF?: string
): MDFRequestActivityDTO {
	return {
		activityPromotion: mdfRequestActivity.activityPromotion,
		ad: mdfRequestActivity.ad,
		assetsLiferayRequired: mdfRequestActivity.assetsLiferayRequired,
		description: mdfRequestActivity.description,
		detailsLeadFollowUp: mdfRequestActivity.detailsLeadFollowUp,
		endDate: mdfRequestActivity.endDate,
		externalReferenceCodeSF,
		gatedLandingPage: mdfRequestActivity.gatedLandingPage,
		goalOfContent: mdfRequestActivity.goalOfContent,
		hiringOutsideWriterOrAgency:
			mdfRequestActivity.hiringOutsideWriterOrAgency,
		howLiferayBrandUsed: mdfRequestActivity.howLiferayBrandUsed,
		keywordsForPPCCampaigns: mdfRequestActivity.keywordsForPPCCampaigns,
		leadFollowUpStrategies: mdfRequestActivity.leadFollowUpStrategies?.join(
			', '
		),
		leadGenerated: mdfRequestActivity.leadGenerated,
		liferayBranding: mdfRequestActivity.liferayBranding,
		liferayParticipationRequirements:
			mdfRequestActivity.liferayParticipationRequirements,
		location: mdfRequestActivity.location,
		marketingActivity: mdfRequestActivity.marketingActivity,
		mdfRequestAmount: mdfRequestActivity.mdfRequestAmount,
		mdfRequestExternalReferenceCodeSF,
		name: mdfRequestActivity.name,
		overallMessageContentCTA: mdfRequestActivity.overallMessageContentCTA,
		primaryThemeOrMessage: mdfRequestActivity.primaryThemeOrMessage,
		r_accountToActivities_accountEntryId: company?.id,
		r_mdfRequestToActivities_c_mdfRequestId: mdfRequestId,
		sourceAndSizeOfInviteeList:
			mdfRequestActivity.sourceAndSizeOfInviteeList,
		specificSites: mdfRequestActivity.specificSites,
		startDate: mdfRequestActivity.startDate,
		tactic: mdfRequestActivity.tactic,
		targetOfLeads: mdfRequestActivity.targetOfLeads,
		totalCostOfExpense: mdfRequestActivity.totalCostOfExpense,
		typeActivity: mdfRequestActivity.typeActivity,
		venueName: mdfRequestActivity.venueName,
	};
}
