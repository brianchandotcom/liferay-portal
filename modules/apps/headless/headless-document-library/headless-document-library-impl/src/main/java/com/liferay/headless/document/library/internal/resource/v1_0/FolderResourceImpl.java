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
import com.liferay.headless.document.library.internal.dto.v1_0.FolderImpl;
import com.liferay.headless.document.library.resource.v1_0.FolderResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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
	public boolean deleteFolder(Long folderId) throws Exception {
		_dlAppService.deleteFolder(folderId);

		return true;
	}

	@Override
	public Page<Folder> getContentSpaceFoldersPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		return _getFolderPage(
			contentSpaceId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			pagination);
	}

	@Override
	public Folder getFolder(Long folderId) throws Exception {
		return _toFolder(_dlAppService.getFolder(folderId));
	}

	@Override
	public Page<Folder> getFolderFoldersPage(
			Long folderId, Pagination pagination)
		throws Exception {

		Folder parentFolder = _toNewFolder(_dlAppService.getFolder(folderId));

		return _getFolderPage(
			parentFolder.getRepositoryId(), parentFolder.getId(), pagination);
	}

	@Override
	public Folder postContentSpaceFolder(Long contentSpaceId, Folder folder)
		throws Exception {

		return _addFolder(contentSpaceId, 0L, folder);
	}

	@Override
	public Folder postFolderFolder(Long folderId, Folder folder)
		throws Exception {

		Folder parentFolder = _toFolder(_dlAppService.getFolder(folderId));

		return _addFolder(
			parentFolder.getRepositoryId(), parentFolder.getId(), folder);
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

		return _toNewFolder(
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

	private boolean _getHasFolders(Long folderId) throws Exception {
		Page<Folder> page = getFolderFoldersPage(folderId, Pagination.of(1, 1));

		if (page.getTotalCount() > 0) {
			return true;
		}

		return false;
	}

	private Folder _toFolder(
			com.liferay.portal.kernel.repository.model.Folder folder)
		throws Exception {

		return _toFolder(folder, _getHasFolders(folder.getFolderId()));
	}

	private Folder _toFolder(
		com.liferay.portal.kernel.repository.model.Folder folder,
		boolean hasFolders) {

		Folder newFolder = new FolderImpl() {
			{
				dateCreated = folder.getCreateDate();
				dateModified = folder.getModifiedDate();
				description = folder.getDescription();
				repositoryId = folder.getGroupId();
				id = folder.getFolderId();
				name = folder.getName();
			}
		};

		newFolder.setHasFolders(hasFolders);

		return newFolder;
	}

	private Folder _toNewFolder(
		com.liferay.portal.kernel.repository.model.Folder folder) {

		return _toFolder(folder, false);
	}

	@Reference
	private DLAppService _dlAppService;

}