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

package com.liferay.headless.document.library.internal.dto.v1_0.converter;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.web.experience.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.web.experience.dto.v1_0.converter.DTOConverterContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "asset.entry.class.name=com.liferay.portal.kernel.repository.model.Folder",
	service = {DTOConverter.class, FolderDTOConverter.class}
)
public class FolderDTOConverter implements DTOConverter {

	@Override
	public Folder toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		com.liferay.portal.kernel.repository.model.Folder folder =
			_dlAppService.getFolder(dtoConverterContext.getResourcePrimKey());

		return new Folder() {
			{
				contentSpaceId = folder.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(folder.getUserId()));
				dateCreated = folder.getCreateDate();
				dateModified = folder.getModifiedDate();
				description = folder.getDescription();
				id = folder.getFolderId();
				name = folder.getName();
				numberOfDocuments = _dlAppService.getFileEntriesCount(
					folder.getRepositoryId(), folder.getFolderId());
				numberOfFolders = _dlAppService.getFoldersCount(
					folder.getRepositoryId(), folder.getFolderId());
			}
		};
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}