/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';

const GET_ACCOUNT_SUBSCRIPTION_USAGE = gql`
	query getAccountSubscriptionUsage(
		$accountKey: String!
		$productKey: String!
	) {
		getAccountSubscriptionUsage(
			accountKey: $accountKey
			productKey: $productKey
		)
			@rest(
				type: "R_AccountSubscriptionUsage"
				path: "/accounts/{args.accountKey}/product/{args.productKey}/usage"
				method: "GET"
			) {
			annualSubscriptions {
				year
				maxConcurrentConsumption
				maxConcurrentQuantity
			}
			currentConsumption
		}
	}
`;

export function useGetAccountSubscriptionUsage(
	accountKey,
	productKey,
	options = {
		notifyOnNetworkStatusChange: false,
		skip: false,
	}
) {
	return useQuery(GET_ACCOUNT_SUBSCRIPTION_USAGE, {
		context: {
			type: 'raysource-rest',
		},
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			accountKey,
			productKey,
		},
	});
}
