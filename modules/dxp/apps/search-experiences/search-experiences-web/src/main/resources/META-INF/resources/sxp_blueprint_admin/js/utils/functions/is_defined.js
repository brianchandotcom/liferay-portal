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
 * Function used to identify whether a required value is not undefined
 *
 * Examples:
 * isDefined(false)
 * => true
 * isDefined([])
 * => true
 * isDefined('')
 * => true
 * isDefined(null)
 * => true
 *
 * @param {String|object} item Item to check
 * @return {boolean}
 */
export default function isDefined(item) {
	return typeof item !== 'undefined';
}
