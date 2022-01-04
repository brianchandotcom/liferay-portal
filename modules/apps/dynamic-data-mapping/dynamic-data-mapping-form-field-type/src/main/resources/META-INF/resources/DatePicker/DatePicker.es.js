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
import moment from 'moment/min/moment-with-locales';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {createAutoCorrectedDatePipe} from 'text-mask-addons';
import {createTextMaskInputElement} from 'text-mask-core';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const DIGIT_REGEX = /\d/i;
const LETTER_REGEX = /[a-z]/i;
const LETTER_DIGIT_REGEX = /[A-Z0-9]/gi;
const NOT_LETTER_REGEX = /[^a-z]/gi;
const YEARS_INDEX = 6;

const getDateMask = (dateDelimiter, dateFormat) => {
	const lastSymbol = dateFormat.slice(-1).match(NOT_LETTER_REGEX);

	dateFormat = lastSymbol ? dateFormat.slice(0, -1) : dateFormat;

	return dateFormat
		.split(dateDelimiter)
		.map((item) => {
			let currentFormat;

			if (item === 'YYYY') {
				currentFormat = 'yyyy';
			}
			else if (item === 'DD') {
				currentFormat = 'dd';
			}
			else {
				currentFormat = 'MM';
			}

			return currentFormat;
		})
		.join(dateDelimiter);
};

const getDelimiter = (dateFormat) => {
	let dateDelimiter = '/';

	if (dateFormat.indexOf('.') !== -1) {
		dateDelimiter = '.';
	}

	if (dateFormat.indexOf('-') !== -1) {
		dateDelimiter = '-';
	}

	return dateDelimiter;
};

const getLocaleDateFormat = (locale, format = 'L') => {
	moment.locale(locale);

	return moment.localeData().longDateFormat(format);
};

const getMaskByDateFormat = (format) => {
	const mask = [];

	for (let i = 0; i < format.length; i++) {
		if (LETTER_REGEX.test(format[i])) {
			mask.push(DIGIT_REGEX);
		}
		else {
			mask.push(`${format[i]}`);
		}
	}

	return mask;
};

const getDateFormat = (locale) => {
	const dateFormat = getLocaleDateFormat(locale);
	const inputMask = getMaskByDateFormat(dateFormat);
	const dateDelimiter = getDelimiter(inputMask);

	return {
		dateMask: getDateMask(dateDelimiter, dateFormat),
		inputMask,
	};
};

const getInitialMonth = (value) => {
	if (moment(value).isValid()) {
		return moment(value).toDate();
	}

	return moment().toDate();
};

const getInitialValue = (
	defaultLanguageId,
	date,
	locale,
	formatInEditingLocale
) => {
	if (typeof date === 'string' && date.indexOf('_') === -1 && date !== '') {
		if (formatInEditingLocale) {
			return moment(date, [
				getLocaleDateFormat(locale),
				'YYYY-MM-DD',
			]).format(getLocaleDateFormat(locale));
		}

		return moment(date, [
			getLocaleDateFormat(defaultLanguageId),
			'YYYY-MM-DD',
		]).format(getLocaleDateFormat(defaultLanguageId));
	}

	return date;
};

const getValueForHidden = (value, locale) => {
	const momentLocale = moment().locale(locale);

	const momentLocaleFormatted = momentLocale.localeData().longDateFormat('L');

	const newMoment = moment(value, momentLocaleFormatted, true);

	if (newMoment.isValid()) {
		return newMoment.locale('en-US').format('YYYY-MM-DD');
	}

	return '';
};

const Months = [
	Liferay.Language.get('january'),
	Liferay.Language.get('february'),
	Liferay.Language.get('march'),
	Liferay.Language.get('april'),
	Liferay.Language.get('may'),
	Liferay.Language.get('june'),
	Liferay.Language.get('july'),
	Liferay.Language.get('august'),
	Liferay.Language.get('september'),
	Liferay.Language.get('october'),
	Liferay.Language.get('november'),
	Liferay.Language.get('december'),
];

const WeekdayShort = [
	Liferay.Language.get('weekday-short-sunday'),
	Liferay.Language.get('weekday-short-monday'),
	Liferay.Language.get('weekday-short-tuesday'),
	Liferay.Language.get('weekday-short-wednesday'),
	Liferay.Language.get('weekday-short-thursday'),
	Liferay.Language.get('weekday-short-friday'),
	Liferay.Language.get('weekday-short-saturday'),
];

const DatePicker = ({
	defaultLanguageId,
	disabled,
	formatInEditingLocale,
	locale,
	localizable,
	localizedValue: localizedValueInitial = {},
	name,
	onBlur,
	onChange,
	onFocus,
	spritemap,
	value: initialValue,
}) => {
	const inputRef = useRef(null);
	const maskInstanceRef = useRef(null);

	const [expanded, setExpand] = useState(false);

	const [localizedValue, setLocalizedValue] = useState(localizedValueInitial);

	const initialValueMemoized = useMemo(
		() =>
			getInitialValue(
				defaultLanguageId,
				initialValue,
				locale,
				formatInEditingLocale
			),
		[defaultLanguageId, formatInEditingLocale, initialValue, locale]
	);

	const [value, setValue] = useState(initialValueMemoized);

	useEffect(() => {
		setValue(initialValueMemoized);
	}, [initialValueMemoized]);

	const [years, setYears] = useState(() => {
		const currentYear = new Date().getFullYear();

		return {
			end: currentYear + 5,
			start: currentYear - 5,
		};
	});

	const {dateMask, inputMask} = getDateFormat(locale);

	useEffect(() => {
		if (inputRef.current && inputMask && dateMask) {
			maskInstanceRef.current = createTextMaskInputElement({
				guide: true,
				inputElement: inputRef.current,
				keepCharPositions: true,
				mask: inputMask,
				pipe: createAutoCorrectedDatePipe(dateMask.toLowerCase()),
				showMask: true,
			});

			const currentValue = localizable ? localizedValue[locale] : value;

			if (currentValue) {
				if (
					currentValue !== inputRef.current.value ||
					!/[//.-]/.test(currentValue)
				) {
					inputRef.current.value = moment(currentValue).format(
						dateMask.toUpperCase()
					);
				}
			}
			else if (initialValueMemoized) {
				var year = parseInt(
					initialValueMemoized.substr(YEARS_INDEX),
					10
				);

				const date = moment(initialValueMemoized);

				if (year <= 50) {
					date.subtract(2000, 'years');
				}
				else if (year > 50 && year < 100) {
					date.subtract(1900, 'years');
				}

				inputRef.current.value = date.format(dateMask.toUpperCase());
			}
			else {
				inputRef.current.value = '';
			}

			if (
				inputRef.current.value.match(LETTER_DIGIT_REGEX) ||
				inputRef.current.value === ''
			) {
				maskInstanceRef.current.update(inputRef.current.value);
			}
		}
	}, [
		dateMask,
		inputMask,
		inputRef,
		initialValueMemoized,
		localizable,
		localizedValue,
		locale,
		value,
	]);

	const handleNavigation = (date) => {
		const currentYear = date.getFullYear();

		setYears({
			end: currentYear + 5,
			start: currentYear - 5,
		});
	};

	return (
		<>
			<input
				aria-hidden="true"
				name={name}
				type="hidden"
				value={getValueForHidden(value, locale)}
			/>
			<ClayDatePicker
				dateFormat={dateMask}
				disabled={disabled}
				expanded={expanded}
				initialMonth={getInitialMonth(value)}
				months={Months}
				onBlur={onBlur}
				onExpandedChange={(expand) => {
					setExpand(expand);
				}}
				onFocus={onFocus}
				onInput={(event) => {
					maskInstanceRef.current.update(event.target.value);
					setLocalizedValue({
						...localizedValue,
						[locale]: event.target.value,
					});
				}}
				onNavigation={handleNavigation}
				onValueChange={(value, eventType) => {
					setLocalizedValue({
						...localizedValue,
						[locale]: value,
					});

					setValue(value);

					if (eventType === 'click') {
						setExpand(false);
						inputRef.current.focus();
					}

					if (
						!value ||
						value ===
							maskInstanceRef.current.state.previousPlaceholder
					) {
						return onChange('');
					}

					if (
						moment(
							value,
							getLocaleDateFormat(locale),
							true
						).isValid()
					) {
						onChange(getValueForHidden(value, locale));
					}
				}}
				ref={inputRef}
				spritemap={spritemap}
				value={value}
				weekdaysShort={WeekdayShort}
				years={years}
			/>
		</>
	);
};

const Main = ({
	defaultLanguageId,
	locale = themeDisplay.getDefaultLanguageId(),
	localizable,
	localizedValue,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	predefinedValue,
	readOnly,
	spritemap,
	value,
	...otherProps
}) => (
	<FieldBase
		{...otherProps}
		localizedValue={localizedValue}
		name={name}
		readOnly={readOnly}
		spritemap={spritemap}
	>
		<DatePicker
			defaultLanguageId={defaultLanguageId}
			disabled={readOnly}
			formatInEditingLocale={
				localizedValue &&
				localizedValue[locale] !== undefined &&
				localizedValue[locale] !== null
			}
			locale={locale}
			localizable={localizable}
			localizedValue={localizedValue}
			name={name}
			onBlur={onBlur}
			onChange={(value) => onChange({}, value)}
			onFocus={onFocus}
			placeholder={placeholder}
			spritemap={spritemap}
			value={value ? value : predefinedValue}
		/>
	</FieldBase>
);

Main.displayName = 'DatePicker';

export default Main;
