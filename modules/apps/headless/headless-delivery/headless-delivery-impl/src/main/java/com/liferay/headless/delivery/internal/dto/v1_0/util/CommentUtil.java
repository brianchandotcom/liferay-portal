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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Optional;

/**
 * @author Javier Gamarra
 */
public class CommentUtil {

	public static Comment toComment(
			com.liferay.portal.kernel.comment.Comment comment,
			CommentManager commentManager, Portal portal)
		throws Exception {

		if (comment == null) {
			return null;
		}

		return new Comment() {
			{
				creator = CreatorUtil.toCreator(
					portal, Optional.empty(), comment.getUser());
				dateCreated = comment.getCreateDate();
				dateModified = comment.getModifiedDate();
				id = comment.getCommentId();
				numberOfComments = commentManager.getChildCommentsCount(
					comment.getCommentId(), WorkflowConstants.STATUS_APPROVED);
				parentCommentId = comment.getParentCommentId();
				text = comment.getBody();
			}
		};
	}

}