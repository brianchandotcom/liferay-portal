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

type DateTimeBound = DateTime | 'now';

export interface DateTimeRangeFilterImplementationArgs
	extends FilterImplementationArgs<SelectedData> {
	entityFieldType: EEntityFieldType;
	max?: DateTimeBound;
	min?: DateTimeBound;
	placeholder?: string;
}

interface DateTime {
	day: number;
	hour: number;
	minute: number;
	month: number;
	year: number;
}

interface SelectedData {
	from: DateTime | null;
	to: DateTime | null;
}

const pad2 = (value: number) => String(value).padStart(2, '0');

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

function formatPartsForClay(dateTime: DateTime, use12Hours: boolean): string {
	if (use12Hours) {
		const period = dateTime.hour >= 12 ? 'PM' : 'AM';
		const hour12 = dateTime.hour % 12 === 0 ? 12 : dateTime.hour % 12;

		return `${dateTime.year}-${pad2(dateTime.month)}-${pad2(
			dateTime.day
		)} ${pad2(hour12)}:${pad2(dateTime.minute)} ${period}`;
	}

	return `${dateTime.year}-${pad2(dateTime.month)}-${pad2(
		dateTime.day
	)} ${pad2(dateTime.hour)}:${pad2(dateTime.minute)}`;
}

function parseClayDateTime(
	value: string | undefined,
	use12Hours: boolean
): DateTime | null {
	if (!value) {
		return null;
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

		return {
			day: Number(match[3]),
			hour,
			minute: Number(match[5]),
			month: Number(match[2]),
			year: Number(match[1]),
		};
	}

	const match = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})$/.exec(value);

	if (!match) {
		return null;
	}

	return {
		day: Number(match[3]),
		hour: Number(match[4]),
		minute: Number(match[5]),
		month: Number(match[2]),
		year: Number(match[1]),
	};
}

function nowInTimeZone(timeZone: string): DateTime {
	const formatter = new Intl.DateTimeFormat('en-US', {
		day: '2-digit',
		hour: '2-digit',
		hour12: false,
		minute: '2-digit',
		month: '2-digit',
		timeZone,
		year: 'numeric',
	});

	const parts: Record<string, string> = {};

	for (const part of formatter.formatToParts(new Date())) {
		if (part.type !== 'literal') {
			parts[part.type] = part.value;
		}
	}

	return {
		day: Number(parts.day),
		hour: parts.hour === '24' ? 0 : Number(parts.hour),
		minute: Number(parts.minute),
		month: Number(parts.month),
		year: Number(parts.year),
	};
}

function resolveBound(bound?: DateTimeBound): DateTime | undefined {
	if (!bound) {
		return undefined;
	}

	if (bound === 'now') {
		return nowInTimeZone(Liferay.ThemeDisplay.getTimeZone());
	}

	return bound;
}

function partsToInstantMs(parts: DateTime): number {
	return Date.UTC(
		parts.year,
		parts.month - 1,
		parts.day,
		parts.hour,
		parts.minute
	);
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

function zonedPartsToOdataDateTime(parts: DateTime, timeZone: string): string {
	const asUTC = new Date(partsToInstantMs(parts));

	const offset = getTimeZoneOffset(timeZone, asUTC);

	return `${parts.year}-${pad2(parts.month)}-${pad2(parts.day)}T${pad2(
		parts.hour
	)}:${pad2(parts.minute)}:00${offset}`;
}

function getSelectedItemsLabel({
	selectedData,
}: ISelectedItemsLabelArgs): string {
	const {from, to} = selectedData as unknown as SelectedData;

	const format = (parts: DateTime) =>
		`${parts.year}-${pad2(parts.month)}-${pad2(parts.day)} ${pad2(
			parts.hour
		)}:${pad2(parts.minute)}`;

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

function getOdataString({id, selectedData}: IOdataStringArgs): string {
	const {from, to} = selectedData as unknown as SelectedData;

	const timeZone = Liferay.ThemeDisplay.getTimeZone();

	const fromOdataString = from && zonedPartsToOdataDateTime(from, timeZone);
	const toOdataString = to && zonedPartsToOdataDateTime(to, timeZone);

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

const DateTimeRangeFilter = ({
	id,
	max,
	min,
	placeholder,
	selectedData,
	setFilter,
}: DateTimeRangeFilterImplementationArgs) => {
	const use12Hours = useMemo(() => isUserLocale12Hour(), []);

	const months = useMemo(() => dateUtils.getMonthsLong(), []);

	const [fromValue, setFromValue] = useState(
		selectedData?.from
			? formatPartsForClay(selectedData.from, use12Hours)
			: ''
	);
	const [toValue, setToValue] = useState(
		selectedData?.to ? formatPartsForClay(selectedData.to, use12Hours) : ''
	);

	const fromParts = parseClayDateTime(fromValue, use12Hours);
	const toParts = parseClayDateTime(toValue, use12Hours);

	const resolvedMin = resolveBound(min);
	const resolvedMax = resolveBound(max);

	const isValidRange =
		!fromParts ||
		!toParts ||
		partsToInstantMs(fromParts) <= partsToInstantMs(toParts);

	const isInBounds = (parts: DateTime | null) => {
		if (!parts) {
			return true;
		}

		const partsMs = partsToInstantMs(parts);

		if (resolvedMin && partsMs < partsToInstantMs(resolvedMin)) {
			return false;
		}

		if (resolvedMax && partsMs > partsToInstantMs(resolvedMax)) {
			return false;
		}

		return true;
	};

	const isWithinBounds = isInBounds(fromParts) && isInBounds(toParts);

	let actionType = 'edit';

	if (selectedData && !fromValue && !toValue) {
		actionType = 'delete';
	}

	if (!selectedData) {
		actionType = 'add';
	}

	const initialFromString = selectedData?.from
		? formatPartsForClay(selectedData.from, use12Hours)
		: '';
	const initialToString = selectedData?.to
		? formatPartsForClay(selectedData.to, use12Hours)
		: '';

	const isChanged =
		actionType === 'delete' ||
		fromValue !== initialFromString ||
		toValue !== initialToString;

	const submitDisabled = !isChanged || !isValidRange || !isWithinBounds;

	const yearRange = {
		end: new Date().getFullYear() + 25,
		start: new Date().getFullYear() - 50,
	};

	const fallbackPlaceholder = use12Hours
		? 'yyyy-mm-dd hh:mm aa'
		: 'yyyy-mm-dd hh:mm';

	return (
		<>
			<ClayDropDown.Caption>
				<div className="fds-date-time-range form-group">
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
							time={true}
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
							time={true}
							use12Hours={use12Hours}
							value={toValue}
							weekdaysShort={dateUtils.getWeekdaysShort()}
							years={yearRange}
						/>
					</ClayForm.Group>

					{(!isValidRange || !isWithinBounds) && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{!isValidRange
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
									from: fromParts,
									to: toParts,
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

const filterImplementation: FilterImplementation<DateTimeRangeFilterImplementationArgs> =
	{
		Component: DateTimeRangeFilter,
		getOdataString,
		getSelectedItemsLabel,
	};

export default filterImplementation;
