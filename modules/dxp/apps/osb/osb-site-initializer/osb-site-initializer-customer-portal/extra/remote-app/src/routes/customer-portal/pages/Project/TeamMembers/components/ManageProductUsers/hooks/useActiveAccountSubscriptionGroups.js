/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		filter: `accountKey eq '${accountKey}' and activationStatus eq '${ACCOUNT_SUBSCRIPTION_GROUPS_STATUS_TYPES.active}' and hasActivation eq true and manageContactsURL ne ''${productNames}`,
		skip: loading,
	});

	return {data, loading: loading || accountSubscriptionGroupsLoading};
}
