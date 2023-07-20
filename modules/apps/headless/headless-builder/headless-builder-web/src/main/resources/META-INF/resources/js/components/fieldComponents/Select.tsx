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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

interface Option {
	label: string;
	value: string;
}

interface SelectProps {
	cleanUp?: voidReturn;
	onClick: (value: string) => void;
	options: Option[];
	placeholder?: string;
}

export function Select({cleanUp, onClick, options, placeholder}: SelectProps) {
	const [dropdownActive, setDropdownActive] = useState(false);
	const [inputValue, setInputValue] = useState('');

	const filteredOptions = options.filter((option) =>
		option.label.toUpperCase().includes(inputValue.toUpperCase())
	);

	const handleBlur = () => {
		if (cleanUp && !options.some((option) => option.label === inputValue)) {
			cleanUp();
		}
	};

	const handleInputChange = (value: string) => {
		setInputValue(value);

		if (!dropdownActive) {
			setDropdownActive(true);
		}
	};

	const handleSelect = (option: Option) => {
		setInputValue(option.label);
		onClick(option.value);
		setDropdownActive(false);
	};

	return (
		<ClayAutocomplete>
			<ClayIcon
				className="select-field-icon"
				onClick={() => setDropdownActive((active) => !active)}
				symbol="caret-double"
			/>

			<ClayAutocomplete.Input
				onBlur={handleBlur}
				onChange={({target: {value}}) => handleInputChange(value)}
				onFocus={() => setDropdownActive((active) => !active)}
				placeholder={
					placeholder ??
					Liferay.Language.get('please-select-an-option')
				}
				value={inputValue}
			/>

			<ClayAutocomplete.DropDown
				active={dropdownActive}
				closeOnClickOutside
				onSetActive={setDropdownActive}
			>
				{filteredOptions.map((option) => (
					<ClayAutocomplete.Item
						key={option.value}
						match={inputValue}
						onClick={() => handleSelect(option)}
					>
						{option.label}
					</ClayAutocomplete.Item>
				))}
			</ClayAutocomplete.DropDown>
		</ClayAutocomplete>
	);
}
