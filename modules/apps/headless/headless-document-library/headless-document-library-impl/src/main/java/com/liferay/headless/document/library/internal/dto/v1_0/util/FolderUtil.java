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

package com.liferay.headless.document.library.internal.dto.v1_0.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
public class FolderUtil {

	public static Folder toFolder(
			com.liferay.portal.kernel.repository.model.Folder folder,
			DLAppService dlAppService, Portal portal,
			UserLocalService userLocalService)
		throws Exception {

		return new Folder() {
			{
				contentSpaceId = folder.getGroupId();
				creator = CreatorUtil.toCreator(
					portal, userLocalService.getUser(folder.getUserId()));
				dateCreated = folder.getCreateDate();
				dateModified = folder.getModifiedDate();
				description = folder.getDescription();
				id = folder.getFolderId();
				name = folder.getName();
				numberOfDocuments = dlAppService.getFileEntriesCount(
					folder.getRepositoryId(), folder.getFolderId());
				numberOfFolders = dlAppService.getFoldersCount(
					folder.getRepositoryId(), folder.getFolderId());
			}
		};
	}

}