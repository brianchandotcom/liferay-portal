/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getDOM from './get_dom';

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export default function getElement(element) {
	const currentElement = getDOM(element);

	return typeof currentElement === 'string'
		? document.querySelector(currentElement)
		: currentElement.jquery
		? currentElement[0]
		: currentElement;
}
