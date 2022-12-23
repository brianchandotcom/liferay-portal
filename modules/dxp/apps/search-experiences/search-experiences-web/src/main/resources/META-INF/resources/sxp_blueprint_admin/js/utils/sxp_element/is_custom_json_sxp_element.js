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

import isDefined from '../functions/is_defined';

/**
 * Used for handling if the element instance is a custom JSON element. This
 * function makes it easier to globally handle the logic for differentiating
 * between a custom JSON element and a standard element.
 * @param {object} uiConfigurationValues
 * @returns {boolean}
 */
export default function isCustomJSONSXPElement(uiConfigurationValues) {
	return isDefined(uiConfigurationValues.sxpElement);
}
