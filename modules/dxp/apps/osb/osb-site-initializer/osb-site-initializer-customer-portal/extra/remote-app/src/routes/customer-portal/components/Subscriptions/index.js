import {useQuery} from '@apollo/client';
import React, {useState} from 'react';
import {getAccountSubscriptions} from '../../../../common/services/liferay/graphql/queries';
import CardSubscription from '../CardSubscription';
import SubscriptionsNavbar from '../SubscriptionsNavbar';

const Subscriptions = ({accountKey}) => {
	const [selectedTag, setSelectedTag] = useState('');
	const [selectedStatus, setSelectedStatus] = useState('');

	const parseAccountSubscriptionGroupERC = (tagName) => {
		return tagName.toLowerCase().replace(' ', '-');
	};

	const {
		data: subscriptionsByTag,
		loading: loadingAccountSubscriptions,
	} = useQuery(getAccountSubscriptions, {
		variables: {
			filter: `accountSubscriptionGroupERC eq '${accountKey}_${parseAccountSubscriptionGroupERC(
				selectedTag
			)}'${
				selectedStatus === 'All'
					? ''
					: ` and subscriptionStatus eq '${selectedStatus}'`
			}`,
		},
	});

	const subscriptionsByTagItems =
		subscriptionsByTag?.c?.accountSubscriptions?.items || [];

	return (
		<div className="d-flex flex-column mx-4">
			<h3>Subscriptions</h3>

			<SubscriptionsNavbar
				accountKey={accountKey}
				setSelectedStatus={setSelectedStatus}
				setSelectedTag={setSelectedTag}
			/>

			<div className="d-flex flex-wrap">
				{!loadingAccountSubscriptions &&
					subscriptionsByTagItems.map((item, index) => (
						<CardSubscription
							cardSubscriptionData={item}
							key={index}
						/>
					))}
			</div>
		</div>
	);
};

export default Subscriptions;
