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

import {useState} from 'react';
import NavSegment from '../../../../../../../../../../../common/components/NavSegment/NavSegment';
import AccountSubscriptionGroupsDropdown from './components/AccountSubscriptionGroupsDropdown/AccountSubscriptionGroupsDropdown';
import useIsTablet from './hooks/useIsTablet';

const AccountSubscriptionGroupsNav = ({
	accountSubscriptionGroups,
	disabled,
	loading,
	onSelect,
}) => {
	const [selectedItemIndex, setSelectedItemIndex] = useState(0);
	const isTablet = useIsTablet();

	const getItems = () =>
		accountSubscriptionGroups?.map((accountSubscriptionGroup) => ({
			key: accountSubscriptionGroup.externalReferenceCode,
			label: accountSubscriptionGroup.name,
		}));

	const handleOnSelect = (currentIndex) => {
		onSelect(accountSubscriptionGroups[currentIndex]);
		setSelectedItemIndex(currentIndex);
	};

	const getNav = () => {
		if (accountSubscriptionGroups?.length === 1) {
			return (
				<h5 className="mb-3 text-brand-primary">
					{accountSubscriptionGroups[0].name}
				</h5>
			);
		}

		if (!isTablet && accountSubscriptionGroups?.length < 5) {
			return (
				<NavSegment
					disabled={disabled}
					items={getItems()}
					loading={loading}
					onSelect={handleOnSelect}
					selectedIndex={selectedItemIndex}
				/>
			);
		}

		return (
			<AccountSubscriptionGroupsDropdown
				accountSubscriptionGroups={accountSubscriptionGroups}
				disabled={disabled}
				loading={loading}
				onSelect={handleOnSelect}
				selectedIndex={selectedItemIndex}
			/>
		);
	};

	return <>{getNav()}</>;
};

export default AccountSubscriptionGroupsNav;
