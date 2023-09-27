/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {useLazyGetAccountSubscriptions} from '../../../../../../../../common/services/liferay/graphql/account-subscriptions';

export default function useAccountSubscriptions(
	accountSubcriptionGroup,
	accountSubscriptionGroupsLoading
) {
	const [lastSubscriptionStatus, setLastSubscriptionStatus] = useState(
		'Active'
	);

	const [
		handleGetAccountSubscriptions,
		{called, data, loading},
	] = useLazyGetAccountSubscriptions();

	const getSubscriptionStatusFilter = (subscriptionStatus) => {
		if (subscriptionStatus) {
			return ` and subscriptionStatus in ('${subscriptionStatus}')`;
		}

		return '';
	};

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
		setLastSubscriptionStatus,
		{
			data,
			loading: accountSubscriptionGroupsLoading || !called || loading,
		},
	];
}
