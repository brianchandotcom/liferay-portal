/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Analytics} from '../types';
import {
	VALIDATION_PROPERTIES_MAXIMUM_LENGTH,
	VALIDATION_PROPERTY_NAME_MAXIMUM_LENGTH,
	VALIDATION_PROPERTY_VALUE_MAXIMUM_LENGTH,
} from './constants';

export const DXP_APPLICATION_IDS = [
	Analytics.ApplicationId.Blog,
	Analytics.ApplicationId.Custom,
	Analytics.ApplicationId.Document,
	Analytics.ApplicationId.Form,
	Analytics.ApplicationId.Page,
	Analytics.ApplicationId.WebContent,
];

const _validate = (validators: ((value?: any) => string)[]) => (value: any) =>
	validators
		.map((validator) => {
			if (typeof validator === 'function') {
				return validator(value);
			}
		})
		.filter(Boolean);

const validateAttributeType = (attributeValue: Analytics.EventProps) => {
	let error = '';

	const valid = ['string', 'number', 'boolean'].includes(
		typeof attributeValue
	);

	if (!valid) {
		error = 'Attribute must be a String, Number, or Boolean.';
	}

	return error;
};

const validateEmptyString = (labelField: string) => (str: string) => {
	let error = '';

	if (!String(str).length) {
		error = `${labelField} is required.`;
	}

	return error;
};

const validateIsString = (labelField: string) => (val: any) => {
	let error = '';

	if (typeof val !== 'string') {
		error = `${labelField} must be a string.`;
	}

	return error;
};

const validateMaxLength =
	(maxAllowed = VALIDATION_PROPERTY_NAME_MAXIMUM_LENGTH) =>
	(str: string) => {
		let error = '';

		if (String(str).length > maxAllowed) {
			error = `${str} exceeds maximum length of ${maxAllowed}`;
		}

		return error;
	};

const validatePropsLength =
	(maxAllowed = VALIDATION_PROPERTIES_MAXIMUM_LENGTH) =>
	({
		eventId,
		eventProps = {},
	}: {
		eventId: string;
		eventProps?: Analytics.EventProps;
	}) => {
		let error = '';

		if (Object.keys(eventProps).length > maxAllowed) {
			error = `The Event ${eventId} attributes list exceeds maximum length of ${maxAllowed}`;
		}

		return error;
	};

const _showErrors = (errorsArr: string[]) =>
	errorsArr.forEach((errMsg) => console.error(new Error(errMsg)));

const isValidEvent = ({
	applicationId,
	eventId,
	eventProps,
}: {
	applicationId: Analytics.ApplicationId;
	eventId: Analytics.EventId;
	eventProps?: Analytics.EventProps;
}) => {
	const validationsEventId = _validate([
		validateIsString('eventId'),
		validateEmptyString('eventId'),
		validateMaxLength(),
	]);
	const validationsEventProps = _validate([validatePropsLength()]);
	const validationsKey = _validate([
		validateEmptyString('eventPropKey'),
		validateMaxLength(),
	]);

	let validateValue: any[] = [];

	// Ignore validations by attribute and string max length if applicationId is from DXP

	if (!DXP_APPLICATION_IDS.includes(applicationId)) {
		validateValue = [
			validateAttributeType,
			validateMaxLength(VALIDATION_PROPERTY_VALUE_MAXIMUM_LENGTH),
		];
	}

	const validationsValue = _validate(validateValue);

	let errors: any[] = [];

	errors = errors.concat(validationsEventId(eventId));

	if (eventProps) {
		errors = errors.concat(validationsEventProps({eventId, eventProps}));

		for (const key in eventProps) {
			errors = errors.concat(
				validationsKey(key),
				validationsValue(eventProps[key])
			);
		}
	}

	if (errors.length) {
		_showErrors(errors);

		return false;
	}

	return true;
};

export {
	isValidEvent,
	validateAttributeType,
	validateEmptyString,
	validateMaxLength,
	validatePropsLength,
	validateIsString,
};
