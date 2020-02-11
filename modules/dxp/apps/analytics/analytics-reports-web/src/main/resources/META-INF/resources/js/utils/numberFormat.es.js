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

/**
 * Example:
 * en-US: `123456` => `123,456`
 * es-ES: `123456` => `123.456`
 */
export function numberFormat(languageTag, number) {
	return Intl.NumberFormat(languageTag).format(number);
}
