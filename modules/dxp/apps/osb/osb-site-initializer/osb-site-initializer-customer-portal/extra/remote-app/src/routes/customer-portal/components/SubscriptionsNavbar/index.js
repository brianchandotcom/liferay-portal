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
