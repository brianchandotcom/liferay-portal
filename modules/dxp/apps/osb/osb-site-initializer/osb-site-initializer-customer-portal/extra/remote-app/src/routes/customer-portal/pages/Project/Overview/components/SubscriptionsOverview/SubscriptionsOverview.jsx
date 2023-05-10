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

import {useEffect} from 'react';
import {useOutletContext} from 'react-router-dom';
import i18n from '../../../../../../../common/I18n';
import Skeleton from '../../../../../../../common/components/Skeleton';
import AccountSubscriptionsList from './components/AccountSubscriptionsList/AccountSubscriptionsList';
import SubscriptionsNavbar from './components/SubscriptionsNavbar/SubscriptionsNavbar';
import useAccountSubscriptionGroups from './hooks/useAccountSubscriptionGroups';
import useAccountSubscriptions from './hooks/useAccountSubscriptions';

const SubscriptionsOverview = ({koroneikiAccount, loading}) => {
	const {setHasQuickLinksPanel, setHasSideMenu} = useOutletContext();
	const [
		{lastAccountSubcriptionGroup, setLastAccountSubscriptionGroup},
		{
			data: accountSubscriptionGroupsData,
			loading: accountSubscriptionGroupsLoading,
		},
	] = useAccountSubscriptionGroups(koroneikiAccount?.accountKey, loading);

	const accountSubscriptionGroups =
		accountSubscriptionGroupsData?.c.accountSubscriptionGroups;

	const [
		setLastSubscriptionStatus,
		{data: accountSubscriptionsData, loading: accountSubscriptionsLoading},
	] = useAccountSubscriptions(
		lastAccountSubcriptionGroup,
		accountSubscriptionGroupsLoading
	);

	const accountSubscriptions =
		accountSubscriptionsData?.c.accountSubscriptions.items;

	useEffect(() => {
		setHasQuickLinksPanel(true);
		setHasSideMenu(true);
	}, [setHasSideMenu, setHasQuickLinksPanel]);

	const handleDropdownOnClick = (selectedStatus) => {
		if (selectedStatus) {
			setLastSubscriptionStatus(selectedStatus.join("', '"));

			return;
		}

		setLastSubscriptionStatus();
	};

	return (
		<div>
			{accountSubscriptionGroupsLoading ? (
				<Skeleton className="mb-4 pb-2" height={35} width={200} />
			) : (
				!accountSubscriptionGroups?.hasPartnership && (
					<h3 className="mb-4 pb-2">
						{i18n.translate('subscriptions')}
					</h3>
				)
			)}

			{!!lastAccountSubcriptionGroup && (
				<>
					<SubscriptionsNavbar
						accountSubscriptionGroups={
							accountSubscriptionGroups?.items
						}
						disabled={accountSubscriptionsLoading}
						loading={accountSubscriptionGroupsLoading}
						onClickDropdownItem={handleDropdownOnClick}
						onSelectNavItem={(accountSubscriptionGroup) => {
							setLastAccountSubscriptionGroup(
								accountSubscriptionGroup
							);
						}}
					/>

					<AccountSubscriptionsList
						accountKey={koroneikiAccount?.accountKey}
						accountSubscriptionGroup={lastAccountSubcriptionGroup}
						accountSubscriptions={accountSubscriptions}
						loading={accountSubscriptionsLoading}
						selectedAccountSubscriptionGroup={
							lastAccountSubcriptionGroup
						}
					/>
				</>
			)}
		</div>
	);
};

export default SubscriptionsOverview;
