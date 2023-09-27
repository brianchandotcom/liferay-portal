/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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