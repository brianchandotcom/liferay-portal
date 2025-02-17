/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useFormState} from 'data-engine-js-components-web';
import React, {useMemo} from 'react';

import FieldBase from '../FieldBase/ReactFieldBase.es';
import {normalizeOptions, normalizeValue} from '../util/options';
import MultipleSelection, {MultipleSelectionProps} from './MultipleSelect';
import SingleSelectBase from './SingleSelectBase';
import {SelectMainProps} from './select.d';
import {toArray} from './selectOperations';

import type {Locale} from '../types';

const Main = ({
	defaultLanguageId,
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
}: SelectMainProps) => {
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

	let newValue: string | string[] | undefined = valueArray;
	let newPredefinedValue = predefinedValueArray;

	if (!multiple) {
		if (
			normalizedOptions.length &&
			newValue?.[0] &&
			!normalizedOptions.find((option) => option.value === newValue?.[0])
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

	const MultipleSelectionComponent =
		MultipleSelection as React.FC<MultipleSelectionProps>;

	return (
		<FieldBase
			label={label}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
			{...otherProps}
		>
			{multiple ? (
				<MultipleSelectionComponent
					defaultLanguageId={defaultLanguageId}
					fixedOptions={[]}
					label={label}
					name={name}
					onChange={onChange}
					options={normalizedOptions}
					predefinedValue={predefinedValueArray}
					readOnly={readOnly}
					value={
						viewMode || !!multipleSelectValues.length
							? multipleSelectValues
							: (predefinedValue as string[])
					}
					{...otherProps}
				/>
			) : (
				<SingleSelectBase
					defaultLanguageId={defaultLanguageId}
					fixedOptions={fixedOptions}
					id={id}
					label={label}
					multiple={multiple}
					name={name}
					onChange={onChange}
					onSelectionChange={onSelectionChange}
					options={normalizedOptions}
					placeholder={placeholder}
					predefinedValue={newPredefinedValue}
					readOnly={readOnly}
					selectedKey={selectedKey ?? newValue}
					showEmptyOption={showEmptyOption}
					viewMode={viewMode}
					{...otherProps}
				/>
			)}

			<input
				name={name}
				type="hidden"
				value={
					multiple
						? JSON.stringify(newValue)
						: newValue?.[0] === 'chooseAnOption'
							? undefined
							: JSON.stringify(newValue)
				}
			/>
		</FieldBase>
	);
};

export default Main;
