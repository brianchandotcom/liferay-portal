/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import Button from '../button/Button.es';
import DropDownAction from './DropDownAction.es';

const {ItemList} = ClayDropDown;

export default ({actions, item}) => {
	const [active, setActive] = useState(false);

	const DropdownButton = (
		<Button className="page-link" displayType="unstyled">
			<ClayIcon symbol="ellipsis-v" />
		</Button>
	);

	actions = actions.filter(action =>
		action.show ? action.show(item) : true
	);

	if (actions.length === 0) {
		return DropdownButton;
	}

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.RightCenter}
			className="dropdown-action"
			onActiveChange={newVal => setActive(newVal)}
			trigger={DropdownButton}
		>
			<ItemList>
				{actions.map((action, index) => (
					<DropDownAction
						action={action}
						item={item}
						key={index}
						setActive={setActive}
					/>
				))}
			</ItemList>
		</ClayDropDown>
	);
};
