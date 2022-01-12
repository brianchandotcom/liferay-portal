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
import client from '../../../../apolloClient';
import {getAccountSubscriptions} from '../../../../common/services/liferay/graphql/queries';
import CardSubscription from '../../components/CardSubscription';
import SubscriptionsFilterByStatus from '../../components/SubscriptionsFilterByStatus';
import SubscriptionsNavbar from '../../components/SubscriptionsNavbar';
import {useCustomerPortal} from '../../context';
import {actionTypes} from '../../context/reducer';
import {SUBSCRIPTIONS_STATUS} from '../../utils/constants';
import {getWebContents} from '../../utils/webContentsGenerator';
import OverviewSkeleton from './Skeleton';

const Overview = ({project, subscriptionGroups}) => {
	const [, dispatch] = useCustomerPortal();
	const [accountSubscriptions, setAccountSubscriptions] = useState([]);
	const [selectedSubscriptionGroup, setSelectedSubscriptionGroup] = useState(
		''
	);
	const [selectedStatus, setSelectedStatus] = useState([
		SUBSCRIPTIONS_STATUS.active,
		SUBSCRIPTIONS_STATUS.expired,
		SUBSCRIPTIONS_STATUS.future,
	]);

	const parseAccountSubscriptionGroupERC = (subscriptionName) => {
		return subscriptionName.toLowerCase().replace(' ', '-');
	};

	const getAccountSubscriptionFilterQueryString = (
		previousValue,
		currentValue,
		currentIndex,
		array
	) => {
		if (currentIndex === array.length - 1) {
			return previousValue + ` subscriptionStatus eq '${currentValue}'`;
		}

		return (
			previousValue +
			` subscriptionStatus eq '${currentValue}' or accountSubscriptionGroupERC eq '${
				project.accountKey
			}_${parseAccountSubscriptionGroupERC(
				selectedSubscriptionGroup
			)}' and`
		);
	};

	useEffect(() => {
		const getSubscriptions = async (
			accountKey,
			subscriptionGroup,
			status
		) => {
			const {data: dataAccountSubscriptions} = await client.query({
				query: getAccountSubscriptions,
				variables: {
					filter: `accountSubscriptionGroupERC eq '${accountKey}_${parseAccountSubscriptionGroupERC(
						subscriptionGroup
					)}'${
						status.length ===
						Object.keys(SUBSCRIPTIONS_STATUS).length
							? ''
							: `${status.reduce(
									getAccountSubscriptionFilterQueryString,
									' and'
							  )}`
					}`,
				},
			});

			if (dataAccountSubscriptions) {
				setAccountSubscriptions(
					dataAccountSubscriptions?.c?.accountSubscriptions?.items
				);
			}
		};

		if (selectedSubscriptionGroup && selectedStatus.length) {
			getSubscriptions(
				project.accountKey,
				selectedSubscriptionGroup,
				selectedStatus
			);
		}

		if (!selectedStatus.length) {
			setAccountSubscriptions([]);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [project, selectedStatus, selectedSubscriptionGroup]);

	useEffect(() => {
		dispatch({
			payload: getWebContents({
				dxpVersion: project.dxpVersion,
				slaCurrent: project.slaCurrent,
				subscriptionGroups,
			}),
			type: actionTypes.UPDATE_QUICK_LINKS,
		});
	}, [dispatch, project, subscriptionGroups]);

	return (
		<div className="d-flex flex-column">
			<h3>Subscriptions</h3>

			<SubscriptionsNavbar
				setSelectedSubscriptionGroup={setSelectedSubscriptionGroup}
				subscriptionGroups={subscriptionGroups}
			/>

			<SubscriptionsFilterByStatus
				selectedStatus={selectedStatus}
				setSelectedStatus={setSelectedStatus}
			/>

			<div className="d-flex flex-wrap mt-4">
				{accountSubscriptions.length ? (
					accountSubscriptions.map((accountSubscription, index) => (
						<CardSubscription
							cardSubscriptionData={accountSubscription}
							key={index}
							selectedSubscriptionGroup={
								selectedSubscriptionGroup
							}
						/>
					))
				) : (
					<p className="mx-auto pt-5">
						No subscriptions match these criteria.
					</p>
				)}
			</div>
		</div>
	);
};

Overview.Skeleton = OverviewSkeleton;
export default Overview;
