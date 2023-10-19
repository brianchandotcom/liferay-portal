/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {FieldBase} from 'frontend-js-components-web';
import React, {FocusEvent, useEffect, useMemo, useState} from 'react';

import {stringIncludesQuery} from '../../utils/string';

interface MultipleSelectProps {
	className?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	onBlur?: (event: FocusEvent<HTMLInputElement>) => void;
	options: MultiSelectItem[];
	placeholder?: string;
	required?: boolean;
	selectAllOption?: boolean;
	setOptions: (options: MultiSelectItem[]) => void;
}

export interface MultiSelectItem extends LabelValueObject {
	checked?: boolean;
}

export function MultipleSelect({
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	onBlur,
	options,
	placeholder,
	required,
	selectAllOption,
	setOptions,
}: MultipleSelectProps) {
	const [dropdownActive, setDropdownActive] = useState<boolean>(false);
	const [multiSelectItems, setMultiSelectItems] = useState<
		LabelValueObject[]
	>([]);
	const [query, setQuery] = useState('');
	const [selectAllChecked, setSelectAllChecked] = useState<boolean>(false);

	const filteredOptions = useMemo(() => {
		return options.filter((option) =>
			stringIncludesQuery(option.label as string, query)
		);
	}, [query, options]);

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
		<FieldBase
			className={className}
			disabled={disabled}
			errorMessage={error}
			helpMessage={feedbackMessage}
			id={id}
			label={label}
			required={required}
		>
			<ClayAutocomplete onBlur={onBlur}>
				<ClayMultiSelect<MultiSelectItem>
					items={multiSelectItems as MultiSelectItem[]}
					loadingState={4}
					onChange={setQuery}
					onFocus={() => setDropdownActive((active) => !active)}
					onItemsChange={(items: MultiSelectItem[]) => {
						if (!items.length && setSelectAllChecked) {
							setSelectAllChecked(false);
						}
						const newDropDownOptions = options?.map((option) => {
							const checkedItem = items.find(
								(item) => item.label === option.label
							);

							if (checkedItem) {
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
					placeholder={placeholder}
				/>

				<ClayAutocomplete.DropDown
					active={dropdownActive}
					alignmentByViewport
					closeOnClickOutside
					onActiveChange={setDropdownActive}
				>
					<ClayDropDown.ItemList>
						{selectAllOption && (
							<div className="dropdown-item">
								<ClayCheckbox
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
							</div>
						)}

						{filteredOptions.map(({checked, label, value}) => (
							<div className="dropdown-item" key={value}>
								<ClayCheckbox
									checked={checked as boolean}
									label={label as string}
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
							</div>
						))}
					</ClayDropDown.ItemList>
				</ClayAutocomplete.DropDown>
			</ClayAutocomplete>
		</FieldBase>
	);
}
