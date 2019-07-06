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
import DropDownAction from './DropDownAction.es';
import React, {useState} from 'react';

const {ItemList} = ClayDropDown;

export default function DropDown(props) {
	const {actions, row} = props;
	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.RightCenter}
			onActiveChange={newVal => setActive(newVal)}
			trigger={
				<ClayIcon
					spritemap={`${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`}
					symbol='ellipsis-v'
				/>
			}
		>
			<ItemList>
				{actions.map((action, index) => (
					<DropDownAction
						key={index}
						action={action}
						row={row}
						setActive={setActive}
					/>
				))}
			</ItemList>
		</ClayDropDown>
	);
}
