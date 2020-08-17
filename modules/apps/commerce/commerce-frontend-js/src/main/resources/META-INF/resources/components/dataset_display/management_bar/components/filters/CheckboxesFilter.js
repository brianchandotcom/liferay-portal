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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox, ClayToggle} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {isValuesArrayChanged} from '../../../utilities/filters';

export const formatValue = (value, items, exclude) => {
	const formattedValue = value
		? value
				.map((v) => {
					return items.reduce(
						(found, item) =>
							found || (item.value === v ? item.label : null),
						null
					);
				})
				.join(', ')
		: '';

	return (
		(exclude ? `(${Liferay.Language.get('exclude')}) ` : '') +
		formattedValue
	);
};

function getOdataString(values, key, exclude = false) {
	if (!values || !values.length) {
		return null;
	}

	return `${key}/any(x:${values
		.map(
			(v) =>
				`(x ${exclude ? 'ne' : 'eq'} ${
					typeof v === 'string' ? `'${v}'` : v
				})`
		)
		.join(exclude ? ' and ' : ' or ')})`;
}
function CheckboxesFilter(props) {
	const [itemsValues, setItemsValues] = useState(
		(props.value && props.value.itemsValues) || []
	);
	const [exclude, setExclude] = useState(
		props.value ? props.value.exclude : false
	);

	function selectCheckbox(selected) {
		if (itemsValues.includes(selected)) {
			return setItemsValues(itemsValues.filter((v) => v !== selected));
		}

		return setItemsValues(itemsValues.concat(selected));
	}

	useEffect(() => {
		setItemsValues(props.value ? props.value.itemsValues : []);
		setExclude(props.value ? props.value.exclude : false);
	}, [props.value]);

	let actionType = 'edit';

	if (props.value && props.value.itemsValues && !itemsValues.length) {
		actionType = 'delete';
	}

	if (!props.value) {
		actionType = 'add';
	}

	let submitDisabled = true;

	if (
		actionType === 'delete' ||
		(!props.value && itemsValues.length) ||
		(props.value &&
			isValuesArrayChanged(props.value.itemsValues, itemsValues)) ||
		(props.value && itemsValues.length && props.value.exclude !== exclude)
	) {
		submitDisabled = false;
	}

	return (
		<>
			<ClayDropDown.Caption className="pb-0">
				<div className="row">
					<div className="col">
						<label htmlFor={`autocomplete-exclude-${props.id}`}>
							{Liferay.Language.get('exclude')}
						</label>
					</div>
					<div className="col-auto">
						<ClayToggle
							id={`autocomplete-exclude-${props.id}`}
							onToggle={() => setExclude(!exclude)}
							toggled={exclude}
						/>
					</div>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<div className="inline-scroller mb-n2 mx-n2 px-2">
					{props.items.map((item, i) => {
						let checked = false;

						if (itemsValues) {
							checked = itemsValues.reduce(
								(acc, el) => acc || el === item.value,
								false
							);
						}

						return (
							<ClayCheckbox
								aria-label={item.label}
								checked={checked}
								key={i}
								label={item.label}
								onChange={() => selectCheckbox(item.value)}
							/>
						);
					})}
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={submitDisabled}
					onClick={() =>
						actionType !== 'delete'
							? props.actions.updateFilterState(
									props.id,
									{
										exclude,
										itemsValues,
									},
									formatValue(
										itemsValues,
										props.items,
										exclude
									),
									getOdataString(
										itemsValues,
										props.id,
										exclude
									)
							  )
							: props.actions.updateFilterState(props.id)
					}
					small
				>
					{actionType === 'add' && Liferay.Language.get('add-filter')}
					{actionType === 'edit' &&
						Liferay.Language.get('edit-filter')}
					{actionType === 'delete' &&
						Liferay.Language.get('delete-filter')}
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
}

CheckboxesFilter.propTypes = {
	id: PropTypes.string.isRequired,
	invisible: PropTypes.bool,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
		})
	),
	label: PropTypes.string.isRequired,
	type: PropTypes.oneOf(['checkbox']).isRequired,
	value: PropTypes.shape({
		exclude: PropTypes.bool,
		itemsValues: PropTypes.arrayOf(
			PropTypes.oneOfType([PropTypes.string, PropTypes.number])
		),
	}),
};

export default CheckboxesFilter;
