/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ERROR_MESSAGES} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/errorMessages';
import {INPUT_TYPES} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/types/inputTypes';
import validateNumberRange from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/validation/validate_number_range';

describe('validateNumberRange', () => {
	const min = 0;
	const max = 1;

	it('returns error message for number below min', () => {
		expect(
			validateNumberRange(-10, INPUT_TYPES.NUMBER, {max, min})
		).toEqual(ERROR_MESSAGES.GREATER_THAN_X);
	});

	it('returns error message for number above max', () => {
		expect(validateNumberRange(10, INPUT_TYPES.NUMBER, {max, min})).toEqual(
			ERROR_MESSAGES.LESS_THAN_X
		);
	});

	it('returns undefined for non-number', () => {
		expect(validateNumberRange('test', INPUT_TYPES.TEXT)).toBeUndefined();
	});

	it('returns undefined for number in range', () => {
		expect(
			validateNumberRange(0.5, INPUT_TYPES.NUMBER, {max, min})
		).toBeUndefined();
	});

	it('returns undefined for number with undefined range', () => {
		expect(validateNumberRange(10, INPUT_TYPES.NUMBER, {})).toBeUndefined();
	});
});
