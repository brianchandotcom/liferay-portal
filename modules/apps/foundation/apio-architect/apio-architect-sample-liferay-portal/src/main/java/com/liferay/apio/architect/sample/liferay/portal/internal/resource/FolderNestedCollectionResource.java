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
import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.router.ItemRouter;
import com.liferay.apio.architect.router.NestedCollectionRouter;
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
@Component(
	immediate = true,
	service =
		{ItemRouter.class, NestedCollectionRouter.class, Representable.class}
)
public class FolderNestedCollectionResource
	implements ItemRouter<DLFolder, LongIdentifier>,
			   NestedCollectionRouter<DLFolder, WebSite, LongIdentifier>,
			   Representable<DLFolder, LongIdentifier> {

	@Override
	public NestedCollectionRoutes<DLFolder> collectionRoutes(
		NestedCollectionRoutes.Builder<DLFolder, LongIdentifier> builder) {

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
		ItemRoutes.Builder<DLFolder, LongIdentifier> builder) {

		return builder.addGetter(
			this::_getDLFolder
		).addRemover(
			this::_deleteDLFolder
		).addUpdater(
			this::_updateDLFolder
		).build();
	}

	@Override
	public Representor<DLFolder, LongIdentifier> representor(
		Representor.Builder<DLFolder, LongIdentifier> builder) {

		return builder.types(
			"Folder"
		).identifier(
			dlFolder -> dlFolder::getFolderId
		).addBidirectionalModel(
			"webSite", "folders", WebSite.class, this::_getWebSiteOptional,
			WebSite::getWebSiteLongIdentifier
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

	private DLFolder _addDLFolder(
		LongIdentifier groupLongIdentifier, Map<String, Object> body) {

		long parentFolderId = 0;

		String name = (String)body.get("name");

		if (Validator.isNull(name)) {
			throw new BadRequestException("Invalid body");
		}

		Try<DLFolder> dlFolderTry = Try.fromFallible(
			() -> _dlFolderService.getFolder(
				groupLongIdentifier.getId(), parentFolderId, name));

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
				groupLongIdentifier.getId(), groupLongIdentifier.getId(), false,
				parentFolderId, name, description, new ServiceContext()));

		return dlFolderTry.getUnchecked();
	}

	private void _deleteDLFolder(LongIdentifier dlFolderLongIdentifier) {
		try {
			_dlFolderService.deleteFolder(dlFolderLongIdentifier.getId());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private DLFolder _getDLFolder(LongIdentifier dlFolderLongIdentifier) {
		try {
			return _dlFolderService.getFolder(dlFolderLongIdentifier.getId());
		}
		catch (NoSuchEntryException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get folder " + dlFolderLongIdentifier.getId(), e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<DLFolder> _getPageItems(
		Pagination pagination, LongIdentifier groupLongIdentifier) {

		try {
			List<DLFolder> dlFolders = _dlFolderService.getFolders(
				groupLongIdentifier.getId(), 0, pagination.getStartPosition(),
				pagination.getEndPosition(), null);
			int count = _dlFolderService.getFoldersCount(
				groupLongIdentifier.getId(), 0);

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
		LongIdentifier dlFolderLongIdentifier, Map<String, Object> body) {

		DLFolder dlFolder = _getDLFolder(dlFolderLongIdentifier);

		String name = (String)body.get("name");
		String description = (String)body.get("description");

		if (Validator.isNull(name) || Validator.isNull(description)) {
			throw new BadRequestException("Invalid body");
		}

		Try<DLFolder> dlFolderTry = Try.fromFallible(
			() -> _dlFolderService.updateFolder(
				dlFolderLongIdentifier.getId(), dlFolder.getParentFolderId(),
				name, description, dlFolder.getDefaultFileEntryTypeId(),
				new ArrayList<>(), dlFolder.getRestrictionType(),
				new ServiceContext()));

		return dlFolderTry.getUnchecked();
	}

	@Reference
	private DLFolderService _dlFolderService;

	@Reference
	private WebSiteService _webSiteService;

}