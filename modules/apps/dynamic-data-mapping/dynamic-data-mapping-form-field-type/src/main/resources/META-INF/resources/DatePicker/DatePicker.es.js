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

const DIGIT_REGEX = /\d/;
const ENDS_WITH_SPECIAL_CHAR_REGEX = /[^\w]$/;
const LETTER_REGEX = /[a-z]/i;
const SEVER_DATE_FORMAT = 'YYYY-MM-DD';

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
	value,
	weekdaysShort,
	...otherProps
}) {
	const inputRef = useRef(null);
	const maskRef = useRef();
	const [expanded, setExpand] = useState(false);

	const {clayFormat, momentFormat, placeholder} = useMemo(() => {
		const momentFormat = moment()
			.locale(locale ?? defaultLanguageId)
			.localeData()
			.longDateFormat('L');

		const clayFormat = momentFormat
			.replace('YYYY', 'yyyy')
			.replace('DD', 'dd');

		const placeholder = clayFormat.replace(/\w/g, '_');

		return {clayFormat, momentFormat, placeholder};
	}, [defaultLanguageId, locale]);

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
			const date = moment(
				rawDate,
				[SEVER_DATE_FORMAT, momentFormat],
				true
			);
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
		const mask = [...momentFormat].map((char) =>
			LETTER_REGEX.test(char) ? DIGIT_REGEX : char
		);

		const pipeFormat = (ENDS_WITH_SPECIAL_CHAR_REGEX.test(momentFormat)
			? momentFormat.slice(0, -1)
			: momentFormat
		).toLowerCase();

		maskRef.current = createTextMaskInputElement({
			guide: true,
			inputElement: inputRef.current,
			keepCharPositions: true,
			mask,
			pipe: createAutoCorrectedDatePipe(pipeFormat),
			showMask: true,
		});
	}, [momentFormat]);

	const handleValueChange = (value, eventType) => {
		if (eventType === 'click') {
			setExpand(false);
			inputRef.current.focus();
		}

		const date = moment(value, momentFormat, true);
		const nextState = {
			formattedDate: value,
			rawDate: '',
		};

		if (date.isValid()) {
			nextState.rawDate = date.locale('en').format(SEVER_DATE_FORMAT);
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
				expanded={expanded}
				months={months}
				onBlur={onBlur}
				onExpandedChange={setExpand}
				onFocus={onFocus}
				onInput={({target: {value}}) => maskRef.current.update(value)}
				onValueChange={handleValueChange}
				placeholder={placeholder}
				ref={inputRef}
				value={formattedDate}
				weekdaysShort={weekdaysShort}
				years={years}
			/>

			<input name={name} type="hidden" value={rawDate} />
		</FieldBase>
	);
}
