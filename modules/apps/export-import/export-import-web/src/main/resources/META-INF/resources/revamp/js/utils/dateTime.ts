/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const BIDI_MARK_PATTERN = /[\u061C\u200E\u200F]/g;

const DATE_FIELD_PATTERN = /d+|M+|y+/g;

const DATE_FORMAT_REFERENCE_DATE = new Date(2024, 10, 22);

const DATE_TIME_PATTERN = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$/;

const FIXED_SPACE_PATTERN = /[\u00A0\u2009\u202F]/g;

const OFFSET_SAMPLE_DISTANCE = 36 * 60 * 60 * 1000;

const STORAGE_DATE_FORMAT = 'yyyy-MM-dd';

const TIME_PATTERN = /^\d{2}:\d{2}$/;

const TIME_SUFFIX_PATTERN = /\s(\d{1,2}:\d{2}|--:--)(?:\s(AM|PM|--))?$/i;

export const UNSET_TIME = '--:--';

const UNSET_TIME_12_HOUR = '--:-- --';

type DateTimeParts = {
	day: number;
	hour: number;
	minute: number;
	month: number;
	year: number;
};

export function getLocaleDateFormat(
	locale: string = Liferay.ThemeDisplay.getBCP47LanguageId()
): string {
	return new Intl.DateTimeFormat(locale, {
		day: '2-digit',
		month: '2-digit',
		year: 'numeric',
	})
		.formatToParts(DATE_FORMAT_REFERENCE_DATE)
		.map((part) => {
			if (part.type === 'day') {
				return 'dd';
			}

			if (part.type === 'month') {
				return 'MM';
			}

			if (part.type === 'year') {
				return 'yyyy';
			}

			if (part.type === 'literal') {
				return part.value
					.replace(BIDI_MARK_PATTERN, '')
					.replace(FIXED_SPACE_PATTERN, ' ');
			}

			return '';
		})
		.join('');
}

export function is12HourLocale(locale: string): boolean {
	return (
		new Intl.DateTimeFormat(locale, {
			hour: 'numeric',
		}).resolvedOptions().hour12 === true
	);
}

export function isCompleteDateTime(dateTime: string): boolean {
	if (!DATE_TIME_PATTERN.test(dateTime)) {
		return false;
	}

	const {day, hour, minute, month, year} = toDateTimeParts(dateTime);

	const date = new Date(Date.UTC(year, month - 1, day, hour, minute));

	return (
		date.getUTCDate() === day &&
		date.getUTCFullYear() === year &&
		date.getUTCHours() === hour &&
		date.getUTCMinutes() === minute &&
		date.getUTCMonth() === month - 1
	);
}

export function isCompleteTime(time: string): boolean {
	if (!TIME_PATTERN.test(time)) {
		return false;
	}

	const {hour, minute} = toTimeParts(time);

	return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
}

export function to12HourTime(time: string): string {
	if (time === UNSET_TIME) {
		return UNSET_TIME_12_HOUR;
	}

	const match = time.match(/^(\d{2}):(\d{2})$/);

	if (!match) {
		return time;
	}

	const [, hourString, minuteString] = match;
	const hour = Number(hourString);
	const period = hour < 12 ? 'AM' : 'PM';
	const hour12 = String(hour % 12 || 12).padStart(2, '0');

	return `${hour12}:${minuteString} ${period}`;
}

export function to24HourTime(time: string): string {
	const match = time.match(/^(\d{1,2}):(\d{2}) (AM|PM)$/i);

	if (!match) {
		return time === UNSET_TIME_12_HOUR ? UNSET_TIME : time;
	}

	const [, hourString, minuteString, period] = match;

	const hour12 = Number(hourString);

	if (hour12 > 12) {
		return time;
	}

	let hour = hour12 % 12;

	if (period.toUpperCase() === 'PM') {
		hour += 12;
	}

	return `${String(hour).padStart(2, '0')}:${minuteString}`;
}

export function toDateText(
	date: Date,
	locale: string = Liferay.ThemeDisplay.getBCP47LanguageId()
): string {
	return date.toLocaleDateString(locale, {timeZone: 'UTC'});
}

export function toDateTimeParts(dateTime: string): DateTimeParts {
	const [date, time = '00:00'] = dateTime.split(' ');

	const [year, month, day] = date.split('-').map(Number);
	const {hour, minute} = toTimeParts(time);

	return {day, hour, minute, month, year};
}

export function toDisplayDateTime(
	dateTime: string,
	dateFormat: string,
	use12Hours: boolean
): string {
	const [dateText, timeText] = splitDateTime(dateTime);

	const displayDate = reorderDate(dateText, STORAGE_DATE_FORMAT, dateFormat);

	if (!timeText) {
		return displayDate;
	}

	return `${displayDate} ${use12Hours ? to12HourTime(timeText) : timeText}`;
}

export function toStorageDateTime(
	dateTime: string,
	dateFormat: string,
	use12Hours: boolean
): string {
	const [dateText, timeText] = splitDateTime(dateTime);

	const storageDate = reorderDate(dateText, dateFormat, STORAGE_DATE_FORMAT);

	if (!timeText) {
		return storageDate;
	}

	return `${storageDate} ${toCanonicalTime(timeText, use12Hours)}`;
}

export function toTimeParts(time: string): {hour: number; minute: number} {
	const [hour, minute] = time.split(':').map(Number);

	return {hour, minute};
}

export function toTimeText(
	date: Date,
	locale: string = Liferay.ThemeDisplay.getBCP47LanguageId()
): string {
	return date.toLocaleTimeString(locale, {
		hour: 'numeric',
		minute: '2-digit',
		timeZone: 'UTC',
	});
}

export function toWallClockDate(dateTime: string): Date {
	const {day, hour, minute, month, year} = toDateTimeParts(dateTime);

	return new Date(Date.UTC(year, month - 1, day, hour, minute));
}

export function toWallClockDateTime(
	isoDateTime: string,
	timeZoneId: string
): string {
	const dateTimeFormatParts = new Intl.DateTimeFormat('en-CA', {
		day: '2-digit',
		hour: '2-digit',
		hourCycle: 'h23',
		minute: '2-digit',
		month: '2-digit',
		timeZone: timeZoneId,
		year: 'numeric',
	}).formatToParts(new Date(isoDateTime));

	const getPart = (type: string) =>
		dateTimeFormatParts.find(
			(dateTimeFormatPart) => dateTimeFormatPart.type === type
		)?.value ?? '';

	return `${getPart('year').padStart(4, '0')}-${getPart('month')}-${getPart(
		'day'
	)} ${getPart('hour')}:${getPart('minute')}`;
}

export function toZonedDate(dateTime: string, timeZoneId: string): Date {
	const wallClockTime = toWallClockTime(dateTime);

	const [earlierTime, laterTime] = [
		wallClockTime - OFFSET_SAMPLE_DISTANCE,
		wallClockTime + OFFSET_SAMPLE_DISTANCE,
	].map(
		(sampleTime) =>
			wallClockTime - getTimeZoneOffset(new Date(sampleTime), timeZoneId)
	);

	const times = [earlierTime, laterTime].filter(
		(time) =>
			getTimeZoneOffset(new Date(time), timeZoneId) ===
			wallClockTime - time
	);

	return new Date(times.length ? Math.min(...times) : earlierTime);
}

function getTimeZoneOffset(date: Date, timeZoneId: string): number {
	return (
		toWallClockTime(toWallClockDateTime(date.toISOString(), timeZoneId)) -
		date.getTime()
	);
}

function padHour(time: string): string {
	const match = time.match(/^(\d):(\d{2})$/);

	if (!match) {
		return time;
	}

	const [, hourString, minuteString] = match;

	return `0${hourString}:${minuteString}`;
}

function reorderDate(
	dateText: string,
	fromFormat: string,
	toFormat: string
): string {
	if (!dateText || fromFormat === toFormat) {
		return dateText;
	}

	const date = toOrderedDate(dateText, fromFormat);

	if (!date) {
		return dateText;
	}

	return toFormat
		.replace('dd', String(date.getDate()).padStart(2, '0'))
		.replace('MM', String(date.getMonth() + 1).padStart(2, '0'))
		.replace('yyyy', String(date.getFullYear()));
}

function splitDateTime(dateTime: string): [string, string] {
	const match = dateTime.match(TIME_SUFFIX_PATTERN);

	if (!match || match.index === undefined) {
		return [dateTime, ''];
	}

	return [dateTime.slice(0, match.index), dateTime.slice(match.index + 1)];
}

function toCanonicalTime(time: string, use12Hours: boolean): string {
	return padHour(use12Hours ? to24HourTime(time) : time);
}

function toOrderedDate(dateText: string, dateFormat: string): Date | null {
	const fields = dateFormat.match(DATE_FIELD_PATTERN);
	const numbers = dateText.match(/\d+/g);

	if (fields?.length !== 3 || numbers?.length !== 3) {
		return null;
	}

	if (
		numbers[fields.findIndex((field) => field.startsWith('y'))].length !== 4
	) {
		return null;
	}

	const values: Record<string, number> = {};

	fields.forEach((field, index) => {
		values[field[0]] = Number(numbers[index]);
	});

	const {M: month, d: day, y: year} = values;

	const date = new Date(year, month - 1, day);

	if (
		date.getDate() !== day ||
		date.getFullYear() !== year ||
		date.getMonth() !== month - 1
	) {
		return null;
	}

	return date;
}

function toWallClockTime(dateTime: string): number {
	return new Date(`${dateTime.replace(' ', 'T')}:00Z`).getTime();
}
