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

import {FragmentEntryLinkComment} from './addFragmentEntryLinkComment';
import {EDIT_FRAGMENT_ENTRY_LINK_COMMENT} from './types';

export default function editFragmentEntryLinkComment({
	fragmentEntryLinkComment,
	fragmentEntryLinkId,
	parentCommentId = '0',
}: {
	fragmentEntryLinkComment: FragmentEntryLinkComment;
	fragmentEntryLinkId: string;
	parentCommentId: string;
}) {
	return {
		fragmentEntryLinkComment,
		fragmentEntryLinkId,
		parentCommentId,
		type: EDIT_FRAGMENT_ENTRY_LINK_COMMENT,
	} as const;
}
