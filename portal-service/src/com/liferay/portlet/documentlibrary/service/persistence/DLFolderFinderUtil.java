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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFolderFinderUtil {
	public static int countF_FE_FS_ByG_F_S(long groupId, long folderId,
		int status, java.lang.String[] mimeTypes, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countF_FE_FS_ByG_F_S(groupId, folderId, status, mimeTypes,
			includeMountFolders);
	}

	public static int countFE_ByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countFE_ByG_F_S(groupId, folderId, status);
	}

	public static int filterCountF_FE_FS_ByG_F_S(long groupId, long folderId,
		int status, java.lang.String[] mimeTypes, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterCountF_FE_FS_ByG_F_S(groupId, folderId, status,
			mimeTypes, includeMountFolders);
	}

	public static int filterCountFE_ByG_F_S(long groupId, long folderId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().filterCountFE_ByG_F_S(groupId, folderId, status);
	}

	public static java.util.List<java.lang.Object> filterFindF_FE_FS_ByG_F_S(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindF_FE_FS_ByG_F_S(groupId, folderId, status,
			mimeTypes, includeMountFolders, start, end, obc);
	}

	public static java.util.List<java.lang.Object> filterFindFE_FS_ByG_F_S(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindFE_FS_ByG_F_S(groupId, folderId, status, start,
			end);
	}

	public static java.util.List<java.lang.Object> findF_FE_FS_ByG_F_S(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findF_FE_FS_ByG_F_S(groupId, folderId, status, mimeTypes,
			includeMountFolders, start, end, obc);
	}

	public static java.util.List<java.lang.Object> findFE_FS_ByG_F_S(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findFE_FS_ByG_F_S(groupId, folderId, status, start, end);
	}

	public static DLFolderFinder getFinder() {
		if (_finder == null) {
			_finder = (DLFolderFinder)PortalBeanLocatorUtil.locate(DLFolderFinder.class.getName());

			ReferenceRegistry.registerReference(DLFolderFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DLFolderFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DLFolderFinderUtil.class, "_finder");
	}

	private static DLFolderFinder _finder;
}