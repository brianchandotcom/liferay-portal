/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {cloneElement, useState} from 'react';

const DropDownAction = ({action: {action, name}, item, setActive}) => {
	if (name === 'divider') {
		return <ClayDropDown.Divider />;
	}

	return (
		<ClayDropDown.Item
			onClick={(event) => {
				event.preventDefault();
				setActive(false);

				if (action) {
					action(item);
				}
			}}
		>
			{typeof name === 'function' ? name(item) : name}
		</ClayDropDown.Item>
	);
};

export default function DropDown({actions, item, noActionsMessage}) {
	const [active, setActive] = useState(false);

	const DropdownButton = (
		<ClayButtonWithIcon
			className="page-link"
			displayType="unstyled"
			symbol="ellipsis-v"
		/>
	);

	actions = actions.filter((action) =>
		action.show ? action.show(item) : true
	);

	if (!actions.length) {
		return cloneElement(DropdownButton, {
			'data-tooltip-align': 'bottom',
			'data-tooltip-delay': '200',
			'disabled': true,
			'title': noActionsMessage,
		});
	}

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.RightCenter}
			className="dropdown-action"
			onActiveChange={(newVal) => setActive(newVal)}
			trigger={DropdownButton}
		>
			<ClayDropDown.ItemList>
				{actions.map((action, index) => (
					<DropDownAction
						action={action}
						item={item}
						key={index}
						setActive={setActive}
					/>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
