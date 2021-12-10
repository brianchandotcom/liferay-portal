import {useQuery} from '@apollo/client';
import React, {useEffect, useState} from 'react';
import {getAccountSubscriptionGroupsByFilter} from '../../../../common/services/liferay/graphql/queries';
import SubscriptionsFilterByStatus from '../SubscriptionsFilterByStatus';

const SubscriptionsNavbar = ({
	accountKey,
	setSelectedStatus,
	setSelectedSubscriptionGroup,
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

	useEffect(() => {
		if (accountSubscriptions) {
			const accountSubsciptionsItems =
				accountSubscriptions?.c?.accountSubscriptionGroups?.items || [];

			setSubscriptionsTags(accountSubsciptionsItems);

			if (accountSubsciptionsItems.length) {
				setSelectedSubscriptionGroup(accountSubsciptionsItems[0]?.name);
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [accountSubscriptions, subscriptionsTags]);

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
									onClick={(event) =>
										setSelectedSubscriptionGroup(
											event.target.value
										)
									}
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
