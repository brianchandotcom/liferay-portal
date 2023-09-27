/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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