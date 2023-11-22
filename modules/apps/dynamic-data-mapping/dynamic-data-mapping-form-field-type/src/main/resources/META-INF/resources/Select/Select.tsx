/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useFormState} from 'data-engine-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

// @ts-ignore

import {normalizeOptions, normalizeValue} from '../util/options';
import {getTooltipTitle} from '../util/tooltip';
import MultipleSelection from './MultipleSelect';
import {MainProps, SelectProps} from './select.d';
import {toArray} from './selectOperations';

import type {Locale} from '../types';

function Select({
	id,
	label,
	name,
	onChange,
	onSelectionChange,
	options,
	placeholder,
	predefinedValue,
	readOnly,
	required,
	selectedKey,
	showEmptyOption,
	viewMode,
}: SelectProps) {
	const [selectedLabel, setSelectedLabel] = useState('');
	let newSelectedKey = selectedKey;

	if (!selectedKey?.length && showEmptyOption) {
		newSelectedKey = 'chooseAnOption';
	}

	if (typeof selectedKey !== 'string' && selectedKey?.[0] === '') {
		newSelectedKey = undefined;
	}

	let selectedItem: string | string[] | undefined = newSelectedKey;

	if (newSelectedKey !== 'chooseAnOption') {
		selectedItem =
			newSelectedKey ??
			(predefinedValue?.length ? predefinedValue : undefined);
	}
	else if (
		newSelectedKey === 'chooseAnOption' &&
		predefinedValue?.[0] &&
		!viewMode
	) {
		selectedItem = predefinedValue?.[0];
	}
	else if (viewMode) {
		selectedItem = selectedItem ?? predefinedValue;
	}

	if (typeof selectedItem !== 'string') {
		selectedItem = selectedItem?.[0];
	}

	useEffect(() => {
		const selectedOption = options.find(
			(option) => option.value === selectedItem?.[0]
		);

		if (selectedOption) {
			setSelectedLabel(selectedOption.label);
		}
		else {
			setSelectedLabel(Liferay.Language.get('choose-an-option'));
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedKey, selectedItem]);

	return (
		<div
			data-tooltip-align="top"
			{...getTooltipTitle({
				placeholder: Liferay.Language.get('choose-an-option'),
				value: selectedLabel,
			})}
		>
			<Picker
				aria-labelledby={name}
				aria-required={required}
				data-testid={id}
				disabled={readOnly}
				items={[{items: options, label}]}
				onSelectionChange={(itemKey: React.Key) => {
					let newItemKey: React.Key | null = itemKey;

					if ((itemKey as string)?.includes('$.')) {
						newItemKey = '.';
					}

					const field = options.find(
						({value}) => value === newItemKey
					);

					if (field.value === 'chooseAnOption') {
						onChange({}, [null]);
					}

					onChange({}, [field.value]);

					if (onSelectionChange) {
						onSelectionChange(itemKey);
					}
				}}
				placeholder={placeholder}
				selectedKey={
					selectedItem === null ? 'chooseAnOption' : selectedItem
				}
			>
				{(group) => (
					<DropDown.Group header={group.label} items={group.items}>
						{(item) => (
							<Option disabled={item.disabled} key={item.value}>
								{item.label}
							</Option>
						)}
					</DropDown.Group>
				)}
			</Picker>
		</div>
	);
}

const Main = ({
	fixedOptions = [],
	label,
	localizedValue = {},
	localizedValueEdited,
	multiple = false,
	name,
	onChange,
	id,
	onSelectionChange,
	options = [],
	placeholder = Liferay.Language.get('choose-an-option'),
	predefinedValue = [],
	readOnly = false,
	showEmptyOption = true,
	value,
	selectedKey,
	...otherProps
}: MainProps) => {
	const {editingLanguageId}: {editingLanguageId: Locale} = useFormState();
	const predefinedValueArray = toArray(predefinedValue);
	const valueArray = toArray(value as string | string[]);
	const {viewMode} = useFormState();

	const normalizedOptions = useMemo(
		() =>
			normalizeOptions({
				editingLanguageId,
				fixedOptions,
				multiple,
				options,
				showEmptyOption,
				valueArray,
			}),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[fixedOptions, multiple, options, showEmptyOption, valueArray]
	);

	const multipleSelectValues = useMemo(
		() =>
			normalizeValue({
				localizedValueEdited,
				multiple,
				normalizedOptions,
				predefinedValueArray,
				valueArray,
			}) as string[],
		[
			localizedValueEdited,
			multiple,
			normalizedOptions,
			predefinedValueArray,
			valueArray,
		]
	);

	let newValue = value;
	let newPredefinedValue = predefinedValueArray;

	if (!multiple) {
		if (
			normalizedOptions.length &&
			value?.[0] &&
			!normalizedOptions.find((option) => option.value === value[0])
		) {
			newValue = undefined;
		}

		if (
			normalizedOptions.length &&
			predefinedValueArray[0] &&
			!normalizedOptions.find(
				(option) => option.value === predefinedValueArray[0]
			)
		) {
			newPredefinedValue = [];
		}
	}

	return (
		<FieldBase
			label={label}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
			{...otherProps}
		>
			<ClayTooltipProvider>
				{multiple ? (
					<MultipleSelection
						fixedOptions={[]}
						label={label}
						localizedValue={undefined}
						localizedValueEdited={undefined}
						name={`${name}_field`}
						onChange={onChange}
						options={normalizedOptions}
						predefinedValue={predefinedValueArray}
						readOnly={readOnly}
						required={otherProps.required}
						showEmptyOption={false}
						value={
							viewMode || !!multipleSelectValues.length
								? multipleSelectValues
								: predefinedValue
						}
						{...otherProps}
					/>
				) : (
					<Select
						fixedOptions={fixedOptions}
						id={id}
						label={label}
						localizedValue={undefined}
						localizedValueEdited={undefined}
						multiple={multiple}
						name={`${name}_field`}
						onChange={onChange}
						onSelectionChange={onSelectionChange}
						options={normalizedOptions}
						placeholder={placeholder}
						predefinedValue={newPredefinedValue}
						readOnly={readOnly}
						required={otherProps.required}
						selectedKey={selectedKey ?? (newValue as string)}
						showEmptyOption={showEmptyOption}
						viewMode={viewMode}
					/>
				)}
			</ClayTooltipProvider>

			<input
				name={name}
				type="hidden"
				value={
					multiple
						? newValue
						: newValue?.[0] === 'chooseAnOption'
						? undefined
						: newValue
				}
			/>
		</FieldBase>
	);
};

export default Main;
