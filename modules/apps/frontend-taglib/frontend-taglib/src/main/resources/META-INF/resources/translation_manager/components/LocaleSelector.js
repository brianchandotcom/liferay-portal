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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useState} from 'react';

const DropDownWithState = ({children}) => {
	const [isDropdownOpen, setIsDropdownOpen] = useState(false);

	return (
		<ClayDropDown
			active={isDropdownOpen}
			alignmentPosition={Align.BottomCenter}
			onActiveChange={isActive => setIsDropdownOpen(isActive)}
			trigger={
				<ClayButtonWithIcon
					borderless
					displayType="secondary"
					small
					symbol="plus"
				/>
			}
		>
			{children({setActive: setIsDropdownOpen})}
		</ClayDropDown>
	);
};
export default function LocaleSelector({locales, onItemClick}) {
	return (
		<DropDownWithState>
			{({setActive}) => (
				<ClayDropDown.ItemList>
					{locales.map(locale => (
						<ClayDropDown.Item
							id={locale.id}
							key={locale.id}
							onClick={() => {
								onItemClick(locale);
								setActive(false);
							}}
							symbolRight={locale.icon}
						>
							{locale.label}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			)}
		</DropDownWithState>
	);
}
