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
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/folder.properties", scope = ServiceScope.PROTOTYPE,
	service = FolderResource.class
)
public class FolderResourceImpl extends BaseFolderResourceImpl {

	@Override
	public Page<Folder> getDocumentsRepositoryFolderPage(
		Long documentsRepositoryId, Pagination pagination) {

		return _getFolderPage(
			documentsRepositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			pagination);
	}

	@Override
	public Folder getFolder(Long folderId) {
		return _toFolder(_getFolder(folderId));
	}

	@Override
	public Page<Folder> getFolderFolderPage(
		Long folderId, Pagination pagination) {

		try {
			com.liferay.portal.kernel.repository.model.Folder parentFolder =
				_dlAppService.getFolder(folderId);

			return _getFolderPage(
				parentFolder.getGroupId(), parentFolder.getFolderId(),
				pagination);
		}
		catch (NoSuchFolderException nsfe) {
			throw new NotFoundException(nsfe);
		}
		catch (PrincipalException pe) {
			throw new NotAuthorizedException(pe);
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(pe);
		}
	}

	@Override
	public Folder postDocumentsRepositoryFolder(
		Long documentsRepositoryId, Folder folder) {

		return _addFolder(documentsRepositoryId, 0L, folder);
	}

	@Override
	public Folder postFolderFolder(Long folderId, Folder folder) {
		com.liferay.portal.kernel.repository.model.Folder parentFolder =
			_getFolder(folderId);

		return _addFolder(
			parentFolder.getGroupId(), parentFolder.getFolderId(), folder);
	}

	@Override
	public Folder putFolder(Long folderId, Folder folder) {
		try {
			return _toFolder(
				_dlAppService.updateFolder(
					folderId, folder.getName(), folder.getDescription(),
					new ServiceContext()));
		}
		catch (NoSuchFolderException nsfe) {
			throw new NotFoundException(nsfe);
		}
		catch (PrincipalException pe) {
			throw new NotAuthorizedException(pe);
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(pe);
		}
	}

	private Folder _addFolder(
		Long documentsRepositoryId, Long parentFolderId, Folder folder) {

		try {
			return _toFolder(
				_dlAppService.addFolder(
					documentsRepositoryId, parentFolderId, folder.getName(),
					folder.getDescription(), new ServiceContext()));
		}
		catch (NoSuchGroupException nsge) {
			throw new NotFoundException(nsge);
		}
		catch (PrincipalException pe) {
			throw new NotAuthorizedException(pe);
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(pe);
		}
	}

	private com.liferay.portal.kernel.repository.model.Folder _getFolder(
		Long folderId) {

		try {
			return _dlAppService.getFolder(folderId);
		}
		catch (NoSuchFolderException nsfe) {
			throw new NotFoundException(nsfe);
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(pe);
		}
	}

	private Page<Folder> _getFolderPage(
		Long documentsRepositoryId, Long parentFolderId,
		Pagination pagination) {

		try {
			return Page.of(
				transform(
					_dlAppService.getFolders(
						documentsRepositoryId, parentFolderId,
						pagination.getStartPosition(),
						pagination.getEndPosition(), null),
					this::_toFolder),
				pagination,
				_dlAppService.getFoldersCount(
					documentsRepositoryId, parentFolderId));
		}
		catch (NoSuchGroupException nsge) {
			throw new NotFoundException(nsge);
		}
		catch (PrincipalException pe) {
			throw new NotAuthorizedException(pe);
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