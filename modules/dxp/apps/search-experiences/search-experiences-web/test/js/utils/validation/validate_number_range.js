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
