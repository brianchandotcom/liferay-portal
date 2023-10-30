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
import {MainProps, SelectProps} from './select';
import {toArray} from './selectOperations';

import type {Locale} from '../types';

function Select({
	label,
	name,
	onChange,
	options,
	predefinedValue,
	readOnly,
	required,
	selectedKey,
}: SelectProps) {
	const [selectedLabel, setSelectedLabel] = useState('');
	let newSelectedKey = selectedKey;
	if (selectedKey === null) {
		newSelectedKey = 'null';
	}

	useEffect(() => {
		const selectedOption = options.find(
			(option) => option.value === selectedKey
		);

		if (selectedOption) {
			return setSelectedLabel(selectedOption.label);
		}

		setSelectedLabel('');
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedKey]);

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
				disabled={readOnly}
				id="picker"
				items={[{items: options, label}]}
				onSelectionChange={(itemKey: React.Key) => {
					let newItemKey: React.Key | null = itemKey;

					if ((itemKey as string)?.includes('$.')) {
						newItemKey = '.';
					}
					else if (itemKey === 'null') {
						newItemKey = null;
					}

					const field = options.find(
						({value}) => value === newItemKey
					);

					onChange({}, [field.value]);
				}}
				placeholder={Liferay.Language.get('choose-an-option')}
				selectedKey={newSelectedKey || predefinedValue?.[0]}
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
	options = [],
	predefinedValue = [],
	readOnly = false,
	showEmptyOption = true,
	value = '',
	selectedKey,
	...otherProps
}: MainProps) => {
	const {editingLanguageId}: {editingLanguageId: Locale} = useFormState();
	const predefinedValueArray = toArray(predefinedValue);
	const valueArray = toArray(value);
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
						label={label}
						localizedValue={undefined}
						localizedValueEdited={undefined}
						multiple={multiple}
						name={`${name}_field`}
						onChange={onChange}
						options={normalizedOptions}
						predefinedValue={predefinedValueArray}
						readOnly={readOnly}
						required={otherProps.required}
						selectedKey={selectedKey || value[0]}
						showEmptyOption={false}
					/>
				)}
			</ClayTooltipProvider>

			<input name={name} type="hidden" value={value} />
		</FieldBase>
	);
};

export default Main;
