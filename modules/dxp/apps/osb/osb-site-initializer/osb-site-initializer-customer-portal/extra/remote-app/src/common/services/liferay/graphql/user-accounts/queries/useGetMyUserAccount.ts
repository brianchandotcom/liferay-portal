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

import {gql, useQuery} from '@apollo/client';
import {CORE_USER_ACCOUNT_FIELDS} from '../fragments';

const GET_USER_ACCOUNT = gql`
	${CORE_USER_ACCOUNT_FIELDS}
	query getMyUserAccount {
		myUserAccount {
			...CoreUserAccountFields
		}
	}
`;

export function useGetMyUserAccount(options = {skip: false}) {
	return useQuery(GET_USER_ACCOUNT, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		skip: options.skip,
	});
}
