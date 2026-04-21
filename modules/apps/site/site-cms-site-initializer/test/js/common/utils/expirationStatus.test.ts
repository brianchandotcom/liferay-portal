/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	EXPIRING_SOON_THRESHOLD_DAYS,
	formatExpirationDate,
	formatExpirationDateLong,
	isExpiringSoon,
} from '../../../../src/main/resources/META-INF/resources/js/common/utils/expirationStatus';

const NOW = new Date('2026-04-21T10:00:00Z');
const MS_PER_DAY = 24 * 60 * 60 * 1000;

describe('expirationStatus', () => {
	describe('isExpiringSoon', () => {
		beforeAll(() => {
			jest.useFakeTimers();
			jest.setSystemTime(NOW);
		});

		afterAll(() => {
			jest.useRealTimers();
		});

		it('returns false when the status is not approved', () => {
			const threeDays = new Date(
				NOW.getTime() + 3 * MS_PER_DAY
			).toISOString();

			expect(isExpiringSoon('draft', threeDays)).toBe(false);
			expect(isExpiringSoon('pending', threeDays)).toBe(false);
			expect(isExpiringSoon('expired', threeDays)).toBe(false);
			expect(isExpiringSoon('scheduled', threeDays)).toBe(false);
		});

		it('returns false when the expiration date is missing', () => {
			expect(isExpiringSoon('approved', null)).toBe(false);
			expect(isExpiringSoon('approved', undefined)).toBe(false);
			expect(isExpiringSoon('approved', '')).toBe(false);
		});

		it('returns false when the expiration date is not parseable', () => {
			expect(isExpiringSoon('approved', 'not-a-date')).toBe(false);
		});

		it('returns false when the expiration date is beyond the threshold', () => {
			const beyond = new Date(
				NOW.getTime() + (EXPIRING_SOON_THRESHOLD_DAYS + 1) * MS_PER_DAY
			).toISOString();

			expect(isExpiringSoon('approved', beyond)).toBe(false);
		});

		it('keeps returning true for approved items whose expiration date has already passed so the badge remains visible until the scheduler flips the status to expired', () => {
			const past = new Date(NOW.getTime() - MS_PER_DAY).toISOString();

			expect(isExpiringSoon('approved', past)).toBe(true);
		});

		it('returns true when the expiration date is within the threshold', () => {
			const within = new Date(
				NOW.getTime() + 3 * MS_PER_DAY
			).toISOString();

			expect(isExpiringSoon('approved', within)).toBe(true);
		});

		it('returns true at the edge of the threshold', () => {
			const edge = new Date(
				NOW.getTime() + EXPIRING_SOON_THRESHOLD_DAYS * MS_PER_DAY - 1000
			).toISOString();

			expect(isExpiringSoon('approved', edge)).toBe(true);
		});
	});

	describe('formatExpirationDate', () => {
		it('formats an ISO string using the portal locale and time zone', () => {
			const formatted = formatExpirationDate('2026-04-23T12:00:00Z');

			expect(formatted).toMatch(/04\/23\/2026/);
			expect(formatted).toMatch(/12:00/);
			expect(formatted).toMatch(/PM/);
		});

		it('accepts a Date object', () => {
			const formatted = formatExpirationDate(
				new Date('2026-04-23T12:00:00Z')
			);

			expect(formatted).toMatch(/04\/23\/2026/);
			expect(formatted).toMatch(/12:00/);
			expect(formatted).toMatch(/PM/);
		});
	});

	describe('formatExpirationDateLong', () => {
		it('formats an ISO string using a long date style for screen readers', () => {
			const formatted = formatExpirationDateLong(
				'2026-02-11T10:30:00Z'
			);

			expect(formatted).toMatch(/February/);
			expect(formatted).toMatch(/11/);
			expect(formatted).toMatch(/2026/);
			expect(formatted).toMatch(/10:30/);
			expect(formatted).toMatch(/AM/);
		});
	});
});
