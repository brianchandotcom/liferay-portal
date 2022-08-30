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

import {ASSET_CATEGORY_ID} from './constants';
import {ERROR_MESSAGES} from './errorMessages';
import {INPUT_TYPES} from './inputTypes';
import {sub} from './language';
import {isDefined, isEmpty} from './utils';

export function validateBoost(configValue, type) {
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

export function validateJSON(configValue, type) {
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

export function validateNumberRange(configValue, type, typeOptions) {
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

export function validateRequired(
	configValue,
	type,
	required = true,
	nullable = false,
	name = ''
) {
	if (!required || nullable) {
		return;
	}

	if (isEmpty(configValue, type)) {
		if (name.includes(ASSET_CATEGORY_ID)) {
			return ERROR_MESSAGES.REQUIRED_CATEGORY_SELECTOR;
		}

		return ERROR_MESSAGES.REQUIRED;
	}
}
