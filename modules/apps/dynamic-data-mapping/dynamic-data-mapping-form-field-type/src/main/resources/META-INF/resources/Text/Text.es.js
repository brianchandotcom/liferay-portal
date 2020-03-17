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

import './TextRegister.soy';

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import templates from './TextAdapter.soy';

/**
 * Use Sync Value to synchronize the initial value with the current internal
 * value, only update the internal value with the new initial value if the
 * values are different and when the value is not changed for more than ms.
 */
const useSyncValue = newValue => {
	// Maintains the reference of the last value to check in later renderings if the
	// value is new or keeps the same, it covers cases where the value typed by
	// the user is sent to LayoutProvider but it does not descend with the new changes.
	const previousValueRef = useRef(newValue);

	const [value, setValue] = useState(newValue);

	useEffect(() => {
		const handler = setTimeout(() => {
			if (value !== newValue && previousValueRef.current !== newValue) {
				previousValueRef.current = newValue;
				setValue(newValue);
			}
		}, 300);

		return () => {
			clearTimeout(handler);
		};
	}, [newValue, value]);

	return [value, setValue];
};

const Text = ({
	disabled,
	fieldName,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	value: initialValue,
}) => {
	const [value, setValue] = useSyncValue(initialValue);

	return (
		<ClayInput
			aria-label="text"
			className="ddm-field-text"
			disabled={disabled}
			id={id}
			name={name}
			onBlur={onBlur}
			onChange={event => {
				if (fieldName === 'name') {
					event.target.value = normalizeFieldName(event.target.value);
				}

				setValue(event.target.value);
				onChange(event);
			}}
			onFocus={onFocus}
			placeholder={placeholder}
			type="text"
			value={value}
		/>
	);
};

const Textarea = ({
	disabled,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	value: initialValue,
}) => {
	const [value, setValue] = useSyncValue(initialValue);

	return (
		<textarea
			className="ddm-field-text form-control"
			disabled={disabled}
			id={id}
			name={name}
			onBlur={onBlur}
			onChange={event => {
				setValue(event.target.value);
				onChange(event);
			}}
			onFocus={onFocus}
			placeholder={placeholder}
			type="text"
			value={value}
		/>
	);
};

const Autocomplete = ({
	disabled,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	options,
	placeholder,
	value: initialValue,
}) => {
	const [value, setValue] = useSyncValue(initialValue);
	const [visible, setVisible] = useState(false);
	const inputRef = useRef(null);
	const itemListRef = useRef(null);

	const filteredItems = options.filter(item => item && item.match(value));

	useEffect(() => {
		if (filteredItems.length === 1 && filteredItems.includes(value)) {
			setVisible(false);
		}
		else {
			setVisible(!!value);
		}
	}, [filteredItems, value]);

	const handleFocus = (event, direction) => {
		const target = event.target;
		const focusabledElements = event.currentTarget.querySelectorAll(
			'button'
		);
		const targetIndex = [...focusabledElements].findIndex(
			current => current === target
		);

		let nextElement;

		if (direction) {
			nextElement = focusabledElements[targetIndex - 1];
		}
		else {
			nextElement = focusabledElements[targetIndex + 1];
		}

		if (nextElement) {
			event.preventDefault();
			event.stopPropagation();
			nextElement.focus();
		}
		else if (targetIndex === 0 && direction) {
			event.preventDefault();
			event.stopPropagation();
			inputRef.current.focus();
		}
	};

	return (
		<ClayAutocomplete>
			<ClayAutocomplete.Input
				disabled={disabled}
				id={id}
				name={name}
				onBlur={onBlur}
				onChange={event => {
					setValue(event.target.value);
					onChange(event);
				}}
				onFocus={onFocus}
				onKeyDown={event => {
					if (
						(event.key === 'Tab' || event.key === 'ArrowDown') &&
						!event.shiftKey &&
						filteredItems.length > 0 &&
						visible
					) {
						event.preventDefault();
						event.stopPropagation();

						const firstElement = itemListRef.current.querySelector(
							'button'
						);
						firstElement.focus();
					}
				}}
				placeholder={placeholder}
				ref={inputRef}
				value={value}
			/>

			<ClayAutocomplete.DropDown
				active={visible && !disabled}
				onSetActive={setVisible}
			>
				<ul
					className="list-unstyled"
					onKeyDown={event => {
						switch (event.key) {
							case 'ArrowDown':
								handleFocus(event, false);
								break;
							case 'ArrowUp':
								handleFocus(event, true);
								break;
							case 'Tab':
								handleFocus(event, event.shiftKey);
								break;
							default:
								break;
						}
					}}
					ref={itemListRef}
				>
					{filteredItems.length === 0 && (
						<ClayDropDown.Item className="disabled">
							{Liferay.Language.get('no-results-were-found')}
						</ClayDropDown.Item>
					)}
					{filteredItems.map(label => (
						<ClayAutocomplete.Item
							key={label}
							match={value}
							onClick={() => {
								setValue(label);
								onChange({target: {value: label}});
							}}
							value={label}
						/>
					))}
				</ul>
			</ClayAutocomplete.DropDown>
		</ClayAutocomplete>
	);
};

const DISPLAY_STYLE = {
	autocomplete: Autocomplete,
	multiline: Textarea,
	singleline: Text,
};

const TextWithFieldBase = ({
	autocomplete,
	autocompleteEnabled,
	displayStyle = 'singleline',
	fieldName,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	options = [],
	placeholder,
	predefinedValue = '',
	readOnly,
	value,
	...otherProps
}) => {
	const optionsMemo = useMemo(() => options.map(option => option.label), [
		options,
	]);
	const Component =
		DISPLAY_STYLE[
			autocomplete || autocompleteEnabled ? 'autocomplete' : displayStyle
		];

	return (
		<FieldBaseProxy {...otherProps} id={id} name={name} readOnly={readOnly}>
			<Component
				disabled={readOnly}
				fieldName={fieldName}
				id={id}
				name={name}
				onBlur={onBlur}
				onChange={onChange}
				onFocus={onFocus}
				options={optionsMemo}
				placeholder={placeholder}
				value={value ? value : predefinedValue}
			/>
		</FieldBaseProxy>
	);
};

const TextProxy = connectStore(({emit, ...otherProps}) => (
	<TextWithFieldBase
		{...otherProps}
		onBlur={event => emit('fieldBlurred', event, event.target.value)}
		onChange={event => emit('fieldEdited', event, event.target.value)}
		onFocus={event => emit('fieldFocused', event, event.target.value)}
	/>
));

const ReactTextAdapter = getConnectedReactComponentAdapter(
	TextProxy,
	templates
);

export {ReactTextAdapter, useSyncValue, TextWithFieldBase};
export default ReactTextAdapter;
