import {useQuery} from '@apollo/client';
import React, {useEffect, useState} from 'react';
import {getAccountSubscriptionGroupsByFilter} from '../../../../common/services/liferay/graphql/queries';
import SubscriptionsFilterByStatus from '../SubscriptionsFilterByStatus';

const SubscriptionsNavbar = ({
	accountKey,
	setSelectedStatus,
	setSelectedTag,
}) => {
	const [subscriptionsTags, setSubscriptionsTags] = useState([]);

	const {
		data: accountSubscriptions,
		loading: isAccountSubscriptionsLoading,
	} = useQuery(getAccountSubscriptionGroupsByFilter, {
		variables: {
			filter: `accountKey eq '${accountKey}' and hasActivation eq true`,
		},
	});

	const handleClick = (event) => {
		event.preventDefault();
		setSelectedTag(event.target.value);
	};

	useEffect(() => {
		if (!isAccountSubscriptionsLoading && accountSubscriptions) {
			const accountSubsciptionsItems =
				accountSubscriptions?.c?.accountSubscriptionGroups?.items || [];
			setSubscriptionsTags(accountSubsciptionsItems);
		}

		setSelectedTag(subscriptionsTags[0]?.name || '');
	}, [
		accountSubscriptions,
		isAccountSubscriptionsLoading,
		setSelectedTag,
		subscriptionsTags,
	]);

	return (
		<div className="rounded-pill">
			{!isAccountSubscriptionsLoading && (
				<>
					<nav className="my-4">
						{subscriptionsTags &&
							subscriptionsTags.map((tag) => (
								<button
									className="mr-2"
									key={tag.name}
									onClick={handleClick}
									value={tag.name}
								>
									{tag.name}
								</button>
							))}
					</nav>

					<SubscriptionsFilterByStatus
						setSelectedStatus={setSelectedStatus}
					/>
				</>
			)}
		</div>
	);
};

export default SubscriptionsNavbar;
