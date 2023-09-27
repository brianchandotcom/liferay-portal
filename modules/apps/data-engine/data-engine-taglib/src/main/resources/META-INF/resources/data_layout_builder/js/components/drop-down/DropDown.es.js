/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import classNames from 'classnames';
import React, {useState} from 'react';

const {Item, ItemList} = ClayDropDown;

export default function DropDown({actions, className, disabled}) {
	const [active, setActive] = useState(false);

	const DropdownButton = (
		<ClayButtonWithIcon
			aria-label={Liferay.Language.get('actions')}
			className="page-link"
			disabled={disabled}
			displayType="unstyled"
			symbol="ellipsis-v"
		/>
	);

	if (!actions.length) {
		return DropdownButton;
	}

	const onSelectItem = (event, action) => {
		event.preventDefault();

		if (typeof action.action === 'function') {
			action.action();
		}

		setActive(false);
	};

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomRight}
			className={classNames('dropdown-action', className)}
			onActiveChange={(item) => setActive(item)}
			trigger={DropdownButton}
		>
			<ItemList>
				{actions.map((action, index) => (
					<Item
						key={index}
						onClick={(event) => onSelectItem(event, action)}
					>
						{action.name}
					</Item>
				))}
			</ItemList>
		</ClayDropDown>
	);
}
