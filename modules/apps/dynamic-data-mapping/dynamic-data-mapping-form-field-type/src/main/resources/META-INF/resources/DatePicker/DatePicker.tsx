/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {datetimeUtils} from '@liferay/object-js-components-web';

// @ts-ignore

import moment from 'moment/min/moment-with-locales';
import React, {useMemo, useState} from 'react';

import FieldBase from '../FieldBase/ReactFieldBase.es';
import DatePickerLocalizedObjectField, {
	DatePickerLocalizedProps,
} from '../localizedObjectFields/DatePickerLocalizedObjectField';
import DatePickerBase from './DatePickerBase';

export enum FirstDayOfWeek {
	Sunday = 0,
	Monday = 1,
	Tuesday = 2,
	Wednesday = 3,
	Thursday = 4,
	Friday = 5,
	Saturday = 6,
}

export type Date = {
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

export type DateMaskParams = {
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

export default function DatePicker({
	defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId(),
	displayErrors,
	errorMessage,
	locale,
	localizable,
	localizedObjectField,
	localizedValue,
	name,
	onChange,
	predefinedValue,
	type,
	valid,
	value,
	...otherProps
}: DatePickerLocalizedProps) {
	const [validField, setValidField] = useState({
		displayErrors,
		errorMessage,
		valid,
	});

	const dateMaskParams: DateMaskParams = useMemo(() => {
		let parameters: DateMaskParams = {};
		parameters = datetimeUtils.generateDateConfigurations({
			defaultLanguageId,
			locale,
			type,
		});

		return parameters;
	}, [defaultLanguageId, locale, type]);

	const date: Date = useMemo(() => {
		let formattedDate = '';
		let year = moment().year();
		const rawDate =
			(localizable
				? localizedValue?.[locale] ??
					localizedValue?.[defaultLanguageId]
				: (value as string)) ??
			predefinedValue ??
			'';

		if (rawDate !== '') {
			const date = moment(rawDate, dateMaskParams.serverFormat, true);
			formattedDate = date
				.locale(locale ?? defaultLanguageId)
				.format(dateMaskParams.momentFormat);
			year = date.year();
		}

		return {
			formattedDate,
			locale,
			name,
			predefinedValue,
			rawDate,
			years: {end: year + 25, start: year - 100},
		};
	}, [
		dateMaskParams,
		defaultLanguageId,
		locale,
		localizable,
		localizedValue,
		name,
		predefinedValue,
		value,
	]);

	const handleDateChange = (date: string) => {
		onChange({}, date);
	};

	const Component =
		Liferay.FeatureFlags['LPD-32050'] && localizedObjectField
			? DatePickerLocalizedObjectField
			: DatePickerBase;

	return (
		<FieldBase
			{...(!localizedObjectField && {localizedValue})}
			{...otherProps}
			displayErrors={validField.displayErrors}
			errorMessage={validField.errorMessage}
			name={name}
			type={type}
			valid={validField.valid}
		>
			<Component
				{...otherProps}
				date={date}
				dateMaskParams={dateMaskParams}
				defaultLanguageId={defaultLanguageId}
				displayErrors={displayErrors}
				errorMessage={errorMessage}
				locale={locale}
				localizable={localizable}
				localizedObjectField={localizedObjectField}
				localizedValue={localizedValue}
				name={name}
				onChange={
					Liferay.FeatureFlags['LPD-32050'] && localizedObjectField
						? onChange
						: handleDateChange
				}
				predefinedValue={predefinedValue}
				setValidField={setValidField}
				type={type}
				valid={valid}
				value={value}
			/>
		</FieldBase>
	);
}
