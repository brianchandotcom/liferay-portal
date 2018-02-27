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

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.blogs.kernel.exception.NoSuchEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.InputStream;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/MediaObject">MediaObject </a> resources through a web
 * API. The resources are mapped from the internal model {@code DLFileEntry}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class MediaObjectNestedCollectionResource
	implements NestedCollectionResource <DLFileEntry, Long, DLFolder, Long> {

	@Override
	public NestedCollectionRoutes<DLFileEntry> collectionRoutes(
		NestedCollectionRoutes.Builder<DLFileEntry, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "media-objects";
	}

	@Override
	public ItemRoutes<DLFileEntry> itemRoutes(
		ItemRoutes.Builder<DLFileEntry, Long> builder) {

		return builder.addGetter(
			this::_getDLFileEntry
		).addRemover(
			this::_deleteDLFileEntry
		).build();
	}

	@Override
	public Representor<DLFileEntry, Long> representor(
		Representor.Builder<DLFileEntry, Long> builder) {

		return builder.types(
			"MediaObject"
		).identifier(
			DLFileEntry::getFileEntryId
		).addBidirectionalModel(
			"folder", "mediaObjects", DLFolder.class,
			this::_getDLFolderOptional, DLFolder::getFolderId
		).addBinary(
			"contentStream", this::_getInputStream
		).addDate(
			"dateCreated", DLFileEntry::getCreateDate
		).addDate(
			"dateModified", DLFileEntry::getModifiedDate
		).addDate(
			"datePublished", DLFileEntry::getLastPublishDate
		).addLinkedModel(
			"author", User.class, this::_getUserOptional
		).addNumber(
			"contentSize", DLFileEntry::getSize
		).addString(
			"fileFormat", DLFileEntry::getMimeType
		).addString(
			"headline", DLFileEntry::getTitle
		).addString(
			"name", DLFileEntry::getName
		).addString(
			"text", DLFileEntry::getDescription
		).build();
	}

	private void _deleteDLFileEntry(Long dlFileEntryId) {
		try {
			_dlFileEntryService.deleteFileEntry(dlFileEntryId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private DLFileEntry _getDLFileEntry(Long dlFileEntryId) {
		try {
			return _dlFileEntryService.getFileEntry(dlFileEntryId);
		}
		catch (NoSuchEntryException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get file " + dlFileEntryId, e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Optional<DLFolder> _getDLFolderOptional(DLFileEntry dlFileEntry) {
		try {
			return Optional.of(
				_dlFolderService.getFolder(dlFileEntry.getFolderId()));
		}
		catch (NoSuchFolderException nsfe) {
			throw new NotFoundException(
				"Unable to get group " + dlFileEntry.getFolderId(), nsfe);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private InputStream _getInputStream(DLFileEntry dlFileEntry) {
		try {
			return dlFileEntry.getContentStream();
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<DLFileEntry> _getPageItems(
		Pagination pagination, Long dlFolderId) {

		try {
			DLFolder dlFolder = _dlFolderService.getFolder(dlFolderId);

			List<DLFileEntry> dlFileEntries =
				_dlFileEntryService.getFileEntries(
					dlFolder.getGroupId(), dlFolder.getFolderId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);
			int count = _dlFileEntryService.getFileEntriesCount(
				dlFolder.getGroupId(), dlFolder.getFolderId());

			return new PageItems<>(dlFileEntries, count);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Optional<User> _getUserOptional(DLFileEntry dlFileEntry) {
		try {
			return Optional.ofNullable(
				_userService.getUserById(dlFileEntry.getUserId()));
		}
		catch (NoSuchUserException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get user " + dlFileEntry.getUserId(), e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	@Reference
	private DLFileEntryService _dlFileEntryService;

	@Reference
	private DLFolderService _dlFolderService;

	@Reference
	private UserLocalService _userService;

}