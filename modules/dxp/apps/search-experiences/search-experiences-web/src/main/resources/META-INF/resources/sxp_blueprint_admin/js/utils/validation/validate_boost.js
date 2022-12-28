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
