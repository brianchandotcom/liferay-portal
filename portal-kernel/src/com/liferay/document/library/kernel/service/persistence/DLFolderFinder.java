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

package com.liferay.document.library.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface DLFolderFinder {

	public int countF_FE_FS_ByG_F_M_M(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int countFE_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int countFE_FS_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int filterCountF_FE_FS_ByG_F_M_M(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int filterCountFE_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int filterCountFE_FS_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int filterCountFE_FS_ByG_F_M(
		long groupId, long folderId, String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public java.util.List<Object> filterFindF_FE_FS_ByG_F_M_M(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public java.util.List<Object> filterFindFE_FS_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public java.util.List<com.liferay.document.library.kernel.model.DLFolder>
		findF_ByNoAssets();

	public java.util.List<com.liferay.document.library.kernel.model.DLFolder>
		findF_ByC_T(long classNameId, String treePath);

	public java.util.List<Object> findF_FE_FS_ByG_F_M_M(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public java.util.List<Object> findFE_FS_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

}