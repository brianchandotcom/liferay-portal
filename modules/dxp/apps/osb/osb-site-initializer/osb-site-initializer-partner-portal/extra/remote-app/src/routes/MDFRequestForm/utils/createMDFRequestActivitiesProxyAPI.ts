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

import MDFRequestActivityDTO from '../../../common/interfaces/dto/mdfRequestActivityDTO';
import LiferayAccountBrief from '../../../common/interfaces/liferayAccountBrief';
import MDFRequestActivity from '../../../common/interfaces/mdfRequestActivity';
import createMDFRequestActivities from '../../../common/services/liferay/object/activity/createMDFRequestActivities';
import updateMDFRequestActivities from '../../../common/services/liferay/object/activity/updateMDFRequestActivities';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';

export default async function createMDFRequestActivitiesProxyAPI(
	mdfRequestActivity: MDFRequestActivity,
	company?: LiferayAccountBrief,
	mdfRequestId?: number,
	mdFRequestExternalReferenceCodeSF?: string
) {
	const dtoMDFRequestActivitySFResponse = await createMDFRequestActivities(
		ResourceName.ACTIVITY_SALESFORCE,
		mdfRequestActivity,
		company,
		mdfRequestId,
		mdFRequestExternalReferenceCodeSF
	);

	let dtoMDFRequestResponse: MDFRequestActivityDTO | undefined = undefined;

	if (dtoMDFRequestActivitySFResponse.externalReferenceCode) {
		if (mdfRequestActivity.id) {
			dtoMDFRequestResponse = await updateMDFRequestActivities(
				ResourceName.ACTIVITY_DXP,
				mdfRequestActivity,
				company,
				mdfRequestId,
				mdFRequestExternalReferenceCodeSF,
				dtoMDFRequestActivitySFResponse.externalReferenceCode
			);
		}
	} else {
		dtoMDFRequestResponse = await createMDFRequestActivities(
			ResourceName.ACTIVITY_DXP,
			mdfRequestActivity,
			company,
			mdfRequestId,
			mdFRequestExternalReferenceCodeSF,
			dtoMDFRequestActivitySFResponse.externalReferenceCode
		);
	}

	return dtoMDFRequestResponse;
}
