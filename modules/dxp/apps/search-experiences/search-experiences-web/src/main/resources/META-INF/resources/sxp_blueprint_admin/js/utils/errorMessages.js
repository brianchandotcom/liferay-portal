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
