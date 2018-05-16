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

package com.liferay.media.object.apio.internal.architect.resource;

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
import com.liferay.folder.apio.architect.identifier.FolderIdentifier;
import com.liferay.folder.apio.architect.identifier.RootFolderIdentifier;
import com.liferay.media.object.apio.architect.identifier.FileEntryIdentifier;
import com.liferay.media.object.apio.internal.architect.form.MediaObjectCreatorForm;
import com.liferay.media.object.apio.internal.helper.MediaObjectHelper;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

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
		FileEntryIdentifier, Long, RootFolderIdentifier> {

	@Override
	public NestedCollectionRoutes<FileEntry, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<FileEntry, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_getFileEntry, (credentials, folderId) -> true,
			MediaObjectCreatorForm::buildForm
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
			_hasPermission::forDeleting
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

	private FileEntry _getFileEntry(
			Long groupId, MediaObjectCreatorForm mediaObjectCreatorForm)
		throws PortalException {

		return _mediaObjectHelper.addFileEntry(
			groupId, 0L, mediaObjectCreatorForm);
	}

	private PageItems<FileEntry> _getPageItems(
			Pagination pagination, Long groupId)
		throws PortalException {

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			groupId, 0, pagination.getStartPosition(),
			pagination.getEndPosition(), null);
		int count = _dlAppService.getFileEntriesCount(groupId, 0);

		return new PageItems<>(fileEntries, count);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private HasPermission<Long, Long> _hasPermission;

	@Reference
	private MediaObjectHelper _mediaObjectHelper;

}