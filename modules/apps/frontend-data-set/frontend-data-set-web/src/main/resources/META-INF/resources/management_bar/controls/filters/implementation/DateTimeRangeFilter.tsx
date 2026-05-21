/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDatePicker from '@clayui/date-picker';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import {format as formatDate, isValid, parse as parseDate} from 'date-fns';
import {dateUtils} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import {EEntityFieldType} from '../utils/types';

import type {
	FilterImplementation,
	FilterImplementationArgs,
	IOdataStringArgs,
	ISelectedItemsLabelArgs,
} from '../Filter';

type Bound = Partial<DateParts> | 'now';

export interface DateTimeRangeFilterImplementationArgs
	extends FilterImplementationArgs<SelectedData> {
	entityFieldType: EEntityFieldType;
	max?: Bound;
	min?: Bound;
	placeholder?: string;
}

interface DateParts {
	day: number;
	hour: number;
	minute: number;
	month: number;
	offset?: string;
	year: number;
}

interface SelectedData {
	from: Partial<DateParts> | null;
	to: Partial<DateParts> | null;
}

const pad2 = (value: number) => String(value).padStart(2, '0');

function normalizeDateParts(
	dateParts: Partial<DateParts> | null | undefined
): DateParts | null {
	if (!dateParts) {
		return null;
	}

	return {
		day: dateParts.day ?? 0,
		hour: dateParts.hour ?? 0,
		minute: dateParts.minute ?? 0,
		month: dateParts.month ?? 0,
		offset: dateParts.offset,
		year: dateParts.year ?? 0,
	};
}

interface DateConfig {
	clayFormat: string;
	dateFormat: string;
	placeholder: string;
	use12Hours: boolean;
}

// Reference date used to probe the locale's date layout via
// Intl.DateTimeFormat.formatToParts(). Values are unambiguous (day 22,
// month 11, year 2024) so each part can be mapped to its date-fns token.

const REFERENCE_DATE = new Date(2024, 10, 22);

export function getDateConfig(dateTime: boolean, locale?: string): DateConfig {
	const resolvedLocale = locale ?? Liferay.ThemeDisplay.getBCP47LanguageId();

	const parts = new Intl.DateTimeFormat(resolvedLocale, {
		day: '2-digit',
		month: '2-digit',
		year: 'numeric',
	}).formatToParts(REFERENCE_DATE);

	let dateFormat = '';

	for (const part of parts) {
		if (part.type === 'day') {
			dateFormat += 'dd';
		}
		else if (part.type === 'month') {
			dateFormat += 'MM';
		}
		else if (part.type === 'year') {
			dateFormat += 'yyyy';
		}
		else if (part.type === 'literal') {
			dateFormat += part.value;
		}
	}

	let clayFormat = dateFormat;
	let use12Hours = false;

	if (dateTime) {
		use12Hours =
			new Intl.DateTimeFormat(resolvedLocale, {
				hour: 'numeric',
			}).resolvedOptions().hour12 === true;

		clayFormat = use12Hours
			? `${dateFormat} hh:mm aa`
			: `${dateFormat} HH:mm`;
	}

	const placeholder = clayFormat.toLowerCase();

	return {clayFormat, dateFormat, placeholder, use12Hours};
}

export function formatDatePartsForClay(
	dateParts: DateParts,
	clayFormat: string
): string {
	const date = new Date(
		dateParts.year,
		dateParts.month - 1,
		dateParts.day,
		dateParts.hour,
		dateParts.minute
	);

	return formatDate(date, clayFormat);
}

export function parseClayValue(
	value: string | undefined,
	clayFormat: string
): DateParts | null {
	if (!value) {
		return null;
	}

	const parsed = parseDate(value, clayFormat, new Date(2000, 0, 1));

	if (!isValid(parsed) || formatDate(parsed, clayFormat) !== value) {
		return null;
	}

	return {
		day: parsed.getDate(),
		hour: parsed.getHours(),
		minute: parsed.getMinutes(),
		month: parsed.getMonth() + 1,
		year: parsed.getFullYear(),
	};
}

function parseGmtOffset(value: string | undefined): string {
	if (!value) {
		return '+00:00';
	}

	const match = /^GMT([+-])(\d{1,2})(?::(\d{2}))?$/.exec(value);

	if (!match) {
		return '+00:00';
	}

	return `${match[1]}${match[2].padStart(2, '0')}:${(match[3] || '00').padStart(2, '0')}`;
}

function parseOffsetMinutes(offset: string | undefined): number {
	if (!offset) {
		return 0;
	}

	const match = /^([+-])(\d{2}):(\d{2})$/.exec(offset);

	if (!match) {
		return 0;
	}

	const sign = match[1] === '+' ? 1 : -1;

	return sign * (Number(match[2]) * 60 + Number(match[3]));
}

function extractPartsInTimeZone(date: Date, timeZone: string): DateParts {
	const formatter = new Intl.DateTimeFormat('en-US', {
		day: '2-digit',
		hour: '2-digit',
		hour12: false,
		minute: '2-digit',
		month: '2-digit',
		timeZone,
		timeZoneName: 'longOffset',
		year: 'numeric',
	});

	const dateParts: Record<string, string> = {};

	for (const part of formatter.formatToParts(date)) {
		if (part.type !== 'literal') {
			dateParts[part.type] = part.value;
		}
	}

	return {
		day: Number(dateParts.day),
		hour: dateParts.hour === '24' ? 0 : Number(dateParts.hour),
		minute: Number(dateParts.minute),
		month: Number(dateParts.month),
		offset: parseGmtOffset(dateParts.timeZoneName),
		year: Number(dateParts.year),
	};
}

function nowInTimeZone(timeZone: string): DateParts {
	return extractPartsInTimeZone(new Date(), timeZone);
}

function toViewerWallClock(dateParts: DateParts): DateParts {
	if (!dateParts.offset) {
		return dateParts;
	}

	const offsetMinutes = parseOffsetMinutes(dateParts.offset);

	const instantMs = datePartsToInstantMs(dateParts) - offsetMinutes * 60_000;

	return extractPartsInTimeZone(
		new Date(instantMs),
		Liferay.ThemeDisplay.getTimeZone()
	);
}

function toViewerDateParts(
	dateParts: Partial<DateParts> | null | undefined
): DateParts | null {
	const normalized = normalizeDateParts(dateParts);

	return normalized ? toViewerWallClock(normalized) : null;
}

function resolveBound(bound?: Bound): DateParts | undefined {
	if (!bound) {
		return undefined;
	}

	if (bound === 'now') {
		return nowInTimeZone(Liferay.ThemeDisplay.getTimeZone());
	}

	const dateParts = normalizeDateParts(bound);

	if (!dateParts || dateParts.year === 0) {
		return undefined;
	}

	return dateParts;
}

function datePartsToInstantMs(dateParts: DateParts): number {
	return Date.UTC(
		dateParts.year,
		dateParts.month - 1,
		dateParts.day,
		dateParts.hour,
		dateParts.minute
	);
}

export function isWithinBounds(
	dateParts: DateParts | null,
	min: Bound | undefined,
	max: Bound | undefined
): boolean {
	if (!dateParts) {
		return true;
	}

	const datePartsMs = datePartsToInstantMs(dateParts);

	const resolvedMin = resolveBound(min);

	if (resolvedMin && datePartsMs < datePartsToInstantMs(resolvedMin)) {
		return false;
	}

	const resolvedMax = resolveBound(max);

	if (resolvedMax && datePartsMs > datePartsToInstantMs(resolvedMax)) {
		return false;
	}

	return true;
}

function getTimeZoneOffset(timeZone: string, atDate: Date): string {
	try {
		const formatter = new Intl.DateTimeFormat('en-US', {
			timeZone,
			timeZoneName: 'longOffset',
		});

		const part = formatter
			.formatToParts(atDate)
			.find((p) => p.type === 'timeZoneName');

		return parseGmtOffset(part?.value);
	}
	catch {

		// Intl.DateTimeFormat may throw for invalid timezones — fall through.

	}

	return '+00:00';
}

function computeOffsetForParts(dateParts: DateParts): string {
	return getTimeZoneOffset(
		Liferay.ThemeDisplay.getTimeZone(),
		new Date(datePartsToInstantMs(dateParts))
	);
}

function partsToOdataDateTime(dateParts: DateParts): string {
	const offset = dateParts.offset || computeOffsetForParts(dateParts);

	const offsetMatch = /^([+-])(\d{2}):(\d{2})$/.exec(offset);

	let offsetMinutes = 0;

	if (offsetMatch) {
		const sign = offsetMatch[1] === '+' ? 1 : -1;

		offsetMinutes =
			sign * (Number(offsetMatch[2]) * 60 + Number(offsetMatch[3]));
	}

	const utcDate = new Date(
		datePartsToInstantMs(dateParts) - offsetMinutes * 60_000
	);

	return `${utcDate.getUTCFullYear()}-${pad2(
		utcDate.getUTCMonth() + 1
	)}-${pad2(utcDate.getUTCDate())}T${pad2(utcDate.getUTCHours())}:${pad2(
		utcDate.getUTCMinutes()
	)}:00Z`;
}

function dateOnlyEdgeIso(dateParts: DateParts, edge: 'from' | 'to'): string {
	const isFrom = edge === 'from';

	const utc = new Date(
		Date.UTC(
			dateParts.year,
			dateParts.month - 1,
			dateParts.day,
			isFrom ? 0 : 23,
			isFrom ? 0 : 59,
			isFrom ? 0 : 59,
			isFrom ? 0 : 999
		)
	);

	return utc.toISOString();
}

function buildOdataString(
	{entityFieldType, id, selectedData}: IOdataStringArgs,
	dateTime: boolean
): string {
	const {from: rawFrom, to: rawTo} = (selectedData ||
		{}) as unknown as SelectedData;
	const from = normalizeDateParts(rawFrom);
	const to = normalizeDateParts(rawTo);

	const edgeToOdata = (dateParts: DateParts, edge: 'from' | 'to'): string => {
		if (dateTime) {
			return partsToOdataDateTime(dateParts);
		}

		const iso = dateOnlyEdgeIso(dateParts, edge);

		if (entityFieldType === EEntityFieldType.DATE) {
			return iso.split('T')[0];
		}

		return iso;
	};

	const fromOdataString = from && edgeToOdata(from, 'from');
	const toOdataString = to && edgeToOdata(to, 'to');

	if (from && to) {
		return `${id} ge ${fromOdataString}) and (${id} le ${toOdataString}`;
	}

	if (from) {
		return `${id} ge ${fromOdataString}`;
	}

	if (to) {
		return `${id} le ${toOdataString}`;
	}

	return '';
}

function buildSelectedItemsLabel(
	{selectedData}: ISelectedItemsLabelArgs,
	dateTime: boolean
): string {
	const {from: rawFrom, to: rawTo} = (selectedData ||
		{}) as unknown as SelectedData;
	const from = toViewerDateParts(rawFrom);
	const to = toViewerDateParts(rawTo);

	const {clayFormat} = getDateConfig(dateTime);

	const format = (dateParts: DateParts) =>
		formatDatePartsForClay(dateParts, clayFormat);

	if (from && to) {
		return `${format(from)} - ${format(to)}`;
	}

	if (from) {
		return `${Liferay.Language.get('from')} ${format(from)}`;
	}

	if (to) {
		return `${Liferay.Language.get('to[date-time]')} ${format(to)}`;
	}

	return '';
}

interface DateTimeRangeFilterProps
	extends DateTimeRangeFilterImplementationArgs {
	dateTime: boolean;
	onClose?: () => void;
}

const DateTimeRangeFilter = ({
	dateTime,
	id,
	max,
	min,
	onClose,
	placeholder,
	selectedData,
	setFilter,
}: DateTimeRangeFilterProps) => {
	const dateConfig = useMemo(() => getDateConfig(dateTime), [dateTime]);

	const months = useMemo(() => dateUtils.getMonthsLong(), []);

	const initialFromDateParts = toViewerDateParts(selectedData?.from);
	const initialToDateParts = toViewerDateParts(selectedData?.to);

	const [fromValue, setFromValue] = useState(
		initialFromDateParts
			? formatDatePartsForClay(
					initialFromDateParts,
					dateConfig.clayFormat
				)
			: ''
	);
	const [toValue, setToValue] = useState(
		initialToDateParts
			? formatDatePartsForClay(initialToDateParts, dateConfig.clayFormat)
			: ''
	);

	const fromDateParts = parseClayValue(fromValue, dateConfig.clayFormat);
	const toDateParts = parseClayValue(toValue, dateConfig.clayFormat);

	const fromFormatInvalid = !!fromValue.length && !fromDateParts;
	const toFormatInvalid = !!toValue.length && !toDateParts;

	const hasInvalidInput = fromFormatInvalid || toFormatInvalid;

	const isValidRange =
		!fromDateParts ||
		!toDateParts ||
		datePartsToInstantMs(fromDateParts) <=
			datePartsToInstantMs(toDateParts);

	const fromOutOfBounds = isWithinBounds(fromDateParts, min, max) === false;
	const toOutOfBounds = isWithinBounds(toDateParts, min, max) === false;

	const withinBounds = !fromOutOfBounds && !toOutOfBounds;

	const fromInvalid = fromFormatInvalid || fromOutOfBounds || !isValidRange;
	const toInvalid = toFormatInvalid || toOutOfBounds || !isValidRange;

	const feedbackId = `${id}-feedback`;
	const hasError = hasInvalidInput || !isValidRange || !withinBounds;

	let actionType = 'edit';

	if (selectedData && !fromValue && !toValue) {
		actionType = 'delete';
	}

	if (!selectedData) {
		actionType = 'add';
	}

	const initialFromString = initialFromDateParts
		? formatDatePartsForClay(initialFromDateParts, dateConfig.clayFormat)
		: '';
	const initialToString = initialToDateParts
		? formatDatePartsForClay(initialToDateParts, dateConfig.clayFormat)
		: '';

	const isChanged =
		actionType === 'delete' ||
		fromValue !== initialFromString ||
		toValue !== initialToString;

	const submitDisabled =
		!isChanged || hasInvalidInput || !isValidRange || !withinBounds;

	const yearRange = {
		end: new Date().getFullYear() + 25,
		start: new Date().getFullYear() - 50,
	};

	const fallbackPlaceholder = dateConfig.placeholder;

	const wrapperClassName = dateTime
		? 'fds-date-time-range form-group'
		: 'fds-date-range form-group';

	const toSelectedData = (dateParts: DateParts | null) => {
		if (!dateParts) {
			return null;
		}

		if (!dateTime) {
			return {
				day: dateParts.day,
				month: dateParts.month,
				year: dateParts.year,
			};
		}

		return dateParts;
	};

	return (
		<>
			<ClayDropDown.Caption>
				<div className={wrapperClassName}>
					<ClayForm.Group className="form-group-sm">
						<label htmlFor={`from-${id}`}>
							{Liferay.Language.get('from')}
						</label>

						<ClayDatePicker
							aria-describedby={
								fromInvalid ? feedbackId : undefined
							}
							aria-invalid={fromInvalid || undefined}
							dateFormat={dateConfig.dateFormat}
							firstDayOfWeek={dateUtils.getFirstDayOfWeek()}
							id={`from-${id}`}
							inputName={`from-${id}`}
							months={months}
							onChange={(value: string) => setFromValue(value)}
							placeholder={placeholder || fallbackPlaceholder}
							time={dateTime}
							use12Hours={dateConfig.use12Hours}
							value={fromValue}
							weekdaysShort={dateUtils.getWeekdaysShort()}
							years={yearRange}
						/>
					</ClayForm.Group>

					<ClayForm.Group className="form-group-sm mt-2">
						<label htmlFor={`to-${id}`}>
							{Liferay.Language.get('to[date-time]')}
						</label>

						<ClayDatePicker
							aria-describedby={
								toInvalid ? feedbackId : undefined
							}
							aria-invalid={toInvalid || undefined}
							dateFormat={dateConfig.dateFormat}
							firstDayOfWeek={dateUtils.getFirstDayOfWeek()}
							id={`to-${id}`}
							inputName={`to-${id}`}
							months={months}
							onChange={(value: string) => setToValue(value)}
							placeholder={placeholder || fallbackPlaceholder}
							time={dateTime}
							use12Hours={dateConfig.use12Hours}
							value={toValue}
							weekdaysShort={dateUtils.getWeekdaysShort()}
							years={yearRange}
						/>
					</ClayForm.Group>

					{hasError && (
						<ClayForm.FeedbackGroup
							aria-live="polite"
							id={feedbackId}
							role="alert"
						>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{hasInvalidInput
									? Liferay.Language.get('date-is-invalid')
									: !isValidRange
										? Liferay.Language.get(
												'date-range-is-invalid.-from-must-be-before-to'
											)
										: Liferay.Language.get(
												'date-is-out-of-range'
											)}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={submitDisabled}
					onClick={() => {
						if (actionType === 'delete') {
							setFilter({active: false});
						}
						else {
							const withOffset = (
								dateParts: DateParts | null
							): DateParts | null =>
								dateParts && {
									...dateParts,
									offset: computeOffsetForParts(dateParts),
								};

							setFilter({
								active: true,
								selectedData: {
									from: toSelectedData(
										withOffset(fromDateParts)
									),
									to: toSelectedData(withOffset(toDateParts)),
								},
							});
						}

						if (onClose) {
							onClose();
						}
					}}
					small
				>
					{actionType === 'add' && Liferay.Language.get('add-filter')}

					{actionType === 'edit' &&
						Liferay.Language.get('show-results')}

					{actionType === 'delete' &&
						Liferay.Language.get('delete-filter')}
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
};

function filterImplementation({
	dateTime,
}: {
	dateTime: boolean;
}): FilterImplementation<DateTimeRangeFilterImplementationArgs> {
	return {
		Component: (args: DateTimeRangeFilterImplementationArgs) => (
			<DateTimeRangeFilter {...args} dateTime={dateTime} />
		),
		getOdataString: (args: IOdataStringArgs) =>
			buildOdataString(args, dateTime),
		getSelectedItemsLabel: (args: ISelectedItemsLabelArgs) =>
			buildSelectedItemsLabel(args, dateTime),
	};
}

export const dateRangeFilterImplementation = filterImplementation({
	dateTime: false,
});
export const dateTimeRangeFilterImplementation = filterImplementation({
	dateTime: true,
});
