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
import ClayForm from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {
	formatDateObject,
	getDateFromDateString,
} from '../../../utilities/dates';
import getAppContext from '../Context';

function DateRangeFilter(props) {
	const {actions} = getAppContext();

	const [fromValue, setFromValue] = useState(
		props.value && props.value.from && formatDateObject(props.value.from)
	);
	const [toValue, setToValue] = useState(
		props.value && props.value.to && formatDateObject(props.value.to)
	);

	useEffect(() => {
		setFromValue(() =>
			props.value && props.value.from
				? formatDateObject(props.value.from)
				: ''
		);
		setToValue(() =>
			props.value && props.value.to
				? formatDateObject(props.value.to)
				: ''
		);
	}, [props.value]);

	return (
		<div className="form-group">
			<ClayForm.Group className="form-group-sm">
				<label htmlFor="basicInput">
					{Liferay.Language.get('from')}
				</label>
				<input
					className="form-control"
					max={toValue || (props.max && formatDateObject(props.max))}
					min={props.min && formatDateObject(props.min)}
					onChange={(e) => setFromValue(e.target.value)}
					pattern="\d{4}-\d{2}-\d{2}"
					placeholder={props.placeholder || 'yyyy-mm-dd'}
					type="date"
					value={fromValue}
				/>
			</ClayForm.Group>
			<ClayForm.Group className="form-group-sm mt-2">
				<label htmlFor="basicInput">{Liferay.Language.get('to')}</label>
				<input
					className="form-control"
					max={props.max && formatDateObject(props.max)}
					min={
						fromValue || (props.min && formatDateObject(props.min))
					}
					onChange={(e) => setToValue(e.target.value)}
					pattern="\d{4}-\d{2}-\d{2}"
					placeholder={props.placeholder || 'yyyy-mm-dd'}
					type="date"
					value={toValue}
				/>
			</ClayForm.Group>

			<div className="mt-3">
				<ClayButton
					className="btn-sm"
					disabled={
						fromValue ===
							(props.value && props.value.from
								? formatDateObject(props.value.from)
								: '') &&
						toValue ===
							(props.value && props.value.to
								? formatDateObject(props.value.to)
								: '')
					}
					onClick={() => {
						if (!fromValue && !toValue) {
							actions.updateFilterValue(props.id, null);
						}
						else {
							actions.updateFilterValue(props.id, {
								from: fromValue
									? getDateFromDateString(fromValue)
									: null,
								to: toValue
									? getDateFromDateString(toValue)
									: null,
							});
						}
					}}
				>
					{props.panelType === 'edit'
						? Liferay.Language.get('edit-filter')
						: Liferay.Language.get('add-filter')}
				</ClayButton>
			</div>
		</div>
	);
}

DateRangeFilter.propTypes = {
	id: PropTypes.string.isRequired,
	invisible: PropTypes.bool,
	label: PropTypes.string.isRequired,
	max: PropTypes.shape({
		day: PropTypes.number,
		month: PropTypes.number,
		year: PropTypes.number,
	}),
	min: PropTypes.shape({
		day: PropTypes.number,
		month: PropTypes.number,
		year: PropTypes.number,
	}),
	placeholder: PropTypes.string,
	type: PropTypes.oneOf(['dateRange']).isRequired,
	value: PropTypes.shape({
		from: PropTypes.shape({
			day: PropTypes.number,
			month: PropTypes.number,
			year: PropTypes.number,
		}),
		to: PropTypes.shape({
			day: PropTypes.number,
			month: PropTypes.number,
			year: PropTypes.number,
		}),
	}),
};

export default DateRangeFilter;
