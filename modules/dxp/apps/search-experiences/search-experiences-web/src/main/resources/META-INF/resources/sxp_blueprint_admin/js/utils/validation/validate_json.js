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

import {ERROR_MESSAGES} from '../errorMessages';
import isDefined from '../functions/is_defined';
import {INPUT_TYPES} from '../types/inputTypes';

export default function validateJSON(configValue, type) {
	if (
		configValue === null ||
		configValue === undefined ||
		!isDefined(configValue) ||
		configValue === ''
	) {
		return;
	}

	if (type !== INPUT_TYPES.JSON) {
		return;
	}

	try {
		JSON.parse(configValue);
	}
	catch {
		return ERROR_MESSAGES.INVALID_JSON;
	}
}
