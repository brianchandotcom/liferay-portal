/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import switchViewportSize from '../actions/switchViewportSize';
import {SWITCH_VIEWPORT_SIZE} from '../actions/types';
import {ViewportSize} from '../config/constants/viewportSizes';

export default function selectedViewportSizeReducer(
	viewportStatus: ViewportSize = 'desktop',
	action: ReturnType<typeof switchViewportSize>
) {
	if (action.type === SWITCH_VIEWPORT_SIZE) {
		return action.size;
	}

	return viewportStatus;
}
