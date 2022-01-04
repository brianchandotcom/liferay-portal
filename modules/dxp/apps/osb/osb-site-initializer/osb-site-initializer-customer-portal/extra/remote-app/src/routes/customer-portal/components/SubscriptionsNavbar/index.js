import React, {useEffect} from 'react';

const SubscriptionsNavbar = ({
	setSelectedSubscriptionGroup,
	subscriptionGroups,
}) => {
	useEffect(() => {
		setSelectedSubscriptionGroup(subscriptionGroups[0]?.name);
	}, [setSelectedSubscriptionGroup, subscriptionGroups]);

	return (
		<div className="rounded-pill">
			<nav className="my-4 pt-2">
				{subscriptionGroups &&
					subscriptionGroups.map((tag) => (
						<button
							className="mr-2"
							key={tag.name}
							onClick={(event) =>
								setSelectedSubscriptionGroup(event.target.value)
							}
							value={tag.name}
						>
							{tag.name}
						</button>
					))}
			</nav>
		</div>
	);
};

export default SubscriptionsNavbar;
