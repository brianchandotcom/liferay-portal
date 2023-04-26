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

import {Liferay} from '..';
import useSWR from 'swr';

import {LiferayAPIs} from '../common/enums/apis';
import liferayFetcher from '../common/utils/fetcher';

interface Account {
	claimPercent: number;
	currency: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	partnerCountry: string;
}

export default function useGetAccountById(accountId: number | undefined) {
	return useSWR(
		accountId
			? [
					`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/accounts/${accountId}`,
					Liferay.authToken,
			  ]
			: null,
		(url, token) => liferayFetcher<Account>(url, token)
	);
}
