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

import {useEffect, useState} from 'react';
import {useLazyGetAccountSubscriptions} from '../../../../../../../../common/services/liferay/graphql/account-subscriptions';

export default function useAccountSubscriptions(
	accountSubcriptionGroup,
	accountSubscriptionGroupsLoading
) {
	const [lastSubscriptionStatus, setLastSubscriptionStatus] = useState();

	const [
		handleGetAccountSubscriptions,
		{data, loading},
	] = useLazyGetAccountSubscriptions();

	const getSubscriptionStatusFilter = (subscriptionStatus) => {
		if (subscriptionStatus) {
			return ` and subscriptionStatus in (${subscriptionStatus})`;
		}

		return '';
	};

	const onSelectSubscriptionStatus = (subscriptionStatus) =>
		setLastSubscriptionStatus(subscriptionStatus);

	useEffect(() => {
		if (accountSubcriptionGroup) {
			handleGetAccountSubscriptions({
				variables: {
					filter: `accountSubscriptionGroupERC eq '${
						accountSubcriptionGroup.externalReferenceCode
					}'${getSubscriptionStatusFilter(lastSubscriptionStatus)}`,
				},
			});
		}
	}, [
		handleGetAccountSubscriptions,
		accountSubcriptionGroup,
		lastSubscriptionStatus,
	]);

	return [
		onSelectSubscriptionStatus,
		{
			data,
			loading: accountSubscriptionGroupsLoading || loading,
		},
	];
}
