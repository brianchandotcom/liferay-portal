/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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