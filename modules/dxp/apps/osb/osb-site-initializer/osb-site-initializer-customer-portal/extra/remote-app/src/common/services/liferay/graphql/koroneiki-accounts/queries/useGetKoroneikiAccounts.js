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

import {gql, useLazyQuery, useQuery} from '@apollo/client';

const GET_KORONEIKI_ACCOUNTS = gql`
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
					accountBrief @client {
						id
					}
					accountKey
					code
					dxpVersion
					liferayContactEmailAddress
					liferayContactName
					liferayContactRole
					maxRequestors
					name
					partner
					region
					slaCurrent
					slaCurrentEndDate
					slaCurrentStartDate
					slaExpired
					slaExpiredEndDate
					slaExpiredStartDate
					slaFuture
					slaFutureEndDate
					slaFutureStartDate
					status @client
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export function useGetKoroneikiAccounts(options) {
	return useQuery(GET_KORONEIKI_ACCOUNTS, {
		fetchPolicy: options.fetchPolicy,
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			filter: options.filter || '',
			page: options.page || 1,
			pageSize: options.pageSize || 20,
		},
	});
}

export function useLazyGetKoroneikiAccounts() {
	return useLazyQuery(GET_KORONEIKI_ACCOUNTS);
}
