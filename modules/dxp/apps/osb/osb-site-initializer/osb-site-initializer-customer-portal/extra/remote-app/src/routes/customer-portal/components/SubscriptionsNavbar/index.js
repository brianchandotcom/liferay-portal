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

import {Button as ClayButton, DropDown} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useRef, useState} from 'react';
import RoundedGroupButtons from '../../../../common/components/RoundedGroupButtons';
import {useCustomerPortal} from '../../context';

const SubscriptionDropDownMenu = ({
	selectedSubscriptionGroup,
	setSelectedSubscriptionGroup,
	subscriptionGroups,
}) => {
	const [active, setActive] = useState(false);

	return (
		<div className="align-items-center d-flex mt-4 pb-3">
			<h6>Type:</h6>

			<DropDown
				active={active}
				closeOnClickOutside
				menuElementAttrs={{
					className: 'subscription-group-filter',
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="font-weight-semi-bold ml-2 pb-2 shadow-none text-brand-primary"
						displayType="unstyled"
					>
						{selectedSubscriptionGroup}

						<ClayIcon symbol="caret-bottom" />
					</ClayButton>
				}
			>
				{subscriptionGroups.map((subscriptionGroup) => (
					<DropDown.Item
						key={subscriptionGroup.name}
						onClick={(event) => {
							setSelectedSubscriptionGroup(event.target.value);
							setActive(false);
						}}
						symbolRight={
							subscriptionGroup.name === selectedSubscriptionGroup
								? 'check'
								: ''
						}
						value={subscriptionGroup.name}
					>
						{subscriptionGroup.name}
					</DropDown.Item>
				))}
			</DropDown>
		</div>
	);
};

const SubscriptionsNavbar = ({
	selectedSubscriptionGroup,
	setSelectedSubscriptionGroup,
	subscriptionGroups,
}) => {
	const [showDropDown, setShowDropDown] = useState(false);
	const [{isQuickLinksExpanded}] = useCustomerPortal();

	const subscriptionNavbarRef = useRef();

	useEffect(() => {
		setSelectedSubscriptionGroup(subscriptionGroups[0]?.name);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [subscriptionGroups]);

	useEffect(() => {
		const updateShowDropDown = () => {
			setShowDropDown(
				subscriptionNavbarRef?.current &&
					subscriptionNavbarRef.current.offsetWidth <
						(isQuickLinksExpanded ? 500 : 570)
			);
		};

		updateShowDropDown();
		window.addEventListener('resize', updateShowDropDown);

		return () => window.removeEventListener('resize', updateShowDropDown);
	}, [isQuickLinksExpanded]);

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
								<SubscriptionDropDownMenu
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
					<SubscriptionDropDownMenu
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
