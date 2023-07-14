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

const GET_ACTIVATION_KEYS = gql`
	query getActivationKeys(
		$accountKey: String!
		$filter: String!
		$page: Number!
		$pageSize: Number!
	) {
		getActivationKeys(
			accountKey: $accountKey
			filter: $filter
			page: $page
			pageSize: $pageSize
			sort: $sort
		)
			@rest(
				type: "R_ActivationKeysPage"
				path: "/accounts/{args.accountKey}/license-keys?filter={args.filter}&page={args.page}&pageSize={args.pageSize}&sort={args.sort}"
				method: "GET"
			) {
			items {
				active
				complimentary
				description
				expirationDate
				hostName
				id
				ipAddresses
				licenseEntryType
				macAddresses
				maxClusterNodes
				name
				productName
				productVersion
				sizing
				startDate
			}
		}
	}
`;

export function useGetActivationKeys(
	accountKey,
	filter,
	page,
	pageSize,
	options = {
		notifyOnNetworkStatusChange: false,
		skip: false,
	}
) {
	return useQuery(GET_ACTIVATION_KEYS, {
		context: {
			type: 'raysource-rest',
		},
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			accountKey,
			filter,
			page,
			pageSize,
			sort: 'startDate:desc',
		},
	});
}
