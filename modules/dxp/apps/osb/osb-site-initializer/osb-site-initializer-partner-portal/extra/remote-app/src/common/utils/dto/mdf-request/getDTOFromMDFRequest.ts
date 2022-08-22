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
import getSummaryActivities from '../../getSummaryActivities';

export function getDTOFromMDFRequest(mdfRequest: MDFRequest): MDFRequestDTO {
	return {
		...getSummaryActivities(mdfRequest.activities),
		additionalOption: mdfRequest.additionalOption,
		country: mdfRequest.country,
		liferayBusinessSalesGoals: mdfRequest.liferayBusinessSalesGoals.join(
			', '
		),
		overallCampaign: mdfRequest.overallCampaign,
		r_accountToMDFRequests_accountEntryId: mdfRequest.company.id,
		requestStatus: mdfRequest.requestStatus,
		targetAudienceRoles: mdfRequest.targetAudienceRoles.join(', '),
		targetMarkets: mdfRequest.targetMarkets.join(', '),
	};
}
