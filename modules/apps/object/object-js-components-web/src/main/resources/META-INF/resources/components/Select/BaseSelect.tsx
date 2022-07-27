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
import ClayDropDown from '@clayui/drop-down';
import React, {ReactNode, useRef} from 'react';

import {FieldBase} from '../FieldBase';

import './index.scss';

export interface CustomItem<T = string> {
	checked?: boolean;
	description?: string;
	label: string;
	value?: T;
}

export interface SelectProps {
	className?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	placeholder?: string;
	required?: boolean;
	value?: string | number | string[];
}

interface IProps extends SelectProps {
	children: ReactNode;
	dropdownActive: boolean;
	setDropdownActive: React.Dispatch<React.SetStateAction<boolean>>;
}

export function BaseSelect({
	children,
	className,
	disabled,
	dropdownActive,
	error,
	feedbackMessage,
	id,
	label,
	placeholder = Liferay.Language.get('choose-an-option'),
	required,
	setDropdownActive,
	value,
}: IProps) {
	const inputRef = useRef(null);

	return (
		<FieldBase
			className={className}
			disabled={disabled}
			errorMessage={error}
			helpMessage={feedbackMessage}
			id={id}
			label={label}
			required={required}
		>
			<ClayAutocomplete>
				<ClayAutocomplete.Input
					defaultValue={value}
					disabled={disabled}
					onClick={() => setDropdownActive((active) => !active)}
					placeholder={placeholder}
					ref={inputRef}
					value={value}
				/>

				<ClayAutocomplete.DropDown
					active={dropdownActive}
					alignElementRef={inputRef}
					alignmentByViewport
					closeOnClickOutside
					onSetActive={setDropdownActive}
				>
					<ClayDropDown.ItemList>{children}</ClayDropDown.ItemList>
				</ClayAutocomplete.DropDown>
			</ClayAutocomplete>
		</FieldBase>
	);
}
