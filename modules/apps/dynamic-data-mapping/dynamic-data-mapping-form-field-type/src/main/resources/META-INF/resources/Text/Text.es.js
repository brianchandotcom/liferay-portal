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
import {ClayInput} from '@clayui/form';
import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';

const Text = ({
	disabled,
	fieldName,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	syncDelay,
	value: initialValue,
}) => {
	const [value, setValue] = useSyncValue(initialValue, syncDelay);

	return (
		<ClayInput
			aria-label="text"
			className="ddm-field-text"
			disabled={disabled}
			id={id}
			name={name}
			onBlur={onBlur}
			onChange={(event) => {
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
	syncDelay,
	value: initialValue,
}) => {
	const [value, setValue] = useSyncValue(initialValue, syncDelay);

	return (
		<textarea
			className="ddm-field-text form-control"
			disabled={disabled}
			id={id}
			name={name}
			onBlur={onBlur}
			onChange={(event) => {
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
	syncDelay,
	value: initialValue,
}) => {
	const [value, setValue] = useSyncValue(initialValue, syncDelay);
	const [visible, setVisible] = useState(false);
	const inputRef = useRef(null);
	const itemListRef = useRef(null);

	const filteredItems = options.filter((item) => item && item.match(value));

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
			(current) => current === target
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
				onChange={(event) => {
					setValue(event.target.value);
					onChange(event);
				}}
				onFocus={onFocus}
				onKeyDown={(event) => {
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
					onKeyDown={(event) => {
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
					{filteredItems.map((label) => (
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

const Main = ({
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
	syncDelay = true,
	value,
	...otherProps
}) => {
	const optionsMemo = useMemo(() => options.map((option) => option.label), [
		options,
	]);
	const Component =
		DISPLAY_STYLE[
			autocomplete || autocompleteEnabled
				? 'autocomplete'
				: displayStyle
				? displayStyle
				: `singleline`
		];

	return (
		<FieldBase {...otherProps} id={id} name={name} readOnly={readOnly}>
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
				syncDelay={syncDelay}
				value={value ? value : predefinedValue}
			/>
		</FieldBase>
	);
};

Main.displayName = 'Text';

export default Main;
