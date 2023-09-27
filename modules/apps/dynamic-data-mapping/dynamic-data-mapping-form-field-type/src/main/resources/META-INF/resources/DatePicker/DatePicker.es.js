/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	createAutoCorrectedDatePipe,
	generateDate,
	generateDateConfigurations,
	generateInputMask,
} from '@liferay/object-js-components-web';
import moment from 'moment/min/moment-with-locales';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {createTextMaskInputElement} from 'text-mask-core';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {getTooltipTitle} from '../util/tooltip';

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
		firstDayOfWeek,
		isDateTime,
		momentFormat,
		placeholder,
		serverFormat,
		use12Hours,
	} = useMemo(() => {
		return generateDateConfigurations({
			defaultLanguageId,
			locale,
			type,
		});
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
			setDate(({formattedDate, name, predefinedValue, rawDate}) =>
				name === date.name &&
				predefinedValue === date.predefinedValue &&
				rawDate === ''
					? {...date, formattedDate}
					: date
			),
		[date]
	);

	/**
	 * Creates the input mask and update it whenever the format changes
	 */
	useEffect(() => {
		const {mask, pipeFormat} = generateInputMask(momentFormat);

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
		const nextState = generateDate({
			isDateTime,
			momentFormat,
			serverFormat,
			value,
		});

		setDate((previousState) => ({...previousState, ...nextState}));

		if (nextState.rawDate !== rawDate) {
			onChange({}, nextState.rawDate);
		}
	};

	const [expanded, setExpanded] = useState(false);

	const handleExpandedChange = (value) => {
		if (value !== expanded) {
			setExpanded(value);

			if (value) {
				onFocus?.();
			}
			else {
				onBlur?.();
			}
		}
	};
	const onInputMask = ({target: {value}}) => {
		try {
			maskRef.current.update(value);
		}
		catch (error) {
			maskRef.current.update('');
		}
	};

	return (
		<FieldBase
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
			{...otherProps}
		>
			<ClayTooltipProvider autoAlign>
				<div
					data-tooltip-align="top"
					{...getTooltipTitle({placeholder, value: formattedDate})}
				>
					<ClayDatePicker
						dateFormat={clayFormat}
						dir={dir}
						disabled={readOnly}
						expanded={expanded}
						firstDayOfWeek={firstDayOfWeek}
						months={months}
						onBlur={onBlur}
						onChange={handleValueChange}
						onExpandedChange={handleExpandedChange}
						onFocus={onFocus}
						onInput={onInputMask}
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
				</div>
			</ClayTooltipProvider>
		</FieldBase>
	);
}
