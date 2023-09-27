/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useState} from 'react';
import Skeleton from '../../../../../../../../../../../../../common/components/Skeleton';

const AccountSubscriptionGroupsDropdown = ({
	accountSubscriptionGroups,
	disabled,
	loading,
	onSelect,
	selectedIndex,
}) => {
	const [active, setActive] = useState(false);

	return (
		<DropDown
			active={active}
			closeOnClickOutside
			menuWidth="shrink"
			onActiveChange={setActive}
			trigger={
				<ClayButton
					borderless
					className="align-items-center d-flex px-2"
					data-testid="subscriptionDropDown"
					disabled={disabled}
					size="sm"
				>
					{loading ? (
						<Skeleton height={16} width={80} />
					) : (
						accountSubscriptionGroups[selectedIndex]?.name
					)}

					<span className="inline-item-after">
						<ClayIcon symbol="caret-bottom" />
					</span>
				</ClayButton>
			}
		>
			{accountSubscriptionGroups?.map(
				(accountSubscriptionGroup, index) => (
					<DropDown.Item
						className="pr-6"
						disabled={index === selectedIndex || disabled}
						key={`${index}-${index}`}
						onClick={() => {
							onSelect(index);
							setActive(false);
						}}
						symbolRight={index === selectedIndex && 'check'}
					>
						{accountSubscriptionGroup.name}
					</DropDown.Item>
				)
			)}
		</DropDown>
	);
};

export default AccountSubscriptionGroupsDropdown;
