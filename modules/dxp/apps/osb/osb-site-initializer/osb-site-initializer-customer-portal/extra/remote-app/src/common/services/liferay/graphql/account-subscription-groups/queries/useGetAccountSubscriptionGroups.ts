/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
