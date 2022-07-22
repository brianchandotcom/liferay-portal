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

import {isValuesArrayChanged} from '../../../utils/index';

const getSelectedItemsLabel = ({items, selectedData}) => {
	const itemsValues = selectedData.itemsValues;

	const itemsLabel = itemsValues
		? itemsValues
				.map((itemsValue) => {
					return items.reduce(
						(found, item) =>
							found ||
							(item.value === itemsValue ? item.label : null),
						null
					);
				})
				.join(', ')
		: '';

	return (
		(selectedData?.exclude ? `(${Liferay.Language.get('exclude')}) ` : '') +
		itemsLabel
	);
};

const getOdataString = ({entityFieldType, id, selectedData}) => {
	const {exclude, itemsValues} = selectedData;

	if (!itemsValues?.length) {
		return null;
	}

	const quotedItemValues = itemsValues.map((itemValue) => {
		return typeof itemValue === 'string' ? `'${itemValue}'` : itemValue;
	});

	if (entityFieldType === 'collection') {
		return `${id}/any(x:${quotedItemValues
			.map((itemValue) => `(x ${exclude ? 'ne' : 'eq'} ${itemValue})`)
			.join(exclude ? ' and ' : ' or ')})`;
	}
	else if (itemsValues.length === 1) {
		return `${id} ${exclude ? 'ne' : 'eq'} ${quotedItemValues[0]}`;
	}
	else if (!exclude) {
		return `${id} in (${quotedItemValues.join(', ')})`;
	}
	else {
		return quotedItemValues.reduce((previous, current) => {
			const value = `(${id} ne ${current})`;

			return previous ? `${previous} and ${value}` : value;
		}, '');
	}
};
function CheckboxesFilter({
	entityFieldType,
	id,
	items,
	selectedData,
	setFilter,
}) {
	const [itemsValues, setItemsValues] = useState(
		selectedData?.itemsValues || []
	);
	const [exclude, setExclude] = useState(selectedData?.exclude || false);

	function selectCheckbox(selected) {
		if (itemsValues.includes(selected)) {
			return setItemsValues(
				itemsValues.filter((value) => value !== selected)
			);
		}

		return setItemsValues(itemsValues.concat(selected));
	}

	useEffect(() => {
		setItemsValues(selectedData?.itemsValues || []);
		setExclude(selectedData?.exclude || false);
	}, [selectedData]);

	let actionType = 'edit';

	if (selectedData?.itemsValues && !itemsValues.length) {
		actionType = 'delete';
	}

	if (!selectedData) {
		actionType = 'add';
	}

	let submitDisabled = true;

	if (
		actionType === 'delete' ||
		(!selectedData && itemsValues.length) ||
		(selectedData &&
			isValuesArrayChanged(selectedData.itemsValues, itemsValues)) ||
		(selectedData && itemsValues.length && selectedData.exclude !== exclude)
	) {
		submitDisabled = false;
	}

	return (
		<>
			<ClayDropDown.Caption className="pb-0">
				<div className="row">
					<div className="col">
						<label htmlFor={`autocomplete-exclude-${id}`}>
							{Liferay.Language.get('exclude')}
						</label>
					</div>

					<div className="col-auto">
						<ClayToggle
							id={`autocomplete-exclude-${id}`}
							onToggle={() => setExclude(!exclude)}
							toggled={exclude}
						/>
					</div>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<div className="inline-scroller mb-n2 mx-n2 px-2">
					{items.map((item, i) => {
						let checked = false;

						if (itemsValues) {
							checked = itemsValues.reduce(
								(acc, element) => acc || element === item.value,
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
					onClick={() => {
						if (actionType === 'delete') {
							setFilter({active: false, id});
						}
						else {
							const newSelectedData = {
								exclude,
								itemsValues,
							};

							setFilter({
								active: true,
								id,
								odataFilterString: getOdataString({
									entityFieldType,
									id,
									selectedData: newSelectedData,
								}),
								selectedData: newSelectedData,
								selectedItemsLabel: getSelectedItemsLabel({
									items,
									selectedData: newSelectedData,
								}),
							});
						}
					}}
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
	entityFieldType: PropTypes.string,
	id: PropTypes.string.isRequired,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
		})
	),
	selectedData: PropTypes.shape({
		exclude: PropTypes.bool,
		itemsValues: PropTypes.arrayOf(
			PropTypes.oneOfType([PropTypes.string, PropTypes.number])
		),
	}),
};

export {getSelectedItemsLabel, getOdataString};
export default CheckboxesFilter;
