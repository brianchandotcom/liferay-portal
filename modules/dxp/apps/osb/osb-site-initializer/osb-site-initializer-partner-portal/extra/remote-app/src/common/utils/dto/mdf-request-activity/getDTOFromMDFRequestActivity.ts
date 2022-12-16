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
	company: LiferayAccountBrief,
	mdfRequestId?: number,
	mdfRequestExternalReferenceCodeSF?: string,
	externalReferenceCodeSF?: string
): MDFRequestActivityDTO {
	const activityDescription = {...mdfRequestActivity.activityDescription};
	delete mdfRequestActivity.activityDescription;

	return {
		...activityDescription,
		...mdfRequestActivity,
		externalReferenceCodeSF,
		leadFollowUpStrategies: activityDescription?.leadFollowUpStrategies?.join(
			', '
		),
		mdfRequestExternalReferenceCodeSF,
		r_accountToActivities_accountEntryId: company.id,
		r_mdfRequestToActivities_c_mdfRequestId: mdfRequestId,
	};
}
