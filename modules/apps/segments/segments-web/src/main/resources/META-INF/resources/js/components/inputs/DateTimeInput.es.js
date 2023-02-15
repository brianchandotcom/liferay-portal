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
import {format, isValid, parse, parseISO} from 'date-fns';
import propTypes from 'prop-types';
import React from 'react';

import {PROPERTY_TYPES} from '../../utils/constants.es';

const OUTPUT_DATE_FORMAT = 'yyyy-MM-dd';
const INPUT_DATE_FORMAT = 'yyyy/MM/dd';

class DateTimeInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		propertyLabel: propTypes.string.isRequired,
		propertyType: propTypes.string.isRequired,
		value: propTypes.string,
	};

	constructor(props) {
		super(props);
		const isoString =
			PROPERTY_TYPES.DATE_TIME === props.propertyType
				? props.value
				: parse(
						props.value,
						OUTPUT_DATE_FORMAT,
						new Date()
				  ).toISOString();

		const actualValue = format(new Date(isoString), INPUT_DATE_FORMAT);

		this.state = {
			expanded: false,
			previousValue: actualValue,
			value: actualValue,
		};
	}

	_handleDateChange = (value) => {
		this.setState({
			value,
		});
	};

	_handleExpandedChange = (expandedState) => {
		this.setState({expanded: expandedState});

		if (expandedState === false) {
			this._saveDateTimeValue();
		}
	};

	_saveDateTimeValue = () => {
		const dateObj = parseISO(this.state.value.replaceAll('/', '-'));

		let dateInput = '';
		let dateOutput = '';

		if (isValid(dateObj)) {
			dateInput = format(new Date(this.state.value), INPUT_DATE_FORMAT);
			dateOutput = format(new Date(this.state.value), OUTPUT_DATE_FORMAT);
		}
		else {
			dateInput = format(new Date(), INPUT_DATE_FORMAT);
			dateOutput = format(new Date(), OUTPUT_DATE_FORMAT);
		}

		if (this.state.previousValue !== dateInput || !isValid(dateObj)) {
			this.setState(
				{
					previousValue: dateInput,
					value: dateInput,
				},
				() => {
					this.props.onChange({
						type: this.props.propertyType,
						value:
							this.props.propertyType === PROPERTY_TYPES.DATE_TIME
								? parse(
										dateOutput,
										OUTPUT_DATE_FORMAT,
										new Date()
								  ).toISOString()
								: dateOutput,
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
					ariaLabels={{
						buttonChooseDate: `${propertyLabel}: ${Liferay.Language.get(
							'select-date'
						)}`,
						buttonDot: `${Liferay.Language.get(
							'select-current-date'
						)}`,
						buttonNextMonth: `${Liferay.Language.get(
							'select-next-month'
						)}`,
						buttonPreviousMonth: `${Liferay.Language.get(
							'select-previous-month'
						)}`,
						dialog: `${Liferay.Language.get('select-date')}`,
						input: `${propertyLabel}: ${Liferay.Language.get(
							'input-a-value'
						)}`,
					}}
					data-testid="date-input"
					dateFormat="yyyy/MM/dd"
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
						start: 1900,
					}}
				/>
			</div>
		);
	}
}

export default DateTimeInput;
