import {useQuery} from '@apollo/client';
import React, {useState} from 'react';
import {getAccountSubscriptions} from '../../../../common/services/liferay/graphql/queries';
import CardSubscription from '../CardSubscription';
import SubscriptionsNavbar from '../SubscriptionsNavbar';

const Subscriptions = ({accountKey}) => {
	const [selectedSubscriptionGroup, setSelectedSubscriptionGroup] = useState('');
	const [selectedStatus, setSelectedStatus] = useState('');

	const parseAccountSubscriptionGroupERC = (subscriptionName) => {
		return subscriptionName.toLowerCase().replace(' ', '-');
	};

	const {
		data: dataAccountSubscriptions,
		loading: loadingAccountSubscriptions,
	} = useQuery(getAccountSubscriptions, {
		variables: {
			filter: `accountSubscriptionGroupERC eq '${accountKey}_${parseAccountSubscriptionGroupERC(
				selectedSubscriptionGroup
			)}'${
				selectedStatus === 'All'
					? ''
					: ` and subscriptionStatus eq '${selectedStatus}'`
			}`,
		},
	});

	const accountSubscriptions =
		dataAccountSubscriptions?.c?.accountSubscriptions?.items || [];

	return (
		<div className="d-flex flex-column mx-4">
			<h3>Subscriptions</h3>

			<SubscriptionsNavbar
				accountKey={accountKey}
				setSelectedStatus={setSelectedStatus}
				setSelectedSubscriptionGroup={setSelectedSubscriptionGroup}
			/>

			<div className="d-flex flex-wrap">
				{!loadingAccountSubscriptions &&
					accountSubscriptions.map((item, index) => (
						<CardSubscription
							cardSubscriptionData={item}
							key={index}
							selectedSubscriptionGroup={selectedSubscriptionGroup}
						/>
					))}
			</div>
		</div>
	);
};

export default Subscriptions;
