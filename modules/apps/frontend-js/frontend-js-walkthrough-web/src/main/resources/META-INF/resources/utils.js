/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import domAlign from 'dom-align';

export const LOCAL_STORAGE_KEYS = {
	CURRENT_STEP: `${themeDisplay.getUserId()}-walkthrough-current-step`,
	POPOVER_VISIBILITY: `${themeDisplay.getUserId()}-walkthrough-popover-visible`,
	SKIPPABLE: `${themeDisplay.getUserId()}-${themeDisplay.getSiteGroupId()}-walkthrough-dismissed`,
};

export function doAlign({sourceElement, targetElement, ...config}) {
	return domAlign(sourceElement, targetElement, {
		...config,
		useCssRight: window.getComputedStyle(sourceElement).direction === 'rtl',
	});
}
