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

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.folder.apio.architect.identifier.FolderIdentifier;
import com.liferay.folder.apio.architect.identifier.RootFolderIdentifier;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.media.object.apio.internal.architect.form.MediaObjectCreatorForm;
import com.liferay.media.object.apio.internal.helper.MediaObjectHelper;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/MediaObject">MediaObject</a> resources through a web
 * API. The resources are mapped from the internal model {@code FileEntry}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class MediaObjectNestedCollectionResource
	implements NestedCollectionResource
		<FileEntry, Long, MediaObjectIdentifier, Long, RootFolderIdentifier> {

	@Override
	public NestedCollectionRoutes<FileEntry, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<FileEntry, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_getFileEntry,
			_hasPermission.forAddingIn(RootFolderIdentifier.class),
			MediaObjectCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "media-object";
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
			"Liferay:Document", "MediaObject"
		).identifier(
			FileEntry::getFileEntryId
		).addBidirectionalModel(
			"folder", "documents", FolderIdentifier.class,
			FileEntry::getFolderId
		).addRelativeURL(
			"contentUrl", this::_getFileEntryPreviewURL
		).addDate(
			"dateCreated", FileEntry::getCreateDate
		).addDate(
			"dateModified", FileEntry::getModifiedDate
		).addLinkedModel(
			"creator", PersonIdentifier.class, FileEntry::getUserId
		).addNumber(
			"sizeInBytes", FileEntry::getSize
		).addRelatedCollection(
			"category", CategoryIdentifier.class
		).addString(
			"contentSize", fileEntry -> String.valueOf(fileEntry.getSize())
		).addString(
			"description", FileEntry::getDescription
		).addString(
			"encodingFormat", FileEntry::getMimeType
		).addString(
			"headline", FileEntry::getTitle
		).addString(
			"name", FileEntry::getFileName
		).addStringList(
			"keywords", this::_getMediaObjectAssetTags
		).build();
	}

	private FileEntry _getFileEntry(
			long groupId, MediaObjectCreatorForm mediaObjectCreatorForm)
		throws PortalException {

		return _mediaObjectHelper.addFileEntry(
			groupId, 0L, mediaObjectCreatorForm);
	}

	private String _getFileEntryPreviewURL(FileEntry fileEntry) {
		return Try.fromFallible(
			fileEntry::getFileVersion
		).map(
			version -> DLUtil.getPreviewURL(
				fileEntry, version, null, "", false, false)
		).orElse(
			null
		);
	}

	private List<String> _getMediaObjectAssetTags(FileEntry fileEntry) {
		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		return ListUtil.toList(assetTags, AssetTag::getName);
	}

	private PageItems<FileEntry> _getPageItems(
			Pagination pagination, long groupId)
		throws PortalException {

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			groupId, 0, pagination.getStartPosition(),
			pagination.getEndPosition(), null);
		int count = _dlAppService.getFileEntriesCount(groupId, 0);

		return new PageItems<>(fileEntries, count);
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private MediaObjectHelper _mediaObjectHelper;

}