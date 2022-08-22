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

import React, {useEffect, useRef} from 'react';
import RoundedGroupButtons from '../../../../../../../../../common/components/RoundedGroupButtons';
import SubscriptionsDropDownMenu from './components/SubscriptionsDropdownMenu';
import useUpdateShowDropDown from './hooks/useUpdateShowDropDown';

const SubscriptionsNavbar = ({
	selectedSubscriptionGroup,
	setSelectedSubscriptionGroup,
	subscriptionGroups,
}) => {
	const subscriptionNavbarRef = useRef();

	const showDropDown = useUpdateShowDropDown();

	useEffect(() => {
		setSelectedSubscriptionGroup(subscriptionGroups[0]?.name);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [subscriptionGroups]);

	return (
		<div className="d-flex rounded-pill w-100" ref={subscriptionNavbarRef}>
			<nav className="mb-2 mt-4 pt-2">
				{subscriptionGroups.length === 1 &&
					subscriptionGroups.map((subscriptionGroup) => (
						<h5
							className="text-brand-primary"
							key={subscriptionGroup.name}
						>
							{subscriptionGroup.name}
						</h5>
					))}

				{subscriptionGroups.length > 1 &&
					subscriptionGroups.length < 5 && (
						<>
							{showDropDown && (
								<SubscriptionsDropDownMenu
									selectedSubscriptionGroup={
										selectedSubscriptionGroup
									}
									setSelectedSubscriptionGroup={
										setSelectedSubscriptionGroup
									}
									subscriptionGroups={subscriptionGroups}
								/>
							)}

							{!showDropDown && (
								<RoundedGroupButtons
									groupButtons={subscriptionGroups.map(
										(subscriptionGroup) => ({
											label: subscriptionGroup.name,

											value: subscriptionGroup.name,
										})
									)}
									handleOnChange={(value) => {
										setSelectedSubscriptionGroup(value);
									}}
									id="subscription-navbar"
								/>
							)}
						</>
					)}

				{subscriptionGroups.length > 4 && (
					<SubscriptionsDropDownMenu
						selectedSubscriptionGroup={selectedSubscriptionGroup}
						setSelectedSubscriptionGroup={
							setSelectedSubscriptionGroup
						}
						subscriptionGroups={subscriptionGroups}
					/>
				)}
			</nav>
		</div>
	);
};

export default SubscriptionsNavbar;
