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

package com.liferay.comment.taglib.internal.permission.util;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.comment.DiscussionPermission;

/**
 * @author Jiaxu Wei
 */
public class DiscussionPermissionUtil {

	public static DiscussionPermission getDiscussionPermission() {
		return _discussionPermissionSnapshot.get();
	}

	private static final Snapshot<DiscussionPermission>
		_discussionPermissionSnapshot = new Snapshot<>(
			DiscussionPermissionUtil.class, DiscussionPermission.class);

}