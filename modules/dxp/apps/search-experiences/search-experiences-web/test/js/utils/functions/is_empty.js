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

import isEmpty from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/functions/is_empty';
import {INPUT_TYPES} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/inputTypes';

describe('isEmpty', () => {
	it('returns true for an empty string', () => {
		expect(isEmpty('')).toEqual(true);
	});

	it('returns true for an empty object', () => {
		expect(isEmpty({})).toEqual(true);
	});

	it('returns true for an empty array', () => {
		expect(isEmpty([])).toEqual(true);
	});

	it('returns false for an object with a property', () => {
		expect(isEmpty({test: 'abc'})).toEqual(false);
	});

	it('returns false for a string with a single character', () => {
		expect(isEmpty('a')).toEqual(false);
	});

	it('returns false for a number', () => {
		expect(isEmpty(0)).toEqual(false);
	});

	it('returns true for an empty fieldMapping', () => {
		expect(isEmpty({field: ''}, INPUT_TYPES.FIELD_MAPPING)).toEqual(true);
	});

	it('returns true for an empty fieldMappingList', () => {
		expect(isEmpty([{field: ''}], INPUT_TYPES.FIELD_MAPPING_LIST)).toEqual(
			true
		);
	});
});
