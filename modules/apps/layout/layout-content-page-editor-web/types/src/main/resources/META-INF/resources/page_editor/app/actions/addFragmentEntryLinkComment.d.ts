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

export interface FragmentEntryLinkComment {
	author: {
		fullName: string;
		portraitURL: string;
		userId: string;
	};
	body: string;
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
	parentCommentId: string;
}): {
	readonly fragmentEntryLinkComment: FragmentEntryLinkComment;
	readonly fragmentEntryLinkId: string;
	readonly parentCommentId: string;
	readonly type: 'ADD_FRAGMENT_ENTRY_LINK_COMMENT';
};
