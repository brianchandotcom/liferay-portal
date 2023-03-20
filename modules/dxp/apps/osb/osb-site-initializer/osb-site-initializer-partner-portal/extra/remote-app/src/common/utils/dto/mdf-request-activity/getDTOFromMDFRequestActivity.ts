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
	externalReferenceCodeSF?: string,
	mdfRequestExternalReferenceCodeSF?: string
): MDFRequestActivityDTO {
	const {activityDescription, ...newMDFRequestActivity} = mdfRequestActivity;

	delete activityDescription?.creator;
	delete activityDescription?.externalReferenceCode;
	delete activityDescription?.status;

	return {
		...activityDescription,
		activityStatus: mdfRequestActivity.activityStatus,
		currency: mdfRequestActivity.currency,
		...newMDFRequestActivity,
		externalReferenceCodeSF,
		leadFollowUpStrategies: activityDescription?.leadFollowUpStrategies?.join(
			', '
		),
		mdfRequestExternalReferenceCodeSF,
		r_accToActs_accountEntryId: company?.id,
		r_mdfReqToActs_c_mdfRequestId: mdfRequestId,
	};
}
