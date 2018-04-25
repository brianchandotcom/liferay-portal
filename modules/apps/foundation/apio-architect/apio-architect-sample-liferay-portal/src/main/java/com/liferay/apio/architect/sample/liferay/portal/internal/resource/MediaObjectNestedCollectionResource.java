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

import static com.liferay.apio.architect.sample.liferay.portal.internal.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.sample.liferay.portal.internal.form.MediaObjectCreatorForm;
import com.liferay.apio.architect.sample.liferay.portal.internal.form.MediaObjectUpdaterForm;
import com.liferay.apio.architect.sample.liferay.portal.internal.identifier.FolderIdentifier;
import com.liferay.apio.architect.sample.liferay.portal.internal.identifier.MediaObjectIdentifier;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;

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
		MediaObjectIdentifier, Long, FolderIdentifier> {

	@Override
	public NestedCollectionRoutes<FileEntry, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<FileEntry, Long> builder) {

		return builder.addCreator(
			this::_addFileEntry, (credentials, folderId) -> true,
			MediaObjectCreatorForm::buildForm
		).addGetter(
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
			(credentials, fileEntryId) -> true
		).addUpdater(
			this::_updateFileEntry, (credentials, fileEntryId) -> true,
			MediaObjectUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<FileEntry, Long> representor(
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

	private FileEntry _addFileEntry(
			Long folderId, MediaObjectCreatorForm mediaObjectCreatorForm)
		throws PortalException {

		Folder folder = _dlAppService.getFolder(folderId);

		BinaryFile binaryFile = mediaObjectCreatorForm.getBinaryFile();

		return _dlAppService.addFileEntry(
			folder.getGroupId(), folderId, mediaObjectCreatorForm.getName(),
			binaryFile.getMimeType(), mediaObjectCreatorForm.getTitle(),
			mediaObjectCreatorForm.getDescription(), null,
			binaryFile.getInputStream(), binaryFile.getSize(),
			new ServiceContext());
	}

	private BinaryFile _getBinaryFile(FileEntry fileEntry) {
		return Try.success(
			fileEntry
		).map(
			file -> new BinaryFile(
				file.getContentStream(), fileEntry.getSize(),
				file.getMimeType())
		).orElse(
			null
		);
	}

	private PageItems<FileEntry> _getPageItems(
			Pagination pagination, Long folderId)
		throws PortalException {

		Folder folder = _dlAppService.getFolder(folderId);

		List<FileEntry> dlFileEntries = _dlAppService.getFileEntries(
			folder.getGroupId(), folder.getFolderId(),
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = _dlAppService.getFileEntriesCount(
			folder.getGroupId(), folder.getFolderId());

		return new PageItems<>(dlFileEntries, count);
	}

	private FileEntry _updateFileEntry(
			Long fileEntryId, MediaObjectUpdaterForm mediaObjectUpdaterForm)
		throws PortalException {

		BinaryFile binaryFile = mediaObjectUpdaterForm.getBinaryFile();

		return _dlAppService.updateFileEntry(
			fileEntryId, mediaObjectUpdaterForm.getName(),
			binaryFile.getMimeType(), mediaObjectUpdaterForm.getTitle(),
			mediaObjectUpdaterForm.getDescription(),
			mediaObjectUpdaterForm.getChangelog(),
			mediaObjectUpdaterForm.getMajorVersion(),
			binaryFile.getInputStream(), binaryFile.getSize(),
			new ServiceContext());
	}

	@Reference
	private DLAppService _dlAppService;

}