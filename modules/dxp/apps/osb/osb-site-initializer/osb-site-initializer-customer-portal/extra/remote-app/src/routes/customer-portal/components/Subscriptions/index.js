import {useQuery} from '@apollo/client';
import React, {useState} from 'react';
import {getAccountSubscriptions} from '../../../../common/services/liferay/graphql/queries';
import CardSubscription from '../CardSubscription';
import SubscriptionsFilterByStatus from '../SubscriptionsFilterByStatus';
import SubscriptionsNavbar from '../SubscriptionsNavbar';

export const POSSIBLE_STATUS_AMOUNT = 3;

const Subscriptions = ({accountKey}) => {
	const [selectedSubscriptionGroup, setSelectedSubscriptionGroup] = useState(
		''
	);
	const [selectedStatus, setSelectedStatus] = useState([
		'Active',
		'Expired',
		'Future',
	]);

	const parseAccountSubscriptionGroupERC = (subscriptionName) => {
		return subscriptionName.toLowerCase().replace(' ', '-');
	};

	const mountQueryString = (acc, cv, index, array) => {
		if (index === array.length - 1) {
			return acc + ` subscriptionStatus eq '${cv}'`;
		}

		return (
			acc +
			` subscriptionStatus eq '${cv}' or accountSubscriptionGroupERC eq '${accountKey}_${parseAccountSubscriptionGroupERC(
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
				selectedStatus.length === POSSIBLE_STATUS_AMOUNT
					? ''
					: `${selectedStatus.reduce(mountQueryString, ' and')}`
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
				possibleStatusAmount={POSSIBLE_STATUS_AMOUNT}
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
					accountSubscriptions.map((item, index) => (
						<CardSubscription
							cardSubscriptionData={item}
							key={index}
							selectedSubscriptionGroup={
								selectedSubscriptionGroup
							}
						/>
					))}
			</div>
		</div>
	);
};

export default Subscriptions;
