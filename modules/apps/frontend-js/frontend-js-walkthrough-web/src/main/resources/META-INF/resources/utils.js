/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
