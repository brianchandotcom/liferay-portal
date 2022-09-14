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
import SubscriptionsNavbar from './components/SubscriptionsNavbar/SubscriptionsNavbar';
import useSubscriptions from './hooks/useSubscriptions';

const SubscriptionsOverview = ({koroneikiAccount, loading}) => {
	const {setHasQuickLinksPanel, setHasSideMenu} = useOutletContext();
	const {
		accountSubcriptionsResult: [
			{onSelectAccountSubscriptionGroup, onSelectSubscriptionStatus},
			{loading: accountSubscriptionsLoading},
		],
		accountSubscriptionGroupsResult: {
			data: accountSubscriptionGroupsData,
			loading: accountSubscriptionGroupsLoading,
		},
	} = useSubscriptions(koroneikiAccount?.accountKey, loading);

	useEffect(() => {
		setHasQuickLinksPanel(true);
		setHasSideMenu(true);
	}, [setHasSideMenu, setHasQuickLinksPanel]);

	const handleDropdownOnClick = (selectedStatus) => {
		if (selectedStatus) {
			onSelectSubscriptionStatus(`'${selectedStatus.join("', '")}'`);

			return;
		}

		onSelectSubscriptionStatus();
	};

	const accountSubscriptionGroups =
		accountSubscriptionGroupsData?.c.accountSubscriptionGroups;

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

			<SubscriptionsNavbar
				accountSubscriptionGroups={accountSubscriptionGroups?.items}
				disabled={accountSubscriptionsLoading}
				loading={accountSubscriptionGroupsLoading}
				onClickDropdownItem={handleDropdownOnClick}
				onSelectNavItem={(accountSubscriptionGroup) => {
					onSelectAccountSubscriptionGroup(accountSubscriptionGroup);
				}}
			/>
		</div>
	);
};

export default SubscriptionsOverview;
