/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiSelect from '@clayui/multi-select';
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

type LabelValueObject = {
	label?: string;
	value?: string;
};

interface MultiSelectItem extends LabelValueObject {
	checked?: boolean;
}

export function MultipleSelect<
	T extends CustomItem<number | string> = CustomItem
>({options, selectAllOption, setOptions, ...restProps}: IProps<T>) {
	const [selectAllChecked, setSelectAllChecked] = useState<boolean>(false);
	const [dropdownActive, setDropdownActive] = useState<boolean>(false);
	const [multiSelectItems, setMultiSelectItems] = useState<
		LabelValueObject[]
	>([]);

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

	useEffect(() => {
		const multiSelectOptions = options.filter(({checked, label}) => {
			if (checked) {
				return {
					label,
				};
			}
		});

		if (multiSelectOptions) {
			setMultiSelectItems(multiSelectOptions as LabelValueObject[]);
		}
	}, [options]);

	return (
		<BaseSelect
			{...restProps}
			dropdownActive={dropdownActive}
			setDropdownActive={setDropdownActive}
			trigger={
				<ClayMultiSelect
					items={multiSelectItems as MultiSelectItem[]}
					loadingState={4}
					onClick={() => setDropdownActive((active) => !active)}
					onInput={() => {}}
					onItemsChange={(items: MultiSelectItem[]) => {
						if (!items.length && setSelectAllChecked) {
							setSelectAllChecked(false);
						}
						const newDropDownOptions = options?.map((option) => {
							const ckeckedItem = items.find(
								(item) => item.label === option.label
							);

							if (ckeckedItem) {
								return {
									...option,
									checked: true,
								};
							}
							else {
								return {
									...option,
									checked: false,
								};
							}
						});

						if (newDropDownOptions) {
							setOptions(newDropDownOptions);
						}
					}}
					onKeyDown={(event) => event.preventDefault()}
					{...restProps}
				/>
			}
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

				{options.map(({checked, label, value}) => (
					<CheckboxItem
						checked={checked}
						key={value}
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
