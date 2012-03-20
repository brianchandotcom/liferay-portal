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

package com.liferay.portlet.journal.service.persistence;

/**
 * @author Brian Wing Shun Chan
 */
public interface JournalFolderFinder {
	public int countF_JA_ByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountF_JA_ByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object> filterFindF_JAByG_F(long groupId,
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object> findF_JAByG_F(long groupId,
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;
}