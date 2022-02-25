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
import {createTextMaskInputElement} from 'text-mask-core';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {createAutoCorrectedDatePipe} from './createAutoCorrectedDatePipe';

const DIGIT_REGEX = /\d/;
const PIPE_FORBIDDEN_ENDING_CHAR_REGEX = /[^\w]/i;
const LETTER_REGEX = /[a-z]/i;
const SERVER_DATE_FORMAT = 'YYYY-MM-DD';
const SERVER_DATE_TIME_FORMAT = 'YYYY-MM-DD HH:mm';
const NOT_LETTER_REGEX = /[^a-z]/gi;
const WORD_CHARACTER_REGEX = /\w/g;
const A_OR_P_CHARACTER_REGEX = /[AP]/i;
const M_CHARACTER_REGEX = /M/i;

export default function DatePicker({
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	dir,
	locale,
	localizable,
	localizedValue,
	months,
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue,
	readOnly,
	type,
	value,
	weekdaysShort,
	...otherProps
}) {
	const inputRef = useRef(null);
	const maskRef = useRef();
	const {
		clayFormat,
		isDateTime,
		momentFormat,
		placeholder,
		serverFormat,
		use12Hours,
	} = useMemo(() => {
		let use12Hours = false;

		const isDateTime = type === 'date_time';
		const momentLocale = moment().locale(locale ?? defaultLanguageId);
		const dateFormat = momentLocale.localeData().longDateFormat('L');
		const time = momentLocale.localeData().longDateFormat('LT');

		let momentFormat = dateFormat;

		if (isDateTime) {
			const [hourFormat] = time.split(NOT_LETTER_REGEX, 1);

			const formattedTime =
				hourFormat.length === 1
					? hourFormat[0] === 'H'
						? `H${time}`
						: `h${time}`
					: time;

			momentFormat = `${dateFormat} ${formattedTime}`;
			use12Hours = time.endsWith('A');
		}

		const clayFormat = dateFormat
			.replace('YYYY', 'yyyy')
			.replace('DD', 'dd');

		const placeholder = momentFormat.replace(WORD_CHARACTER_REGEX, '_');

		const serverFormat = isDateTime
			? SERVER_DATE_TIME_FORMAT
			: SERVER_DATE_FORMAT;

		return {
			clayFormat,
			isDateTime,
			momentFormat,
			placeholder,
			serverFormat,
			use12Hours,
		};
	}, [defaultLanguageId, locale, type]);

	const date = useMemo(() => {
		let formattedDate = '';
		let year = moment().year();
		const rawDate =
			(localizable
				? localizedValue?.[locale] ??
				  localizedValue?.[defaultLanguageId]
				: value) ??
			predefinedValue ??
			'';

		if (rawDate !== '') {
			const date = moment(rawDate, serverFormat, true);
			formattedDate = date
				.locale(locale ?? defaultLanguageId)
				.format(momentFormat);
			year = date.year();
		}

		return {
			formattedDate,
			locale,
			name,
			predefinedValue,
			rawDate,
			years: {end: year + 5, start: year - 5},
		};
	}, [
		momentFormat,
		defaultLanguageId,
		locale,
		localizable,
		localizedValue,
		name,
		predefinedValue,
		serverFormat,
		value,
	]);

	const [{formattedDate, rawDate, years}, setDate] = useState(date);

	/**
	 * Updates the rawDate state whenever the prop value or localizedValue changes,
	 * but it keep user's input case theres no language change.
	 */
	useEffect(
		() =>
			setDate(({formattedDate, name, predefinedValue}) =>
				name === date.name && predefinedValue === date.predefinedValue
					? {...date, formattedDate}
					: date
			),
		[date]
	);

	/**
	 * Creates the input mask and update it whenever the format changes
	 */
	useEffect(() => {
		const mask = [];
		[...momentFormat].forEach((char) => {
			if (char === 'A' || char === 'a') {
				mask.push(A_OR_P_CHARACTER_REGEX);
				mask.push(M_CHARACTER_REGEX);

				return;
			}
			mask.push(LETTER_REGEX.test(char) ? DIGIT_REGEX : char);
		});

		const pipeFormat = momentFormat
			.split(PIPE_FORBIDDEN_ENDING_CHAR_REGEX)
			.reduce((format, item) => {
				switch (item) {
					case 'YYYY':
						return `${format} yyyy`;
					case 'MM':
						return `${format} mm`;
					case 'DD':
						return `${format} dd`;
					case 'mm':
						return `${format} MM`;
					case 'A':
					case 'a':
						return format;
					case '':
						return `${format} `;
					default:
						return `${format} ${item}`;
				}
			}, '')
			.trim();

		maskRef.current = createTextMaskInputElement({
			guide: true,
			inputElement: inputRef.current,
			keepCharPositions: true,
			mask,
			pipe: createAutoCorrectedDatePipe(pipeFormat),
			showMask: true,
		});
	}, [momentFormat]);

	const handleValueChange = (value) => {
		let formattedDate = value;
		if (isDateTime) {
			const firstSpace = value.indexOf(' ');
			formattedDate = value.substring(0, firstSpace);
			const formattedTime = value
				.substring(firstSpace)
				.replaceAll('-', '_');
			formattedDate = `${formattedDate}${formattedTime}`;
		}
		const nextState = {
			formattedDate,
			rawDate: '',
		};

		const date = moment(formattedDate, momentFormat, true);
		if (date.isValid()) {
			nextState.rawDate = date.locale('en').format(serverFormat);
			nextState.years = {end: date.year() + 5, start: date.year() - 5};
		}

		setDate((previousState) => ({...previousState, ...nextState}));

		if (nextState.rawDate !== rawDate) {
			onChange({}, nextState.rawDate);
		}
	};

	return (
		<FieldBase
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
			{...otherProps}
		>
			<ClayDatePicker
				dateFormat={clayFormat}
				dir={dir}
				disabled={readOnly}
				months={months}
				onBlur={onBlur}
				onFocus={onFocus}
				onInput={({target: {value}}) => maskRef.current.update(value)}
				onValueChange={handleValueChange}
				placeholder={placeholder}
				ref={inputRef}
				time={isDateTime}
				use12Hours={use12Hours}
				value={formattedDate}
				weekdaysShort={weekdaysShort}
				years={years}
				yearsCheck={false}
			/>

			<input name={name} type="hidden" value={rawDate} />
		</FieldBase>
	);
}
