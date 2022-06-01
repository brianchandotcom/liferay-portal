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
import React, {useRef, useState} from 'react';

import {FieldBase} from './FieldBase';

import './FormCustomSelect.scss';

export function FormCustomSelect<T extends CustomItem = CustomItem>({
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	onChange,
	options,
	placeholder = Liferay.Language.get('choose-an-option'),
	required,
	value,
}: IProps<T>) {
	const [active, setActive] = useState<boolean>(false);
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
					onClick={() => setActive((active) => !active)}
					placeholder={placeholder}
					ref={inputRef}
					value={value}
				/>

				<ClayAutocomplete.DropDown
					active={active}
					alignElementRef={inputRef}
					closeOnClickOutside
					onSetActive={setActive}
				>
					<ClayDropDown.ItemList>
						{options?.map((option, index) => (
							<ClayDropDown.Item
								key={index}
								onClick={() => {
									setActive(false);
									onChange?.(option);
								}}
							>
								<div>{option.label}</div>

								{option.description && (
									<span className="text-small">
										{option.description}
									</span>
								)}
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayAutocomplete.DropDown>
			</ClayAutocomplete>
		</FieldBase>
	);
}

export interface CustomItem {
	description?: string;
	label: string;
	value?: string;
}
interface IProps<T extends CustomItem = CustomItem> {
	className?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	onChange?: (selected: T) => void;
	options: T[];
	placeholder?: string;
	required?: boolean;
	value?: string | number | string[];
}
