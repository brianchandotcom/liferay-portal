/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
