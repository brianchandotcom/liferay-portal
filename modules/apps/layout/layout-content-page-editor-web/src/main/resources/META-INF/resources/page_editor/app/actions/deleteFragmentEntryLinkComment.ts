/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DELETE_FRAGMENT_ENTRY_LINK_COMMENT} from './types';

export default function deleteFragmentEntryLinkComment({
	commentId,
	fragmentEntryLinkId,
	parentCommentId,
}: {
	commentId: string;
	fragmentEntryLinkId: string;
	parentCommentId?: string;
}) {
	return {
		commentId,
		fragmentEntryLinkId,
		parentCommentId,
		type: DELETE_FRAGMENT_ENTRY_LINK_COMMENT,
	} as const;
}
