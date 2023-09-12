/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toggleShowResolvedComments from '../actions/toggleShowResolvedComments';
export default function showResolvedCommentsReducer(
	toggleResolvedComments: boolean | undefined,
	action: ReturnType<typeof toggleShowResolvedComments>
): boolean;
