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
