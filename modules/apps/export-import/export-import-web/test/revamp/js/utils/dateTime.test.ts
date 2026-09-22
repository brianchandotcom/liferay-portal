/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getTimePlaceholder,
	is12HourLocale,
	to12HourTime,
	to24HourTime,
	toCanonicalTime,
} from '../../../../src/main/resources/META-INF/resources/revamp/js/utils/dateTime';

describe('getTimePlaceholder', () => {
	it('names the expected shape for each clock', () => {
		expect(getTimePlaceholder(true)).toBe('HH:MM AM');
		expect(getTimePlaceholder(false)).toBe('HH:MM');
	});
});

describe('is12HourLocale', () => {
	it('accepts the 12-hour clock whenever the locale marks a day period', () => {
		expect(is12HourLocale('en-AU')).toBe(true);
		expect(is12HourLocale('en-US')).toBe(true);
		expect(is12HourLocale('ko-KR')).toBe(true);
		expect(is12HourLocale('es-ES')).toBe(false);
		expect(is12HourLocale('ja-JP')).toBe(false);
	});

	it('matches the clock the locale renders a time on', () => {
		const rendersDayPeriod = (locale: string) =>
			new Intl.DateTimeFormat(locale, {
				hour: 'numeric',
				minute: '2-digit',
				timeZone: 'UTC',
			})
				.formatToParts(new Date(Date.UTC(2026, 0, 1, 15)))
				.some((part) => part.type === 'dayPeriod');

		['en-US', 'es-ES', 'hi-IN', 'ja-JP', 'ko-KR'].forEach((locale) => {
			expect([locale, is12HourLocale(locale)]).toEqual([
				locale,
				rendersDayPeriod(locale),
			]);
		});
	});
});

describe('to12HourTime', () => {
	it('renders a canonical time on the 12-hour clock', () => {
		expect(to12HourTime('00:00')).toBe('12:00 AM');
		expect(to12HourTime('12:00')).toBe('12:00 PM');
		expect(to12HourTime('17:05')).toBe('05:05 PM');
	});

	it('renders the unset sentinel with an unset period', () => {
		expect(to12HourTime('--:--')).toBe('--:-- --');
	});

	it('passes anything else through untouched', () => {
		expect(to12HourTime('7:3')).toBe('7:3');
		expect(to12HourTime('')).toBe('');
	});
});

describe('to24HourTime', () => {
	it('reads midnight and noon from the 12-hour clock', () => {
		expect(to24HourTime('12:00 AM')).toBe('00:00');
		expect(to24HourTime('12:00 PM')).toBe('12:00');
	});

	it('accepts a short hour and a lowercase period', () => {
		expect(to24HourTime('5:00 pm')).toBe('17:00');
	});

	it('reads an hour of zero as the twelve it stands for', () => {
		expect(to24HourTime('0:30 AM')).toBe('00:30');
		expect(to24HourTime('0:30 PM')).toBe('12:30');
	});

	it('passes an hour above twelve through untouched', () => {
		expect(to24HourTime('13:30 PM')).toBe('13:30 PM');
		expect(to24HourTime('23:15 AM')).toBe('23:15 AM');
		expect(to24HourTime('24:00 PM')).toBe('24:00 PM');
	});

	it('leaves a time without a period alone', () => {
		expect(to24HourTime('17:00')).toBe('17:00');
	});

	it('maps the unset 12-hour sentinel back to the unset time', () => {
		expect(to24HourTime('--:-- --')).toBe('--:--');
	});

	it('passes anything else through untouched', () => {
		expect(to24HourTime('junk')).toBe('junk');
	});
});

describe('toCanonicalTime', () => {
	it('pads a single-digit hour on the 24-hour clock', () => {
		expect(toCanonicalTime('8:00', false)).toBe('08:00');
	});

	it('converts and pads on the 12-hour clock', () => {
		expect(toCanonicalTime('8:00 pm', true)).toBe('20:00');
		expect(toCanonicalTime('8:00', true)).toBe('08:00');
		expect(toCanonicalTime('17:00', true)).toBe('17:00');
	});

	it('leaves a 12-hour time alone on the 24-hour clock', () => {
		expect(toCanonicalTime('5:00 PM', false)).toBe('5:00 PM');
	});
});
