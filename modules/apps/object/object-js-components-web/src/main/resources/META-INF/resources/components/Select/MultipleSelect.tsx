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

import React, {useEffect, useState} from 'react';

import {BaseSelect, CustomItem, SelectProps} from './BaseSelect';
import {CheckboxItem} from './CheckBoxItem';

import './index.scss';

interface IProps<T extends CustomItem<number | string> = CustomItem>
	extends SelectProps {
	options: T[];
	selectAllOption?: boolean;
	setOptions: (options: T[]) => void;
	setSelectAllChecked?: Function;
}

export function MultipleSelect<
	T extends CustomItem<number | string> = CustomItem
>({options, selectAllOption, setOptions, ...restProps}: IProps<T>) {
	const [selectAllChecked, setSelectAllChecked] = useState<boolean>(false);
	const [dropdownActive, setDropdownActive] = useState<boolean>(false);

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
		<BaseSelect
			{...restProps}
			dropdownActive={dropdownActive}
			setDropdownActive={setDropdownActive}
			value={options
				.reduce<string[]>((acc, value) => {
					if (value.checked) {
						acc.push(value.label);
					}

					return acc;
				}, [])
				.join(', ')}
		>
			<>
				{selectAllOption && (
					<CheckboxItem
						checked={selectAllChecked}
						label={Liferay.Language.get('select-all')}
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

				{options.map(({checked, label, value}, index) => (
					<CheckboxItem
						checked={checked}
						key={index}
						label={label}
						onChange={({target: {checked}}) => {
							setOptions(
								options.map((option) =>
									option.label === label &&
									option.value === value
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
		</BaseSelect>
	);
}
