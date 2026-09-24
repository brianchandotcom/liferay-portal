/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import {FieldBase} from 'frontend-js-components-web';
import {dateUtils} from 'frontend-js-web';
import React, {useState} from 'react';

import {
	UNSET_TIME,
	getLocaleDateFormat,
	is12HourLocale,
	toDisplayDateTime,
	toStorageDateTime,
} from '../../utils/dateTime';

import type {FirstDayOfWeekLocale} from 'frontend-js-web';

const DATE_PATTERN = /^\d{4}-\d{2}-\d{2}$/;

function applyDefaultTime(
	value: string,
	defaultTime: FieldDatePickerProps['defaultTime']
): string {
	if (!defaultTime) {
		return value;
	}

	const [datePart, timePart] = value.split(' ');

	if (!DATE_PATTERN.test(datePart) || timePart !== UNSET_TIME) {
		return value;
	}

	return `${datePart} ${
		typeof defaultTime === 'function' ? defaultTime(datePart) : defaultTime
	}`;
}

export type FieldDatePickerProps = {
	defaultTime?: string | ((date: string) => string);
	disabled?: boolean;
	errorMessage?: string;
	formGroupProps?: {className: string};
	helpMessage?: string;
	id?: string;
	label: string;
	name: string;
	required?: boolean;
	use12Hours?: boolean;
	value?: string;
} & React.ComponentProps<typeof ClayDatePicker>;

const FieldDatePicker = (props: FieldDatePickerProps) => {
	const locale = Liferay.ThemeDisplay.getBCP47LanguageId();

	const {
		dateFormat = getLocaleDateFormat(locale),
		defaultTime,
		disabled,
		errorMessage: externalErrorMessage,
		firstDayOfWeek = dateUtils.getFirstDayOfWeek(
			locale as FirstDayOfWeekLocale
		),
		formGroupProps,
		helpMessage,
		id,
		label,
		months = dateUtils.getMonthsLong(locale),
		name,
		onBlur,
		onChange,
		placeholder,
		required,
		time,
		timezone = '',
		use12Hours = is12HourLocale(locale),
		value = '',
		weekdaysShort = dateUtils.getWeekdaysShort(locale),
		...restProps
	} = props;

	const [draft, setDraft] = useState<string | null>(null);
	const [internalErrorMessage, setInternalErrorMessage] =
		useState<string>('');

	const fieldId = id ?? name;

	const handleOnBlur = (event: React.FocusEvent<HTMLInputElement>) => {
		const storageDateTime = toStorageDateTime(
			event.target.value,
			dateFormat,
			use12Hours
		);

		const val = applyDefaultTime(
			time && DATE_PATTERN.test(storageDateTime)
				? `${storageDateTime} ${UNSET_TIME}`
				: storageDateTime,
			defaultTime
		);

		setDraft(null);

		setInternalErrorMessage(
			val && !dateUtils.isValid(val)
				? Liferay.Language.get('the-field-value-is-invalid')
				: ''
		);

		if (val !== value) {
			onChange?.(val);
		}

		onBlur?.(event);
	};

	const handleOnChange = (val: string) => {
		const storageValue = applyDefaultTime(
			toStorageDateTime(val, dateFormat, use12Hours),
			defaultTime
		);

		setDraft(val);

		if (
			internalErrorMessage &&
			(!storageValue || dateUtils.isValid(storageValue))
		) {
			setInternalErrorMessage('');
		}

		onChange?.(storageValue);
	};

	const errorMessage = internalErrorMessage || externalErrorMessage;

	const displayValue =
		draft !== null &&
		toStorageDateTime(draft, dateFormat, use12Hours) === value
			? draft
			: toDisplayDateTime(value, dateFormat, use12Hours);

	return (
		<FieldBase
			className={formGroupProps?.className}
			disabled={disabled}
			errorMessage={errorMessage}
			helpMessage={helpMessage}
			id={fieldId}
			label={label}
			required={required}
		>
			<ClayDatePicker
				{...restProps}
				aria-describedby={
					errorMessage || helpMessage
						? `${fieldId}fieldFeedback`
						: undefined
				}
				aria-invalid={!!errorMessage}
				dateFormat={dateFormat}
				disabled={disabled}
				firstDayOfWeek={firstDayOfWeek}
				id={fieldId}
				inputName={name}
				months={months}
				onBlur={handleOnBlur}
				onChange={handleOnChange}
				placeholder={
					placeholder ??
					(time
						? `${dateFormat} ${use12Hours ? 'HH:MM AM' : 'HH:MM'}`.toUpperCase()
						: undefined)
				}
				time={time}
				timezone={timezone}
				use12Hours={use12Hours}
				value={displayValue}
				weekdaysShort={weekdaysShort}
			/>
		</FieldBase>
	);
};

export default FieldDatePicker;
