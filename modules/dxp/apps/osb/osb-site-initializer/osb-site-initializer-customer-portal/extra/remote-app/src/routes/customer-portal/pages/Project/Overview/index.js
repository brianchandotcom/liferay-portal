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

import classNames from 'classnames';
import {useEffect, useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import client from '../../../../../apolloClient';
import {getAccountSubscriptions} from '../../../../../common/services/liferay/graphql/queries';
import CardSubscription from '../../../components/CardSubscription';
import SubscriptionsFilterByStatus from '../../../components/SubscriptionsFilterByStatus';
import SubscriptionsNavbar from '../../../components/SubscriptionsNavbar';
import {useCustomerPortal} from '../../../context';
import {actionTypes} from '../../../context/reducer';
import {SUBSCRIPTIONS_STATUS} from '../../../utils/constants';
import {getWebContents} from '../../../utils/getWebContents';
import OverviewSkeleton from './Skeleton';

const Overview = () => {
	const {project, subscriptionGroups} = useOutletContext();
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

	const [
		subscriptionGroupsWithSubscriptions,
		setSubscriptionGroupsWithSubscriptions,
	] = useState([]);

	const parseAccountSubscriptionGroupERC = (subscriptionName) => {
		return subscriptionName.toLowerCase().replace(' ', '-');
	};

	const subscriptionsCards = accountSubscriptions.filter(
		(subscription) =>
			subscription.accountSubscriptionGroupERC.replace(
				`${project.accountKey}_`,
				''
			) === parseAccountSubscriptionGroupERC(selectedSubscriptionGroup) &&
			selectedStatus.includes(subscription.subscriptionStatus)
	);

	useEffect(() => {
		const getAllSubscriptions = async (accountKey) => {
			const {data: dataAccountSubscriptions} = await client.query({
				query: getAccountSubscriptions,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});

			if (dataAccountSubscriptions) {
				const dataAllSubscriptions =
					dataAccountSubscriptions?.c?.accountSubscriptions?.items;

				const accountSubscriptionGroups = subscriptionGroups.filter(
					(subscriptionGroup) =>
						dataAllSubscriptions.some(
							(subscription) =>
								subscription.accountSubscriptionGroupERC.replace(
									`${accountKey}_`,
									''
								) ===
								parseAccountSubscriptionGroupERC(
									subscriptionGroup.name
								)
						)
				);

				setAccountSubscriptions(dataAllSubscriptions);

				setSubscriptionGroupsWithSubscriptions(
					accountSubscriptionGroups.sort(
						(
							previousAccountSubscriptionGroup,
							nextAccountSubscriptionGroup
						) =>
							previousAccountSubscriptionGroup?.tabOrder -
							nextAccountSubscriptionGroup?.tabOrder
					)
				);
			}
		};

		getAllSubscriptions(project.accountKey);
	}, [project, subscriptionGroups]);

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
		<div className="d-flex flex-column mr-4 mt-6">
			<h3>Subscriptions</h3>

			{!!subscriptionGroupsWithSubscriptions.length && (
				<>
					<div
						className={classNames('align-items-center d-flex', {
							'justify-content-between':
								subscriptionGroupsWithSubscriptions.length < 5,
							'justify-content-evenly':
								subscriptionGroupsWithSubscriptions.length > 4,
						})}
					>
						<SubscriptionsNavbar
							selectedSubscriptionGroup={
								selectedSubscriptionGroup
							}
							setSelectedSubscriptionGroup={
								setSelectedSubscriptionGroup
							}
							subscriptionGroups={
								subscriptionGroupsWithSubscriptions
							}
						/>

						<SubscriptionsFilterByStatus
							selectedStatus={selectedStatus}
							setSelectedStatus={setSelectedStatus}
						/>
					</div>

					<div className="cp-overview-cards-subscription d-flex flex-wrap mt-4">
						{subscriptionsCards.length ? (
							subscriptionsCards.map(
								(accountSubscription, index) => (
									<CardSubscription
										cardSubscriptionData={
											accountSubscription
										}
										key={index}
										selectedSubscriptionGroup={
											selectedSubscriptionGroup
										}
									/>
								)
							)
						) : (
							<p className="mx-auto pt-5">
								No subscriptions match these criteria.
							</p>
						)}
					</div>
				</>
			)}
		</div>
	);
};

Overview.Skeleton = OverviewSkeleton;
export default Overview;
