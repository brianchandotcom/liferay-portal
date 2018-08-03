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

package com.liferay.folder.apio.internal.architect.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.folder.apio.architect.identifier.FolderIdentifier;
import com.liferay.folder.apio.architect.identifier.RootFolderIdentifier;
import com.liferay.folder.apio.internal.architect.form.FolderForm;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose folder resources through a web
 * API. The resources are mapped from the internal model {@code Folder}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class FolderNestedCollectionResource
	implements NestedCollectionResource
		<Folder, Long, FolderIdentifier, Long, RootFolderIdentifier> {

	@Override
	public NestedCollectionRoutes<Folder, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Folder, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addFolder,
			_hasPermission.forAddingIn(RootFolderIdentifier.class),
			FolderForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "folder";
	}

	@Override
	public ItemRoutes<Folder, Long> itemRoutes(
		ItemRoutes.Builder<Folder, Long> builder) {

		return builder.addGetter(
			_dlAppService::getFolder
		).addRemover(
			idempotent(_dlAppService::deleteFolder), _hasPermission::forDeleting
		).addUpdater(
			this::_updateFolder, _hasPermission::forUpdating,
			FolderForm::buildForm
		).build();
	}

	@Override
	public Representor<Folder> representor(
		Representor.Builder<Folder, Long> builder) {

		return builder.types(
			"Folder"
		).identifier(
			Folder::getFolderId
		).addDate(
			"dateCreated", Folder::getCreateDate
		).addDate(
			"dateModified", Folder::getCreateDate
		).addRelatedCollection(
			"subFolders", FolderIdentifier.class
		).addString(
			"description", Folder::getDescription
		).addString(
			"name", Folder::getName
		).build();
	}

	private Folder _addFolder(long groupId, FolderForm folderForm)
		throws PortalException {

		long parentFolderId = 0;

		return _dlAppService.addFolder(
			groupId, parentFolderId, folderForm.getName(),
			folderForm.getDescription(), new ServiceContext());
	}

	private PageItems<Folder> _getPageItems(Pagination pagination, long groupId)
		throws PortalException {

		List<Folder> folders = _dlAppService.getFolders(
			groupId, 0, pagination.getStartPosition(),
			pagination.getEndPosition(), null);
		int count = _dlAppService.getFoldersCount(groupId, 0);

		return new PageItems<>(folders, count);
	}

	private Folder _updateFolder(long folderId, FolderForm folderForm)
		throws PortalException {

		return _dlAppService.updateFolder(
			folderId, folderForm.getName(), folderForm.getDescription(),
			new ServiceContext());
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	private HasPermission<Long> _hasPermission;

}