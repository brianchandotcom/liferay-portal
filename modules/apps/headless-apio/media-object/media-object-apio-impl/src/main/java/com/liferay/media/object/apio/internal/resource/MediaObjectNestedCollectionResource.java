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

package com.liferay.media.object.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.folder.apio.identifier.FolderIdentifier;
import com.liferay.media.object.apio.identifier.FileEntryIdentifier;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/MediaObject">MediaObject </a> resources through a web
 * API. The resources are mapped from the internal model {@code FileEntry}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class MediaObjectNestedCollectionResource
	implements NestedCollectionResource<FileEntry, Long,
		FileEntryIdentifier, Long, FolderIdentifier> {

	@Override
	public NestedCollectionRoutes<FileEntry, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<FileEntry, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "media-objects";
	}

	@Override
	public ItemRoutes<FileEntry, Long> itemRoutes(
		ItemRoutes.Builder<FileEntry, Long> builder) {

		return builder.addGetter(
			_dlAppService::getFileEntry
		).addRemover(
			idempotent(_dlAppService::deleteFileEntry),
			_hasPermission.forDeleting(FileEntry.class)
		).build();
	}

	@Override
	public Representor<FileEntry> representor(
		Representor.Builder<FileEntry, Long> builder) {

		return builder.types(
			"MediaObject"
		).identifier(
			FileEntry::getFileEntryId
		).addBidirectionalModel(
			"folder", "mediaObject", FolderIdentifier.class,
			FileEntry::getFolderId
		).addBinary(
			"contentStream", this::_getBinaryFile
		).addDate(
			"dateCreated", FileEntry::getCreateDate
		).addDate(
			"dateModified", FileEntry::getModifiedDate
		).addDate(
			"datePublished", FileEntry::getLastPublishDate
		).addLinkedModel(
			"author", PersonIdentifier.class, FileEntry::getUserId
		).addNumber(
			"contentSize", FileEntry::getSize
		).addString(
			"fileFormat", FileEntry::getMimeType
		).addString(
			"headline", FileEntry::getTitle
		).addString(
			"name", FileEntry::getFileName
		).addString(
			"text", FileEntry::getDescription
		).build();
	}

	private BinaryFile _getBinaryFile(FileEntry fileEntry) {
		return Try.fromFallible(
			() -> new BinaryFile(
				fileEntry.getContentStream(), fileEntry.getSize(),
				fileEntry.getMimeType())
		).orElse(
			null
		);
	}

	private PageItems<FileEntry> _getPageItems(
			Pagination pagination, Long folderId)
		throws PortalException {

		Folder folder = _dlAppService.getFolder(folderId);

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			folder.getGroupId(), folderId, pagination.getStartPosition(),
			pagination.getEndPosition(), null);
		int count = _dlAppService.getFileEntriesCount(
			folder.getGroupId(), folderId);

		return new PageItems<>(fileEntries, count);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private HasPermission _hasPermission;

}