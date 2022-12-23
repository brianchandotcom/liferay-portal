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
 * Function for parsing custom json element text into sxpElement
 *
 * @param {object} sxpElement Original sxpElement (default)
 * @param {object} uiConfigurationValues Contains custom JSON for sxpElement
 * @return {object}
 */
export default function parseCustomSXPElement(
	sxpElement,
	uiConfigurationValues
) {
	try {
		if (isDefined(uiConfigurationValues.sxpElement)) {
			return JSON.parse(uiConfigurationValues.sxpElement);
		}

		return sxpElement;
	}
	catch {
		return sxpElement;
	}
}
