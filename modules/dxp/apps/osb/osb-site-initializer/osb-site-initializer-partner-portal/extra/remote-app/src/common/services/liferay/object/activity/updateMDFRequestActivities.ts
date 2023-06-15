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

import {Liferay} from '../..';
import MDFRequestDTO from '../../../../interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../../../interfaces/mdfRequest';
import MDFRequestActivity from '../../../../interfaces/mdfRequestActivity';
import getDTOFromMDFRequestActivity from '../../../../utils/dto/mdf-request-activity/getDTOFromMDFRequestActivity';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';
import {ResourceName} from '../enum/resourceName';

export default async function updateMDFRequestActivities(
	apiOption: ResourceName,
	mdfRequestActivity: MDFRequestActivity,
	mdfRequest: MDFRequest,
	mdfRequestDTO?: MDFRequestDTO,
	externalReferenceCodeFromSF?: string
) {
	return await liferayFetcher.put(
		`/o/${LiferayAPIs.OBJECT}/${apiOption}/by-external-reference-code/${mdfRequestActivity.externalReferenceCode}`,
		Liferay.authToken,
		getDTOFromMDFRequestActivity(
			mdfRequestActivity,
			mdfRequest,
			mdfRequestDTO,
			externalReferenceCodeFromSF
		)
	);
}
