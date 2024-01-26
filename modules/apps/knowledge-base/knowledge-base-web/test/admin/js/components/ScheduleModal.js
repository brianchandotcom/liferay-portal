/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isValidDate} from '../../../../src/main/resources/META-INF/resources/js/admin/components/ScheduleModal';

describe('isValidDate', () => {
	it('return true for future dates in the current year', () => {
		const currentYear = new Date().getFullYear();
		const validDate = `${currentYear}-12-31 23:59`;
		expect(isValidDate(validDate)).toBe(true);
	});

	it('return true for future dates in the next year', () => {
		const currentYear = new Date().getFullYear();
		const validDate = `${currentYear + 1}-07-20 00:00`;
		expect(isValidDate(validDate)).toBe(true);
	});

	it('return false for dates in the past', () => {
		const pastDate = '2022-01-01 00:00';
		expect(isValidDate(pastDate)).toBe(false);
	});

	it('return false for dates with invalid format', () => {
		const invalidDate = 'invalid-date-format';
		expect(isValidDate(invalidDate)).toBe(false);
	});

	it('return false for future dates not in the current or next year', () => {
		const currentYear = new Date().getFullYear();
		const invalidYear = `${currentYear + 10}-07-20 00:00`;
		expect(isValidDate(invalidYear)).toBe(false);
	});
});
