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
import {useGetAccountSubscriptionGroups} from '../../../../../../../../common/services/liferay/graphql/account-subscription-groups';
import {useLazyGetAccountSubscriptions} from '../../../../../../../../common/services/liferay/graphql/account-subscriptions';

export default function useSubscriptions(accountKey, loading) {
	const [
		lastAccountSubcriptionGroup,
		setLastAccountSubscriptionGroup,
	] = useState();

	const [lastSubscriptionStatus, setLastSubscriptionStatus] = useState();

	const accountSubscriptionGroupsQueryResult = useGetAccountSubscriptionGroups(
		{
			filter: `accountKey eq '${accountKey}' and hasActivation eq true`,
			skip: loading,
			sort: 'tabOrder:asc',
		}
	);

	const accountSubscriptionGroups =
		accountSubscriptionGroupsQueryResult.data?.c.accountSubscriptionGroups
			.items;
	const accountSubscriptionGroupsLoading =
		loading || accountSubscriptionGroupsQueryResult.loading;

	const [
		handleGetAccountSubscriptions,
		accountSubscriptionsQueryResult,
	] = useLazyGetAccountSubscriptions();

	const getSubscriptionStatusFilter = (subscriptionStatus) => {
		if (subscriptionStatus) {
			return ` and subscriptionStatus in (${subscriptionStatus})`;
		}

		return '';
	};

	const onSelectAccountSubscriptionGroup = (accountSubscriptionGroup) =>
		setLastAccountSubscriptionGroup(accountSubscriptionGroup);

	const onSelectSubscriptionStatus = (subscriptionStatus) =>
		setLastSubscriptionStatus(subscriptionStatus);

	useEffect(() => {
		if (
			!accountSubscriptionGroupsLoading &&
			!!accountSubscriptionGroups.length
		) {
			setLastAccountSubscriptionGroup(accountSubscriptionGroups[0]);
		}
	}, [accountSubscriptionGroups, accountSubscriptionGroupsLoading]);

	useEffect(() => {
		if (lastAccountSubcriptionGroup) {
			handleGetAccountSubscriptions({
				variables: {
					filter: `accountSubscriptionGroupERC eq '${
						lastAccountSubcriptionGroup.externalReferenceCode
					}'${getSubscriptionStatusFilter(lastSubscriptionStatus)}`,
				},
			});
		}
	}, [
		handleGetAccountSubscriptions,
		lastAccountSubcriptionGroup,
		lastSubscriptionStatus,
	]);

	return {
		accountSubcriptionsResult: [
			{
				onSelectAccountSubscriptionGroup,
				onSelectSubscriptionStatus,
			},
			{
				...accountSubscriptionsQueryResult,
				loading:
					accountSubscriptionGroupsLoading ||
					accountSubscriptionsQueryResult.loading,
			},
		],
		accountSubscriptionGroupsResult: {
			...accountSubscriptionGroupsQueryResult,
			loading: accountSubscriptionGroupsLoading,
		},
		lastAccountSubcriptionGroup,
	};
}
