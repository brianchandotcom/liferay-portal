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

import ClayDatePicker from '@clayui/date-picker';
import dateFns from 'date-fns';
import propTypes from 'prop-types';
import React from 'react';

import {PROPERTY_TYPES} from '../../utils/constants.es';

const INPUT_DATE_FORMAT = 'YYYY-MM-DD';

class DateTimeInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		propertyLabel: propTypes.string.isRequired,
		value: propTypes.string,
	};

	state = {};

	previousValue = {};

	static getDerivedStateFromProps(props, state) {
		let returnVal = null;

		if (props.value !== state.initialValue) {
			returnVal = {
				expanded: false,
				initialValue: props.value,
				value: dateFns.format(new Date(props.value), INPUT_DATE_FORMAT),
			};
		}

		return returnVal;
	}

	_handleDateChange = (value) => {
		this.setState({value});
	};

	_handleExpandedChange = (expandedState) => {
		this.setState({expanded: expandedState});

		if (expandedState === false) {
			this._saveDateTimeValue();
		}
	};

	_saveDateTimeValue = () => {
		const date = dateFns.format(this.state.value, INPUT_DATE_FORMAT);

		if (date !== 'Invalid Date') {
			this.setState(
				{
					value: date,
				},
				() => {
					this.props.onChange({
						type: PROPERTY_TYPES.DATE_TIME,
						value: dateFns
							.parse(date, INPUT_DATE_FORMAT)
							.toISOString(),
					});
				}
			);
		}
		else {
			const resetDate = dateFns.format(new Date(), INPUT_DATE_FORMAT);

			this.setState(
				{
					value: resetDate,
				},
				() => {
					this.props.onChange({
						type: PROPERTY_TYPES.DATE_TIME,
						value: dateFns
							.parse(resetDate, INPUT_DATE_FORMAT)
							.toISOString(),
					});
				}
			);
		}
	};

	render() {
		const {expanded, value} = this.state;
		const {disabled, propertyLabel} = this.props;

		return (
			<div className="criterion-input date-input">
				<ClayDatePicker
					aria-label={`${propertyLabel}: ${Liferay.Language.get(
						'select-date'
					)}`}
					disabled={disabled}
					expanded={expanded}
					months={[
						`${Liferay.Language.get('january')}`,
						`${Liferay.Language.get('february')}`,
						`${Liferay.Language.get('march')}`,
						`${Liferay.Language.get('april')}`,
						`${Liferay.Language.get('may')}`,
						`${Liferay.Language.get('june')}`,
						`${Liferay.Language.get('july')}`,
						`${Liferay.Language.get('august')}`,
						`${Liferay.Language.get('september')}`,
						`${Liferay.Language.get('october')}`,
						`${Liferay.Language.get('november')}`,
						`${Liferay.Language.get('december')}`,
					]}
					onBlur={this._saveDateTimeValue}
					onChange={this._handleDateChange}
					onExpandedChange={this._handleExpandedChange}
					value={value}
					years={{
						end: new Date().getFullYear(),
						start: 1900
					}}
				/>
			</div>
		);
	}
}

export default DateTimeInput;
