/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					claimPercent: mdfRequest.claimPercent,
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
