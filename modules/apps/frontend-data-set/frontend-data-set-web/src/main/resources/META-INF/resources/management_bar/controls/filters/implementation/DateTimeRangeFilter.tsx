/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDatePicker from '@clayui/date-picker';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
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
		year: dateParts.year ?? 0,
	};
}

function isUserLocale12Hour(): boolean {
	try {
		const locale = Liferay.ThemeDisplay.getBCP47LanguageId();
		const formatter = new Intl.DateTimeFormat(locale, {hour: 'numeric'});

		return formatter.resolvedOptions().hour12 === true;
	}
	catch {
		return false;
	}
}

function formatDatePartsForClay(
	dateParts: DateParts,
	use12Hours: boolean,
	dateTime: boolean
): string {
	const date = `${dateParts.year}-${pad2(dateParts.month)}-${pad2(dateParts.day)}`;

	if (!dateTime) {
		return date;
	}

	if (use12Hours) {
		const period = dateParts.hour >= 12 ? 'PM' : 'AM';
		const hour12 = dateParts.hour % 12 === 0 ? 12 : dateParts.hour % 12;

		return `${date} ${pad2(hour12)}:${pad2(dateParts.minute)} ${period}`;
	}

	return `${date} ${pad2(dateParts.hour)}:${pad2(dateParts.minute)}`;
}

function buildDateParts(
	year: number,
	month: number,
	day: number,
	hour: number,
	minute: number
): DateParts | null {
	const back = new Date(Date.UTC(year, month - 1, day, hour, minute));

	if (
		back.getUTCFullYear() !== year ||
		back.getUTCMonth() !== month - 1 ||
		back.getUTCDate() !== day ||
		back.getUTCHours() !== hour ||
		back.getUTCMinutes() !== minute
	) {
		return null;
	}

	return {day, hour, minute, month, year};
}

export function parseClayValue(
	value: string | undefined,
	use12Hours: boolean,
	dateTime: boolean
): DateParts | null {
	if (!value) {
		return null;
	}

	if (!dateTime) {
		const match = /^(\d{4})-(\d{2})-(\d{2})$/.exec(value);

		if (!match) {
			return null;
		}

		return buildDateParts(
			Number(match[1]),
			Number(match[2]),
			Number(match[3]),
			0,
			0
		);
	}

	if (use12Hours) {
		const match =
			/^(\d{4})-(\d{2})-(\d{2}) (\d{1,2}):(\d{2}) (AM|PM)$/i.exec(value);

		if (!match) {
			return null;
		}

		let hour = Number(match[4]);
		const period = match[6].toUpperCase();

		if (period === 'PM' && hour !== 12) {
			hour += 12;
		}

		if (period === 'AM' && hour === 12) {
			hour = 0;
		}

		return buildDateParts(
			Number(match[1]),
			Number(match[2]),
			Number(match[3]),
			hour,
			Number(match[5])
		);
	}

	const match = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})$/.exec(value);

	if (!match) {
		return null;
	}

	return buildDateParts(
		Number(match[1]),
		Number(match[2]),
		Number(match[3]),
		Number(match[4]),
		Number(match[5])
	);
}

function nowInTimeZone(timeZone: string): DateParts {
	const formatter = new Intl.DateTimeFormat('en-US', {
		day: '2-digit',
		hour: '2-digit',
		hour12: false,
		minute: '2-digit',
		month: '2-digit',
		timeZone,
		year: 'numeric',
	});

	const dateParts: Record<string, string> = {};

	for (const part of formatter.formatToParts(new Date())) {
		if (part.type !== 'literal') {
			dateParts[part.type] = part.value;
		}
	}

	return {
		day: Number(dateParts.day),
		hour: dateParts.hour === '24' ? 0 : Number(dateParts.hour),
		minute: Number(dateParts.minute),
		month: Number(dateParts.month),
		year: Number(dateParts.year),
	};
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

		if (!part) {
			return '+00:00';
		}

		const match = /^GMT([+-])(\d{1,2})(?::(\d{2}))?$/.exec(part.value);

		if (match) {
			const sign = match[1];
			const hours = match[2].padStart(2, '0');
			const minutes = (match[3] || '00').padStart(2, '0');

			return `${sign}${hours}:${minutes}`;
		}
	}
	catch {

		// Intl.DateTimeFormat may throw for invalid timezones — fall through.

	}

	return '+00:00';
}

function zonedDatePartsToOdataDateTime(
	dateParts: DateParts,
	timeZone: string
): string {
	const naiveUTCMs = datePartsToInstantMs(dateParts);

	const offset = getTimeZoneOffset(timeZone, new Date(naiveUTCMs));

	const offsetMatch = /^([+-])(\d{2}):(\d{2})$/.exec(offset);

	let offsetMinutes = 0;

	if (offsetMatch) {
		const sign = offsetMatch[1] === '+' ? 1 : -1;

		offsetMinutes =
			sign * (Number(offsetMatch[2]) * 60 + Number(offsetMatch[3]));
	}

	const utcDate = new Date(naiveUTCMs - offsetMinutes * 60 * 1000);

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
			return zonedDatePartsToOdataDateTime(
				dateParts,
				Liferay.ThemeDisplay.getTimeZone()
			);
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

function prettifyDate(dateParts: DateParts): string {
	const date = new Date(dateParts.year, dateParts.month - 1, dateParts.day);

	return date.toLocaleDateString();
}

function buildSelectedItemsLabel(
	{selectedData}: ISelectedItemsLabelArgs,
	dateTime: boolean
): string {
	const {from: rawFrom, to: rawTo} = (selectedData ||
		{}) as unknown as SelectedData;
	const from = normalizeDateParts(rawFrom);
	const to = normalizeDateParts(rawTo);

	const format = (dateParts: DateParts) => {
		if (!dateTime) {
			return prettifyDate(dateParts);
		}

		return `${dateParts.year}-${pad2(dateParts.month)}-${pad2(dateParts.day)} ${pad2(
			dateParts.hour
		)}:${pad2(dateParts.minute)}`;
	};

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
}

const DateTimeRangeFilter = ({
	dateTime,
	id,
	max,
	min,
	placeholder,
	selectedData,
	setFilter,
}: DateTimeRangeFilterProps) => {
	const use12Hours = useMemo(
		() => (dateTime ? isUserLocale12Hour() : false),
		[dateTime]
	);

	const months = useMemo(() => dateUtils.getMonthsLong(), []);

	const initialFromDateParts = normalizeDateParts(selectedData?.from);
	const initialToDateParts = normalizeDateParts(selectedData?.to);

	const [fromValue, setFromValue] = useState(
		initialFromDateParts
			? formatDatePartsForClay(initialFromDateParts, use12Hours, dateTime)
			: ''
	);
	const [toValue, setToValue] = useState(
		initialToDateParts
			? formatDatePartsForClay(initialToDateParts, use12Hours, dateTime)
			: ''
	);

	const fromDateParts = parseClayValue(fromValue, use12Hours, dateTime);
	const toDateParts = parseClayValue(toValue, use12Hours, dateTime);

	const hasInvalidInput =
		(!!fromValue.length && !fromDateParts) ||
		(!!toValue.length && !toDateParts);

	const isValidRange =
		!fromDateParts ||
		!toDateParts ||
		datePartsToInstantMs(fromDateParts) <=
			datePartsToInstantMs(toDateParts);

	const withinBounds =
		isWithinBounds(fromDateParts, min, max) &&
		isWithinBounds(toDateParts, min, max);

	let actionType = 'edit';

	if (selectedData && !fromValue && !toValue) {
		actionType = 'delete';
	}

	if (!selectedData) {
		actionType = 'add';
	}

	const initialFromString = initialFromDateParts
		? formatDatePartsForClay(initialFromDateParts, use12Hours, dateTime)
		: '';
	const initialToString = initialToDateParts
		? formatDatePartsForClay(initialToDateParts, use12Hours, dateTime)
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

	const fallbackPlaceholder = !dateTime
		? 'yyyy-mm-dd'
		: use12Hours
			? 'yyyy-mm-dd hh:mm aa'
			: 'yyyy-mm-dd hh:mm';

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
							dateFormat="yyyy-MM-dd"
							firstDayOfWeek={dateUtils.getFirstDayOfWeek()}
							inputName={`from-${id}`}
							months={months}
							onChange={(value: string) => setFromValue(value)}
							placeholder={placeholder || fallbackPlaceholder}
							time={dateTime}
							use12Hours={use12Hours}
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
							dateFormat="yyyy-MM-dd"
							firstDayOfWeek={dateUtils.getFirstDayOfWeek()}
							inputName={`to-${id}`}
							months={months}
							onChange={(value: string) => setToValue(value)}
							placeholder={placeholder || fallbackPlaceholder}
							time={dateTime}
							use12Hours={use12Hours}
							value={toValue}
							weekdaysShort={dateUtils.getWeekdaysShort()}
							years={yearRange}
						/>
					</ClayForm.Group>

					{(hasInvalidInput || !isValidRange || !withinBounds) && (
						<ClayForm.FeedbackGroup>
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
							setFilter({
								active: true,
								selectedData: {
									from: toSelectedData(fromDateParts),
									to: toSelectedData(toDateParts),
								},
							});
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
