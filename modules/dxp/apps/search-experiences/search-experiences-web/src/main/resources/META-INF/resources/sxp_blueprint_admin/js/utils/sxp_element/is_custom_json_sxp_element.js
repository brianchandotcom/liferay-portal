/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
