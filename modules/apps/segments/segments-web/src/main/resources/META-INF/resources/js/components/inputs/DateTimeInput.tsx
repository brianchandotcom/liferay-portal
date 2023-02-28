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
import {usePrevious} from '@liferay/frontend-js-react-web';
import {format, isValid, parse, parseISO} from 'date-fns';
import {default as React, useState} from 'react';

import {PROPERTY_TYPES} from '../../utils/constants';

const OUTPUT_DATE_FORMAT = 'yyyy-MM-dd';
const INPUT_DATE_FORMAT = 'yyyy/MM/dd';

interface DateRange {
	end: string;
	start: string;
}

interface Props {
	disabled?: boolean;
	onChange: (payload: {type: string; value: DateRange | string}) => void;
	propertyLabel: string;
	propertyType: string;
	range?: boolean;
	value?: DateRange | string;
}

function DateTimeInput({
	disabled,
	onChange,
	propertyLabel,
	propertyType,
	range,
	value: initialValue,
}: Props) {
	const [expanded, setExpanded] = useState(false);

	const [value, setValue] = useState<DateRange | string>(() => {
		const formatInputDate = (dateValue: string | undefined) => {
			let parsedDateValue = dateValue || new Date().toISOString();

			if (propertyType !== PROPERTY_TYPES.DATE_TIME) {
				parsedDateValue = parse(
					parsedDateValue,
					OUTPUT_DATE_FORMAT,
					new Date()
				).toISOString();
			}

			return format(new Date(parsedDateValue), INPUT_DATE_FORMAT);
		};

		if (typeof initialValue === 'object') {
			return {
				end: formatInputDate(initialValue?.end),
				start: formatInputDate(initialValue?.start),
			};
		}

		return formatInputDate(initialValue);
	});

	const previousValue = usePrevious(value);

	const saveDateTimeValue = () => {
		const getFormattedDate = (_nextValue: string) => {
			const dateObject = parseISO(_nextValue.replace(/\//g, '-'));

			let dateInput = '';
			let dateOutput = '';

			if (isValid(dateObject)) {
				dateInput = format(new Date(_nextValue), INPUT_DATE_FORMAT);
				dateOutput = format(new Date(_nextValue), OUTPUT_DATE_FORMAT);
			}
			else {
				dateInput = format(new Date(), INPUT_DATE_FORMAT);
				dateOutput = format(new Date(), OUTPUT_DATE_FORMAT);
			}

			if (propertyType === PROPERTY_TYPES.DATE_TIME) {
				dateOutput = parse(
					dateOutput,
					OUTPUT_DATE_FORMAT,
					new Date()
				).toISOString();
			}

			return [dateInput, dateOutput, dateObject] as const;
		};

		if (typeof value === 'object') {
			const [
				endDateInput,
				endDateOutput,
				endDateObject,
			] = getFormattedDate(value.end);

			const [
				startDateInput,
				startDateOutput,
				startDateObject,
			] = getFormattedDate(value.start);

			if (typeof previousValue === 'object') {
				if (
					previousValue.start !== startDateInput ||
					previousValue.end !== endDateInput ||
					!isValid(startDateObject) ||
					!isValid(endDateObject)
				) {
					onChange({
						type: propertyType,
						value: {end: endDateOutput, start: startDateOutput},
					});
				}
			}
			else {
				onChange({
					type: propertyType,
					value: {end: endDateOutput, start: startDateOutput},
				});
			}
		}
		else {
			const [dateInput, dateOutput, dateObject] = getFormattedDate(value);

			if (previousValue !== dateInput || !isValid(dateObject)) {
				setValue(dateInput);
				onChange({type: propertyType, value: dateOutput});
			}
		}
	};

	const onValueChange = (nextValue: string) => {
		if (range) {
			const [start, end] = nextValue.split(' - ');

			setValue({end, start});
		}
		else {
			setValue(nextValue);
		}
	};

	const onExpandedChange = (nextExpanded: boolean) => {
		setExpanded(nextExpanded);

		if (!nextExpanded) {
			saveDateTimeValue();
		}
	};

	return (
		<div className="criterion-input date-input">
			<ClayDatePicker
				ariaLabels={{
					buttonChooseDate: `${propertyLabel}: ${Liferay.Language.get(
						'select-date'
					)}`,
					buttonDot: `${Liferay.Language.get('select-current-date')}`,
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
				onBlur={saveDateTimeValue}
				onChange={onValueChange}
				onExpandedChange={onExpandedChange}
				range={range}
				value={
					typeof value === 'object'
						? `${value.start} - ${value.end}`
						: value
				}
				years={{
					end: new Date().getFullYear(),
					start: 1900,
				}}
			/>
		</div>
	);
}

export default DateTimeInput;
