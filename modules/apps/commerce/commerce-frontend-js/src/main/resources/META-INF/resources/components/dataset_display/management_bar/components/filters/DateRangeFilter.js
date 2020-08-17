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
} from '../../../utilities/dates';

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
function DateRangeFilter(props) {
	const [fromValue, setFromValue] = useState(
		props.value && props.value.from && formatDateObject(props.value.from)
	);
	const [toValue, setToValue] = useState(
		props.value && props.value.to && formatDateObject(props.value.to)
	);

	let actionType = 'edit';

	if (props.value && !fromValue && !toValue) {
		actionType = 'delete';
	}

	if (!props.value) {
		actionType = 'add';
	}

	let submitDisabled = true;

	if (
		actionType === 'delete' ||
		((!props.value || !props.value.from) && fromValue) ||
		((!props.value || !props.value.to) && toValue) ||
		(props.value &&
			props.value.from &&
			fromValue !== formatDateObject(props.value.from)) ||
		(props.value &&
			props.value.to &&
			toValue !== formatDateObject(props.value.to))
	) {
		submitDisabled = false;
	}

	return (
		<>
			<ClayDropDown.Caption>
				<div className="form-group">
					<ClayForm.Group className="form-group-sm">
						<label htmlFor={`from-${props.id}`}>
							{Liferay.Language.get('from')}
						</label>
						<input
							className="form-control"
							id={`from-${props.id}`}
							max={
								toValue ||
								(props.max && formatDateObject(props.max))
							}
							min={props.min && formatDateObject(props.min)}
							onChange={(e) => setFromValue(e.target.value)}
							pattern="\d{4}-\d{2}-\d{2}"
							placeholder={props.placeholder || 'yyyy-mm-dd'}
							type="date"
							value={fromValue || ''}
						/>
					</ClayForm.Group>
					<ClayForm.Group className="form-group-sm mt-2">
						<label htmlFor={`to-${props.id}`}>
							{Liferay.Language.get('to')}
						</label>
						<input
							className="form-control"
							id={`to-${props.id}`}
							max={props.max && formatDateObject(props.max)}
							min={
								fromValue ||
								(props.min && formatDateObject(props.min))
							}
							onChange={(e) => setToValue(e.target.value)}
							pattern="\d{4}-\d{2}-\d{2}"
							placeholder={props.placeholder || 'yyyy-mm-dd'}
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
							props.actions.updateFilterState(props.id);
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
							props.actions.updateFilterState(
								props.id,
								newValue,
								formatDateRangeObject(newValue),
								getOdataString(newValue, props.id)
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
	invisible: PropTypes.bool,
	label: PropTypes.string.isRequired,
	max: dateShape,
	min: dateShape,
	placeholder: PropTypes.string,
	type: PropTypes.oneOf(['dateRange']).isRequired,
	value: PropTypes.shape({
		from: dateShape,
		to: dateShape,
	}),
};

export default DateRangeFilter;
