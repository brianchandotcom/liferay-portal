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

import com.liferay.message.boards.kernel.model.MBDiscussion;

/**
 * @author Adolfo Pérez
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.comment.constants.CommentConstants}
 */
@Deprecated
public class CommentConstants {

	public static Class<?> getDiscussionClass() {
		return MBDiscussion.class;
	}

	public static String getDiscussionClassName() {
		return MBDiscussion.class.getName();
	}

}