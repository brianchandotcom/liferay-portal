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
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {
	formatDateObject,
	getDateFromDateString,
} from '../../../utilities/dates';
import getAppContext from '../Context';

function DateFilter({
	id,
	inputText,
	max,
	min,
	panelType,
	placeholder,
	value: valueProp,
}) {
	const {actions} = getAppContext();
	const [value, setValue] = useState(
		valueProp ? formatDateObject(valueProp) : ''
	);

	useEffect(() => {
		setValue(() => (value ? formatDateObject(value) : ''));
	}, [value]);

	return (
		<div className="form-group">
			<div className="input-group">
				<div
					className={classNames('input-group-item', {
						'input-group-prepend': inputText,
					})}
				>
					<input
						className="form-control"
						max={max && formatDateObject(max)}
						min={min && formatDateObject(min)}
						onChange={(event) => setValue(event.target.value)}
						pattern="\d{4}-\d{2}-\d{2}"
						placeholder={placeholder || 'yyyy-mm-dd'}
						type="date"
						value={value}
					/>
				</div>
			</div>
			<div className="mt-3">
				<ClayButton
					className="btn-sm"
					disabled={value == (value ? formatDateObject(value) : '')}
					onClick={() => {
						actions.updateFilterValue(
							id,
							value ? getDateFromDateString(value) : null
						);
					}}
				>
					{panelType === 'edit'
						? Liferay.Language.get('edit-filter')
						: Liferay.Language.get('add-filter')}
				</ClayButton>
			</div>
		</div>
	);
}

DateFilter.propTypes = {
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
	type: PropTypes.oneOf(['date']).isRequired,
	value: PropTypes.shape({
		day: PropTypes.number,
		month: PropTypes.number,
		year: PropTypes.number,
	}),
};

export default DateFilter;
