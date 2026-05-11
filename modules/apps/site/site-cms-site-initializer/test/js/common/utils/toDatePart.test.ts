/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toDatePart from '../../../../src/main/resources/META-INF/resources/js/common/utils/toDatePart';

function setTimeZone(timeZone: string) {
	globalThis.Liferay.ThemeDisplay.getTimeZone = () => timeZone;
}

describe('toDatePart', () => {
	it('returns wall-clock parts in the configured time zone', () => {
		setTimeZone('America/Los_Angeles');

		expect(toDatePart(new Date('2026-05-11T22:30:00Z'))).toEqual({
			day: 11,
			hour: 15,
			minute: 30,
			month: 5,
			offset: '-07:00',
			year: 2026,
		});
	});

	it('honors a positive offset', () => {
		setTimeZone('Asia/Tokyo');

		expect(toDatePart(new Date('2026-05-11T22:30:00Z'))).toEqual({
			day: 12,
			hour: 7,
			minute: 30,
			month: 5,
			offset: '+09:00',
			year: 2026,
		});
	});

	it('handles DST (Europe/London in summer)', () => {
		setTimeZone('Europe/London');

		expect(toDatePart(new Date('2026-05-11T22:30:00Z'))).toEqual({
			day: 11,
			hour: 23,
			minute: 30,
			month: 5,
			offset: '+01:00',
			year: 2026,
		});
	});

	it('normalizes midnight reported as hour 24', () => {
		setTimeZone('UTC');

		const result = toDatePart(new Date('2026-05-11T00:00:00Z'));

		expect(result.hour).toBe(0);
	});

	it('emits "+00:00" for UTC', () => {
		setTimeZone('UTC');

		expect(toDatePart(new Date('2026-05-11T22:30:00Z')).offset).toBe(
			'+00:00'
		);
	});
});
