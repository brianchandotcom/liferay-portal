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

const hasErrors = errors => {
	return Object.keys(errors).some(key => errors[key]);
};

const validateDuration = (days, hours) => {
	if (!days && !hours) {
		return Liferay.Language.get('a-duration-time-is-required');
	}

	return '';
};

const validateHours = hours => {
	const hoursRegex = /([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?/;

	if (hours && hours.trim().length && hours.match(hoursRegex)) {
		return '';
	}

	return Liferay.Language.get('value-must-be-an-hour-below');
};

const validateName = name => {
	if (!name || !name.trim()) {
		return Liferay.Language.get('a-name-is-required');
	}

	return '';
};

const validateNodeKeys = nodeKeys => {
	if (!nodeKeys || !nodeKeys.length) {
		return Liferay.Language.get('at-least-one-parameter-is-required');
	}

	return '';
};

export {
	hasErrors,
	validateDuration,
	validateHours,
	validateName,
	validateNodeKeys,
};
