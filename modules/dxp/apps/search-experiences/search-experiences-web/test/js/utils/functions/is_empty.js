/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isEmpty from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/functions/is_empty';
import {INPUT_TYPES} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/types/inputTypes';

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
