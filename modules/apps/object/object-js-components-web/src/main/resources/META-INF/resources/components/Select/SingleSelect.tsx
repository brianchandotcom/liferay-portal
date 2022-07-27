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

import ClayDropDown from '@clayui/drop-down';
import React, {useState} from 'react';

import {BaseSelect, CustomItem, SelectProps} from './BaseSelect';

import './index.scss';

interface IProps<T extends CustomItem<number | string> = CustomItem>
	extends SelectProps {
	onChange?: (selected: T) => void;
	options: T[];
}

export function SingleSelect<
	T extends CustomItem<number | string> = CustomItem
>({onChange = () => {}, options, ...otherProps}: IProps<T>) {
	const [dropdownActive, setDropdownActive] = useState<boolean>(false);

	return (
		<BaseSelect
			dropdownActive={dropdownActive}
			setDropdownActive={setDropdownActive}
			{...otherProps}
		>
			{options.map((option, index) => (
				<ClayDropDown.Item
					key={index}
					onClick={() => {
						setDropdownActive(false);
						onChange(option);
					}}
				>
					<div>{option.label}</div>

					{option.description && (
						<span className="text-small">{option.description}</span>
					)}
				</ClayDropDown.Item>
			))}
		</BaseSelect>
	);
}
