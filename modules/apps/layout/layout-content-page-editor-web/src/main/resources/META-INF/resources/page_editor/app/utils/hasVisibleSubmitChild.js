/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getLayoutDataItemUniqueClassName from './getLayoutDataItemUniqueClassName';
import isVisible from './isVisible';

export default function hasVisibleSubmitChild(itemId, globalContext) {
	const element = document.querySelector(
		`.${getLayoutDataItemUniqueClassName(itemId)}`
	);

	return Array.from(
		element.querySelectorAll(
			'input[type=submit], button[type=submit], button:not([type])'
		)
	).some((buttonElement) => isVisible(buttonElement, globalContext));
}
