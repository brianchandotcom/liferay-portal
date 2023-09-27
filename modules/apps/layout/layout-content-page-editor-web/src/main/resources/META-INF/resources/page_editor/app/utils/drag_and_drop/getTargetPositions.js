/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ORIENTATIONS} from './constants/orientations';
import {TARGET_POSITIONS} from './constants/targetPositions';

export default function getTargetPositions(orientation) {
	return orientation === ORIENTATIONS.horizontal
		? {
				end: TARGET_POSITIONS.RIGHT,
				start: TARGET_POSITIONS.LEFT,
		  }
		: {
				end: TARGET_POSITIONS.BOTTOM,
				start: TARGET_POSITIONS.TOP,
		  };
}
