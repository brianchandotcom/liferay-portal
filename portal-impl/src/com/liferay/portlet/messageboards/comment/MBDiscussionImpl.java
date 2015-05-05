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

package com.liferay.portlet.messageboards.comment;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.Discussion;

/**
 * @author Adolfo Pérez
 */
public class MBDiscussionImpl implements Discussion {

	public MBDiscussionImpl(
		Comment rootComment, boolean maxCommentsLimitExceeded) {

		_rootComment = rootComment;
		_maxCommentsLimitExceeded = maxCommentsLimitExceeded;
	}

	@Override
	public Comment getRootComment() {
		return _rootComment;
	}

	@Override
	public boolean isMaxCommentsLimitExceeded() {
		return _maxCommentsLimitExceeded;
	}

	private final boolean _maxCommentsLimitExceeded;
	private final Comment _rootComment;

}