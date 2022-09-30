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

import MDFRequestActivity from '../../../common/interfaces/mdfRequestActivity';
import createMDFRequestActivities from '../../../common/services/liferay/object/activity/createMDFRequestActivities';
import {resourceName} from '../../../common/services/liferay/object/enum/resourceName';

export default async function createMDFRequestActivitiesProxyAPI(
	mdfRequestActivity: MDFRequestActivity,
	mdfRequestId?: number,
	mdFRequestExternalReferenceCodeSF?: string
) {
	const dtoMDFRequestActivitySFResponse = await createMDFRequestActivities(
		resourceName.ACTIVITY_SALESFORCE,
		mdfRequestActivity,
		mdfRequestId,
		mdFRequestExternalReferenceCodeSF
	);

	if (dtoMDFRequestActivitySFResponse.externalReferenceCode) {
		const dtoMDFRequestResponse = await createMDFRequestActivities(
			resourceName.ACTIVITY_DXP,
			mdfRequestActivity,
			mdfRequestId,
			mdFRequestExternalReferenceCodeSF,
			dtoMDFRequestActivitySFResponse.externalReferenceCode
		);

		return dtoMDFRequestResponse;
	}
}
