import {useQuery} from '@apollo/client';
import React, {useState} from 'react';
import {getAccountSubscriptions} from '../../../../common/services/liferay/graphql/queries';
import CardSubscription from '../CardSubscription';
import SubscriptionsFilterByStatus from '../SubscriptionsFilterByStatus';
import SubscriptionsNavbar from '../SubscriptionsNavbar';

export const POSSIBLE_STATUS = {
	active: 'Active',
	expired: 'Expired',
	future: 'Future',
};

const Subscriptions = ({accountKey}) => {
	const [selectedSubscriptionGroup, setSelectedSubscriptionGroup] = useState(
		''
	);
	const [selectedStatus, setSelectedStatus] = useState([
		POSSIBLE_STATUS.active,
		POSSIBLE_STATUS.expired,
		POSSIBLE_STATUS.future,
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
			` subscriptionStatus eq '${currentValue}' or accountSubscriptionGroupERC eq '${accountKey}_${parseAccountSubscriptionGroupERC(
				selectedSubscriptionGroup
			)}' and`
		);
	};

	const {
		data: dataAccountSubscriptions,
		loading: loadingAccountSubscriptions,
	} = useQuery(getAccountSubscriptions, {
		variables: {
			filter: `accountSubscriptionGroupERC eq '${accountKey}_${parseAccountSubscriptionGroupERC(
				selectedSubscriptionGroup
			)}'${
				selectedStatus.length === Object.keys(POSSIBLE_STATUS).length
					? ''
					: `${selectedStatus.reduce(
							getAccountSubscriptionFilterQueryString,
							' and'
					  )}`
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
				possibleStatusAmount={Object.keys(POSSIBLE_STATUS).length}
				selectedStatus={selectedStatus}
				setSelectedStatus={setSelectedStatus}
				setSelectedSubscriptionGroup={setSelectedSubscriptionGroup}
			/>

			<SubscriptionsFilterByStatus
				selectedStatus={selectedStatus}
				setSelectedStatus={setSelectedStatus}
			/>

			<div className="d-flex flex-wrap">
				{!loadingAccountSubscriptions &&
				accountSubscriptions.length > 0 ? (
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

export default Subscriptions;
