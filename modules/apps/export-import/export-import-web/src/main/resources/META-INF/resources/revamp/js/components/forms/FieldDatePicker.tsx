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
	isCompleteDate,
	isCompleteDateTime,
	toDisplayDateTime,
	toStorageDateTime,
} from '../../utils/dateTime';

import type {FirstDayOfWeekLocale} from 'frontend-js-web';

function appendUnsetTime(storageDateTime: string): string {
	return isCompleteDate(storageDateTime)
		? `${storageDateTime} ${UNSET_TIME}`
		: storageDateTime;
}

function applyDefaultTime(
	value: string,
	defaultTime: FieldDatePickerProps['defaultTime']
): string {
	if (!defaultTime) {
		return value;
	}

	const [datePart, timePart] = value.split(' ');

	if (!isCompleteDate(datePart) || timePart !== UNSET_TIME) {
		return value;
	}

	return `${datePart} ${
		typeof defaultTime === 'function' ? defaultTime(datePart) : defaultTime
	}`;
}

function isValidStorageDateTime(storageDateTime: string): boolean {
	return (
		!storageDateTime ||
		isCompleteDate(storageDateTime) ||
		isCompleteDateTime(storageDateTime)
	);
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

		const nextValue = applyDefaultTime(
			time ? appendUnsetTime(storageDateTime) : storageDateTime,
			defaultTime
		);

		setDraft(null);

		setInternalErrorMessage(
			isValidStorageDateTime(nextValue)
				? ''
				: Liferay.Language.get('please-enter-a-valid-date')
		);

		if (nextValue !== value) {
			onChange?.(nextValue);
		}

		onBlur?.(event);
	};

	const handleOnChange = (displayDateTime: string) => {
		const storageDateTime = applyDefaultTime(
			toStorageDateTime(displayDateTime, dateFormat, use12Hours),
			defaultTime
		);

		setDraft(displayDateTime);

		if (internalErrorMessage && isValidStorageDateTime(storageDateTime)) {
			setInternalErrorMessage('');
		}

		onChange?.(storageDateTime);
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
				aria-required={required}
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
