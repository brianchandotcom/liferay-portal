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

import './CheckboxMultipleRegister.soy';

import {ClayCheckbox} from '@clayui/form';
import classNames from 'classnames';
import React, {useState} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import {setJSONArrayValue} from '../util/setters.es';
import templates from './CheckboxMultipleAdapter.soy';

const Switcher = ({
	checked,
	disabled,
	inline,
	label,
	name,
	onBlur,
	onChange,
	onFocus,
	value,
}) => (
	<div
		className={classNames('lfr-ddm-form-field-checkbox-switch', {
			'lfr-ddm-form-field-checkbox-switch-inline': inline,
		})}
	>
		<label className="simple-toggle-switch toggle-switch">
			<input
				checked={checked}
				className="toggle-switch-check"
				disabled={disabled}
				name={name}
				onBlur={onBlur}
				onChange={onChange}
				onFocus={onFocus}
				type="checkbox"
				value={value}
			/>
			<span aria-hidden="true" className="toggle-switch-bar">
				<span className="toggle-switch-handle"></span>
			</span>
			<span className="toggle-switch-label">{label}</span>
		</label>
	</div>
);

const CheckboxMultiple = ({
	disabled,
	inline,
	isSwitcher,
	name,
	onBlur,
	onChange,
	onFocus,
	options,
	predefinedValue,
	value: initialValue,
}) => {
	const [value, setValue] = useState(initialValue);

	const displayValues = value && value.length > 0 ? value : predefinedValue;
	const Toggle = isSwitcher ? Switcher : ClayCheckbox;

	const handleChange = event => {
		const {target} = event;
		const newValue = value.filter(
			currentValue => currentValue !== target.value
		);

		if (target.checked) {
			newValue.push(target.value);
		}

		setValue(newValue);
		onChange(event, newValue);
	};

	return (
		<div className="lfr-ddm-checkbox-multiple">
			{options.map(option => (
				<Toggle
					checked={displayValues.includes(option.value)}
					disabled={disabled}
					inline={inline}
					key={option.value}
					label={option.label}
					name={name}
					onBlur={onBlur}
					onChange={handleChange}
					onFocus={onFocus}
					value={option.value}
				/>
			))}
		</div>
	);
};

const CheckboxMultipleProxy = connectStore(
	({
		dispatch,
		emit,
		inline,
		name,
		options = [
			{
				label: 'Option 1',
			},
			{
				label: 'Option 2',
			},
		],
		predefinedValue,
		readOnly,
		showAsSwitcher = true,
		value,
		...otherProps
	}) => (
		<FieldBaseProxy
			dispatch={dispatch}
			name={name}
			readOnly={readOnly}
			{...otherProps}
		>
			<CheckboxMultiple
				disabled={readOnly}
				inline={inline}
				isSwitcher={showAsSwitcher}
				name={name}
				onBlur={event =>
					emit('fieldBlurred', event, event.target.value)
				}
				onChange={(event, value) => emit('fieldEdited', event, value)}
				onFocus={event => emit('fieldFocused', event)}
				options={options}
				predefinedValue={setJSONArrayValue(predefinedValue)}
				value={setJSONArrayValue(value)}
			/>
		</FieldBaseProxy>
	)
);

const ReactCheckboxMultipleAdapter = getConnectedReactComponentAdapter(
	CheckboxMultipleProxy,
	templates
);

export {ReactCheckboxMultipleAdapter};
export default ReactCheckboxMultipleAdapter;
