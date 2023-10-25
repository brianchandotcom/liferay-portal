/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.view.count;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Preston Crary
 */
public class ViewCountManagerUtil {

	public static void deleteViewCount(
			long companyId, long classNameId, long classPK)
		throws PortalException {

		ViewCountManager viewCountManager = _viewCountManagerSnapshot.get();

		viewCountManager.deleteViewCount(companyId, classNameId, classPK);
	}

	public static long getViewCount(
		long companyId, long classNameId, long classPK) {

		ViewCountManager viewCountManager = _viewCountManagerSnapshot.get();

		return viewCountManager.getViewCount(companyId, classNameId, classPK);
	}

	public static Table<?> getViewCountEntryTable() {
		ViewCountManager viewCountManager = _viewCountManagerSnapshot.get();

		return viewCountManager.getViewCountEntryTable();
	}

	public static void incrementViewCount(
		long companyId, long classNameId, long classPK, int increment) {

		ViewCountManager viewCountManager = _viewCountManagerSnapshot.get();

		viewCountManager.incrementViewCount(
			companyId, classNameId, classPK, increment);
	}

	public static boolean isViewCountEnabled() {
		ViewCountManager viewCountManager = _viewCountManagerSnapshot.get();

		return viewCountManager.isViewCountEnabled();
	}

	public static boolean isViewCountEnabled(long classNameId) {
		ViewCountManager viewCountManager = _viewCountManagerSnapshot.get();

		return viewCountManager.isViewCountEnabled(classNameId);
	}

	private static final Snapshot<ViewCountManager> _viewCountManagerSnapshot =
		new Snapshot<>(ViewCountManagerUtil.class, ViewCountManager.class);

}