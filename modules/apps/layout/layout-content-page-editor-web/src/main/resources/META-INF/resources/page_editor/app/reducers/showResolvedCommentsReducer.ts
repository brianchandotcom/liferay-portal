/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toggleShowResolvedComments from '../actions/toggleShowResolvedComments';
import {TOGGLE_SHOW_RESOLVED_COMMENTS} from '../actions/types';

export default function showResolvedCommentsReducer(
	toggleResolvedComments = false,
	action: ReturnType<typeof toggleShowResolvedComments>
) {
	switch (action.type) {
		case TOGGLE_SHOW_RESOLVED_COMMENTS:
			return action.showResolvedComments;

		default:
			return toggleResolvedComments;
	}
}
