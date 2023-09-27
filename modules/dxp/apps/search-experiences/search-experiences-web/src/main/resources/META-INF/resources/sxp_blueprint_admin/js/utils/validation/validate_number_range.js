/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
