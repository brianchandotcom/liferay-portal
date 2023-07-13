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

import MDFRequest from '../../../common/interfaces/mdfRequest';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFRequest from '../../../common/services/liferay/object/mdf-requests/createMDFRequest';
import updateMDFRequest from '../../../common/services/liferay/object/mdf-requests/updateMDFRequest';

const submitMDFRequest = async (mdfRequest: MDFRequest) => {
	const dtoMDFRequest = mdfRequest.id
		? await updateMDFRequest(ResourceName.MDF_REQUEST_DXP, mdfRequest)
		: await createMDFRequest(ResourceName.MDF_REQUEST_DXP, mdfRequest);

	return dtoMDFRequest;
};

export default submitMDFRequest;
