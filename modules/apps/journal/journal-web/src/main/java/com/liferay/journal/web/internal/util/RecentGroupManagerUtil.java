/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.util;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.site.util.RecentGroupManager;

/**
 * @author Víctor Galán
 */
public class RecentGroupManagerUtil {

	public static RecentGroupManager getRecentGroupManager() {
		return _recentGroupManagerSnapshot.get();
	}

	private static final Snapshot<RecentGroupManager>
		_recentGroupManagerSnapshot = new Snapshot<>(
			RecentGroupManagerUtil.class, RecentGroupManager.class);

}