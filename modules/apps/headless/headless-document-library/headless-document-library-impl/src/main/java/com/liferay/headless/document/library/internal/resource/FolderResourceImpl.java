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

package com.liferay.headless.document.library.internal.resource;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.document.library.dto.Folder;
import com.liferay.headless.document.library.resource.FolderResource;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/folder.properties", service = FolderResource.class
)
public class FolderResourceImpl extends BaseFolderResourceImpl {

	@Override
	public Page<Folder> getDocumentsRepositoryFolderPage(
		Long parentId, Pagination pagination) {

		return _getFolderPage(
			parentId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, pagination);
	}

	@Override
	public Page<Folder> getFolderFolderPage(
		Long parentId, Pagination pagination) {

		try {
			com.liferay.portal.kernel.repository.model.Folder parentFolder =
				_dlAppService.getFolder(parentId);

			return _getFolderPage(
				parentFolder.getGroupId(), parentFolder.getFolderId(),
				pagination);
		}
		catch (NoSuchFolderException nsfe) {
			throw new NotFoundException(nsfe);
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(pe);
		}
	}

	private Page<Folder> _getFolderPage(
		Long groupId, Long parentFolderId, Pagination pagination) {

		try {
			return new Page<>(
				transform(
					_dlAppService.getFolders(
						groupId, parentFolderId, pagination.getStartPosition(),
						pagination.getEndPosition(), null),
					this::_toFolder),
				_dlAppService.getFoldersCount(groupId, parentFolderId));
		}
		catch (NoSuchGroupException nsge) {
			throw new NotFoundException(nsge);
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(pe);
		}
	}

	private Folder _toFolder(
		com.liferay.portal.kernel.repository.model.Folder folder) {

		return new Folder() {
			{
				setDescription(folder.getDescription());
				setId(folder.getFolderId());
				setName(folder.getName());
			}
		};
	}

	@Reference
	private DLAppService _dlAppService;

}