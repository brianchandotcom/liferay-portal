/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ORIENTATIONS} from './constants/orientations';

export default function getTargetData(targetRect, orientation) {
	return orientation === ORIENTATIONS.horizontal
		? {
				end: targetRect.right,
				length: targetRect.width,
				start: targetRect.left,
		  }
		: {
				end: targetRect.bottom,
				length: targetRect.height,
				start: targetRect.top,
		  };
}
