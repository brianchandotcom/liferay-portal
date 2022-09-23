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
import {apiOption} from '../../../common/services/liferay/common/enums/apiOption';
import createMDFRequest from '../../../common/services/liferay/object/mdf-requests/createMDFRequest';
import {getDTOFromMDFRequest} from '../../../common/utils/dto/mdf-request/getDTOFromMDFRequest';

export default async function createViaProxyApi(mdfRequest: MDFRequest) {
	const dtoMDFRequestSF = getDTOFromMDFRequest(mdfRequest);

	const dtoMDFRequestSFResponse = await createMDFRequest(
		dtoMDFRequestSF,
		apiOption.MDF_REQUEST_SALESFORCE
	);

	if (dtoMDFRequestSFResponse.externalReferenceCode) {
		const dtoMDFRequest = getDTOFromMDFRequest(
			mdfRequest,
			dtoMDFRequestSFResponse.externalReferenceCode
		);

		const dtoMDFRequestResponse = await createMDFRequest(
			dtoMDFRequest,
			apiOption.MDF_REQUEST_DXP
		);

		return dtoMDFRequestResponse;
	}
}
