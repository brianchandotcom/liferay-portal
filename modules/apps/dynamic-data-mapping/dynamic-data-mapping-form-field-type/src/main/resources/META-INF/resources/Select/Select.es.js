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

import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import React, {forwardRef, useMemo, useRef, useState} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import HiddenSelectInput from './HiddenSelectInput.es';
import VisibleSelectInput from './VisibleSelectInput.es';

/**
 * Mapping to be used to match keyCodes
 * returned from keydown events.
 */
const KEYCODES = {
	ARROW_DOWN: 40,
	ARROW_UP: 38,
	ENTER: 13,
	SHIFT: 16,
	SPACE: 32,
	TAB: 9,
};

/**
 * Appends a new value on the current value state
 * @param options {Object}
 * @param options.value {Array|String}
 * @param options.valueToBeAppended {Array|String}
 * @returns {Array}
 */
function appendValue({value, valueToBeAppended}) {
	const currentValue = toArray(value);
	const newValue = [...currentValue];

	if (value) {
		newValue.push(valueToBeAppended);
	}

	return newValue;
}

/**
 * Removes a value from the value array.
 * @param options {Object}
 * @param options.value {Array|String}
 * @param options.valueToBeRemoved {Array|String}
 * @returns {Array}
 */
function removeValue({value, valueToBeRemoved}) {
	const currentValue = toArray(value);

	return currentValue.filter((v) => v !== valueToBeRemoved);
}

/**
 * Wraps the given argument into an array.
 * @param value {Array|String}
 */
function toArray(value = '') {
	let newValue = value;

	if (!Array.isArray(newValue)) {
		newValue = [newValue];
	}

	return newValue;
}

function normalizeValue({
	multiple,
	normalizedOptions,
	predefinedValueArray,
	valueArray,
}) {
	const assertValue = valueArray.length ? valueArray : predefinedValueArray;

	const valueWithoutMultiple = assertValue.filter((_, index) => {
		return multiple ? true : index === 0;
	});

	return valueWithoutMultiple.filter((value) =>
		normalizedOptions.some((option) => value === option.value)
	);
}

/**
 * Some parameters on each option
 * needs to be prepared in case of
 * multiple selected values(when the value state is an array).
 */
function assertOptionParameters({multiple, option, valueArray}) {
	const included = valueArray.includes(option.value);

	return {
		...option,
		active: !multiple && included,
		checked: multiple && included,
		type: multiple ? 'checkbox' : 'item',
	};
}

function normalizeOptions({fixedOptions, multiple, options, valueArray}) {
	const emptyOption = {
		label: Liferay.Language.get('choose-an-option'),
		value: '',
	};

	const newOptions = [
		...options.map((option, index) => ({
			...assertOptionParameters({multiple, option, valueArray}),
			separator:
				Array.isArray(fixedOptions) &&
				fixedOptions.length > 0 &&
				index === options.length - 1,
		})),
		...fixedOptions.map((option) =>
			assertOptionParameters({multiple, option, valueArray})
		),
	].filter(({value}) => value !== '');

	if (!multiple) {
		return [emptyOption, ...newOptions];
	}

	return newOptions;
}

function handleDropdownItemClick({currentValue, multiple, option}) {
	const itemValue = option.value;

	let newValue;

	if (multiple) {
		if (currentValue.includes(itemValue)) {
			newValue = removeValue({
				value: currentValue,
				valueToBeRemoved: itemValue,
			});
		}
		else {
			newValue = appendValue({
				value: currentValue,
				valueToBeAppended: itemValue,
			});
		}
	}
	else {
		newValue = [itemValue];
	}

	return newValue;
}

const DropdownItem = ({
	currentValue,
	expand,
	index,
	multiple,
	onSelect,
	option,
	options,
}) => (
	<>
		<ClayDropDown.Item
			active={expand && currentValue === option.label}
			data-testid={`dropdownItem-${index}`}
			label={option.label}
			onClick={(event) => {
				event.preventDefault();
				event.stopPropagation();

				onSelect({
					currentValue,
					event,
					multiple,
					option,
				});
			}}
			value={options.value}
		>
			{multiple ? (
				<ClayCheckbox
					aria-label={option.label}
					checked={currentValue.includes(option.value)}
					data-testid={`labelItem-${option.value}`}
					label={option.label}
					onChange={(event) => {
						onSelect({
							currentValue,
							event,
							multiple,
							option,
						});
					}}
				/>
			) : (
				option.label
			)}
		</ClayDropDown.Item>

		{option && option.separator && <ClayDropDown.Divider />}
	</>
);

const Trigger = forwardRef(
	(
		{
			onCloseButtonClicked,
			onTriggerClicked,
			onTriggerKeyDown,
			readOnly,
			value,
			...otherProps
		},
		ref
	) => {
		return (
			<>
				{!readOnly && (
					<HiddenSelectInput value={value} {...otherProps} />
				)}
				<VisibleSelectInput
					onClick={onTriggerClicked}
					onCloseButtonClicked={onCloseButtonClicked}
					onKeyDown={onTriggerKeyDown}
					readOnly={readOnly}
					ref={ref}
					value={value}
					{...otherProps}
				/>
			</>
		);
	}
);

const Select = ({
	multiple,
	onCloseButtonClicked,
	onDropdownItemClicked,
	onExpand,
	options,
	predefinedValue,
	readOnly,
	value,
	...otherProps
}) => {
	const menuElementRef = useRef(null);
	const triggerElementRef = useRef(null);

	const [currentValue, setCurrentValue] = useSyncValue(value, false);
	const [expand, setExpand] = useState(false);

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
			menuElementRef.current.focus();
		}
	};

	const handleSelect = ({currentValue, event, multiple, option}) => {
		const newValue = handleDropdownItemClick({
			currentValue,
			multiple,
			option,
		});

		setCurrentValue(newValue);

		onDropdownItemClicked({event, value: newValue});

		if (!multiple) {
			setExpand(false);

			onExpand({event, expand: false});
		}
	};

	return (
		<>
			<Trigger
				multiple={multiple}
				onCloseButtonClicked={({event, value}) => {
					const newValue = removeValue({
						value: currentValue,
						valueToBeRemoved: value,
					});

					setCurrentValue(newValue);

					onCloseButtonClicked({event, value: newValue});
				}}
				onTriggerClicked={(event) => {
					if (readOnly) {
						return;
					}

					setExpand(!expand);
					onExpand({event, expand: !expand});
				}}
				onTriggerKeyDown={(event) => {
					if (
						(event.keyCode === KEYCODES.TAB ||
							event.keyCode === KEYCODES.ARROW_DOWN) &&
						!event.shiftKey &&
						expand
					) {
						event.preventDefault();
						event.stopPropagation();

						const firstElement = menuElementRef.current.querySelector(
							'button'
						);

						firstElement.focus();
					}

					if (
						event.keyCode === KEYCODES.ENTER ||
						(event.keyCode === KEYCODES.SPACE && !event.shiftKey)
					) {
						event.preventDefault();
						event.stopPropagation();

						setExpand(!expand);

						onExpand({event, expand: !expand});
					}
				}}
				options={options}
				predefinedValue={predefinedValue}
				readOnly={readOnly}
				ref={triggerElementRef}
				value={currentValue}
				{...otherProps}
			/>
			<ClayDropDown.Menu
				active={expand}
				alignElementRef={triggerElementRef}
				className="ddm-btn-full ddm-select-dropdown"
				onKeyDown={(event) => {
					switch (event.keyCode) {
						case KEYCODES.ARROW_DOWN:
							handleFocus(event, false);
							break;
						case KEYCODES.ARROW_UP:
							handleFocus(event, true);
							break;
						case KEYCODES.TAB:
							handleFocus(event, event.shiftKey);
							break;
						default:
							break;
					}
				}}
				onSetActive={setExpand}
				ref={menuElementRef}
			>
				<ClayDropDown.ItemList>
					{options.map((option, index) => (
						<DropdownItem
							currentValue={currentValue}
							expand={expand}
							index={index}
							key={`${option.value}-${index}`}
							multiple={multiple}
							onSelect={handleSelect}
							option={option}
							options={options}
						/>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown.Menu>
		</>
	);
};

const Main = ({
	fixedOptions = [],
	label,
	localizedValue = {},
	multiple,
	name,
	onChange,
	onExpand = () => {},
	options = [],
	predefinedValue = [],
	readOnly = false,
	value = [],
	...otherProps
}) => {
	const predefinedValueArray = toArray(predefinedValue);
	const valueArray = toArray(value);

	const normalizedOptions = useMemo(
		() =>
			normalizeOptions({
				fixedOptions,
				multiple,
				options,
				valueArray,
			}),
		[fixedOptions, multiple, options, valueArray]
	);

	value = useMemo(
		() =>
			normalizeValue({
				multiple,
				normalizedOptions,
				predefinedValueArray,
				valueArray,
			}),
		[multiple, normalizedOptions, predefinedValueArray, valueArray]
	);

	return (
		<FieldBaseProxy
			label={label}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
			{...otherProps}
		>
			<Select
				multiple={multiple}
				name={name}
				onCloseButtonClicked={onChange}
				onDropdownItemClicked={onChange}
				onExpand={onExpand}
				options={normalizedOptions}
				predefinedValue={predefinedValueArray}
				readOnly={readOnly}
				value={value}
				{...otherProps}
			/>
		</FieldBaseProxy>
	);
};

Main.displayName = 'Select';

const SelectProxy = connectStore(({emit, ...otherProps}) => (
	<Main
		{...otherProps}
		onChange={({event, value}) => emit('fieldEdited', event, value)}
		onExpand={({event, expand}) => {
			if (expand) {
				emit('fieldFocused', event, event.target.value);
			}
			else {
				emit('fieldBlurred', event, event.target.value);
			}
		}}
	/>
));

const ReactSelectAdapter = getConnectedReactComponentAdapter(
	SelectProxy,
	'select'
);

export {ReactSelectAdapter, Main};
export default ReactSelectAdapter;
