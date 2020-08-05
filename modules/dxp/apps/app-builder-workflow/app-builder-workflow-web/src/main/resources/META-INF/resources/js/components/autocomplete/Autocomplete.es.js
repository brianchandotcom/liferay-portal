/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAutocomplete from '@clayui/autocomplete';
import {ClayInput} from '@clayui/form';
import React, {useCallback, useEffect, useState} from 'react';

import AutocompleteDropDown from './AutocompleteDropDown.es';

export default function Autocomplete({
	children,
	defaultValue = '',
	disabled,
	id = '',
	items,
	onChange,
	onSelect,
	placeholder = Liferay.Language.get('select-or-type-an-option'),
}) {
	const [activeItem, setActiveItem] = useState(-1);
	const [dropDownVisible, setDropDownVisible] = useState(false);
	const [filteredItems, setFilteredItems] = useState(items);
	const [value, setValue] = useState(defaultValue);
	const [selected, setSelected] = useState(false);

	const handleChange = useCallback(
		({target: {value}}) => {
			if (selected) {
				onSelect();
				setSelected(false);
			}

			setDropDownVisible(true);
			setValue(value);
			// eslint-disable-next-line react-hooks/exhaustive-deps
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[onSelect, selected]
	);

	const handleSelect = useCallback(
		(item) => {
			onSelect(item);
			setActiveItem(-1);
			setDropDownVisible(false);
			setSelected(true);
			setValue(item.name);
		},
		[onSelect]
	);

	const onBlur = useCallback(() => {
		setDropDownVisible(false);
		setActiveItem(-1);

		if (!selected) {
			setValue('');
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selected]);

	const onFocus = () => {
		setDropDownVisible(true);
	};

	const onKeyDown = ({key}) => {
		const item = filteredItems[activeItem];

		const updateIndex = (index) => {
			setActiveItem(index);

			const element = document.querySelector(
				`#dropDownList${id} > li:nth-child(${index})`
			);

			if (typeof element?.scrollIntoView === 'function') {
				element.scrollIntoView();
			}
		};

		if (key === 'ArrowUp' && activeItem > 0) {
			updateIndex(activeItem - 1);
		}

		if (key === 'ArrowDown' && activeItem < filteredItems.length - 1) {
			updateIndex(activeItem + 1);
		}

		if (key === 'Enter' && item) {
			handleSelect(item);
		}

		if (key === 'Tab') {
			onBlur();
		}
	};

	useEffect(() => {
		if (disabled) {
			setValue('');
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [disabled]);

	useEffect(() => {
		setFilteredItems(items);
	}, [items]);

	useEffect(() => {
		if (!onChange) {
			const match = new RegExp(
				value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'),
				'gi'
			);

			setFilteredItems(
				items ? items.filter((item) => item.name.match(match)) : []
			);
		}
		else {
			onChange(value);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [value]);

	return (
		<>
			<ClayAutocomplete>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayAutocomplete.Input
							className={`form-control ${
								children
									? 'input-group-inset input-group-inset-after'
									: ''
							}`}
							disabled={disabled}
							onBlur={onBlur}
							onChange={handleChange}
							onFocus={onFocus}
							onKeyDown={onKeyDown}
							placeholder={placeholder}
							value={defaultValue || value}
						/>

						{children}
					</ClayInput.GroupItem>
				</ClayInput.Group>

				<AutocompleteDropDown
					active={dropDownVisible}
					activeItem={activeItem}
					id={id}
					items={filteredItems}
					match={value}
					onSelect={handleSelect}
					setActiveItem={setActiveItem}
				/>
			</ClayAutocomplete>
		</>
	);
}
