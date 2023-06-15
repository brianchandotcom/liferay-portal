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

export function getMDFRequestFromDTO(mdfRequest: MDFRequestDTO): MDFRequest {
	return {
		...mdfRequest,
		activities:
			mdfRequest.mdfReqToActs?.map((activityItem) => {
				const {
					actToBgts,
					activityStatus,
					endDate,
					externalReferenceCode,
					id,
					mdfRequestAmount,
					name,
					r_accToActs_accountEntryERC,
					r_accToActs_accountEntryId,
					r_mdfReqToActs_c_mdfRequestERC,
					r_mdfReqToActs_c_mdfRequestId,
					startDate,
					submitted,
					tactic,
					totalCostOfExpense,
					typeActivity,
					...activityDescription
				} = activityItem;

				return {
					activityDescription: {
						...activityDescription,
						assetsLiferayRequired: String(
							activityItem.assetsLiferayRequired
						),
						leadFollowUpStrategies: activityItem.leadFollowUpStrategies?.split(
							', '
						),
						leadGenerated: String(activityItem.leadGenerated),
					},
					activityStatus,
					budgets: actToBgts || [],
					endDate: endDate?.split('T')[0],
					externalReferenceCode,
					id,
					mdfRequestAmount,
					mdfRequestExternalReferenceCode: r_mdfReqToActs_c_mdfRequestERC,
					name,
					r_accToActs_accountEntryERC,
					r_accToActs_accountEntryId,
					r_mdfReqToActs_c_mdfRequestERC,
					r_mdfReqToActs_c_mdfRequestId,
					startDate: startDate?.split('T')[0],
					submitted,
					tactic,
					totalCostOfExpense,
					typeActivity,
				};
			}) || [],
		additionalOption: mdfRequest.additionalOption,
		company: mdfRequest.r_accToMDFReqs_accountEntry,
		liferayBusinessSalesGoals:
			mdfRequest.liferayBusinessSalesGoalsOther !== ''
				? (
						'Other - Please describe; ' +
						mdfRequest.liferayBusinessSalesGoals
				  )
						?.split('; ')
						.filter((request) => request !== '')
				: mdfRequest.liferayBusinessSalesGoals
						?.split('; ')
						.filter((request) => request !== ''),
		liferayBusinessSalesGoalsOther:
			mdfRequest.liferayBusinessSalesGoalsOther,
		mdfRequestStatus: mdfRequest.mdfRequestStatus,
		targetAudienceRoles: mdfRequest.targetAudienceRoles
			?.split('; ')
			.filter((request) => request !== ''),
		targetMarkets: mdfRequest.targetMarkets
			?.split('; ')
			.filter((request) => request !== ''),
	};
}
