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
import {usePrevious} from '@liferay/frontend-js-react-web';
import {normalizeFieldName} from 'data-engine-js-components-web';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';
import withConfirmationField from '../util/withConfirmationField.es';

const CounterContainer = ({
	counter,
	displayErrors,
	error,
	maxLength,
	setError,
	showCounter,
	valid,
}) => {
	if (
		!showCounter ||
		(displayErrors === true && !valid && Object.keys(error).length === 0)
	) {
		return null;
	}

	const message = Liferay.Util.sub(
		Liferay.Language.get('x-of-x-characters'),
		counter,
		maxLength
	);

	if (counter > maxLength) {
		if (error.errorMessage !== message) {
			setError({
				displayErrors: true,
				errorMessage: message,
				valid: false,
			});
		}

		return null;
	}

	if (error.displayErrors) {
		setError({});
	}

	return (
		<span aria-hidden="true" className="form-text">
			{message}
		</span>
	);
};

const Text = ({
	defaultLanguageId,
	disabled,
	displayErrors,
	editingLanguageId,
	error,
	fieldName,
	id,
	invalidCharacters,
	localizable,
	localizedValue,
	maxLength,
	name,
	normalizeField,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	setError,
	shouldUpdateValue,
	showCounter,
	syncDelay,
	valid,
	value: initialValue,
}) => {
	const [value, setValue] = useSyncValue(
		initialValue,
		syncDelay,
		editingLanguageId
	);

	const inputRef = useRef(null);

	const prevEditingLanguageId = usePrevious(editingLanguageId);

	useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId && localizable) {
			const newValue =
				localizedValue[editingLanguageId] !== undefined
					? localizedValue[editingLanguageId]
					: localizedValue[defaultLanguageId];
			setValue(newValue);
		}
	}, [
		defaultLanguageId,
		editingLanguageId,
		localizable,
		localizedValue,
		prevEditingLanguageId,
		setValue,
	]);

	useEffect(() => {
		if (
			normalizeField &&
			inputRef.current &&
			inputRef.current.value !== initialValue &&
			(inputRef.current.value === '' || shouldUpdateValue)
		) {
			setValue(initialValue);
			onChange({target: {value: initialValue}});
		}
	}, [
		initialValue,
		inputRef,
		fieldName,
		normalizeField,
		onChange,
		setValue,
		shouldUpdateValue,
	]);

	return (
		<>
			<ClayInput
				className="ddm-field-text"
				dir={Liferay.Language.direction[editingLanguageId]}
				disabled={disabled}
				id={id}
				lang={editingLanguageId}
				maxLength={showCounter ? '' : maxLength}
				name={name}
				onBlur={(event) => {
					if (normalizeField) {
						onBlur({target: {value: initialValue}});
					}
					else {
						onBlur(event);
					}
				}}
				onChange={(event) => {
					const {value} = event.target;

					if (normalizeField) {
						event.target.value = normalizeFieldName(value);
					}
					else if (invalidCharacters) {
						const regex = new RegExp(invalidCharacters, 'g');

						event.target.value = value.replace(regex, '');
					}
					setValue(event.target.value);
					onChange(event);
				}}
				onFocus={onFocus}
				placeholder={placeholder}
				ref={inputRef}
				type="text"
				value={value}
			/>

			<CounterContainer
				counter={value?.length}
				displayErrors={displayErrors}
				error={error}
				maxLength={maxLength}
				setError={setError}
				showCounter={showCounter}
				valid={valid}
			/>
		</>
	);
};

const Textarea = ({
	disabled,
	displayErrors,
	editingLanguageId,
	error,
	id,
	maxLength,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	setError,
	showCounter,
	syncDelay,
	valid,
	value: initialValue,
}) => {
	const [value, setValue] = useSyncValue(initialValue, syncDelay);

	return (
		<>
			<textarea
				className="ddm-field-text form-control"
				dir={Liferay.Language.direction[editingLanguageId]}
				disabled={disabled}
				id={id}
				lang={editingLanguageId}
				name={name}
				onBlur={onBlur}
				onChange={(event) => {
					setValue(event.target.value);
					onChange(event);
				}}
				onFocus={onFocus}
				placeholder={placeholder}
				style={disabled ? {resize: 'none'} : null}
				type="text"
				value={value}
			/>

			<CounterContainer
				counter={value?.length}
				displayErrors={displayErrors}
				error={error}
				maxLength={maxLength}
				setError={setError}
				showCounter={showCounter}
				valid={valid}
			/>
		</>
	);
};

const Autocomplete = ({
	disabled,
	editingLanguageId,
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
	const [selectedItem, setSelectedItem] = useState(false);
	const [value, setValue] = useSyncValue(initialValue, syncDelay);
	const [visible, setVisible] = useState(false);
	const inputRef = useRef(null);
	const itemListRef = useRef(null);

	const escapeChars = (string) =>
		string.replace(/[.*+\-?^${}()|[\]\\]/g, '\\$&');

	const filteredItems = options.filter(
		(item) => item && item.match(escapeChars(value))
	);

	const isValidItem = useCallback(() => {
		return (
			!selectedItem &&
			filteredItems.length > 1 &&
			!filteredItems.includes(value)
		);
	}, [filteredItems, selectedItem, value]);

	useEffect(() => {
		const ddmPageContainerLayout = inputRef.current.closest(
			'.ddm-page-container-layout'
		);

		if (
			!isValidItem() &&
			ddmPageContainerLayout &&
			ddmPageContainerLayout.classList.contains('hide')
		) {
			setVisible(false);
		}
	}, [filteredItems, isValidItem, value, selectedItem]);

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
				dir={Liferay.Language.direction[editingLanguageId]}
				disabled={disabled}
				id={id}
				lang={editingLanguageId}
				name={name}
				onBlur={onBlur}
				onChange={(event) => {
					setValue(event.target.value);
					setVisible(!!event.target.value);
					setSelectedItem(false);
					onChange(event);
				}}
				onFocus={(event) => {
					if (isValidItem() && event.target.value) {
						setVisible(true);
					}

					onFocus(event);
				}}
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

					{filteredItems.map((label, index) => (
						<ClayAutocomplete.Item
							key={index}
							match={value}
							onClick={() => {
								setValue(label);
								setVisible(false);
								setSelectedItem(true);
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
	defaultLanguageId,
	displayErrors,
	displayStyle = 'singleline',
	showCounter,
	fieldName,
	id,
	invalidCharacters = '',
	locale,
	localizable,
	localizedValue = {},
	maxLength,
	name,
	normalizeField = false,
	onBlur,
	onChange,
	onFocus,
	options = [],
	placeholder,
	predefinedValue = '',
	readOnly,
	shouldUpdateValue = false,
	syncDelay = true,
	valid,
	value,
	...otherProps
}) => {
	const optionsMemo = useMemo(() => options.map((option) => option.label), [
		options,
	]);

	const [error, setError] = useState({});

	const Component =
		DISPLAY_STYLE[
			autocomplete || autocompleteEnabled
				? 'autocomplete'
				: displayStyle ?? `singleline`
		];

	return (
		<FieldBase
			{...otherProps}
			{...error}
			displayErrors={error.displayErrors ?? displayErrors}
			fieldName={fieldName}
			id={id}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
			valid={error.valid ?? valid}
		>
			<Component
				defaultLanguageId={defaultLanguageId}
				disabled={readOnly}
				displayErrors={displayErrors}
				editingLanguageId={locale}
				error={error}
				fieldName={fieldName}
				id={id}
				invalidCharacters={invalidCharacters}
				localizable={localizable}
				localizedValue={localizedValue}
				maxLength={maxLength}
				name={name}
				normalizeField={normalizeField}
				onBlur={onBlur}
				onChange={onChange}
				onFocus={onFocus}
				options={optionsMemo}
				placeholder={placeholder}
				setError={setError}
				shouldUpdateValue={shouldUpdateValue}
				showCounter={showCounter}
				syncDelay={syncDelay}
				valid={valid}
				value={value ? value : predefinedValue}
			/>
		</FieldBase>
	);
};

Main.displayName = 'Text';

export default withConfirmationField(Main);
