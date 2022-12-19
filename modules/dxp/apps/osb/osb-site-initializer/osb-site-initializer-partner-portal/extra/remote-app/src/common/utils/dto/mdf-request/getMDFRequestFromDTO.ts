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

import MDFRequestDTO from '../../../interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../../interfaces/mdfRequest';

export function getMDFRequestFromDTO(
	mdfRequest: MDFRequestDTO,
	externalReferenceCodeSF?: string
): MDFRequest {
	return {
		accountExternalReferenceCodeSF: externalReferenceCodeSF,
		activities: mdfRequest.mdfRequestToActivities?.map((activity) => ({
			...activity,
			budgets: activity.activityToBudgets?.map((budget) => ({
				cost: budget.cost,
				expense: budget.expense,
			})),
			leadFollowUpStrategies: activity.leadFollowUpStrategies?.split(';'),
			mdfRequestId: activity.r_mdfRequestToActivities_c_mdfRequestId,
		})),
		additionalOption: mdfRequest.additionalOption,
		company: mdfRequest.r_accountToMDFRequests_accountEntry,
		country: mdfRequest.country,
		liferayBusinessSalesGoals: mdfRequest.liferayBusinessSalesGoals?.split(
			';'
		),
		maxDateActivity: mdfRequest.maxDateActivity,
		minDateActivity: mdfRequest.minDateActivity,
		overallCampaignDescription: mdfRequest.overallCampaignDescription,
		overallCampaignName: mdfRequest.overallCampaignName,
		requestStatus: mdfRequest.requestStatus,
		targetAudienceRoles: mdfRequest.targetAudienceRoles?.split(';'),
		targetMarkets: mdfRequest.targetMarkets?.split(';'),
		totalCostOfExpense: mdfRequest.totalCostOfExpense,
		totalMDFRequestAmount: mdfRequest.totalMDFRequestAmount,
	};
}
