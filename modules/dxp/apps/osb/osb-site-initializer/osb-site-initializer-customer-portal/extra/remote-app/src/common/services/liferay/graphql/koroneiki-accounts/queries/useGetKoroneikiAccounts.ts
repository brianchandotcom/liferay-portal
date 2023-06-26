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
import {CORE_KORONEIKI_ACCOUNT_FIELDS} from '../fragments/coreKoroneikiAccountFields';

const GET_KORONEIKI_ACCOUNTS = gql`
	${CORE_KORONEIKI_ACCOUNT_FIELDS}
	query getKoroneikiAccounts(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			koroneikiAccounts(
				filter: $filter
				page: $page
				pageSize: $pageSize
			) {
				items {
					...CoreKoroneikiAccountFields
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export function useGetKoroneikiAccounts(
	options = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		page: 1,
		pageSize: 20,
		skip: false,
	}
) {
	return useQuery(GET_KORONEIKI_ACCOUNTS, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			filter: options.filter || '',
			page: options.page || 1,
			pageSize: options.pageSize || 20,
		},
	});
}
