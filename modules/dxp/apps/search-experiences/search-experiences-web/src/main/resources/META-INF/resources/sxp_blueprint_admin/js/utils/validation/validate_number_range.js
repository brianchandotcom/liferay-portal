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
import sub from '../language/sub';
import {INPUT_TYPES} from '../types/inputTypes';

export default function validateNumberRange(configValue, type, typeOptions) {
	if (configValue === null) {
		return;
	}
	if (![INPUT_TYPES.NUMBER, INPUT_TYPES.SLIDER].includes(type)) {
		return;
	}

	if (isDefined(typeOptions.min) && configValue < typeOptions.min) {
		return sub(ERROR_MESSAGES.GREATER_THAN_X, [typeOptions.min]);
	}

	if (isDefined(typeOptions.max) && configValue > typeOptions.max) {
		return sub(ERROR_MESSAGES.LESS_THAN_X, [typeOptions.max]);
	}
}
