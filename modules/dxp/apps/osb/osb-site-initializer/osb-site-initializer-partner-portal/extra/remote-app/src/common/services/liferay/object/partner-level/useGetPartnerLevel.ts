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

import useSWR from 'swr';

import {Liferay} from '../..';
import PartnerLevel from '../../../../interfaces/partnerLevel';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';

export default function useGetPartnerLevel(externalReferenceCode?: string) {
	return useSWR(
		externalReferenceCode
			? [
					`/o/${LiferayAPIs.OBJECT}/partnerlevels/by-external-reference-code/${externalReferenceCode}`,
					Liferay.authToken,
			  ]
			: null,
		(url, token) => liferayFetcher<PartnerLevel>(url, token)
	);
}
