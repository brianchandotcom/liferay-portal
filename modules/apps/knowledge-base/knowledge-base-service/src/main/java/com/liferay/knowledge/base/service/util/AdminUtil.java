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

package com.liferay.knowledge.base.service.util;

import com.liferay.knowledge.base.util.AdminHelper;
import com.liferay.portal.kernel.diff.DiffVersionsInfo;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 * @deprecated As of 1.4.0, with no direct replacement
 */
@Deprecated
public class AdminUtil {

	public static String[] escapeSections(String[] sections) {
		return _adminHelper.escapeSections(sections);
	}

	public static DiffVersionsInfo getDiffVersionsInfo(
		long groupId, long kbArticleResourcePrimKey, int sourceVersion,
		int targetVersion) {

		return _adminHelper.getDiffVersionsInfo(
			groupId, kbArticleResourcePrimKey, sourceVersion, targetVersion);
	}

	public static String getKBArticleDiff(
			long resourcePrimKey, int sourceVersion, int targetVersion,
			String param)
		throws Exception {

		return _adminHelper.getKBArticleDiff(
			resourcePrimKey, sourceVersion, targetVersion, param);
	}

	public static String[] unescapeSections(String sections) {
		return _adminHelper.unescapeSections(sections);
	}

	public void setAdminHelper(AdminHelper adminHelper) {
		_adminHelper = adminHelper;
	}

	private static AdminHelper _adminHelper;

}