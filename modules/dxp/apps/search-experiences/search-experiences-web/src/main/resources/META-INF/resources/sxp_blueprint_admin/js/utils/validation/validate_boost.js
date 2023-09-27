/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ERROR_MESSAGES} from '../errorMessages';
import {INPUT_TYPES} from '../types/inputTypes';

export default function validateBoost(configValue, type) {
	if (configValue === null) {
		return;
	}

	if (type === INPUT_TYPES.FIELD_MAPPING && configValue.boost < 0) {
		return ERROR_MESSAGES.NEGATIVE_BOOST;
	}

	if (
		type === INPUT_TYPES.FIELD_MAPPING_LIST &&
		configValue.some(({boost}) => boost < 0)
	) {
		return ERROR_MESSAGES.NEGATIVE_BOOST;
	}
}
