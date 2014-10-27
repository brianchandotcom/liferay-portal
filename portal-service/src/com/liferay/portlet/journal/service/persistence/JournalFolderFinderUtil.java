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

package com.liferay.portlet.journal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class JournalFolderFinderUtil {
	public static int countF_A_ByG_F(long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition) {
		return getFinder().countF_A_ByG_F(groupId, folderId, queryDefinition);
	}

	public static int filterCountF_A_ByG_F(long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition) {
		return getFinder()
				   .filterCountF_A_ByG_F(groupId, folderId, queryDefinition);
	}

	public static java.util.List<java.lang.Object> filterFindF_A_ByG_F(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition) {
		return getFinder()
				   .filterFindF_A_ByG_F(groupId, folderId, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findF_ByNoAssets() {
		return getFinder().findF_ByNoAssets();
	}

	public static java.util.List<java.lang.Object> findF_A_ByG_F(long groupId,
		long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition) {
		return getFinder().findF_A_ByG_F(groupId, folderId, queryDefinition);
	}

	public static JournalFolderFinder getFinder() {
		if (_finder == null) {
			_finder = (JournalFolderFinder)PortalBeanLocatorUtil.locate(JournalFolderFinder.class.getName());

			ReferenceRegistry.registerReference(JournalFolderFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(JournalFolderFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(JournalFolderFinderUtil.class,
			"_finder");
	}

	private static JournalFolderFinder _finder;
}