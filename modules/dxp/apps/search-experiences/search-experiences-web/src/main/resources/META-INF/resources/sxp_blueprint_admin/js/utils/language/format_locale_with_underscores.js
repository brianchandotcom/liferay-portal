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
 * Formats locale from pattern with dashes (BCP47) to default pattern
 * with underscore.
 *
 * Example:
 * formatLocaleWithUnderscore("en-US")
 * => "en_US"
 *
 * @param {string} locale Language identifier
 * @returns {string}
 */
export default function formatLocaleWithUnderscores(locale) {
	return locale.replaceAll('-', '_');
}
