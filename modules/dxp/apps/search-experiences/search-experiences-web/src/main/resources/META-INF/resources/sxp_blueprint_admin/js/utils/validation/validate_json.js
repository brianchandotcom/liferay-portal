/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
