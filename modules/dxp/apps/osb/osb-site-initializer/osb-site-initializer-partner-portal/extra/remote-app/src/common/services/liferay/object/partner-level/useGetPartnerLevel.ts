/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
