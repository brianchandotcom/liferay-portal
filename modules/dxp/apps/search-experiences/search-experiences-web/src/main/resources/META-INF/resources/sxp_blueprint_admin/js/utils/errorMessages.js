/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const DEFAULT_ERROR = Liferay.Language.get(
	'an-unexpected-error-occurred'
);

export const ERROR_MESSAGES = {
	GREATER_THAN_X: Liferay.Language.get(
		'please-enter-a-value-greater-than-or-equal-to-x'
	),
	INVALID_JSON: Liferay.Language.get(
		'unable-to-apply-changes-due-to-invalid-json'
	),
	LESS_THAN_X: Liferay.Language.get(
		'please-enter-a-value-less-than-or-equal-to-x'
	),
	NEGATIVE_BOOST: Liferay.Language.get(
		'boost-must-be-greater-than-or-equal-to-0'
	),
	REQUIRED: Liferay.Language.get('this-field-is-required'),
	REQUIRED_CATEGORY_SELECTOR: Liferay.Language.get(
		'this-field-is-required-please-select-from-the-dropdown-list-or-type-in-an-ID-number'
	),
};
