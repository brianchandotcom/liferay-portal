/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isDefined from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/functions/is_defined';

describe('isDefined', () => {
	it('returns false for undefined', () => {
		expect(isDefined(undefined)).toEqual(false);
	});

	it('returns true for null', () => {
		expect(isDefined(null)).toEqual(true);
	});

	it('returns true for empty string', () => {
		expect(isDefined('')).toEqual(true);
	});

	it('returns true for 0', () => {
		expect(isDefined(0)).toEqual(true);
	});

	it('returns true for []', () => {
		expect(isDefined([])).toEqual(true);
	});

	it('returns true for empty object', () => {
		expect(isDefined({})).toEqual(true);
	});

	it('returns true for object', () => {
		expect(isDefined({test: [1, 2, 3]})).toEqual(true);
	});
});
