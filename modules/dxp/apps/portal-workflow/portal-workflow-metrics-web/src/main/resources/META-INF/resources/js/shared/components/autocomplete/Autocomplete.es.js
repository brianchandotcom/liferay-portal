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

import PromisesResolver from '../promises-resolver/PromisesResolver.es';
import {DropDown} from './AutocompleteDropDown.es';

const Autocomplete = ({
	children,
	defaultValue = '',
	disabled,
	items,
	onChange,
	onSelect,
	placeholder = '',
	promises = []
}) => {
	const [activeItem, setActiveItem] = useState(-1);
	const [dropDownItems, setDropDownItems] = useState([]);
	const [value, setValue] = useState(defaultValue);
	const [dropDownVisible, setDropDownVisible] = useState(false);
	const [selected, setSelected] = useState(false);

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

	const handleFocus = () => {
		setDropDownVisible(true);
	};

	const handleKeyDown = useCallback(
		({keyCode}) => {
			const item = dropDownItems[activeItem];

			if (keyCode === 13 && item) {
				handleSelect(item);
			}

			if (keyCode === 38 && activeItem > 0) {
				setActiveItem(activeItem - 1);
			}

			if (keyCode === 40 && activeItem < dropDownItems.length - 1) {
				setActiveItem(activeItem + 1);
			}
		},
		[activeItem, dropDownItems, handleSelect]
	);

	const handleSelect = useCallback(
		item => {
			onSelect(item);
			setActiveItem(-1);
			setDropDownVisible(false);
			setSelected(true);
			setValue(item.name);
		},
		[onSelect]
	);

	useEffect(() => {
		setDropDownItems(items);
	}, [items]);

	useEffect(() => {
		if (!onChange) {
			const regExpValue = formatRegExp(value);
			const match = new RegExp(regExpValue, 'gi');
			setDropDownItems(
				items ? items.filter(item => item.name.match(match)) : []
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
							data-testid="autocompleteInput"
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
					setSelected={setSelected}
				/>
			</ClayAutocomplete>
		</PromisesResolver>
	);
};

const formatRegExp = value => {
	return value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
};

Autocomplete.DropDown = DropDown;

export {Autocomplete};
