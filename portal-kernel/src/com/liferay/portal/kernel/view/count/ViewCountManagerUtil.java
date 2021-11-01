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

package com.liferay.portal.kernel.view.count;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Preston Crary
 */
public class ViewCountManagerUtil {

	public static void deleteViewCount(
			long companyId, long classNameId, long classPK)
		throws PortalException {

		_viewCountManager.deleteViewCount(companyId, classNameId, classPK);
	}

	public static long getViewCount(
		long companyId, long classNameId, long classPK) {

		return _viewCountManager.getViewCount(companyId, classNameId, classPK);
	}

	public static Table<?> getViewCountEntryTable() {
		return _viewCountManager.getViewCountEntryTable();
	}

	public static void incrementViewCount(
		long companyId, long classNameId, long classPK, int increment) {

		_viewCountManager.incrementViewCount(
			companyId, classNameId, classPK, increment);
	}

	public static boolean isViewCountEnabled() {
		return _viewCountManager.isViewCountEnabled();
	}

	public static boolean isViewCountEnabled(long classNameId) {
		return _viewCountManager.isViewCountEnabled(classNameId);
	}

	private static volatile ViewCountManager _viewCountManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			ViewCountManager.class, ViewCountManagerUtil.class,
			"_viewCountManager", true);

}