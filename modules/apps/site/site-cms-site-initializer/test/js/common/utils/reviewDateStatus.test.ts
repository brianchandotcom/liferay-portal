/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isReviewDateOverdue} from '../../../../src/main/resources/META-INF/resources/js/common/utils/reviewDateStatus';

const NOW = new Date('2026-04-21T10:00:00Z');

describe('reviewDateStatus', () => {
	describe('isReviewDateOverdue', () => {
		beforeAll(() => {
			jest.useFakeTimers();
			jest.setSystemTime(NOW);
		});

		afterAll(() => {
			jest.useRealTimers();
		});

		it('returns false when the review date is missing', () => {
			expect(isReviewDateOverdue(undefined)).toBe(false);
			expect(isReviewDateOverdue('')).toBe(false);
		});

		it('returns false when the review date is not parseable', () => {
			expect(isReviewDateOverdue('not-a-date')).toBe(false);
		});

		it('returns true when the review date is in the past', () => {
			expect(isReviewDateOverdue('2026-04-20T10:00:00Z')).toBe(true);
		});

		it('returns true at the start of today in UTC', () => {
			expect(isReviewDateOverdue('2026-04-21T00:00:00Z')).toBe(true);
		});

		it('returns true at the end of today in UTC', () => {
			expect(isReviewDateOverdue('2026-04-21T23:59:59Z')).toBe(true);
		});

		it('returns false at the start of tomorrow in UTC', () => {
			expect(isReviewDateOverdue('2026-04-22T00:00:00Z')).toBe(false);
		});

		it('returns false when the review date is further in the future', () => {
			expect(isReviewDateOverdue('2026-05-01T10:00:00Z')).toBe(false);
		});

		it('accepts a Date object', () => {
			expect(isReviewDateOverdue(new Date('2026-04-20T10:00:00Z'))).toBe(
				true
			);
			expect(isReviewDateOverdue(new Date('2026-04-22T00:00:00Z'))).toBe(
				false
			);
		});
	});
});
