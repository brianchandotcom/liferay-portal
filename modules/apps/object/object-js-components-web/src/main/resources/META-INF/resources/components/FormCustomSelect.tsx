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
import React, {useEffect, useRef, useState} from 'react';

import {CheckboxItem} from './CheckBoxItem';
import {FieldBase} from './FieldBase';

import './FormCustomSelect.scss';

export function FormCustomSelect<T extends CustomItem = CustomItem>({
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	multipleChoice,
	onChange,
	options,
	placeholder = Liferay.Language.get('choose-an-option'),
	required,
	selectAllOption,
	setOptions,
	value,
}: IProps<T>) {
	const [active, setActive] = useState<boolean>(false);
	const [selectAllChecked, setSelectAllChecked] = useState<boolean>(false);
	const inputRef = useRef(null);

	useEffect(() => {
		if (selectAllOption) {
			let firstRender = false;

			const notAllSelected = options.find((option) => {
				if (option.checked === undefined) {
					firstRender = true;
				}

				return option.checked !== undefined && !option.checked;
			});

			if (!firstRender && !notAllSelected) {
				setSelectAllChecked(true);
			}
		}
	}, [options, selectAllOption]);

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
					value={
						multipleChoice
							? options
									.reduce<string[]>((acc, value) => {
										if (value.checked) {
											acc.push(value.label);
										}

										return acc;
									}, [])
									.join(', ')
							: value
					}
				/>

				<ClayAutocomplete.DropDown
					active={active}
					alignElementRef={inputRef}
					alignmentByViewport
					closeOnClickOutside
					onSetActive={setActive}
				>
					<ClayDropDown.ItemList>
						{multipleChoice && setOptions ? (
							<>
								{selectAllOption && (
									<CheckboxItem
										checked={selectAllChecked}
										label={Liferay.Language.get(
											'select-all'
										)}
										onChange={({target: {checked}}) => {
											setOptions(
												options.map((option) => {
													return {
														...option,
														checked,
													};
												})
											);
											setSelectAllChecked(checked);
										}}
									/>
								)}

								{options.map(({checked, label, value}) => (
									<CheckboxItem
										checked={checked}
										key={value}
										label={label}
										onChange={({target: {checked}}) => {
											setOptions(
												options.map((option) =>
													option.label === label
														? {
																...option,
																checked,
														  }
														: option
												)
											);

											if (!checked) {
												setSelectAllChecked(checked);
											}
										}}
									/>
								))}
							</>
						) : (
							options?.map((option, index) => (
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
							))
						)}
					</ClayDropDown.ItemList>
				</ClayAutocomplete.DropDown>
			</ClayAutocomplete>
		</FieldBase>
	);
}

export interface CustomItem {
	checked?: boolean;
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
	multipleChoice?: boolean;
	onChange?: (selected: T) => void;
	options: T[];
	placeholder?: string;
	required?: boolean;
	selectAllOption?: boolean;
	setOptions?: (options: T[]) => void;
	value?: string | number | string[];
}
