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

import com.liferay.announcements.kernel.exception.NoSuchEntryException;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderModel;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.folder.apio.architect.identifier.DLFolderIdentifier;
import com.liferay.folder.apio.internal.architect.form.FolderForm;
import com.liferay.portal.apio.architect.context.auth.MockPermissions;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose folder resources through a web
 * API. The resources are mapped from the internal model {@code DLFolder}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class FolderNestedCollectionResource
	implements
		NestedCollectionResource<DLFolder, Long, DLFolderIdentifier,
			Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<DLFolder, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<DLFolder, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addDLFolder, MockPermissions::validPermission,
			FolderForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "folders";
	}

	@Override
	public ItemRoutes<DLFolder, Long> itemRoutes(
		ItemRoutes.Builder<DLFolder, Long> builder) {

		return builder.addGetter(
			this::_getDLFolder
		).addRemover(
			this::_deleteDLFolder, MockPermissions::validPermission
		).addUpdater(
			this::_updateDLFolder, MockPermissions::validPermission,
			FolderForm::buildForm
		).build();
	}

	@Override
	public Representor<DLFolder, Long> representor(
		Representor.Builder<DLFolder, Long> builder) {

		return builder.types(
			"Folder"
		).identifier(
			DLFolder::getFolderId
		).addBidirectionalModel(
			"webSite", "folders", WebSiteIdentifier.class,
			DLFolderModel::getGroupId
		).addDate(
			"dateCreated", DLFolder::getCreateDate
		).addDate(
			"dateModified", DLFolder::getCreateDate
		).addDate(
			"datePublished", DLFolder::getCreateDate
		).addString(
			"name", DLFolder::getName
		).addString(
			"path", this::_getPath
		).build();
	}

	private DLFolder _addDLFolder(Long groupId, FolderForm folderForm) {
		long parentFolderId = 0;

		Try<DLFolder> dlFolderTry = Try.fromFallible(
			() -> _dlFolderService.getFolder(
				groupId, parentFolderId, folderForm.getName()));

		if (dlFolderTry.isSuccess()) {
			throw new BadRequestException(
				"A folder with that name already exists");
		}

		dlFolderTry = Try.fromFallible(
			() -> _dlFolderService.addFolder(
				groupId, groupId, false, parentFolderId, folderForm.getName(),
				folderForm.getDescription(), new ServiceContext()));

		return dlFolderTry.getUnchecked();
	}

	private void _deleteDLFolder(Long dlFolderId) {
		try {
			_dlFolderService.deleteFolder(dlFolderId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private DLFolder _getDLFolder(Long dlFolderId) {
		try {
			return _dlFolderService.getFolder(dlFolderId);
		}
		catch (NoSuchEntryException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get folder " + dlFolderId, e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<DLFolder> _getPageItems(
		Pagination pagination, Long groupId) {

		try {
			List<DLFolder> dlFolders = _dlFolderService.getFolders(
				groupId, 0, pagination.getStartPosition(),
				pagination.getEndPosition(), null);
			int count = _dlFolderService.getFoldersCount(groupId, 0);

			return new PageItems<>(dlFolders, count);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private String _getPath(DLFolder dlFolder) {
		try {
			return dlFolder.getPath();
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private DLFolder _updateDLFolder(Long dlFolderId, FolderForm folderForm) {
		DLFolder dlFolder = _getDLFolder(dlFolderId);

		Try<DLFolder> dlFolderTry = Try.fromFallible(
			() -> _dlFolderService.updateFolder(
				dlFolderId, dlFolder.getParentFolderId(), folderForm.getName(),
				folderForm.getDescription(),
				dlFolder.getDefaultFileEntryTypeId(), new ArrayList<>(),
				dlFolder.getRestrictionType(), new ServiceContext()));

		return dlFolderTry.getUnchecked();
	}

	@Reference
	private DLFolderService _dlFolderService;

}