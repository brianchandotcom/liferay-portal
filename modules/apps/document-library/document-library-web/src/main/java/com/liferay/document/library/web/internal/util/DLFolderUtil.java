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

package com.liferay.document.library.web.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryServiceUtil;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.RepositoryUtil;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class DLFolderUtil {

	public static boolean isRepositoryRoot(Folder folder) {
		if (folder.isMountPoint() ||
			(folder.isRoot() &&
			 RepositoryUtil.isExternalRepository(folder.getRepositoryId()))) {

			return true;
		}

		return false;
	}

	public static void validateDepotFolder(
			long folderId, long groupId, long scopeGroupId)
		throws PortalException {

		if (groupId == scopeGroupId) {
			return;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (!group.isDepot()) {
			return;
		}

		List<Long> groupConnectedDepotEntries = ListUtil.toList(
			DepotEntryServiceUtil.getGroupConnectedDepotEntries(
				scopeGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			DepotEntry::getGroupId);

		if (!groupConnectedDepotEntries.contains(groupId)) {
			throw new NoSuchFolderException("{folderId=" + folderId + "}");
		}
	}

}