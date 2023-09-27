/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
