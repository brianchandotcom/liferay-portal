/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
public class MBMessageFinderUtil {
	public static int countByC_T(java.util.Date createDate, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByC_T(createDate, threadId);
	}

	public static int countByG_U_C_S(long groupId, long userId,
		long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByG_U_C_S(groupId, userId, categoryIds, status);
	}

	public static int countByG_U_C_A_S(long groupId, long userId,
		long[] categoryIds, boolean anonymous, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByG_U_C_A_S(groupId, userId, categoryIds, anonymous,
			status);
	}

	public static int filterCountByG_C_S(long groupId, long[] categoryIds,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().filterCountByG_C_S(groupId, categoryIds, status);
	}

	public static int filterCountByG_U_C_S(long groupId, long userId,
		long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterCountByG_U_C_S(groupId, userId, categoryIds, status);
	}

	public static int filterCountByG_U_C_A_S(long groupId, long userId,
		long[] categoryIds, boolean anonymous, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterCountByG_U_C_A_S(groupId, userId, categoryIds,
			anonymous, status);
	}

	public static java.util.List<java.lang.Long> filterFindByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindByG_C_S(groupId, categoryIds, status, start, end);
	}

	public static java.util.List<java.lang.Long> filterFindByG_U_C_S(
		long groupId, long userId, long[] categoryIds, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindByG_U_C_S(groupId, userId, categoryIds, status,
			start, end);
	}

	public static java.util.List<java.lang.Long> filterFindByG_U_C_A_S(
		long groupId, long userId, long[] categoryIds, boolean anonymous,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindByG_U_C_A_S(groupId, userId, categoryIds,
			anonymous, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByNoAssets()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByNoAssets();
	}

	public static java.util.List<java.lang.Long> findByG_U_C_S(long groupId,
		long userId, long[] categoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByG_U_C_S(groupId, userId, categoryIds, status, start,
			end);
	}

	public static java.util.List<java.lang.Long> findByG_U_C_A_S(long groupId,
		long userId, long[] categoryIds, boolean anonymous, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByG_U_C_A_S(groupId, userId, categoryIds, anonymous,
			status, start, end);
	}

	public static MBMessageFinder getFinder() {
		if (_finder == null) {
			_finder = (MBMessageFinder)PortalBeanLocatorUtil.locate(MBMessageFinder.class.getName());

			ReferenceRegistry.registerReference(MBMessageFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(MBMessageFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(MBMessageFinderUtil.class, "_finder");
	}

	private static MBMessageFinder _finder;
}