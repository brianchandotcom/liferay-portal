/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	createAutoCorrectedDatePipe,
	datetimeUtils,
} from '@liferay/object-js-components-web';

// @ts-ignore

import moment from 'moment/min/moment-with-locales';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {createTextMaskInputElement} from 'text-mask-core';

import FieldBase from '../FieldBase/ReactFieldBase.es';
import {getTooltipTitle} from '../util/tooltip';

import type {Locale, LocalizedValue} from '../types';

const DIGIT_REGEX = /\d/;

interface DatePickerProps {
	defaultLanguageId: Locale;
	dir: 'ltr' | 'rtl';
	displayErrors?: boolean;
	errorMessage?: string;
	htmlAutocompleteAttribute: string;
	locale: Locale;
	localizable: boolean;
	localizedValue?: LocalizedValue<string>;
	months: string[];
	name: string;
	onBlur: any;
	onChange: any;
	onFocus: any;
	predefinedValue?: string;
	readOnly: boolean;
	required: boolean;
	type: 'date' | 'date_time';
	valid: boolean;
	value: string;
	weekdaysShort: string[];
}

enum FirstDayOfWeek {
	Sunday = 0,
	Monday = 1,
	Tuesday = 2,
	Wednesday = 3,
	Thursday = 4,
	Friday = 5,
	Saturday = 6,
}

type Date = {
	formattedDate: string;
	locale?: string;
	name?: string;
	predefinedValue?: string;
	rawDate?: string;
	years?: {
		end: number;
		start: number;
	};
};

type DateMaskParams = {
	clayFormat?: string;
	firstDayOfWeek?: FirstDayOfWeek;
	isDateTime?: boolean;
	momentFormat?: string;
	months?: string[];
	placeholder?: string;
	serverFormat?: string;
	use12Hours?: boolean;
	weekdaysShort?: string[];
};

type MaskRef = {
	state: {
		previousConformedValue: string;
		previousPlaceholder: string;
	};
	update: (value: string) => void;
};

export default function DatePicker({
	defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId(),
	dir,
	displayErrors,
	errorMessage,
	htmlAutocompleteAttribute,
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
	required,
	type,
	valid,
	value,
	weekdaysShort,
	...otherProps
}: DatePickerProps) {
	const inputRef = useRef(null);
	const maskRef = useRef<null | MaskRef>(null);
	const [validField, setValidField] = useState({
		displayErrors,
		errorMessage,
		valid,
	});
	const {
		clayFormat,
		firstDayOfWeek,
		isDateTime = false,
		momentFormat = '',
		placeholder = '',
		serverFormat = '',
		use12Hours,
	} = useMemo(() => {
		let dateMaskParameters: DateMaskParams = {};
		dateMaskParameters = datetimeUtils.generateDateConfigurations({
			defaultLanguageId,
			locale,
			type,
		});

		return dateMaskParameters;
	}, [defaultLanguageId, locale, type]);

	const date: Date = useMemo(() => {
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
	useEffect(() => {
		setDate(({formattedDate, name, predefinedValue, rawDate}) =>
			name === date.name &&
			predefinedValue === date.predefinedValue &&
			rawDate === ''
				? {...date, formattedDate}
				: date
		);
	}, [date]);

	/**
	 * Always update formattedDate if there is a value in rawDate
	 */
	useEffect(() => {
		if (date.rawDate !== '') {
			const formattedDate = moment(rawDate, serverFormat, true)
				.locale(locale ?? defaultLanguageId)
				.format(momentFormat);

			setDate((previousState) => ({...previousState, formattedDate}));
		}
	}, [date, defaultLanguageId, locale, momentFormat, rawDate, serverFormat]);

	/**
	 * Creates the input mask and update it whenever the format changes
	 */
	useEffect(() => {
		const {mask, pipeFormat} =
			datetimeUtils.generateInputMask(momentFormat);

		maskRef.current = createTextMaskInputElement({
			guide: true,
			inputElement: inputRef.current,
			keepCharPositions: true,
			mask,
			pipe: createAutoCorrectedDatePipe(pipeFormat),
			showMask: true,
		});
	}, [momentFormat]);

	const handleValueChange = (value: string) => {
		const nextState = datetimeUtils.generateDate({
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

	const handleBlur = () => {
		if (!required) {
			const isInputFilled = DIGIT_REGEX.test(formattedDate);

			const isValidMomentFormat = moment(
				formattedDate,
				momentFormat,
				true
			).isValid();

			if (!isInputFilled || isValidMomentFormat) {
				setValidField({
					displayErrors,
					errorMessage,
					valid,
				});

				return;
			}

			setValidField({
				displayErrors: true,
				errorMessage: Liferay.Language.get('please-enter-a-valid-date'),
				valid: false,
			});

			return;
		}

		setValidField({
			displayErrors: !!errorMessage && !valid,
			errorMessage,
			valid,
		});

		onBlur?.();
	};

	const handleExpandedChange = (value: boolean) => {
		if (value !== expanded) {
			setExpanded(value);

			if (value) {
				onFocus?.();
				setValidField({
					displayErrors,
					errorMessage,
					valid,
				});
			}
			else {
				handleBlur();
			}
		}
	};
	const onInputMask: React.FocusEventHandler<HTMLInputElement> = ({
		target: {value},
	}) => {
		try {
			maskRef.current?.update(value);
		}
		catch (error) {
			maskRef.current?.update('');
		}
	};

	useEffect(() => {
		if (required) {
			setValidField({
				displayErrors,
				errorMessage,
				valid,
			});
		}

		if (predefinedValue && !valid) {
			setValidField({
				displayErrors: !!errorMessage && !valid,
				errorMessage,
				valid,
			});
		}
	}, [displayErrors, errorMessage, required, predefinedValue, valid]);

	return (
		<FieldBase
			displayErrors={validField.displayErrors}
			errorMessage={validField.errorMessage}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
			type={type}
			valid={validField.valid}
			{...otherProps}
		>
			<ClayTooltipProvider autoAlign>
				<div
					data-tooltip-align="top"
					{...getTooltipTitle({placeholder, value: formattedDate})}
				>
					<ClayDatePicker
						{...(htmlAutocompleteAttribute && {
							autoComplete: htmlAutocompleteAttribute,
						})}
						aria-required={required}
						ariaLabels={{
							buttonChooseDate: `${Liferay.Language.get(
								'select-date'
							)}`,
							buttonDot: `${Liferay.Language.get(
								'select-current-date'
							)}`,
							buttonNextMonth: `${Liferay.Language.get(
								'select-next-month'
							)}`,
							buttonPreviousMonth: `${Liferay.Language.get(
								'select-previous-month'
							)}`,
							dialog: `${Liferay.Language.get('select-date')}`,
						}}
						dateFormat={clayFormat}
						dir={dir}
						disabled={readOnly}
						expanded={expanded}
						firstDayOfWeek={firstDayOfWeek}
						id={name}
						months={months}
						onBlur={handleBlur}
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
