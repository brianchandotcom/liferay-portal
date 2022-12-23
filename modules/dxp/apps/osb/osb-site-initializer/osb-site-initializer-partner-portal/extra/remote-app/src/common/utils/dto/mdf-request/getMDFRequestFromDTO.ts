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
import LiferayPicklist from '../../../interfaces/liferayPicklist';
import MDFRequest from '../../../interfaces/mdfRequest';

export function getMDFRequestFromDTO(
	mdfRequest: MDFRequestDTO,
	requestUpdateStatus: LiferayPicklist
): MDFRequest {
	return {
		...mdfRequest,
		activities:
			mdfRequest.mdfRequestToActivities?.map((activity) => ({
				...activity,
				budgets: activity.activityToBudgets || [],
				endDate: activity.endDate?.split('T')[0],
				leadFollowUpStrategies: activity.leadFollowUpStrategies?.split(
					'; '
				),
				leadGenerated: String(activity.leadGenerated),
				mdfRequestId: activity.r_mdfRequestToActivities_c_mdfRequestId,
				startDate: activity.startDate?.split('T')[0],
			})) || [],
		additionalOption: mdfRequest.additionalOption,
		company: mdfRequest.r_accountToMDFRequests_accountEntry,
		liferayBusinessSalesGoals: mdfRequest.liferayBusinessSalesGoals?.split(
			'; '
		),
		mdfRequestStatus: requestUpdateStatus,
		targetAudienceRoles: mdfRequest.targetAudienceRoles?.split('; '),
		targetMarkets: mdfRequest.targetMarkets?.split('; '),
	};
}
