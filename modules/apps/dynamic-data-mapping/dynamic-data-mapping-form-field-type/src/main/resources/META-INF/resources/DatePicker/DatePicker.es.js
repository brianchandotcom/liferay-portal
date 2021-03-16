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
import {useSyncValue} from '../hooks/useSyncValue.es';

const DIGIT_REGEX = /\d/i;
const LETTER_REGEX = /[a-z]/i;

const getDateMask = (dateDelimiter, dateFormat) => {
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

	if (dateFormat.indexOf('.') != -1) {
		dateDelimiter = '.';
	}

	if (dateFormat.indexOf('-') != -1) {
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

const transformToDate = (
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
			]).toDate();
		}

		return moment(date, [
			getLocaleDateFormat(defaultLanguageId),
			'YYYY-MM-DD',
		]).toDate();
	}

	return date;
};

const getInitialMonth = (value) => {
	if (moment(value).isValid()) {
		return moment(value).toDate();
	}

	return moment().toDate();
};

const getValueForHidden = (value) => {
	if (moment(value).isValid()) {
		return moment(value).format('YYYY-MM-DD');
	}

	return '';
};

const DatePicker = ({
	defaultLanguageId,
	disabled,
	formatInEditingLocale,
	locale,
	name,
	onChange,
	spritemap,
	value: initialValue,
}) => {
	const inputRef = useRef(null);
	const maskInstance = useRef(null);

	const [expanded, setExpand] = useState(false);

	const [localizedValue, setLocalizedValue] = useState({});

	const initialValueMemoized = useMemo(
		() =>
			transformToDate(
				defaultLanguageId,
				initialValue,
				locale,
				formatInEditingLocale
			),
		[defaultLanguageId, formatInEditingLocale, initialValue, locale]
	);

	const [value, setValue] = useSyncValue(initialValueMemoized);
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
			maskInstance.current = createTextMaskInputElement({
				guide: true,
				inputElement: inputRef.current,
				keepCharPositions: true,
				mask: inputMask,
				pipe: createAutoCorrectedDatePipe(dateMask.toLowerCase()),
				showMask: true,
			});

			if (localizedValue[locale]) {
				if (typeof localizedValue[locale] === 'string') {
					inputRef.current.value = localizedValue[locale];
				}
				else {
					inputRef.current.value = moment(
						localizedValue[locale]
					).format(dateMask.toUpperCase());
				}
			}
			else if (initialValueMemoized) {
				inputRef.current.value = moment(initialValueMemoized).format(
					dateMask.toUpperCase()
				);
			}
			else {
				inputRef.current.value = '';
			}

			maskInstance.current.update(inputRef.current.value);
		}
	}, [
		dateMask,
		inputMask,
		inputRef,
		initialValueMemoized,
		localizedValue,
		locale,
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
				value={getValueForHidden(value)}
			/>
			<ClayDatePicker
				dateFormat={dateMask}
				disabled={disabled}
				expanded={expanded}
				initialMonth={getInitialMonth(value)}
				onExpandedChange={(expand) => {
					setExpand(expand);
				}}
				onInput={(event) => {
					maskInstance.current.update(event.target.value);
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
						value === maskInstance.current.state.previousPlaceholder
					) {
						return onChange('');
					}

					if (moment(value).isValid()) {
						onChange(
							moment(value, getLocaleDateFormat(locale)).format(
								'L'
							)
						);
					}
				}}
				ref={inputRef}
				spritemap={spritemap}
				value={value}
				years={years}
			/>
		</>
	);
};

const Main = ({
	defaultLanguageId,
	locale = themeDisplay.getDefaultLanguageId(),
	localizedValue,
	name,
	onChange,
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
				localizedValue && localizedValue[locale] != undefined
			}
			locale={locale}
			name={name}
			onChange={(value) => onChange({}, value)}
			placeholder={placeholder}
			spritemap={spritemap}
			value={value ? value : predefinedValue}
		/>
	</FieldBase>
);

Main.displayName = 'DatePicker';

export default Main;
