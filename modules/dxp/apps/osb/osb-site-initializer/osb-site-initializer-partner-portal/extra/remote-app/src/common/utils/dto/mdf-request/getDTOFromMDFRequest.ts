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
import {Liferay} from '../../../services/liferay';
import getSummaryActivities from '../../getSummaryActivities';

export function getDTOFromMDFRequest(
	mdfRequest: MDFRequest,
	externalReferenceCodeSF?: string
): MDFRequestDTO {
	return {
		...getSummaryActivities(mdfRequest.activities),
		accountExternalReferenceCodeSF:
			mdfRequest.accountExternalReferenceCodeSF,
		additionalOption: mdfRequest.additionalOption,
		country: mdfRequest.country,
		emailAddress: Liferay.ThemeDisplay.getUserEmailAddress(),
		externalReferenceCodeSF,
		liferayBusinessSalesGoals: mdfRequest.liferayBusinessSalesGoals.join(
			'; '
		),
		liferaysUserIdSF: Number(Liferay.ThemeDisplay.getUserId()),
		overallCampaignDescription: mdfRequest.overallCampaignDescription,
		overallCampaignName: mdfRequest.overallCampaignName,
		r_accountToMDFRequests_accountEntryId: mdfRequest.company.id,
		r_userToMDFRequests_userId: Number(Liferay.ThemeDisplay.getUserId()),
		requestStatus: mdfRequest.requestStatus,
		targetAudienceRoles: mdfRequest.targetAudienceRoles.join('; '),
		targetMarkets: mdfRequest.targetMarkets.join('; '),
	};
}
