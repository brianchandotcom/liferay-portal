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
import ClayForm from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {
	convertObjectDateToIsoString,
	formatDateObject,
	formatDateRangeObject,
	getDateFromDateString,
} from '../../../utils/dates';

function getOdataString(value, key) {
	if (value.from && value.to) {
		return `${key} ge ${convertObjectDateToIsoString(
			value.from,
			'from'
		)}) and (${key} le ${convertObjectDateToIsoString(value.to, 'to')}`;
	}
	if (value.from) {
		return `${key} ge ${convertObjectDateToIsoString(value.from, 'from')}`;
	}
	if (value.to) {
		return `${key} le ${convertObjectDateToIsoString(value.to, 'to')}`;
	}
}
function DateRangeFilter({
	id,
	max,
	min,
	placeholder,
	updateFilterState,
	value: valueProp,
}) {
	const [fromValue, setFromValue] = useState(
		valueProp?.from && formatDateObject(valueProp.from)
	);
	const [toValue, setToValue] = useState(
		valueProp?.to && formatDateObject(valueProp.to)
	);

	let actionType = 'edit';

	if (valueProp && !fromValue && !toValue) {
		actionType = 'delete';
	}

	if (!valueProp) {
		actionType = 'add';
	}

	let submitDisabled = true;

	if (
		actionType === 'delete' ||
		((!valueProp || !valueProp.from) && fromValue) ||
		((!valueProp || !valueProp.to) && toValue) ||
		(valueProp &&
			valueProp.from &&
			fromValue !== formatDateObject(valueProp.from)) ||
		(valueProp &&
			valueProp.to &&
			toValue !== formatDateObject(valueProp.to))
	) {
		submitDisabled = false;
	}

	return (
		<>
			<ClayDropDown.Caption>
				<div className="form-group">
					<ClayForm.Group className="form-group-sm">
						<label htmlFor={`from-${id}`}>
							{Liferay.Language.get('from')}
						</label>
						<input
							className="form-control"
							id={`from-${id}`}
							max={toValue || (max && formatDateObject(max))}
							min={min && formatDateObject(min)}
							onChange={(e) => setFromValue(e.target.value)}
							pattern="\d{4}-\d{2}-\d{2}"
							placeholder={placeholder || 'yyyy-mm-dd'}
							type="date"
							value={fromValue || ''}
						/>
					</ClayForm.Group>
					<ClayForm.Group className="form-group-sm mt-2">
						<label htmlFor={`to-${id}`}>
							{Liferay.Language.get('to')}
						</label>
						<input
							className="form-control"
							id={`to-${id}`}
							max={max && formatDateObject(max)}
							min={fromValue || (min && formatDateObject(min))}
							onChange={(e) => setToValue(e.target.value)}
							pattern="\d{4}-\d{2}-\d{2}"
							placeholder={placeholder || 'yyyy-mm-dd'}
							type="date"
							value={toValue || ''}
						/>
					</ClayForm.Group>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={submitDisabled}
					onClick={() => {
						if (actionType === 'delete') {
							updateFilterState(id);
						}
						else {
							const newValue = {
								from: fromValue
									? getDateFromDateString(fromValue)
									: null,
								to: toValue
									? getDateFromDateString(toValue)
									: null,
							};
							updateFilterState(
								id,
								newValue,
								formatDateRangeObject(newValue),
								getOdataString(newValue, id)
							);
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

const dateShape = PropTypes.shape({
	day: PropTypes.number,
	month: PropTypes.number,
	year: PropTypes.number,
});

DateRangeFilter.propTypes = {
	id: PropTypes.string.isRequired,
	max: dateShape,
	min: dateShape,
	placeholder: PropTypes.string,
	updateFilterState: PropTypes.func.isRequired,
	value: PropTypes.shape({
		from: dateShape,
		to: dateShape,
	}),
};

export default DateRangeFilter;
