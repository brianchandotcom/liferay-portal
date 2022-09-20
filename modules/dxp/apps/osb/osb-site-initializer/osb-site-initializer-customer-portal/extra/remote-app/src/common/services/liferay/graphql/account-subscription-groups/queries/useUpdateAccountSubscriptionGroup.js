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

import {gql, useMutation} from '@apollo/client';

export const UPDATE_ACCOUNT_SUBSCRIPTION_GROUP = gql`
	mutation updateAccountSubscriptionGroup(
		$accountSubscriptionGroupId: Long!
		$AccountSubscriptionGroup: InputC_AccountSubscriptionGroup!
	) {
		c {
			updateAccountSubscriptionGroup(
				accountSubscriptionGroupId: $accountSubscriptionGroupId
				AccountSubscriptionGroup: $AccountSubscriptionGroup
			) {
				accountSubscriptionGroupId
				accountKey
				activationStatus
			}
		}
	}
`;

export function useUpdateAccountSubscriptionGroup(variables) {
	return useMutation(UPDATE_ACCOUNT_SUBSCRIPTION_GROUP, {
		variables,
	});
}
