/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestDTO from '../../../interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../../interfaces/mdfRequest';
import {Liferay} from '../../../services/liferay';

export function getDTOFromMDFRequest(
	mdfRequest: MDFRequest,
	externalReferenceCodeFromSF?: string
): MDFRequestDTO {
	return {
		accountExternalReferenceCode: mdfRequest.company?.externalReferenceCode,
		additionalOption: mdfRequest.additionalOption,
		claimPercent: mdfRequest.claimPercent,
		companyName: mdfRequest.company?.name,
		currency: mdfRequest.currency,
		emailAddress: mdfRequest.id
			? mdfRequest.emailAddress
			: Liferay.ThemeDisplay.getUserEmailAddress(),
		externalReferenceCode: externalReferenceCodeFromSF,
		liferayBusinessSalesGoals: mdfRequest.liferayBusinessSalesGoals?.includes(
			'Other - Please describe'
		)
			? mdfRequest.liferayBusinessSalesGoals
					?.filter((item) => item !== 'Other - Please describe')
					.join('; ')
			: mdfRequest.liferayBusinessSalesGoals?.join('; '),
		liferayBusinessSalesGoalsOther:
			mdfRequest?.liferayBusinessSalesGoalsOther,
		liferaysUserIdSF: mdfRequest.id
			? mdfRequest.liferaysUserIdSF
			: Number(Liferay.ThemeDisplay.getUserId()),
		maxDateActivity: mdfRequest.maxDateActivity,
		mdfRequestStatus: mdfRequest.mdfRequestStatus,
		minDateActivity: mdfRequest.minDateActivity,
		overallCampaignDescription: mdfRequest.overallCampaignDescription,
		overallCampaignName: mdfRequest.overallCampaignName,
		partnerCountry: mdfRequest.partnerCountry,
		r_accToMDFReqs_accountEntryERC:
			mdfRequest.company?.externalReferenceCode,
		r_accToMDFReqs_accountEntryId: mdfRequest.company?.id,
		r_usrToMDFReqs_userId: mdfRequest.id
			? mdfRequest.r_usrToMDFReqs_userId
			: Number(Liferay.ThemeDisplay.getUserId()),
		submitted: mdfRequest.submitted,
		targetAudienceRoles: mdfRequest.targetAudienceRoles?.join('; '),
		targetMarkets: mdfRequest.targetMarkets?.join('; '),
		totalClaimedRequest: mdfRequest.totalClaimedRequest,
		totalCostOfExpense: mdfRequest.totalCostOfExpense,
		totalMDFRequestAmount: mdfRequest.totalMDFRequestAmount,
		totalPaidAmount: mdfRequest.totalPaidAmount,
	};
}
