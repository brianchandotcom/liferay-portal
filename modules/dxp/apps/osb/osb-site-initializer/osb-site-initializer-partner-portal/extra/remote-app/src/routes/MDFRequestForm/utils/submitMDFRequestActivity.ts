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

import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import MDFRequestActivity from '../../../common/interfaces/mdfRequestActivity';
import createMDFRequestActivity from '../../../common/services/liferay/object/activity/createMDFRequestActivity';
import updateMDFRequestActivity from '../../../common/services/liferay/object/activity/updateMDFRequestActivity';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';

const submitMDFRequestActivity = async (
	mdfRequestActivity: MDFRequestActivity,
	mdfRequestDTO: MDFRequestDTO,
	mdfRequest: MDFRequest
) => {
	const dtoMDFClaimActivity = mdfRequestActivity.id
		? await updateMDFRequestActivity(
				ResourceName.ACTIVITY_DXP,
				mdfRequestActivity,
				mdfRequest,
				mdfRequestDTO
		  )
		: await createMDFRequestActivity(
				ResourceName.ACTIVITY_DXP,
				mdfRequestActivity,
				mdfRequest,
				mdfRequestDTO
		  );

	return dtoMDFClaimActivity;
};

export default submitMDFRequestActivity;
