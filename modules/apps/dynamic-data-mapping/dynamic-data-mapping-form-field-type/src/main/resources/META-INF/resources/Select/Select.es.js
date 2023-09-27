/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useFormState} from 'data-engine-js-components-web';
import React, {forwardRef, useEffect, useMemo, useRef, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';
import {normalizeOptions, normalizeValue} from '../util/options';
import {getTooltipTitle} from '../util/tooltip';
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
 * Maximum number of items to be shown without the Search bar
 */
const MAX_ITEMS = 11;

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

	if (newValue && typeof newValue === 'string') {
		try {
			newValue = JSON.parse(newValue);
		}
		catch (error) {}
	}

	if (!Array.isArray(newValue)) {
		newValue = [newValue];
	}

	return newValue;
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
	else if (itemValue === null) {
		newValue = [];
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
}) => (
	<>
		<ClayDropDown.Item
			active={expand && currentValue === option.label}
			data-testid={`dropdownItem-${index}`}
			disabled={option.disabled}
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
			value={option.reference}
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

const DropdownList = ({
	currentValue,
	expand,
	handleSelect,
	label,
	multiple,
	options,
}) => (
	<ClayDropDown.ItemList>
		<ClayDropDown.Group header={label}>
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
		</ClayDropDown.Group>
	</ClayDropDown.ItemList>
);

const DropdownListWithSearch = ({
	currentValue,
	expand,
	handleSelect,
	multiple,
	options,
	showEmptyOption,
}) => {
	const [query, setQuery] = useState('');
	const [filteredOptions, setFilteredOptions] = useState([]);

	useEffect(() => {
		let result = options.filter(
			(option) =>
				option.value &&
				option.label.toLowerCase().includes(query.toLowerCase())
		);

		if (showEmptyOption && !multiple) {
			const emptyOption = {
				label: Liferay.Language.get('choose-an-option'),
				value: null,
			};

			result = [emptyOption, ...result];
		}

		setFilteredOptions(result);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [options, query]);

	return (
		<>
			<ClayDropDown.Search onChange={setQuery} value={query} />
			{filteredOptions.length ? (
				<DropdownList
					currentValue={currentValue}
					expand={expand}
					handleSelect={handleSelect}
					multiple={multiple}
					options={filteredOptions}
				/>
			) : (
				<div className="dropdown-section text-muted">
					{Liferay.Language.get('empty-list')}
				</div>
			)}
		</>
	);
};

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
	defaultSearch,
	label,
	multiple,
	onChange,
	onCloseButtonClicked,
	onDropdownItemClicked,
	onExpand,
	options,
	predefinedValue,
	readOnly,
	showEmptyOption,
	value,
	...otherProps
}) => {
	const {viewMode} = useFormState();
	const menuElementRef = useRef(null);
	const triggerElementRef = useRef(null);
	const [currentValue, setCurrentValue] = useSyncValue(value, false);
	const [expand, setExpand] = useState(false);
	const [selectedLabel, setSelectedLabel] = useState('');
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

			triggerElementRef.current.firstChild.focus();
		}
	};

	const inputTrigger =
		document.querySelector(
			'[data-field-name|="selectedObjectField"] > .lfr__ddm-select-input-trigger'
		) ??
		document.querySelector(
			`[data-field-reference|='${otherProps.fieldReference}'] .lfr__ddm-select-input-trigger`
		);

	let leftRect;

	if (inputTrigger) {
		const rect = inputTrigger.getBoundingClientRect();
		leftRect = rect.left;
	}

	useEffect(() => {
		const [selectedValue] = currentValue;
		const selectedOption = options.find(
			(option) => option.value === selectedValue
		);

		if (selectedOption) {
			return setSelectedLabel(selectedOption.label);
		}

		setSelectedLabel('');
	}, [currentValue, options, value]);

	useEffect(() => {
		if (viewMode && currentValue.length !== 0) {
			onChange({target: {value: currentValue}});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayTooltipProvider>
			<div
				data-tooltip-align="top"
				{...getTooltipTitle({
					placeholder: Liferay.Language.get('choose-an-option'),
					value: selectedLabel,
				})}
			>
				<Trigger
					multiple={multiple}
					onChange={onChange}
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

						if (expand) {
							triggerElementRef.current.firstChild.focus();
						}
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
							(event.keyCode === KEYCODES.SPACE &&
								!event.shiftKey)
						) {
							event.preventDefault();
							event.stopPropagation();

							setExpand(!expand);

							onExpand({event, expand: !expand});

							if (expand) {
								triggerElementRef.current.firstChild.focus();
							}
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
					alignmentPosition={5}
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
					style={{
						left: leftRect,
						maxWidth: inputTrigger
							? inputTrigger.offsetWidth
							: '500px',
						width: '100%',
					}}
				>
					{options.length > MAX_ITEMS || defaultSearch ? (
						<DropdownListWithSearch
							currentValue={currentValue}
							expand={expand}
							handleSelect={handleSelect}
							label={label}
							multiple={multiple}
							options={options}
							showEmptyOption={showEmptyOption}
						/>
					) : (
						<DropdownList
							currentValue={currentValue}
							expand={expand}
							handleSelect={handleSelect}
							label={label}
							multiple={multiple}
							options={options}
						/>
					)}
				</ClayDropDown.Menu>
			</div>
		</ClayTooltipProvider>
	);
};

const Main = ({
	defaultSearch = false,
	editingLanguageId,
	fixedOptions = [],
	label,
	localizedValue = {},
	localizedValueEdited,
	multiple,
	name,
	onBlur = () => {},
	onChange,
	onFocus = () => {},
	options = [],
	predefinedValue = [],
	readOnly = false,
	showEmptyOption = true,
	value = [],
	...otherProps
}) => {
	const predefinedValueArray = toArray(predefinedValue);
	const valueArray = toArray(value);

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

	value = useMemo(
		() =>
			normalizeValue({
				localizedValueEdited,
				multiple,
				normalizedOptions,
				predefinedValueArray,
				valueArray,
			}),
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
			<Select
				defaultSearch={defaultSearch}
				label={label}
				multiple={multiple}
				name={`${name}_field`}
				onChange={onChange}
				onCloseButtonClicked={({event, value}) =>
					onChange(event, value)
				}
				onDropdownItemClicked={({event, value}) =>
					onChange(event, value)
				}
				onExpand={({event, expand}) => {
					if (expand) {
						onFocus(event);
					}
					else {
						onBlur(event);
					}
				}}
				options={normalizedOptions}
				predefinedValue={predefinedValueArray}
				readOnly={readOnly}
				showEmptyOption={showEmptyOption}
				value={value}
				{...otherProps}
			/>

			<input name={name} type="hidden" value={value} />
		</FieldBase>
	);
};

Main.displayName = 'Select';

export default Main;
