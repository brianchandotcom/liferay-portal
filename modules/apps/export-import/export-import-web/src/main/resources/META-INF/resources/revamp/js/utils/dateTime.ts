/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const TIME_PATTERN = /^\d{2}:\d{2}$/;

const TIME_PLACEHOLDER = 'HH:MM';

const TIME_PLACEHOLDER_12_HOUR = 'hh:mm AM';

export const UNSET_TIME = '--:--';

const UNSET_TIME_12_HOUR = '--:-- --';

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

export function toTimeParts(time: string): {hour: number; minute: number} {
	const [hour, minute] = time.split(':').map(Number);

	return {hour, minute};
}

function padHour(time: string): string {
	const match = time.match(/^(\d):(\d{2})$/);

	if (!match) {
		return time;
	}

	const [, hourString, minuteString] = match;

	return `0${hourString}:${minuteString}`;
}
