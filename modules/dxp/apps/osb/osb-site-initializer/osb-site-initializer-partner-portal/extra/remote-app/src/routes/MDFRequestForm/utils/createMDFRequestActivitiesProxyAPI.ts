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
import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import MDFRequestActivity from '../../../common/interfaces/mdfRequestActivity';
import createMDFRequestActivities from '../../../common/services/liferay/object/activity/createMDFRequestActivities';
import updateMDFRequestActivities from '../../../common/services/liferay/object/activity/updateMDFRequestActivities';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';

export default async function createMDFRequestActivitiesProxyAPI(
	mdfRequestActivity: MDFRequestActivity,
	mdfRequestDTO: MDFRequestDTO,
	mdfRequest: MDFRequest
) {
	let dtoMDFRequestActivitySFResponse:
		| MDFRequestActivityDTO
		| undefined = undefined;

	if (
		mdfRequestActivity.externalReferenceCode &&
		mdfRequestActivity.submitted
	) {
		dtoMDFRequestActivitySFResponse = await updateMDFRequestActivities(
			ResourceName.ACTIVITY_SALESFORCE,
			mdfRequestActivity,
			mdfRequest
		);
	}
	else {
		dtoMDFRequestActivitySFResponse = await createMDFRequestActivities(
			ResourceName.ACTIVITY_SALESFORCE,
			mdfRequestActivity,
			mdfRequest,
			mdfRequestDTO
		);
	}

	let dtoMDFRequestResponse: MDFRequestActivityDTO | undefined = undefined;

	if (dtoMDFRequestActivitySFResponse?.externalReferenceCode) {
		if (mdfRequestActivity.id && mdfRequestActivity.externalReferenceCode) {
			mdfRequestActivity.submitted = true;

			dtoMDFRequestResponse = await updateMDFRequestActivities(
				ResourceName.ACTIVITY_DXP,
				mdfRequestActivity,
				mdfRequest,
				mdfRequestDTO,
				dtoMDFRequestActivitySFResponse.externalReferenceCode
			);
		}
		else {
			mdfRequestActivity.submitted = true;

			dtoMDFRequestResponse = await createMDFRequestActivities(
				ResourceName.ACTIVITY_DXP,
				mdfRequestActivity,
				mdfRequest,
				mdfRequestDTO,
				dtoMDFRequestActivitySFResponse.externalReferenceCode
			);
		}
	}

	return dtoMDFRequestResponse;
}
