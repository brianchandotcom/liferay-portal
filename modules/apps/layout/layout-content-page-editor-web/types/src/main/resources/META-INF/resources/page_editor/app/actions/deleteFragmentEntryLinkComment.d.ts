/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function deleteFragmentEntryLinkComment({
	commentId,
	fragmentEntryLinkId,
	parentCommentId,
}: {
	commentId: string;
	fragmentEntryLinkId: string;
	parentCommentId?: string;
}): {
	readonly commentId: string;
	readonly fragmentEntryLinkId: string;
	readonly parentCommentId: string | undefined;
	readonly type: 'DELETE_FRAGMENT_ENTRY_LINK_COMMENT';
};
