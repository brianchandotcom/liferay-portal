/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
