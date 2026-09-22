/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const DATE_TIME_PATTERN = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$/;

const OFFSET_SAMPLE_DISTANCE = 36 * 60 * 60 * 1000;

const TIME_PATTERN = /^\d{2}:\d{2}$/;

const TIME_PLACEHOLDER = 'HH:MM';

const TIME_PLACEHOLDER_12_HOUR = 'hh:mm AM';

export const UNSET_TIME = '--:--';

const UNSET_TIME_12_HOUR = '--:-- --';

type DateTimeParts = {
	day: number;
	hour: number;
	minute: number;
	month: number;
	year: number;
};

export function getTimePlaceholder(use12Hours: boolean): string {
	return (
		use12Hours ? TIME_PLACEHOLDER_12_HOUR : TIME_PLACEHOLDER
	).toUpperCase();
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

export function toCanonicalTime(time: string, use12Hours: boolean): string {
	return padHour(use12Hours ? to24HourTime(time) : time);
}

export function toDateTimeParts(dateTime: string): DateTimeParts {
	const [date, time = '00:00'] = dateTime.split(' ');

	const [year, month, day] = date.split('-').map(Number);
	const {hour, minute} = toTimeParts(time);

	return {day, hour, minute, month, year};
}

export function toLocalDate(dateTime: string): Date {
	const {day, hour, minute, month, year} = toDateTimeParts(dateTime);

	return new Date(year, month - 1, day, hour, minute);
}

export function toTimeParts(time: string): {hour: number; minute: number} {
	const [hour, minute] = time.split(':').map(Number);

	return {hour, minute};
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
	} as Intl.DateTimeFormatOptions).formatToParts(new Date(isoDateTime));

	const getPart = (type: string) =>
		dateTimeFormatParts.find(
			(dateTimeFormatPart) => dateTimeFormatPart.type === type
		)?.value ?? '';

	return `${getPart('year')}-${getPart('month')}-${getPart(
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

function toWallClockTime(dateTime: string): number {
	return new Date(`${dateTime.replace(' ', 'T')}:00Z`).getTime();
}
