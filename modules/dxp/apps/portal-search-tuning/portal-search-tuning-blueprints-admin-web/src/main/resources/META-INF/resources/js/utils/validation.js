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

import {ERROR_MESSAGES} from './errorMessages';
import {INPUT_TYPES} from './inputTypes';
import {isDefined, sub} from './utils';

export const validateBoost = (configValue, name, type) => {
	if (name == 'boost' && configValue < 0) {
		return ERROR_MESSAGES.NEGATIVE_BOOST;
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
};

export const validateJSON = (configValue, type) => {
	if (type !== INPUT_TYPES.JSON) {
		return;
	}

	try {
		JSON.parse(configValue);
	}
	catch {
		return ERROR_MESSAGES.INVALID_JSON;
	}
};

export const validateNumberRange = (configValue, type, typeOptions) => {
	if (![INPUT_TYPES.NUMBER, INPUT_TYPES.SLIDER].includes(type)) {
		return;
	}

	if (isDefined(typeOptions.min) && configValue < typeOptions.min) {
		return sub(ERROR_MESSAGES.GREATER_THAN_X, [typeOptions.min]);
	}

	if (isDefined(typeOptions.max) && configValue > typeOptions.max) {
		return sub(ERROR_MESSAGES.LESS_THAN_X, [typeOptions.max]);
	}
};

export const validateRequired = (configValue, type, required = true) => {
	if (!required) {
		return;
	}

	if (configValue === '') {
		return ERROR_MESSAGES.REQUIRED;
	}

	if (JSON.stringify(configValue) === '[]') {
		return ERROR_MESSAGES.REQUIRED;
	}

	if (type === INPUT_TYPES.FIELD_MAPPING && !configValue.field) {
		return ERROR_MESSAGES.REQUIRED;
	}

	if (
		type === INPUT_TYPES.FIELD_MAPPING_LIST &&
		configValue.every(({field}) => !field)
	) {
		return ERROR_MESSAGES.REQUIRED;
	}
};
