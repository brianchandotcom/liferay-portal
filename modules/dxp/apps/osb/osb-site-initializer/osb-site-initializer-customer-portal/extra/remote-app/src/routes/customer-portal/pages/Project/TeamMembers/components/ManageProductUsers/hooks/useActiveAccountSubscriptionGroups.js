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

import {useGetAccountSubscriptionGroups} from '../../../../../../../../common/services/liferay/graphql/account-subscription-groups/queries/useGetAccountSubscriptionGroups';
import {ACCOUNT_SUBSCRIPTION_GROUPS_STATUS_TYPES} from '../../../../../../../../common/utils/constants/accountSubscriptionGroupsStatusTypes';

export default function useActiveAccountSubscriptionGroups(
	accountKey,
	loading,
	products
) {
	const productNames = products
		? ` and name in ('${products.join("', '")}')`
		: '';

	const {
		data,
		loading: accountSubscriptionGroupsLoading,
	} = useGetAccountSubscriptionGroups({
		filter: `accountKey eq '${accountKey}' and activationStatus eq '${ACCOUNT_SUBSCRIPTION_GROUPS_STATUS_TYPES.active}'${productNames}`,
		skip: loading,
	});

	return {data, loading: loading || accountSubscriptionGroupsLoading};
}
