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
import {useCallback, useMemo, useState} from 'react';

const RolesDropdown = ({accountRoles, currentRoleBriefNames, onClick}) => {
	const [active, setActive] = useState(false);
	const [items, setItems] = useState(
		accountRoles.map((accountRole) => ({
			active: currentRoleBriefNames.includes(accountRole.name),
			label: accountRole.name,
		}))
	);

	const activeItems = useMemo(() => items.filter((item) => item.active), [
		items,
	]);

	const getTriggerLabel = useCallback(
		() => (
			<div className="text-truncate">
				{!activeItems.length
					? '(Empty role selection)'
					: activeItems.map((item) => item.label).join(', ')}
			</div>
		),
		[activeItems]
	);

	const handleOnClick = (index) => {
		items[index].active = !items[index].active;
		const currentActiveItems = items.filter((item) => item.active);

		onClick(currentActiveItems.map((item) => item.label));

		setItems([...items]);
	};

	const getDropdownItems = () =>
		items.map((item, index) => (
			<DropDown.Item
				className="pr-6"
				key={`${item.label}-${index}`}
				onClick={() => handleOnClick(index)}
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
					{getTriggerLabel()}

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
