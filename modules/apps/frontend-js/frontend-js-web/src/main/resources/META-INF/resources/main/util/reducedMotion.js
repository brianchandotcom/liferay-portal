/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
