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

package com.liferay.message.boards.comment.internal;

import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;

/**
 * @author Adolfo Pérez
 */
public class MBDiscussionImpl implements Discussion {

	public MBDiscussionImpl(
		DiscussionComment rootDiscussionComment,
		boolean maxCommentsLimitExceeded, int discussionCommentsCount) {

		_rootDiscussionComment = rootDiscussionComment;
		_maxCommentsLimitExceeded = maxCommentsLimitExceeded;
		_discussionCommentsCount = discussionCommentsCount;
	}

	@Override
	public int getDiscussionCommentsCount() {
		return _discussionCommentsCount;
	}

	@Override
	public DiscussionComment getRootDiscussionComment() {
		return _rootDiscussionComment;
	}

	@Override
	public boolean isMaxCommentsLimitExceeded() {
		return _maxCommentsLimitExceeded;
	}

	private final int _discussionCommentsCount;
	private final boolean _maxCommentsLimitExceeded;
	private final DiscussionComment _rootDiscussionComment;

}