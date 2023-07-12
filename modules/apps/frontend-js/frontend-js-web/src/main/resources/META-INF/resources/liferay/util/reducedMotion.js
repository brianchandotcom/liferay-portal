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

import {
	CONSTANTS,
	accessibilityMenuAtom,
} from '@liferay/accessibility-settings-state-web';
import {State} from '@liferay/frontend-js-state-web';

function isReducedMotion() {
	const accessibilityMenu = State.read(accessibilityMenuAtom);
	const reducedMotion =
		accessibilityMenu[CONSTANTS.ACCESSIBILITY_SETTING_REDUCED_MOTION];

	if (reducedMotion?.value) {
		return true;
	}

	return window.matchMedia('(prefers-reduced-motion: reduce)').matches;
}

export {isReducedMotion};
