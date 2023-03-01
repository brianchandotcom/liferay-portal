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
import {default as React, useRef, useState} from 'react';

import {PROPERTY_TYPES} from '../../utils/constants';

const INTERNAL_DATE_FORMAT = 'yyyy-MM-dd';
const DISPLAY_DATE_FORMAT = 'yyyy/MM/dd';

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

	const [displayDate, setDisplayDate] = useState<DateRange | string>(() => {
		const toDisplayDate = (internalDate: string | undefined) => {
			let isoDate = new Date().toISOString();

			if (internalDate && propertyType !== PROPERTY_TYPES.DATE_TIME) {
				isoDate = parse(
					internalDate,
					INTERNAL_DATE_FORMAT,
					new Date()
				).toISOString();
			}

			return format(new Date(isoDate), DISPLAY_DATE_FORMAT);
		};

		if (typeof initialValue === 'object') {
			return {
				end: toDisplayDate(initialValue?.end),
				start: toDisplayDate(initialValue?.start),
			};
		}

		return toDisplayDate(initialValue);
	});

	const previousDisplayDateRef = useRef(displayDate);

	const saveDateTimeValue = () => {
		const toInternalDate = (_displayDate: string) => {
			const dateObject = parseISO(_displayDate.replace(/\//g, '-'));

			let internalDate = '';

			if (isValid(dateObject)) {
				internalDate = format(
					new Date(_displayDate),
					INTERNAL_DATE_FORMAT
				);
			}
			else {
				internalDate = format(new Date(), INTERNAL_DATE_FORMAT);
			}

			if (propertyType === PROPERTY_TYPES.DATE_TIME) {
				internalDate = parse(
					internalDate,
					INTERNAL_DATE_FORMAT,
					new Date()
				).toISOString();
			}

			return [internalDate, dateObject] as const;
		};

		if (typeof displayDate === 'object') {
			const [internalEndDate, endDateObject] = toInternalDate(
				displayDate.end
			);
			const [internalStartDate, startDateObject] = toInternalDate(
				displayDate.start
			);
			const previousDisplayDate = previousDisplayDateRef.current as DateRange;

			if (
				previousDisplayDate.start !== displayDate.start ||
				previousDisplayDate.end !== displayDate.end ||
				!isValid(startDateObject) ||
				!isValid(endDateObject)
			) {
				previousDisplayDateRef.current = displayDate;

				onChange({
					type: propertyType,
					value: {
						end: internalEndDate,
						start: internalStartDate,
					},
				});
			}
		}
		else {
			const [internalDate, dateObject] = toInternalDate(displayDate);

			if (
				previousDisplayDateRef.current !== displayDate ||
				!isValid(dateObject)
			) {
				previousDisplayDateRef.current = displayDate;
				onChange({type: propertyType, value: internalDate});
			}
		}
	};

	const onDisplayDateChange = (nextDisplayDate: string) => {
		if (range) {
			const [start, end] = nextDisplayDate.split(' - ');

			setDisplayDate({end, start});
		}
		else {
			setDisplayDate(nextDisplayDate);
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
				onChange={onDisplayDateChange}
				onExpandedChange={onExpandedChange}
				range={range}
				value={
					typeof displayDate === 'object'
						? `${displayDate.start} - ${displayDate.end}`
						: displayDate
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
