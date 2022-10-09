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
import {Button, DropDown} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {useMemo, useState} from 'react';
import isSupportSeatRole from '../../../../../../../../../../../../common/utils/isSupportSeatRole';

const RolesDropdown = ({
	accountRoles,
	availableSupportSeatsCount,
	currentRoleBriefName,
	hasAccountSupportSeatRole,
	onClick,
	supportSeatsCount,
}) => {
	const [active, setActive] = useState(false);

	const [selectedAccountRoleName, setSelectedAccountRoleName] = useState(
		currentRoleBriefName
	);

	const items = useMemo(
		() =>
			accountRoles.map((accountRole) => ({
				active: accountRole.name === selectedAccountRoleName,
				disabled: hasAccountSupportSeatRole
					? supportSeatsCount === 1
					: isSupportSeatRole(accountRole.name) &&
					  availableSupportSeatsCount <= 0,
				label: accountRole.name,
			})),
		[
			accountRoles,
			availableSupportSeatsCount,
			hasAccountSupportSeatRole,
			selectedAccountRoleName,
			supportSeatsCount,
		]
	);

	const handleOnClick = (accountRoleName) => {
		if (accountRoleName !== selectedAccountRoleName) {
			onClick(accountRoleName);
			setSelectedAccountRoleName(accountRoleName);
		}
	};

	const getDropdownItems = () =>
		items.map((item, index) => (
			<DropDown.Item
				className="pr-6"
				disabled={item.disabled}
				key={`${item.label}-${index}`}
				onClick={() => handleOnClick(item.label)}
				symbolRight={item.active && 'check'}
			>
				{item.label}
			</DropDown.Item>
		));

	return (
		<DropDown
			active={active}
			closeOnClickOutside
			menuWidth="shrink"
			onActiveChange={setActive}
			trigger={
				<Button
					className="align-items-center d-flex justify-content-between w-100"
					displayType="secondary"
					outline
					small
				>
					<div className="text-truncate">
						{selectedAccountRoleName}
					</div>

					<span className="inline-item inline-item-after mt-1">
						<ClayIcon symbol="caret-bottom" />
					</span>
				</Button>
			}
		>
			{getDropdownItems()}
		</DropDown>
	);
};

export default RolesDropdown;
