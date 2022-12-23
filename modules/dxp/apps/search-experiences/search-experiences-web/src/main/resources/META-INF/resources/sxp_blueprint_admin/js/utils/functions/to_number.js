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
 * Function turn string into number, otherwise returns itself.
 *
 * Example:
 * toNumber('234')
 * => 234
 * toNumber(234)
 * => 234
 * toNumber('0234')
 * => '0234'
 *
 * @param {String} str String
 * @return {number}
 */
export default function toNumber(str) {
	try {
		return JSON.parse(str);
	}
	catch {
		return str;
	}
}
