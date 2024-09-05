/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getLayoutDataItemUniqueClassName from './getLayoutDataItemUniqueClassName';
import {hasVisibleFormButtonChild} from './hasVisibleFormButtonChild';
import isVisible from './isVisible';

export default function hasVisibleSubmitChild(
	itemId,
	globalContext,
	layoutData,
	fragmentEntryLinks,
	viewportSize
) {
	if (!Liferay.FeatureFlags['LPD-10727']) {
		const element = document.querySelector(
			`.${getLayoutDataItemUniqueClassName(itemId)}`
		);

		if (!element) {
			return false;
		}

		return Array.from(
			element.querySelectorAll(
				'input[type=submit], button[type=submit], button:not([type])'
			)
		).some((buttonElement) => isVisible(buttonElement, globalContext));
	}

	return hasVisibleFormButtonChild({
		fragmentEntryLinks,
		itemId,
		layoutData,
		type: 'submit',
		viewportSize,
	});
}
