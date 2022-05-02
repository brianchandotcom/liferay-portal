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
import {ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const getSelectedItemsLabel = ({items, selectedData}) => {
	const {exclude, itemValue} = selectedData;

	return (
		(exclude ? `(${Liferay.Language.get('exclude')}) ` : '') +
		items.find((item) => item.value === itemValue).label
	);
};

const getOdataString = ({id, selectedData}) => {
	const {exclude, itemValue} = selectedData;

	return `${id} ${exclude ? 'ne' : 'eq'} ${
		typeof itemValue === 'string' ? `'${itemValue}'` : itemValue
	}`;
};
function RadioFilter({id, items, selectedData, setFilter}) {
	const [itemValue, setItemValue] = useState(selectedData?.itemValue);
	const [exclude, setExclude] = useState(!!selectedData?.exclude);

	const actionType = selectedData ? 'edit' : 'add';

	let submitDisabled = true;

	if (
		(!selectedData && itemValue !== undefined) ||
		(selectedData && selectedData.itemValue !== itemValue) ||
		(selectedData &&
			itemValue !== undefined &&
			selectedData.exclude !== exclude)
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
					<ClayRadioGroup
						onChange={setItemValue}
						value={itemValue ?? ''}
					>
						{items.map((item) => (
							<ClayRadio
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}
					</ClayRadioGroup>
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
								itemValue,
							};

							setFilter({
								id,
								odataFilterString: getOdataString({
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
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
}

RadioFilter.propTypes = {
	id: PropTypes.string.isRequired,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
		})
	),
	selectedData: PropTypes.shape({
		exclude: PropTypes.bool,
		itemValue: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
	}),
};

export {getSelectedItemsLabel, getOdataString};
export default RadioFilter;
