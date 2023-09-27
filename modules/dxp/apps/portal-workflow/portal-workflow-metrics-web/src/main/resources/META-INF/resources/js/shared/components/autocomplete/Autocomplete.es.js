/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import {ClayInput} from '@clayui/form';
import React, {useCallback, useEffect, useState} from 'react';

import PromisesResolver from '../promises-resolver/PromisesResolver.es';
import {DropDown} from './AutocompleteDropDown.es';

const formatRegExp = (value) => {
	return value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
};

const Autocomplete = ({
	children,
	defaultValue = '',
	disabled,
	items,
	onChange,
	onSelect,
	placeholder = '',
	promises = [],
}) => {
	const [activeItem, setActiveItem] = useState(-1);
	const [dropDownItems, setDropDownItems] = useState([]);
	const [value, setValue] = useState(defaultValue);
	const [dropDownVisible, setDropDownVisible] = useState(false);
	const [selected, setSelected] = useState(false);
	const keyArrowDown = 38;
	const keyArrowUp = 40;
	const keyEnter = 13;

	const handleBlur = useCallback(() => {
		setDropDownVisible(false);
		setActiveItem(-1);

		if (!selected) {
			setValue('');
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selected]);

	const handleChange = useCallback(
		({target: {value}}) => {
			onSelect();
			setSelected(false);
			setDropDownVisible(true);
			setValue(value);
		},

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[onSelect, selected]
	);

	const handleFocus = () => {
		setDropDownVisible(true);
	};

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

	const handleKeyDown = useCallback(
		({keyCode}) => {
			const item = dropDownItems[activeItem];

			if (keyCode === keyArrowDown && activeItem > 0) {
				setActiveItem(activeItem - 1);
			}
			else if (
				keyCode === keyArrowUp &&
				activeItem < dropDownItems.length - 1
			) {
				setActiveItem(activeItem + 1);
			}
			else if (keyCode === keyEnter && item) {
				handleSelect(item);
			}
		},
		[activeItem, dropDownItems, handleSelect]
	);

	useEffect(() => {
		setDropDownItems(items);
	}, [items]);

	useEffect(() => {
		if (!onChange) {
			const regExpValue = formatRegExp(value);
			const match = new RegExp(regExpValue, 'gi');
			setDropDownItems(
				items ? items.filter((item) => item.name.match(match)) : []
			);
		}
		else {
			onChange(value);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [value]);

	useEffect(() => {
		if (disabled) {
			setValue('');
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [disabled]);

	return (
		<PromisesResolver promises={promises}>
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
							onBlur={handleBlur}
							onChange={handleChange}
							onFocus={handleFocus}
							onKeyDown={handleKeyDown}
							placeholder={placeholder}
							value={defaultValue || value}
						/>

						{children}
					</ClayInput.GroupItem>
				</ClayInput.Group>

				<Autocomplete.DropDown
					active={dropDownVisible}
					activeItem={activeItem}
					items={dropDownItems}
					match={value}
					onSelect={handleSelect}
					setActiveItem={setActiveItem}
				/>
			</ClayAutocomplete>
		</PromisesResolver>
	);
};

Autocomplete.DropDown = DropDown;

export {Autocomplete};
