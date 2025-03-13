/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LIMIT_FAILED_ATTEMPTS} from '../../src/utils/constants';
import {getRetryDelay} from '../../src/utils/delay';

describe('getRetryDelay', () => {
	it('returns a delay as a function of attempt number with a maximum delay', () => {
		expect(getRetryDelay(1, LIMIT_FAILED_ATTEMPTS)).toEqual(1000);
		expect(getRetryDelay(2, LIMIT_FAILED_ATTEMPTS)).toEqual(2000);
		expect(getRetryDelay(3, LIMIT_FAILED_ATTEMPTS)).toEqual(3000);
		expect(getRetryDelay(4, LIMIT_FAILED_ATTEMPTS)).toEqual(5000);
		expect(getRetryDelay(5, LIMIT_FAILED_ATTEMPTS)).toEqual(8000);
		expect(getRetryDelay(6, LIMIT_FAILED_ATTEMPTS)).toEqual(13000);
		expect(getRetryDelay(7, LIMIT_FAILED_ATTEMPTS)).toEqual(21000);
		expect(getRetryDelay(30, LIMIT_FAILED_ATTEMPTS)).toEqual(21000);
	});
});
