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

package com.liferay.media.object.apio.internal.architect;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.blogs.kernel.exception.NoSuchEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryModel;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.folder.apio.architect.identifier.DLFolderIdentifier;
import com.liferay.media.object.apio.architect.identifier.DLFileEntryIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.architect.context.auth.MockPermissions;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;

import java.io.InputStream;

import java.util.List;

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
	implements NestedCollectionResource<DLFileEntry, Long,
		DLFileEntryIdentifier, Long, DLFolderIdentifier> {

	@Override
	public NestedCollectionRoutes<DLFileEntry, Long> collectionRoutes(
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
	public ItemRoutes<DLFileEntry, Long> itemRoutes(
		ItemRoutes.Builder<DLFileEntry, Long> builder) {

		return builder.addGetter(
			this::_getDLFileEntry
		).addRemover(
			this::_deleteDLFileEntry, MockPermissions::validPermission
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
			"folder", "mediaObjects", DLFolderIdentifier.class,
			DLFileEntryModel::getFolderId
		).addBinary(
			"contentStream", this::_getInputStream
		).addDate(
			"dateCreated", DLFileEntry::getCreateDate
		).addDate(
			"dateModified", DLFileEntry::getModifiedDate
		).addDate(
			"datePublished", DLFileEntry::getLastPublishDate
		).addLinkedModel(
			"author", PersonIdentifier.class, DLFileEntryModel::getUserId
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

	@Reference
	private DLFileEntryService _dlFileEntryService;

	@Reference
	private DLFolderService _dlFolderService;

}