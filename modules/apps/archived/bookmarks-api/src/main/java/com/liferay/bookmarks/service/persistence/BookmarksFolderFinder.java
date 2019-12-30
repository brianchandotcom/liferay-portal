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

package com.liferay.bookmarks.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface BookmarksFolderFinder {

	public int countF_E_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int filterCountF_E_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public java.util.List<Object> filterFindBF_E_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder>
		findByNoAssets();

	public java.util.List<Object> findF_E_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

}