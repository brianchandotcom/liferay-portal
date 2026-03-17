/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import {FieldBase} from 'frontend-js-components-web';
import {dateUtils} from 'frontend-js-web';
import React, {useState} from 'react';

export type FieldDatePickerProps = {
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
		disabled,
		errorMessage: externalErrorMessage,
		firstDayOfWeek = dateUtils.getFirstDayOfWeek(locale),
		formGroupProps,
		helpMessage,
		id,
		label,
		months = dateUtils.getMonthsLong(locale),
		name,
		onBlur,
		onChange,
		required,
		timezone = Liferay.ThemeDisplay.getTimeZone(),
		value = '',
		weekdaysShort = dateUtils.getWeekdaysShort(locale),
		...restProps
	} = props;

	const [internalErrorMessage, setInternalErrorMessage] =
		useState<string>('');

	const fieldId = id ?? name;

	const handleOnBlur = (event: React.FocusEvent<HTMLInputElement>) => {
		const val = event.target.value;

		setInternalErrorMessage(
			val && !dateUtils.isValid(val)
				? Liferay.Language.get('the-field-value-is-invalid')
				: ''
		);

		onBlur?.(event);
	};

	const handleOnChange = (val: string) => {
		if (internalErrorMessage && (!val || dateUtils.isValid(val))) {
			setInternalErrorMessage('');
		}

		onChange?.(val);
	};

	const errorMessage = internalErrorMessage || externalErrorMessage;

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
				disabled={disabled}
				firstDayOfWeek={firstDayOfWeek}
				id={fieldId}
				inputName={name}
				months={months}
				onBlur={handleOnBlur}
				onChange={handleOnChange}
				timezone={timezone}
				value={value}
				weekdaysShort={weekdaysShort}
			/>
		</FieldBase>
	);
};

export default FieldDatePicker;
