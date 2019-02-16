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

package com.liferay.headless.document.library.internal.dto.v1_0.util;

import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.internal.dto.v1_0.CommentImpl;

/**
 * @author Javier Gamarra
 */
public class CommentUtil {

	public static Comment toComment(
			com.liferay.portal.kernel.comment.Comment comment)
		throws Exception {

		if (comment == null) {
			return null;
		}

		return new CommentImpl() {
			{
				setCreator(CreatorUtil.toCreator(comment.getUser()));
				setDateCreated(comment.getCreateDate());
				setDateModified(comment.getModifiedDate());
				setId(comment.getCommentId());
				setText(comment.getBody());
			}
		};
	}

}