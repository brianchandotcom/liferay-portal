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

const GET_ACCOUNT_SUBSCRIPTION_GROUPS = gql`
	query getAccountSubscriptionGroups(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$sort: String
	) {
		c {
			accountSubscriptionGroups(
				filter: $filter
				page: $page
				pageSize: $pageSize
				sort: $sort
			) {
				items {
					accountSubscriptionGroupId
					accountKey
					activationStatus
					externalReferenceCode
					hasActivation
					manageContactsURL
					name
					tabOrder
					menuOrder
					logoPath @client
					isProvisioned @client
				}
				lastPage
				page
				pageSize
				totalCount
				hasPartnership @client
			}
		}
	}
`;

export function useGetAccountSubscriptionGroups(
	options = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		page: 1,
		pageSize: 20,
		skip: false,
		sort: '',
	}
) {
	return useQuery(GET_ACCOUNT_SUBSCRIPTION_GROUPS, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			filter: options.filter || '',
			page: options.page || 1,
			pageSize: options.pageSize || 20,
			sort: options.sort || '',
		},
	});
}
