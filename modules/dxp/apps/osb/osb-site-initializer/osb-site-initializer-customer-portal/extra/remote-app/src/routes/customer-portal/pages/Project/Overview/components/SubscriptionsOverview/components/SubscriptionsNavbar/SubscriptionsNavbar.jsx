/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AccountSubscriptionGroupsNav from './components/AccountSubscriptionGroupsNav/AccountSubscriptionGroupsNav';
import SubscriptionStatusDropdown from './components/SubscriptionStatusDropdown/SubscriptionStatusDropdown';

const SubscriptionsNavbar = ({
	accountSubscriptionGroups,
	disabled,
	loading,
	onClickDropdownItem,
	onSelectNavItem,
}) => (
	<div>
		<AccountSubscriptionGroupsNav
			accountSubscriptionGroups={accountSubscriptionGroups}
			disabled={disabled}
			loading={loading}
			onSelect={onSelectNavItem}
		/>

		<SubscriptionStatusDropdown
			disabled={disabled}
			loading={loading}
			onClick={onClickDropdownItem}
		/>
	</div>
);

export default SubscriptionsNavbar;
