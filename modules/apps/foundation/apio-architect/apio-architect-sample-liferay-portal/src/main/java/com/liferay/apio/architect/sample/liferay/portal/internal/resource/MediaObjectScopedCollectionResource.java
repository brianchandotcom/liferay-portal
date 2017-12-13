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

import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.resource.ScopedCollectionResource;
import com.liferay.apio.architect.routes.Routes;
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
@Component(immediate = true, service = CollectionResource.class)
public class MediaObjectScopedCollectionResource
	implements ScopedCollectionResource<DLFileEntry, LongIdentifier> {

	@Override
	public String getName() {
		return "media-objects";
	}

	@Override
	public Representor<DLFileEntry, LongIdentifier> representor(
		Representor.Builder<DLFileEntry, LongIdentifier> builder) {

		return builder.types(
			"MediaObject"
		).identifier(
			dlFileEntry -> dlFileEntry::getFileEntryId
		).addBidirectionalModel(
			"folder", "mediaObjects", DLFolder.class,
			this::_getDLFolderOptional,
			dlFolder -> (LongIdentifier)dlFolder::getFolderId
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

	@Override
	public Routes<DLFileEntry> routes(
		Routes.Builder<DLFileEntry, LongIdentifier> builder) {

		return builder.addCollectionPageGetter(
			this::_getPageItems, LongIdentifier.class
		).addCollectionPageItemGetter(
			this::_getDLFileEntry
		).addCollectionPageItemRemover(
			this::_deleteDLFileEntry
		).build();
	}

	private void _deleteDLFileEntry(LongIdentifier dlFileEntryLongIdentifier) {
		try {
			_dlFileEntryService.deleteFileEntry(
				dlFileEntryLongIdentifier.getId());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private DLFileEntry _getDLFileEntry(
		LongIdentifier dlFileEntryLongIdentifier) {

		try {
			return _dlFileEntryService.getFileEntry(
				dlFileEntryLongIdentifier.getId());
		}
		catch (NoSuchEntryException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get file " + dlFileEntryLongIdentifier.getId(), e);
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
		Pagination pagination, LongIdentifier dlFolderLongIdentifier) {

		try {
			DLFolder dlFolder = _dlFolderService.getFolder(
				dlFolderLongIdentifier.getId());

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