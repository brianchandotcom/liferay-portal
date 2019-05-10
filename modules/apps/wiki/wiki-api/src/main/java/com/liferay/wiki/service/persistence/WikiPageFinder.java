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

import org.osgi.annotation.versioning.ProviderType;

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

	public int countByModifiedDate(
		long groupId, long nodeId, java.util.Date modifiedDate, boolean before);

	public int countByModifiedDate(
		long groupId, long nodeId, java.sql.Timestamp modifiedDate,
		boolean before);

	public int countByG_N_H_S(
		long groupId, long nodeId, boolean head,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.wiki.model.WikiPage> queryDefinition);

	public int filterCountByCreateDate(
		long groupId, long nodeId, java.util.Date createDate, boolean before);

	public int filterCountByCreateDate(
		long groupId, long nodeId, java.sql.Timestamp modifiedDate,
		boolean before);

	public int filterCountByModifiedDate(
		long groupId, long nodeId, java.util.Date modifiedDate, boolean before);

	public int filterCountByModifiedDate(
		long groupId, long nodeId, java.sql.Timestamp createDate,
		boolean before);

	public int filterCountByG_N_H_S(
		long groupId, long nodeId, boolean head,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.wiki.model.WikiPage> queryDefinition);

	public java.util.List<com.liferay.wiki.model.WikiPage>
		filterFindByCreateDate(
			long groupId, long nodeId, java.util.Date createDate,
			boolean before, int start, int end);

	public java.util.List<com.liferay.wiki.model.WikiPage>
		filterFindByCreateDate(
			long groupId, long nodeId, java.sql.Timestamp createDate,
			boolean before, int start, int end);

	public java.util.List<com.liferay.wiki.model.WikiPage>
		filterFindByModifiedDate(
			long groupId, long nodeId, java.util.Date modifiedDate,
			boolean before, int start, int end);

	public java.util.List<com.liferay.wiki.model.WikiPage>
		filterFindByModifiedDate(
			long groupId, long nodeId, java.sql.Timestamp modifiedDate,
			boolean before, int start, int end);

	public java.util.List<com.liferay.wiki.model.WikiPage> filterFindByG_N_H_S(
		long groupId, long nodeId, boolean head,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.wiki.model.WikiPage> queryDefinition);

	public com.liferay.wiki.model.WikiPage findByResourcePrimKey(
			long resourcePrimKey)
		throws com.liferay.wiki.exception.NoSuchPageException;

	public java.util.List<com.liferay.wiki.model.WikiPage> findByCreateDate(
		long groupId, long nodeId, java.util.Date createDate, boolean before,
		int start, int end);

	public java.util.List<com.liferay.wiki.model.WikiPage> findByCreateDate(
		long groupId, long nodeId, java.sql.Timestamp createDate,
		boolean before, int start, int end);

	public java.util.List<com.liferay.wiki.model.WikiPage> findByModifiedDate(
		long groupId, long nodeId, java.util.Date modifiedDate, boolean before,
		int start, int end);

	public java.util.List<com.liferay.wiki.model.WikiPage> findByModifiedDate(
		long groupId, long nodeId, java.sql.Timestamp modifiedDate,
		boolean before, int start, int end);

	public java.util.List<com.liferay.wiki.model.WikiPage> findByNoAssets();

	public java.util.List<com.liferay.wiki.model.WikiPage> findByG_N_H_S(
		long groupId, long nodeId, boolean head,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.wiki.model.WikiPage> queryDefinition);

}