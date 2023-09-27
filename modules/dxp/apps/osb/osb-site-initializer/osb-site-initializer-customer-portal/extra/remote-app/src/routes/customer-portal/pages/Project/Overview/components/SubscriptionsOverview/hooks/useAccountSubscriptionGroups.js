/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {useGetAccountSubscriptionGroups} from '../../../../../../../../common/services/liferay/graphql/account-subscription-groups';

export default function useAccountSubscriptionGroups(
	accountKey,
	koroneikiAccountLoading
) {
	const [
		lastAccountSubcriptionGroup,
		setLastAccountSubscriptionGroup,
	] = useState();

	const {data, loading} = useGetAccountSubscriptionGroups({
		filter: `accountKey eq '${accountKey}'`,
		skip: koroneikiAccountLoading,
		sort: 'tabOrder:asc',
	});

	const accountSubscriptionGroups = data?.c.accountSubscriptionGroups.items;

	useEffect(() => {
		if (!loading && !!accountSubscriptionGroups?.length) {
			setLastAccountSubscriptionGroup(accountSubscriptionGroups[0]);
		}
	}, [accountSubscriptionGroups, loading]);

	return [
		{lastAccountSubcriptionGroup, setLastAccountSubscriptionGroup},
		{
			data,
			loading: koroneikiAccountLoading || loading,
		},
	];
}
