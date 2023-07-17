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

import UserAccount from '../../../interfaces/userAccount';
import {LiferayAPIs} from '../common/enums/apis';
import LiferayItems from '../common/interfaces/liferayItems';
import liferayFetcher from '../common/utils/fetcher';

export default function useGetAccountUserAccounts(
	accountExternalReferenceCode: string | undefined
) {
	return useSWR(
		accountExternalReferenceCode
			? [
					`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/accounts/by-external-reference-code/${accountExternalReferenceCode}/user-accounts`,
					Liferay.authToken,
			  ]
			: null,
		(url, token) => liferayFetcher<LiferayItems<UserAccount[]>>(url, token)
	);
}
