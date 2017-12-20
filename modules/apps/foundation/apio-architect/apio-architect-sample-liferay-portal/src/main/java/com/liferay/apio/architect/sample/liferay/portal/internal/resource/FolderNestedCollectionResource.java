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

package com.liferay.apio.architect.sample.liferay.portal.internal.resource;

import com.liferay.announcements.kernel.exception.NoSuchEntryException;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSite;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSiteService;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	implements NestedCollectionResource <DLFolder, Long, WebSite, Long> {

	@Override
	public NestedCollectionRoutes<DLFolder> collectionRoutes(
		NestedCollectionRoutes.Builder<DLFolder, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addDLFolder
		).build();
	}

	@Override
	public String getName() {
		return "folders";
	}

	@Override
	public ItemRoutes<DLFolder> itemRoutes(
		ItemRoutes.Builder<DLFolder, Long> builder) {

		return builder.addGetter(
			this::_getDLFolder
		).addRemover(
			this::_deleteDLFolder
		).addUpdater(
			this::_updateDLFolder
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
			"webSite", "folders", WebSite.class, this::_getWebSiteOptional,
			WebSite::getWebSiteId
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

	private DLFolder _addDLFolder(Long groupId, Map<String, Object> body) {
		long parentFolderId = 0;

		String name = (String)body.get("name");

		if (Validator.isNull(name)) {
			throw new BadRequestException("Invalid body");
		}

		Try<DLFolder> dlFolderTry = Try.fromFallible(
			() -> _dlFolderService.getFolder(groupId, parentFolderId, name));

		if (dlFolderTry.isSuccess()) {
			throw new BadRequestException(
				"A folder with that name already exists");
		}

		String description = (String)body.get("description");

		if (Validator.isNull(description)) {
			throw new BadRequestException("Invalid body");
		}

		dlFolderTry = Try.fromFallible(
			() -> _dlFolderService.addFolder(
				groupId, groupId, false, parentFolderId, name, description,
				new ServiceContext()));

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

	private Optional<WebSite> _getWebSiteOptional(DLFolder dlFolder) {
		return _webSiteService.getWebSite(dlFolder.getGroupId());
	}

	private DLFolder _updateDLFolder(
		Long dlFolderId, Map<String, Object> body) {

		DLFolder dlFolder = _getDLFolder(dlFolderId);

		String name = (String)body.get("name");
		String description = (String)body.get("description");

		if (Validator.isNull(name) || Validator.isNull(description)) {
			throw new BadRequestException("Invalid body");
		}

		Try<DLFolder> dlFolderTry = Try.fromFallible(
			() -> _dlFolderService.updateFolder(
				dlFolderId, dlFolder.getParentFolderId(), name, description,
				dlFolder.getDefaultFileEntryTypeId(), new ArrayList<>(),
				dlFolder.getRestrictionType(), new ServiceContext()));

		return dlFolderTry.getUnchecked();
	}

	@Reference
	private DLFolderService _dlFolderService;

	@Reference
	private WebSiteService _webSiteService;

}