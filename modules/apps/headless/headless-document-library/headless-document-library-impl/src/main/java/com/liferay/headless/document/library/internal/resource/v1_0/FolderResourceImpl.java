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

package com.liferay.headless.document.library.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.resource.v1_0.FolderResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/folder.properties",
	scope = ServiceScope.PROTOTYPE, service = FolderResource.class
)
public class FolderResourceImpl extends BaseFolderResourceImpl {

	@Override
	public Response deleteFolder(Long folderId) throws Exception {
		_dlAppService.deleteFolder(folderId);

		return buildNoContentResponse();
	}

	@Override
	public Page<Folder> getDocumentsRepositoryFolderPage(
			Long documentsRepositoryId, Pagination pagination)
		throws Exception {

		return _getFolderPage(
			documentsRepositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			pagination);
	}

	@Override
	public Folder getFolder(Long folderId) throws Exception {
		return _toFolder(_dlAppService.getFolder(folderId));
	}

	@Override
	public Page<Folder> getFolderFolderPage(
			Long folderId, Pagination pagination)
		throws Exception {

		Folder parentFolder = _toFolder(_dlAppService.getFolder(folderId));

		return _getFolderPage(
			parentFolder.getDocumentsRepositoryId(), parentFolder.getId(),
			pagination);
	}

	@Override
	public Folder postDocumentsRepositoryFolder(
			Long documentsRepositoryId, Folder folder)
		throws Exception {

		return _addFolder(documentsRepositoryId, 0L, folder);
	}

	@Override
	public Folder postFolderFolder(Long folderId, Folder folder)
		throws Exception {

		Folder parentFolder = _toFolder(_dlAppService.getFolder(folderId));

		return _addFolder(
			parentFolder.getDocumentsRepositoryId(), parentFolder.getId(),
			folder);
	}

	@Override
	public Folder putFolder(Long folderId, Folder folder) throws Exception {
		return _toFolder(
			_dlAppService.updateFolder(
				folderId, folder.getName(), folder.getDescription(),
				new ServiceContext()));
	}

	private Folder _addFolder(
			Long documentsRepositoryId, Long parentFolderId, Folder folder)
		throws Exception {

		return _toFolder(
			_dlAppService.addFolder(
				documentsRepositoryId, parentFolderId, folder.getName(),
				folder.getDescription(), new ServiceContext()));
	}

	private Page<Folder> _getFolderPage(
			Long documentsRepositoryId, Long parentFolderId,
			Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_dlAppService.getFolders(
					documentsRepositoryId, parentFolderId,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toFolder),
			pagination,
			_dlAppService.getFoldersCount(
				documentsRepositoryId, parentFolderId));
	}

	private Folder _toFolder(
		com.liferay.portal.kernel.repository.model.Folder folder) {

		return new Folder() {
			{
				setDateCreated(folder.getCreateDate());
				setDateModified(folder.getModifiedDate());
				setDescription(folder.getDescription());
				setDocumentsRepositoryId(folder.getGroupId());
				setId(folder.getFolderId());
				setName(folder.getName());
			}
		};
	}

	@Reference
	private DLAppService _dlAppService;

}