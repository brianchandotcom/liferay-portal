/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getLocaleDateFormat,
	getTimePlaceholder,
	is12HourLocale,
	to12HourTime,
	to24HourTime,
	toCanonicalTime,
	toDisplayDateTime,
	toStorageDateTime,
	toWallClockDateTime,
	toZonedDate,
} from '../../../../src/main/resources/META-INF/resources/revamp/js/utils/dateTime';

describe('getLocaleDateFormat', () => {
	it('follows the order the locale writes a date in', () => {
		expect(getLocaleDateFormat('en-US')).toBe('MM/dd/yyyy');
		expect(getLocaleDateFormat('en-GB')).toBe('dd/MM/yyyy');
		expect(getLocaleDateFormat('de-DE')).toBe('dd.MM.yyyy');
		expect(getLocaleDateFormat('ja-JP')).toBe('yyyy/MM/dd');
	});

	it('keeps the literals a locale puts between the fields', () => {
		expect(getLocaleDateFormat('hu-HU')).toBe('yyyy. MM. dd.');
		expect(getLocaleDateFormat('ko-KR')).toBe('yyyy. MM. dd.');
	});

	it('falls back to the portal locale', () => {
		expect(getLocaleDateFormat()).toBe('MM/dd/yyyy');
	});
});

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

describe('toDisplayDateTime', () => {
	it('writes the stored date in the display order', () => {
		expect(toDisplayDateTime('2026-09-26 17:00', 'MM/dd/yyyy', true)).toBe(
			'09/26/2026 05:00 PM'
		);
		expect(toDisplayDateTime('2026-09-26 17:00', 'dd.MM.yyyy', false)).toBe(
			'26.09.2026 17:00'
		);
	});

	it('writes a date whose display order holds spaces', () => {
		expect(
			toDisplayDateTime('2026-09-26 17:00', 'yyyy. MM. dd.', false)
		).toBe('2026. 09. 26. 17:00');
	});

	it('keeps the unset time a calendar pick leaves behind', () => {
		expect(toDisplayDateTime('2026-09-26 --:--', 'MM/dd/yyyy', false)).toBe(
			'09/26/2026 --:--'
		);
	});

	it('carries a date with no time at all', () => {
		expect(toDisplayDateTime('2026-09-26', 'dd.MM.yyyy', false)).toBe(
			'26.09.2026'
		);
	});

	it('passes a half-typed or unreadable date through untouched', () => {
		expect(toDisplayDateTime('2026-09', 'dd.MM.yyyy', false)).toBe(
			'2026-09'
		);
		expect(toDisplayDateTime('not a date', 'dd.MM.yyyy', false)).toBe(
			'not a date'
		);
		expect(toDisplayDateTime('', 'dd.MM.yyyy', false)).toBe('');
	});
});

describe('toStorageDateTime', () => {
	it('reads the display order back into the stored order', () => {
		expect(
			toStorageDateTime('09/26/2026 05:00 PM', 'MM/dd/yyyy', true)
		).toBe('2026-09-26 17:00');
		expect(toStorageDateTime('26.09.2026 17:00', 'dd.MM.yyyy', false)).toBe(
			'2026-09-26 17:00'
		);
	});

	it('reads a display order that holds spaces', () => {
		expect(
			toStorageDateTime('2026. 09. 26. 17:00', 'yyyy. MM. dd.', false)
		).toBe('2026-09-26 17:00');
	});

	it('refuses a date the display order cannot account for', () => {
		expect(toStorageDateTime('13/45/2026 17:00', 'MM/dd/yyyy', true)).toBe(
			'13/45/2026 17:00'
		);
	});

	it('reads back a date typed in a locale whose order holds invisible marks', () => {
		expect(
			toStorageDateTime(
				'22/11/2024 17:00',
				getLocaleDateFormat('ar'),
				false
			)
		).toBe('2024-11-22 17:00');
	});

	it('reads back a date typed in a locale whose order holds a trailing word', () => {
		expect(
			toStorageDateTime(
				'22.11.2024 17:00',
				getLocaleDateFormat('bg-BG'),
				false
			)
		).toBe('2024-11-22 17:00');
	});

	it('round-trips every display order it is given', () => {
		const dateFormats = [
			'MM/dd/yyyy',
			'dd/MM/yyyy',
			'dd.MM.yyyy',
			'yyyy/MM/dd',
			'yyyy. MM. dd.',
		];

		dateFormats.forEach((dateFormat) => {
			const displayValue = toDisplayDateTime(
				'2026-09-26 17:00',
				dateFormat,
				false
			);

			expect(toStorageDateTime(displayValue, dateFormat, false)).toBe(
				'2026-09-26 17:00'
			);
		});
	});
});

describe('toWallClockDateTime', () => {
	it('formats the instant as a wall clock date time in the time zone', () => {
		expect(
			toWallClockDateTime('2026-07-20T19:30:00.000Z', 'America/New_York')
		).toBe('2026-07-20 15:30');

		expect(toWallClockDateTime('2026-07-20T19:30:00.000Z', 'UTC')).toBe(
			'2026-07-20 19:30'
		);
	});
});

describe('toZonedDate', () => {
	it('interprets the wall clock time in the given time zone', () => {
		expect(
			toZonedDate('2026-07-20 15:30', 'America/New_York').toISOString()
		).toBe('2026-07-20T19:30:00.000Z');

		expect(
			toZonedDate('2026-07-20 15:30', 'Asia/Tokyo').toISOString()
		).toBe('2026-07-20T06:30:00.000Z');

		expect(toZonedDate('2026-07-20 15:30', 'UTC').toISOString()).toBe(
			'2026-07-20T15:30:00.000Z'
		);
	});

	it('resolves a wall clock time in the autumn overlap to the earlier instant', () => {
		expect(
			toZonedDate('2026-10-25 02:30', 'Europe/Madrid').toISOString()
		).toBe('2026-10-25T00:30:00.000Z');

		expect(
			toZonedDate('2026-11-01 01:30', 'America/New_York').toISOString()
		).toBe('2026-11-01T05:30:00.000Z');

		expect(
			toZonedDate('2026-04-05 02:30', 'Pacific/Auckland').toISOString()
		).toBe('2026-04-04T13:30:00.000Z');
	});

	it('resolves the offset in effect at the wall clock time around a DST transition', () => {
		expect(
			toZonedDate('2026-03-29 01:30', 'Europe/Madrid').toISOString()
		).toBe('2026-03-29T00:30:00.000Z');

		expect(
			toZonedDate('2026-10-25 01:30', 'Europe/Madrid').toISOString()
		).toBe('2026-10-24T23:30:00.000Z');

		expect(
			toZonedDate('2026-03-08 03:30', 'America/New_York').toISOString()
		).toBe('2026-03-08T07:30:00.000Z');
	});

	it('shifts a wall clock time inside the spring forward gap past the gap', () => {
		expect(
			toZonedDate('2026-03-29 02:30', 'Europe/Madrid').toISOString()
		).toBe('2026-03-29T01:30:00.000Z');

		expect(
			toZonedDate('2026-03-08 02:30', 'America/New_York').toISOString()
		).toBe('2026-03-08T07:30:00.000Z');

		expect(
			toZonedDate('2026-03-29 00:00', 'Atlantic/Azores').toISOString()
		).toBe('2026-03-29T01:00:00.000Z');

		expect(
			toZonedDate('2026-09-27 02:30', 'Pacific/Auckland').toISOString()
		).toBe('2026-09-26T14:30:00.000Z');
	});
});
