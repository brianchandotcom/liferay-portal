/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface FragmentEntryLinkComment {
	author: {
		fullName: string;
		portraitURL: string;
		userId: string;
	};
	body: string;
	children?: FragmentEntryLinkComment[];
	commentId: string;
	dateDescription: string;
	edited: false;
	modifiedDateDescription: string;
	resolved: false;
}
export default function addFragmentEntryLinkComment({
	fragmentEntryLinkComment,
	fragmentEntryLinkId,
	parentCommentId,
}: {
	fragmentEntryLinkComment: FragmentEntryLinkComment;
	fragmentEntryLinkId: string;
	parentCommentId?: string;
}): {
	readonly fragmentEntryLinkComment: FragmentEntryLinkComment;
	readonly fragmentEntryLinkId: string;
	readonly parentCommentId: string | undefined;
	readonly type: 'ADD_FRAGMENT_ENTRY_LINK_COMMENT';
};
