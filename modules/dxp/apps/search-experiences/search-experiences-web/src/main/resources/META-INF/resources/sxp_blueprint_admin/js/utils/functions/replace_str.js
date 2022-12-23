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
 * Function to replace all instances of a string.
 *
 * Example:
 * replaceStr('title_${configuration.language}', '${configuration.language}', 'en_US')
 * => 'title_en_US'
 *
 * @param {String} str Original string
 * @param {String} search Snippet to look for
 * @param {String} replace Snippet to replace with
 * @return {String}
 */
export default function replaceStr(str, search, replace) {
	return str.split(search).join(replace);
}
