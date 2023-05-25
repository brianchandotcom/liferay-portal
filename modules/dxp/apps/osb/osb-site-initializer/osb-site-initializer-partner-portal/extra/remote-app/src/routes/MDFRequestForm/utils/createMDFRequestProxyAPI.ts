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
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFRequest from '../../../common/services/liferay/object/mdf-requests/createMDFRequest';
import updateMDFRequest from '../../../common/services/liferay/object/mdf-requests/updateMDFRequest';

export default async function createMDFRequestProxyAPI(mdfRequest: MDFRequest) {
	let dtoMDFRequestSFResponse: MDFRequestDTO | undefined = undefined;

	if (mdfRequest.externalReferenceCode && mdfRequest.submitted) {
		dtoMDFRequestSFResponse = await updateMDFRequest(
			ResourceName.MDF_REQUEST_SALESFORCE,
			mdfRequest
		);
	}
	else {
		dtoMDFRequestSFResponse = await createMDFRequest(
			ResourceName.MDF_REQUEST_SALESFORCE,
			mdfRequest
		);
	}

	let dtoMDFRequestResponse: MDFRequestDTO | undefined = undefined;

	if (dtoMDFRequestSFResponse.externalReferenceCode) {
		if (mdfRequest.id) {
			mdfRequest.submitted = true;

			dtoMDFRequestResponse = await updateMDFRequest(
				ResourceName.MDF_REQUEST_DXP,
				mdfRequest,
				dtoMDFRequestSFResponse.externalReferenceCode
			);
		}
		else {
			mdfRequest.submitted = true;

			dtoMDFRequestResponse = await createMDFRequest(
				ResourceName.MDF_REQUEST_DXP,
				mdfRequest,
				dtoMDFRequestSFResponse.externalReferenceCode
			);
		}

		return dtoMDFRequestResponse;
	}
}
