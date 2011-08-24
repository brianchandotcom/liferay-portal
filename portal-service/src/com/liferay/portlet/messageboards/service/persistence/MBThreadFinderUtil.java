/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class MBThreadFinderUtil {
	public static int countByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByG_C_S(groupId, categoryId, status);
	}

	public static int countByS_G_U_C_S(long groupId, long userId,
		long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByS_G_U_C_S(groupId, userId, categoryIds, status);
	}

	public static int filterCountByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().filterCountByG_C(groupId, categoryId);
	}

	public static int filterCountByG_C_S(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().filterCountByG_C_S(groupId, categoryId, status);
	}

	public static int filterCountByS_G_U_C_S(long groupId, long userId,
		long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterCountByS_G_U_C_S(groupId, userId, categoryIds, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().filterFindByG_C(groupId, categoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindByG_C_S(groupId, categoryId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByS_G_U_C_S(
		long groupId, long userId, long[] categoryIds, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindByS_G_U_C_S(groupId, userId, categoryIds, status,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByG_C_S(groupId, categoryId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByNoAssets()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByNoAssets();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByS_G_U_C_S(
		long groupId, long userId, long[] categoryIds, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByS_G_U_C_S(groupId, userId, categoryIds, status,
			start, end);
	}

	public static MBThreadFinder getFinder() {
		if (_finder == null) {
			_finder = (MBThreadFinder)PortalBeanLocatorUtil.locate(MBThreadFinder.class.getName());

			ReferenceRegistry.registerReference(MBThreadFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(MBThreadFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(MBThreadFinderUtil.class, "_finder");
	}

	private static MBThreadFinder _finder;
}