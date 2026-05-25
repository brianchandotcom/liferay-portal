/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {formatMillisecondsAsSeconds} from '../../../../src/main/resources/META-INF/resources/js/activity_dashboard/utils/formatters';

describe('formatMillisecondsAsSeconds', () => {
	it('converts whole seconds', () => {
		expect(formatMillisecondsAsSeconds(2000)).toBe('2.0s');
	});

	it('formats fractional milliseconds to one decimal', () => {
		expect(formatMillisecondsAsSeconds(2600)).toBe('2.6s');
	});

	it('formats zero as 0.0s', () => {
		expect(formatMillisecondsAsSeconds(0)).toBe('0.0s');
	});
});
