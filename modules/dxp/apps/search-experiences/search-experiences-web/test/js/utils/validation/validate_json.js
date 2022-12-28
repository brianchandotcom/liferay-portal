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
import validateJSON from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/validation/validate_json';

describe('validateJSON', () => {
	it('returns error message for invalid json', () => {
		expect(validateJSON('{test}', INPUT_TYPES.JSON)).toEqual(
			ERROR_MESSAGES.INVALID_JSON
		);
	});

	it('returns undefined for non-json', () => {
		expect(validateJSON('{}', INPUT_TYPES.TEXT)).toBeUndefined();
	});

	it('returns undefined for valid json', () => {
		expect(validateJSON('{}', INPUT_TYPES.JSON)).toBeUndefined();
	});
});
