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
 * Function to return a new object with renamed keys.
 *
 * Example:
 * renameKeys({"en-US": "Hello", "zh-CN": "Ni Hao"}, (str) => str.replace('-', '_'))
 * => {en_US: "Hello", zh_CN: "Ni Hao"}
 *
 * @param {Object} object Original object
 * @return {Object}
 */
export default function renameKeys(object, func) {
	const newObj = {};

	Object.keys(object).map((key) => {
		newObj[`${func(key)}`] = object[key];
	});

	return newObj;
}
