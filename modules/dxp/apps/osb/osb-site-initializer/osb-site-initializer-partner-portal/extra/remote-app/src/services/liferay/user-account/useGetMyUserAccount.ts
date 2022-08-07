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

import UserAccount from '../../../utils/types/userAccount';
import {liferayAPIs} from '../common/apis';
import liferayFetcher from '../common/fetcher';

export default function useGetMyUserAccount() {
	return useSWR(
		[
			`/o/${liferayAPIs.HEADERLESS_ADMIN_USER}/my-user-account`,
			Liferay.authToken,
		],
		(url, token) => liferayFetcher<UserAccount>(url, token)
	);
}
