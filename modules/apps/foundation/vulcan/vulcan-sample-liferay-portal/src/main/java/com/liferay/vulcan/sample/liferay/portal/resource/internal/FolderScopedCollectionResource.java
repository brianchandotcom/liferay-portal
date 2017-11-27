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

package com.liferay.vulcan.sample.liferay.portal.resource.internal;

import com.liferay.blogs.kernel.exception.NoSuchEntryException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.resource.CollectionResource;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.Representor.Builder;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.ScopedCollectionResource;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.result.Try;
import com.liferay.vulcan.sample.liferay.portal.website.WebSite;
import com.liferay.vulcan.sample.liferay.portal.website.WebSiteService;

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
@Component(immediate = true, service = CollectionResource.class)
public class FolderScopedCollectionResource
	implements ScopedCollectionResource<DLFolder, LongIdentifier> {

	@Override
	public Representor<DLFolder, LongIdentifier> buildRepresentor(
		Representor.Builder<DLFolder, LongIdentifier> representorBuilder) {

		return representorBuilder.identifier(
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
		).addType(
			"Folder"
		).build();
	}

	@Override
	public String getName() {
		return "folders";
	}

	@Override
	public Routes<DLFolder> routes(
		RoutesBuilder<DLFolder, LongIdentifier> routesBuilder) {

		return routesBuilder.addCollectionPageGetter(
			this::_getPageItems, LongIdentifier.class
		).addCollectionPageItemCreator(
			this::_addDLFolder, LongIdentifier.class
		).addCollectionPageItemGetter(
			this::_getDLFolder
		).addCollectionPageItemRemover(
			this::_deleteDLFolder
		).addCollectionPageItemUpdater(
			this::_updateDLFolder
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