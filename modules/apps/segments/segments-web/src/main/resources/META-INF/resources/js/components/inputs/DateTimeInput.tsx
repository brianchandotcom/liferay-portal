/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import {format, isValid, parse} from 'date-fns';
import {default as React, useEffect, useRef, useState} from 'react';

import {PROPERTY_TYPES} from '../../utils/constants';

const INTERNAL_DATE_FORMAT = 'yyyy-MM-dd';
const DISPLAY_DATE_FORMAT = 'yyyy/MM/dd';

interface Props {
	disabled?: boolean;
	onChange: (payload: {type: string; value: string}) => void;
	propertyLabel: string;
	propertyType: string;
	value?: string;
}

function DateTimeInput({
	disabled,
	onChange,
	propertyLabel,
	propertyType,
	value,
}: Props) {
	const [expanded, setExpanded] = useState(false);

	const [displayDate, setDisplayDate] = useState<string>(() =>
		toDisplayDate(value || new Date().toISOString())
	);

	const previousDisplayDateRef = useRef(displayDate);

	useEffect(() => {
		const nextDisplayDate = toDisplayDate(
			value || new Date().toISOString()
		);

		previousDisplayDateRef.current = nextDisplayDate;
		setDisplayDate(nextDisplayDate);
	}, [value]);

	const saveDateTimeValue = () => {
		const validDate = toDisplayDate(
			displayDate,
			previousDisplayDateRef.current
		);

		setDisplayDate(validDate);

		const internalDate =
			propertyType === PROPERTY_TYPES.DATE_TIME
				? toInternalDateTime(validDate)
				: toInternalDate(validDate);

		const previousDisplayDate = previousDisplayDateRef.current;

		if (!datesAreEqual(previousDisplayDate, validDate)) {
			previousDisplayDateRef.current = validDate;

			onChange({
				type: propertyType,
				value: internalDate,
			});
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
				onChange={setDisplayDate}
				onExpandedChange={onExpandedChange}
				value={displayDate}
				years={{
					end: new Date().getFullYear(),
					start: 1900,
				}}
			/>
		</div>
	);
}

function datesAreEqual(dateA: string, dateB: string) {
	return dateA === dateB;
}

function toDisplayDate(internalOrIsoDate: string, previousDate?: string) {
	let dateObject = new Date(internalOrIsoDate);

	const resetDate = previousDate ? new Date(previousDate) : new Date();

	if (!isValid(dateObject)) {
		dateObject = parse(internalOrIsoDate, INTERNAL_DATE_FORMAT, resetDate);
	}

	if (!isValid(dateObject)) {
		dateObject = resetDate;
	}

	return format(dateObject, DISPLAY_DATE_FORMAT);
}

function toInternalDate(displayOrIsoDate: string) {
	let dateObject = new Date(displayOrIsoDate);

	if (!isValid(dateObject)) {
		dateObject = parse(displayOrIsoDate, DISPLAY_DATE_FORMAT, new Date());
	}

	if (!isValid(dateObject)) {
		dateObject = new Date();
	}

	return format(dateObject, INTERNAL_DATE_FORMAT);
}

function toInternalDateTime(displayOrIsoDate: string) {
	return new Date(toInternalDate(displayOrIsoDate)).toISOString();
}

export default DateTimeInput;
