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
import React, {useState, useCallback, useEffect} from 'react';

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
	promises
}) => {
	const [dropDownItems, setDropDownItems] = useState([]);
	const [value, setValue] = useState('');
	const [dropdownVisible, setDropdownVisible] = useState(false);
	const [selectedValue, setSelectedValue] = useState();

	const handleChange = useCallback(
		value => {
			if (selectedValue) {
				onSelect();
				setSelectedValue();
			}
			setValue(value);
			setDropdownVisible(() => value.length > 0);

			// eslint-disable-next-line react-hooks/exhaustive-deps
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[selectedValue]
	);

	const handleSelect = useCallback(
		item => {
			onSelect(item);
			setSelectedValue(item.name);
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
		setValue(defaultValue);
	}, [defaultValue]);

	useEffect(() => {
		if (disabled) {
			setValue('');
			setDropdownVisible(false);
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
							onChange={({target}) => handleChange(target.value)}
							placeholder={placeholder}
							value={value}
						/>

						{children}
					</ClayInput.GroupItem>
				</ClayInput.Group>

				<Autocomplete.DropDown
					active={dropdownVisible}
					items={dropDownItems}
					match={value}
					onSelect={handleSelect}
					setActive={setDropdownVisible}
					setValue={setValue}
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
