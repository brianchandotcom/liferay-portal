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

package com.liferay.wiki.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiPage;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface WikiPageFinder {

	public int countByCreateDate(
		long groupId, long nodeId, java.util.Date createDate, boolean before);

	public int countByCreateDate(
		long groupId, long nodeId, java.sql.Timestamp createDate,
		boolean before);

	public int filterCountByCreateDate(
		long groupId, long nodeId, java.util.Date createDate, boolean before);

	public int filterCountByCreateDate(
		long groupId, long nodeId, java.sql.Timestamp createDate,
		boolean before);

	public java.util.List<WikiPage>
		filterFindByCreateDate(
			long groupId, long nodeId, java.util.Date createDate,
			boolean before, int start, int end);

	public java.util.List<WikiPage>
		filterFindByCreateDate(
			long groupId, long nodeId, java.sql.Timestamp createDate,
			boolean before, int start, int end);

	public WikiPage findByResourcePrimKey(long resourcePrimKey)
		throws NoSuchPageException;

	public java.util.List<WikiPage>
		findByCreateDate(
			long groupId, long nodeId, java.util.Date createDate,
			boolean before, int start, int end);

	public java.util.List<WikiPage>
		findByCreateDate(
			long groupId, long nodeId, java.sql.Timestamp createDate,
			boolean before, int start, int end);

	public java.util.List<WikiPage> findByNoAssets();
}