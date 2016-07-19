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

package com.liferay.portal.kernel.comment;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsStats;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public interface DiscussionComment extends Comment {

	public List<DiscussionComment> getDescendantComments();

	public int getDescendantCommentsCount();

	public DiscussionComment getParentComment() throws PortalException;

	public RatingsEntry getRatingsEntry();

	public RatingsStats getRatingsStats();

	/**
	 * @deprecated As of 7.0.2, replaced by {@link #getDescendantComments()}
	 */
	@Deprecated
	public List<DiscussionComment> getThreadComments();

	/**
	 * @deprecated As of 7.0.2, replaced by {@link
	 *             #getDescendantCommentsCount()}
	 */
	@Deprecated
	public int getThreadCommentsCount();

	public DiscussionCommentIterator getThreadDiscussionCommentIterator();

	public DiscussionCommentIterator getThreadDiscussionCommentIterator(
		int from);

}