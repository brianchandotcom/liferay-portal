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

package com.liferay.knowledge.base.web.internal.util;

import com.liferay.diff.DiffVersionsInfo;
import com.liferay.knowledge.base.util.AdminHelper;
import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Lance Ji
 */
public class AdminUtil {

	public static DiffVersionsInfo getDiffVersionsInfo(
		long groupId, long kbArticleResourcePrimKey, int sourceVersion,
		int targetVersion) {

		AdminHelper adminHelper = _adminHelperSnapshot.get();

		return adminHelper.getDiffVersionsInfo(
			groupId, kbArticleResourcePrimKey, sourceVersion, targetVersion);
	}

	public static String[] unescapeSections(String sections) {
		AdminHelper adminHelper = _adminHelperSnapshot.get();

		return adminHelper.unescapeSections(sections);
	}

	private static final Snapshot<AdminHelper> _adminHelperSnapshot =
		new Snapshot<>(AdminUtil.class, AdminHelper.class);

}